<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
 アルバムのコンテンツ表示
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アルバムページ参照" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="アルバム名" />
		</jsp:include>
	
		<%-- アルバム種別の一覧
		--%>
		<%
			ViewAlbumPageDTO viewAlbumPageDTO = (ViewAlbumPageDTO)request.getAttribute("viewAlbumPageDTO");
		%>

		<%-- 一覧を出力する
		--%>
		<div id="ContentsList">
			<table>
				<c:forEach var="photoDTO" items="${viewAlbumPageDTO.albumPageDTO.photoDTOList}" varStatus="varStatus">
					<tr>
						<!-- 写真表示 -->
						<td>
							<img src="${viewAlbumPageDTO.baseURL}/ads/restImage.do/${photoDTO.materialID}">
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
	</body>
</html>