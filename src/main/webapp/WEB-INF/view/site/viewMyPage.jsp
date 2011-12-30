<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.mugenunagi.amalbum.album.datamodel.dto.view.ViewMyPageDTO" %>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
  マイページ
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="MyPage" />
		</jsp:include>
		
		<script language="javascript">
		// アルバムの新規作成
		function createNewAlbum( createAlbumForm ) {
			if( !confirm( "新規にアルバム [ " + createAlbumForm.albumName.value + " ] を作成します。よろしいですか？" ) ){
				// キャンセル
				return;
			}
			createAlbumForm.submit();
		}
		</script>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="parts/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeaderParts" />
			<jsp:param name="pageName" value="アルバム一覧" />
		</jsp:include>
	
		<%-- アルバム種別の一覧
		--%>
		<%
			ViewMyPageDTO viewMyPageDTO = (ViewMyPageDTO)request.getAttribute("viewMyPageDTO");
			request.setAttribute( "albumCategoryListPartsDTO", viewMyPageDTO.getAlbumCategoryListPartsDTO() );
			request.setAttribute( "linkTarget", request.getContextPath()+"/site/viewAlbumSection.do" );
		%>
		<jsp:include page="parts/AlbumCategoryList.jsp">
			<jsp:param name="divID" value="AlbumCategoryListParts"/>
		</jsp:include>
		
		<%-- 新規登録系の入力フィールド
		--%>
		<form:form name="editForm" modelAttribute="createAlbumForm" method="POST" action="createAlbum.do">
			アルバム名：<form:input type="text" path="albumName" /><BR>
			説明：<form:input type="text" path="brief" /><BR>
			<input type="button" name="createButton" value="作成" onClick="javascript:createNewAlbum( editForm );"/>
		</form:form>
	</body>
</html>
