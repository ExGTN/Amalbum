<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <html>
	<head>
		<title>Amalbum</title>
	</head>
	<body>
	<h1>Amalbum</h1>
	<p>
		<%
		    String hello = "Hello World!";
		    out.println(hello);
		    out.println("<BR>\n");
		    out.println( "hello<BR>\n" );
		%>
	</p>
	${message}<BR>
	
	<c:forEach var="obj" items="${barkList}" varStatus="varStatus">
		鳴き声：<c:out value="${obj}"/><br>
	</c:forEach>
	
	</body>
</html>
