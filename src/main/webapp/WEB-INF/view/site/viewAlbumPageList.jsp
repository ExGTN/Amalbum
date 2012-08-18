<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
  マイページ
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アルバムページ一覧" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="アルバム名" />
		</jsp:include>
	
		<%-- アルバムページの一覧
		--%>
		<%
			ViewAlbumPageListDTO viewAlbumPageListDTO = (ViewAlbumPageListDTO)request.getAttribute("viewAlbumPageListDTO");
		%>

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
	</body>
</html>
