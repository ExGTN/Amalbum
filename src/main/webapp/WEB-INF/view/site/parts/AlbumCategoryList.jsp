<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.mugenunagi.amalbum.datamodel.dto.parts.AlbumCategoryListPartsDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- アルバムカテゴリーの一覧表示
	[ Required Attributes ]
		name=albumCategoryListPartsDTO, class=AlbumCategoryListPartsDTO
--%>
<%	// パラメータを取得する
	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = (AlbumCategoryListPartsDTO)( request.getAttribute( "albumCategoryListPartsDTO" ) );
%>

<%-- 一覧を出力する
--%>
<div id="${param['divID']}">
	<c:forEach var="albumCategory" items="${albumCategoryListPartsDTO.albumCategoryList}" varStatus="varStatus">
		カテゴリ：${albumCategory.contentsGroupEntity.name}<br>
	</c:forEach>
</div>
