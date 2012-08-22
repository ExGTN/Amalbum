<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>
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
		<script src="viewAlbumPage.js"></script>
	</head>

	<body class='APVBody'>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.name}" />
		</jsp:include>

		<%-- ページ全体のコメント入力へのリンク --%>
		<c:if test="${viewAlbumPageDTO.editMode}">
			<form action='${viewAlbumPageDTO.baseURL}/aas/editAlbumPageComment.do' method='POST'>
				<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
				<input type='hidden' name='editMode' value='true'>
				<div align='right'><input type='submit' value='コメント編集'></input></div>
			</form>
		</c:if>
		<HR>


		<%-- 一覧を出力する
		--%>
		<c:forEach var="photoDTO" items="${viewAlbumPageDTO.albumPageDTO.photoDTOList}" varStatus="varStatus">
			<div class="APVPhotoFrame">
				<!-- 写真表示 -->
				<div class="APVPhoto">
					<a href="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/0" target="_blank">
						<img src="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/1">
					</a>
					<BR>
					ファイル名（DTO未実装）
				</div>

				<%-- 回転指示ボタン --%>
				<c:if test="${viewAlbumPageDTO.editMode}">
					<form action='${viewAlbumPageDTO.baseURL}/aas/rotateImage.do' method='POST'>
						<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPageList.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.parentID}'>
						<input type='hidden' name='editMode' value='true'>
						<input type='submit' name='rotate' value='画像を左回転'></input>&nbsp;
						<input type='submit' name='rotate' value='画像を右回転'></input>
					</form>
				</c:if>

				<!-- 説明表示 -->
				<div class="APVPhotoComment">
					${photoDTO.description}
				</div>

				<c:if test="${viewAlbumPageDTO.editMode}">
					<%-- コメント編集ボタン --%>
					<form action='${viewAlbumPageDTO.baseURL}/aas/editPhotoComment.do' method='POST'>
						<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
						<input type='hidden' name='editMode' value='true'>
						<div align='right'>
							<input type='submit' value='コメント編集'/>
						</div>
					</form>

					<%-- ファイル削除ボタン --%>
					<form name='deleteFileForm${varStatus.count}' action='${viewAlbumPageDTO.baseURL}/aas/deletePhoto.do' method='POST'>
						<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
						<input type='hidden' name='editMode' value='true'>
						<div align='right'>
							<input type='button' value='ファイル削除' onClick='javascript:onDeleteFile( document.deleteFileForm${varStatus.count} )' />
						</div>
					</form>
				</c:if>
			</div>
			<HR>
		</c:forEach>

		<%-- エディットモードへ
		--%>
		<c:if test="${viewAlbumPageDTO.editMode==false}">
			<form name='toEditMode' action='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}' method='GET'>
			<input type='hidden' name='editMode' value='true'>
			<div align='right'><input type='submit' value='エディットモードへ'></div>
			</form>
		</c:if>
		<c:if test="${viewAlbumPageDTO.editMode==true}">
			<form name='toReferMode' action='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}' method='GET'>
			<input type='hidden' name='editMode' value='false'>
			<div align='right'><input type='submit' value='参照モードへ'></div>
			</form>
		</c:if>

		<%-- アップロードのフォーム
		--%>
		<form name="fileUploadForm" method="POST" enctype="multipart/form-data" action="${viewAlbumPageDTO.baseURL}/aas/uploadFile.do">
			<input type="file" name="uploadFile" size="30"/>
			<input type="hidden" name="contentsGroupID" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}" />
			<input type="hidden" name="returnPath" value="${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}" />
			<input type="submit" value="アップロード" />
		</form>

		<%-- 戻るリンク
		--%>
		<A href="${viewAlbumPageDTO.baseURL}/viewAlbumPageList.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.parentID}">戻る</A>

	</body>
</html>
