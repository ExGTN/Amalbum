<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.datamodel.dto.view.ViewAlbumDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
 アルバムのコンテンツ表示
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アルバム" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="parts/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeaderParts" />
			<jsp:param name="pageName" value="アルバム名" />
		</jsp:include>
	
		<%-- アルバム種別の一覧
		--%>
		<%
			ViewAlbumDTO viewAlbumDTO = (ViewAlbumDTO)request.getAttribute("viewAlbumDTO");
			request.setAttribute( "albumContentsListPartsDTO", viewAlbumDTO.getAlbumContentsListPartsDTO() );
			request.setAttribute( "restImage", request.getContextPath()+"/site/restImage.do" );
		%>
		<jsp:include page="parts/AlbumContentsList.jsp">
			<jsp:param name="divID" value="AlbumContentsListParts"/>
		</jsp:include>
	</body>
</html>
