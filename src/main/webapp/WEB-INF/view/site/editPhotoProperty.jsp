<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
コンテンツのプロパティ編集画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="写真プロパティ編集" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="写真プロパティ編集" />
		</jsp:include>

		<!-- フォーム -->
		<H2>コメント編集</H2>
		<form:form commandName="editPhotoPropertyForm" method="POST" action="${baseURL}/updatePhotoProperty.do">
			<form:hidden path="contentsID" />	<!-- コンテンツID -->
			<form:textarea path='description' cols='80' rows='30' /><BR>
			<input type='submit' value='送信'></input><BR>
		</form:form>
		<c:if test="${returnPath!=null}" >
			<a href="${returnPath}">戻る</a>
		</c:if>
	</body>
</html>
