<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>
<%-- アルバムページの一覧のDTO
--%>
<%
	ViewAlbumPageListDTO viewAlbumPageListDTO = (ViewAlbumPageListDTO)request.getAttribute("viewAlbumPageListDTO");
%>

<%-- ==========================================================================
  アルバムページ一覧の画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.name} - ${viewAlbumPageListDTO.albumPageListDTO.albumInfo.brief}" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し（アルバム名と簡易説明を表示する）
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.name} - ${viewAlbumPageListDTO.albumPageListDTO.albumInfo.brief}" />
		</jsp:include>

		<%-- 一覧を出力する
		--%>
		<div id="AlbumPageList">
			<table>
				<c:forEach var="albumPageInfo" items="${viewAlbumPageListDTO.albumPageListDTO.albumPageList}" varStatus="varStatus">
					<tr>
						<!-- Brief表示 -->
						<td>
							<a href="${viewAlbumPageListDTO.baseURL}/viewAlbumPage.do/${albumPageInfo.contentsGroupID}">
								${albumPageInfo.name}
							</a>
						</td>
						<!-- 簡易説明表示 -->
						<td>
							${albumPageInfo.brief}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<!-- フォーム部分 -->
		<HR>
		<form name="fileUploadForm" method="POST" enctype="multipart/form-data" action="${viewAlbumPageListDTO.baseURL}/aas/uploadFileToDefaultAlbumPage.do">
			<input type="file" name="uploadFile" size="30"/>
			<input type="hidden" name="albumID" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="hidden" name="defaultAlbumPageName" value="${viewAlbumPageListDTO.defaultAlbumPageName}" />
			<input type="hidden" name="returnPath" value="${viewAlbumPageListDTO.baseURL}/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="submit" value="アップロード" />
		</form>
		<form action="${viewAlbumPageListDTO.baseURL}/aas/createAlbumPage.do" method="POST">
			ページ名：<input type="text" name="name" value="" />
			<input type="hidden" name="albumID" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="hidden" name="returnPath" value="${viewAlbumPageListDTO.baseURL}/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="submit" value="アルバムページを作る" />
		</form>
	</body>
</html>
