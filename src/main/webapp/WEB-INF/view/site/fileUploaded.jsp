<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% request.setCharacterEncoding( "UTF-8" ); %>

<%-- ==========================================================================
ファイルアップロード完了確認の画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="アップロード完了" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="アップロード完了" />
		</jsp:include>
	
		アップロード完了<BR>
		
		<c:if test="${returnPath!=null}" >
			<a href="${returnPath}">戻る</a>
		</c:if>
	</body>
</html>
