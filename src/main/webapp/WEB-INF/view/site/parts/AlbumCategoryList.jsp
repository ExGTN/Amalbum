<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mugenunagi.amalbum.datamodel.dto.parts.AlbumCategoryListPartsDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- アルバムカテゴリーの一覧表示
	[ Required Attributes ]
		name=albumCategoryListPartsDTO, class=AlbumCategoryListPartsDTO : 表示内容を格納したDTO
		name=linktarget, class=String : リンク先のベースURL
--%>
<%	// パラメータを取得する
	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = (AlbumCategoryListPartsDTO)( request.getAttribute( "albumCategoryListPartsDTO" ) );
%>

<%-- 一覧を出力する
--%>
<div id="${param['divID']}">
	<table>
		<tr><th>アルバム名</th><th>説明</th></tr>
		<c:forEach var="albumCategory" items="${albumCategoryListPartsDTO.albumCategoryList}" varStatus="varStatus">
			<tr>
				<!-- タイトル表示 -->
				<td>
					<a href="${requestScope.linkTarget}/${albumCategory.contentsGroupEntity.contentsGroupID}">
						${albumCategory.contentsGroupEntity.name}
					</a>
				</td>

				<!-- 説明表示 -->
				<td>
					${albumCategory.contentsGroupEntity.description}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
