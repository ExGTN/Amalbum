<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
ログイン画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="ログイン" />
		</jsp:include>
		<script src="${pageContext.request.contextPath}/md5.js"></script>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="ログイン" />
		</jsp:include>

		<form:form commandName="loginForm" method="POST" action="${pageContext.request.contextPath}/site/loginCheck.do">
			<table>
				<tr>
					<td>UserID</td><td><form:input path="userID" /></td>
				</tr>
				<tr>
					<td>Password</td><td><input type="password" name="password" /></td>
				</tr>
			</table>
			<input type='hidden' name='passwordMD5' value=''/>
			<input type='submit' value='送信' onclick='javascript:passwordMD5.value=CybozuLabs.MD5.calc(password.value);'></input>
		</form:form>
	</body>
</html>
