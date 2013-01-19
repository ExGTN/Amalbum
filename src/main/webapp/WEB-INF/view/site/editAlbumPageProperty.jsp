<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
アルバムページのプロパティ編集画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アルバムページプロパティ編集" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="アルバムページプロパティ編集" />
		</jsp:include>

		<!-- フォーム -->
		<H2>コメント編集</H2>
		<form:form commandName="editAlbumPagePropertyForm" method="POST" action="${baseURL}/updateAlbumPageProperty.do">

			<form:hidden path="contentsGroupID" />
			ページタイトル：<form:input path="brief" size="80"/><BR>
			コメント：<BR>
			<form:textarea path="description"  rows="30" cols="80" /><BR>

			<input type='submit' value='送信'></input><BR>
		</form:form>		
		<a href="${baseURL}/viewAlbumPage.do/${albumPageDTO.albumPageInfo.contentsGroupID}?editMode=true">戻る</a>
	</body>
</html>
