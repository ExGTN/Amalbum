<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mugenunagi.amalbum.datamodel.dto.parts.AlbumContentsListPartsDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- アルバムコンテンツの一覧表示
	[ Required Attributes ]
		name=albumContentsListPartsDTO, class=AlbumContentsListPartsDTO : 表示内容を格納したDTO
--%>
<%	// パラメータを取得する
	AlbumContentsListPartsDTO albumContentsListPartsDTO = (AlbumContentsListPartsDTO)( request.getAttribute( "albumContentsListPartsDTO" ) );
%>

<%-- 一覧を出力する
--%>
<div id="${param['divID']}">
	<table>
		<c:forEach var="albumContents" items="${albumContentsListPartsDTO.albumContentsList}" varStatus="varStatus">
			<tr>
				<!-- 写真表示 -->
				<td>
					<img src="${albumContentsListPartsDTO.localContentsBasePath}/${albumContents.contentsEntity.dir}/${albumContents.contentsEntity.name}">
				</td>
			</tr>
			<tr>
				<!-- 説明表示 -->
				<td>
					${albumContents.contentsEntity.description}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
