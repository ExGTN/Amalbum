<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mugenunagi.amalbum.datamodel.dto.view.ViewAlbumSectionDTO" %>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
  マイページ
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アルバムセクション一覧" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="parts/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeaderParts" />
			<jsp:param name="pageName" value="アルバムセクション一覧" />
		</jsp:include>
	
		<%-- アルバム種別の一覧
		--%>
		<%
			ViewAlbumSectionDTO viewAlbumSectionDTO = (ViewAlbumSectionDTO)request.getAttribute("viewAlbumSectionDTO");
			request.setAttribute( "albumCategoryListPartsDTO", viewAlbumSectionDTO.getAlbumCategoryListPartsDTO() );
			request.setAttribute( "linkTarget", request.getContextPath()+"/site/viewAlbum.do" );
		%>
		<jsp:include page="parts/AlbumCategoryList.jsp">
			<jsp:param name="divID" value="AlbumCategoryListParts"/>
		</jsp:include>
	</body>
</html>
