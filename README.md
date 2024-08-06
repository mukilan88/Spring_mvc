Spring MVC Module
-----------------
- Spring MVC Module is used to develop dynamic web applications
- MVC stands for Model View and Controller
- MVC architecture is used to make the Model independent of View



- The front end controller in Spring MVC is "DispatcherServlet" which need to be configured in web.xml file

- DispatcherServlet loads the Spring bean XML configuration file

- In Spring bean XML configuration file we configure the Controller classes

- Controller class will invoke the Model and Model will return the result back to the Controller and based on the result, Controller invokes the respective View pages


Spring MVC Annotations
----------------------
@Controller
-----------
used to mark the class as the Controller class

	EX:
		@Controller
		public class LoginController
		{
		   ...
		}


@RequestMapping
---------------
used to map the request url

	Ex:
		login.jsp
		---------
		<form action="login">
		   username ....
		   password ...
		   <input type="submit"/>	
		</form>

		@Controller
		public class LoginController
		{
		   @RequestMapping("/login")
		   authenticate()
		   {
			...
		   }
		}


Developing a Spring MVC Application in Eclipse using Maven
---------------------------------------------------------
- Configure tomcat server in eclipse workspace

- Create a Maven Project

	Click on File -> New -> Maven Project

	Click Next

	In Catalog Select : Internal
	In Artifact Id Select : maven-archetype-webapp

	Click Next

	Group Id : springmvc
	Artifact Id : SpringMVCProj
	Package : com.spring.mvc

	and click Finish

	Note
	----
	If errors in the project,
		Right click on SpringMVCProj -> Properties -> Targeted Runtimes -> 
		Make check mark to "Apache Tomcat v9" -> Click Apply and Close

- Add the webmvc dependency in pom.xml file
-------------------------------------------------
    <project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>springmvc</groupId>
	<artifactId>SpringMVCProj</artifactId>
	<packaging>war</packaging>
	<version>0.0.1-SNAPSHOT</version>
	<name>SpringMVCProj Maven Webapp</name>
	<url>http://maven.apache.org</url>
	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>3.8.1</version>
			<scope>test</scope>
		</dependency>
		<!--
		https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>6.1.11</version>
		</dependency>
	</dependencies>
	<build>
		<finalName>SpringMVCProj</finalName>
	</build>
    </project>
-------------------------------------------------
- Create a welcome page "login.jsp" in src/main/webapp folder of SpringMVCProj

    <form action="login" method="post">
	username<input type="text" name="username" /><br> password<input
		type="password" name="password" /><br> <input type="submit"
		value="Login" />
    </form>
-------------------------------------------------

- Create a folder "java" in src/main folder

- Create a package "com.spring.mvc.controller" in src/main/java folder

- Create a Controller class "LoginController" in com.spring.mvc.controller package

    package com.spring.mvc.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.ModelAndView;

    import com.spring.mvc.model.LoginAction;

    import jakarta.servlet.http.HttpServletRequest;

    @Controller
    
    public class LoginController {
	@RequestMapping("/login")
	public ModelAndView authenticate(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String result = LoginAction.verifyUser(username, password);
		if ("success".equals(result))
			return new ModelAndView("success.jsp", "username", username);
		else
			return new ModelAndView("failure.jsp");
	}
    }
-------------------------------------------------
- Create a package "com.spring.mvc.model" in src/main/java folder

- Create a Model/Action/Service class "LoginAction" in com.spring.mvc.model package

    package com.spring.mvc.model;

    public class LoginAction {

	public static String verifyUser(String username, String password) {

		if ("admin".equals(username) && "admin".equals(password))
			return "success";
		else
			return "failure";
	}
    }
-------------------------------------------------
- Create a view page "success.jsp" in webapp folder

    <%@ page contentType="text/html;charset=UTF-8" language="java"%>
    <html>
    <head>
    <title>Success</title>
    </head>
    <body>
        Welcome,
        <%=request.getAttribute("username")%><br> Click
        <a href="login.jsp">Here</a> to login
    </body>
    </html>
-------------------------------------------------
- Create a view page "failure.jsp" in webapp folder

    <%@ page contentType="text/html;charset=UTF-8" language="java"%>
    <html>
    <head>
    <title>Login Failure</title>
    </head>
    <body>
        Login Failure
        <br> Click
        <a href="login.jsp">Here</a> to login
    </body>
    </html>
-------------------------------------------------

- Update web.xml file (SpringMVCProj/src/main/webapp/WEB-INF) to configure Spring MVC Front end controller,
  DispatcherServlet
	
    <!DOCTYPE web-app PUBLIC
    "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd" >
    <web-app>
        <display-name>Archetype Created Web Application</display-name>
        <servlet>
            <servlet-name>springmvc</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>springmvc</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>
-------------------------------------------------
- Create a Spring bean XML configuration file "springmvc-servlet.xml" in WEB-INF folder

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:ctx="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
        <ctx:annotation-config></ctx:annotation-config>
        <ctx:component-scan
            base-package="com.spring.mvc.controller" />
    </beans>
-------------------------------------------------
Note
----
	The name of the spring bean xml configuration file should be "servlet-name-servlet.xml" where servlet-name is the servlet name given in <servlet-name> element of web.xml file


- Run the application login.jsp
