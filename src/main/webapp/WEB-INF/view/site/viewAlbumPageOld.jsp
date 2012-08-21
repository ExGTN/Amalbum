<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>
<%-- コンテンツの一覧
--%>

<%
	ViewAlbumPageDTO viewAlbumPageDTO = (ViewAlbumPageDTO)request.getAttribute("viewAlbumPageDTO");
%>

<%-- ==========================================================================
 アルバムのコンテンツ表示
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.name}" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.name}" />
		</jsp:include>
	
		<%-- 一覧を出力する
		--%>
		<div id="ContentsList">
			<table>
				<c:forEach var="photoDTO" items="${viewAlbumPageDTO.albumPageDTO.photoDTOList}" varStatus="varStatus">
					<tr>
						<!-- 写真表示 -->
						<td>
							<a href="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/0">
								<img src="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/1">
							</a>
						</td>
					</tr>
					<tr>
						<!-- 説明表示 -->
						<td>
							${photoDTO.description}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<%-- アップロードのフォーム
		--%>
		<form name="fileUploadForm" method="POST" enctype="multipart/form-data" action="${viewAlbumPageDTO.baseURL}/aas/uploadFile.do">
			<input type="file" name="uploadFile" />
			<input type="hidden" name="contentsGroupID" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}" />
			<input type="hidden" name="returnPath" value="${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}" />
			<input type="submit" value="アップロード" />
		</form>
	</body>
</html>
