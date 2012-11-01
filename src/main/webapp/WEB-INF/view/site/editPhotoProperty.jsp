<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<form name="editForm" method="POST" action="${baseURL}/updatePhotoProperty.do">

			<input type="hidden" name="contentsID" value="${photoDTO.contentsID}"/>	<!-- コンテンツID -->
			<textarea name='description' cols='80' rows='30'>${photoDTO.description}</textarea><BR>

			<input type="hidden" name="returnPath" value="${returnPath}"/>
			<input type="hidden" name="editMode" value="${editMode}"/>
			<input type="hidden" name="baseURL" value="${baseURL}"/>

			<input type='submit' value='送信'></input><BR>
		</form>		
		<c:if test="${returnPath!=null}" >
			<a href="${returnPath}">戻る</a>
		</c:if>
	</body>
</html>
