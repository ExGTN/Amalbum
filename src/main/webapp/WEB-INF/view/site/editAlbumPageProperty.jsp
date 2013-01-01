<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<form name="editForm" method="POST" action="${baseURL}/updateAlbumPageProperty.do">

			<input type="hidden" name="contentsGroupID" value="${albumPageDTO.albumPageInfo.contentsGroupID}"/>
			ページタイトル：<input type="text" name="brief" size="80" value="${albumPageDTO.albumPageInfo.brief}"><BR>
			コメント：<BR>
			<textarea name='description' cols='80' rows='30'>${albumPageDTO.albumPageInfo.description}</textarea><BR>

			<input type='submit' value='送信'></input><BR>
		</form>		
		<a href="${baseURL}/viewAlbumPage.do/${albumPageDTO.albumPageInfo.contentsGroupID}?editMode=true">戻る</a>
	</body>
</html>
