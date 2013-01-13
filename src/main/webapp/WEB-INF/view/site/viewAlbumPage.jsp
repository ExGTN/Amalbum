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
		<script src="<%=request.getContextPath()%>/viewAlbumPage.js"></script>
	</head>

	<body class='APVBody'>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="${viewAlbumPageDTO.albumPageDTO.albumPageInfo.name} - ${viewAlbumPageDTO.albumPageDTO.albumPageInfo.brief}" />
		</jsp:include>

		<%-- ページ全体のコメント --%>
		${viewAlbumPageDTO.albumPageDTO.albumPageInfo.description}<BR>
		
		<%-- ページ全体のコメント入力へのリンク --%>
		<c:if test="${viewAlbumPageDTO.editMode}">
			<form action='${viewAlbumPageDTO.baseURL}/aas/editAlbumPageProperty.do' method='POST'>
				<input type='hidden' name='contentsGroupID' value='${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
				<div align='right'><input type='submit' value='コメント編集'></input></div>
			</form>
		</c:if>
		<HR>


		<%-- 一覧を出力する
		--%>
		<c:forEach var="photoDTO" items="${viewAlbumPageDTO.albumPageDTO.photoDTOList}" varStatus="varStatus">
			<div class="APVPhotoFrame">
				<c:choose>
					<c:when test="${photoDTO.contentsType==0}">
						<!-- 写真表示 -->
						<div class="APVPhoto">
							<a href="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/0" target="_blank">
								<img src="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/1">
							</a>
							<BR>
							${photoDTO.path}
						</div>
					</c:when>
					<c:when test="${photoDTO.contentsType==1}">
						<!-- 動画表示 -->
						<div class="APVPhoto">
							<a href="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/2" target="_blank">
								<img src="${viewAlbumPageDTO.baseURL}/ads/restPhoto.do/${photoDTO.contentsID}/3">
							</a>
							<BR>
							${photoDTO.path}
						</div>
					</c:when>
					<c:otherwise>
						${photoDTO.path}<BR>
						不明なコンテンツタイプ(Type=${photoDTO.contentsType})<BR>
					</c:otherwise>
				</c:choose>

				<%-- 回転指示ボタン --%>
				<c:if test="${viewAlbumPageDTO.editMode}">
					<form action='${viewAlbumPageDTO.baseURL}/aas/rotateImage.do' method='POST'>
						<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPageList.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.parentID}'>
						<input type='hidden' name='editMode' value='true'>
						<input type='hidden' name='contentsID' value='${photoDTO.contentsID}'>
						<input type='hidden' name='rotate' value=''>
						<input type='button' name='rotateLeftButton'  value='画像を左回転' onclick='javascript:rotate.value="left";submit();'></input>&nbsp;
						<input type='button' name='rotateRightButton' value='画像を右回転' onclick='javascript:rotate.value="right";submit();'></input>
					</form>
				</c:if>

				<!-- 説明表示 -->
				<div class="APVPhotoComment">
					${photoDTO.description}
				</div>

				<c:if test="${viewAlbumPageDTO.editMode}">
					<%-- コメント編集ボタン --%>
					<form action='${viewAlbumPageDTO.baseURL}/editPhotoProperty.do' method='POST'>
						<input type='hidden' name='returnPath' value='${viewAlbumPageDTO.baseURL}/viewAlbumPage.do/${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
						<input type='hidden' name='editMode' value='true'>
						<input type='hidden' name='contentsID' value='${photoDTO.contentsID}'>
						<input type='hidden' name='description' value='${photoDTO.description}'>
						<input type='hidden' name='baseURL' value='${viewAlbumPageDTO.baseURL}'>
						<div align='right'>
							<input type='submit' value='コメント編集'/>
						</div>
					</form>

					<%-- ファイル削除ボタン --%>
					<form name='deleteFileForm${varStatus.count}' action='${viewAlbumPageDTO.baseURL}/aas/deletePhoto.do' method='POST'>
						<input type='hidden' name='contentsGroupID' value='${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
						<input type='hidden' name='contentsID' value='${photoDTO.contentsID}'>
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
			
			<form name='deleteDirForm' action='${viewAlbumPageDTO.baseURL}/aas/deleteAlbumPage.do' method='POST'>
				<input type='hidden' name='contentsGroupID' value='${viewAlbumPageDTO.albumPageDTO.albumPageInfo.contentsGroupID}'>
				<div align='right'><input type='button' value='このディレクトリを削除' onClick='javascript:onDeleteDir();'></div>
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

		<div align="right"><font size="-2">
			このページでは、一部<a target="_vp" href="http://www.visualpharm.com/">VisualPharm</a>で公開されている素材を利用させていただいております。
			<a target="_cc" href="http://creativecommons.org/licenses/by-nd/3.0/"><img src="<%=request.getContextPath()%>/images/88x31.png"></a>
		</font></div>

	</body>
</html>
