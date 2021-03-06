<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO" %>
<% request.setCharacterEncoding( "UTF-8" ); %>
<%-- アルバムページの一覧のDTO
--%>
<%
	ViewAlbumPageListDTO viewAlbumPageListDTO = (ViewAlbumPageListDTO)request.getAttribute("viewAlbumPageListDTO");
%>

<%-- ==========================================================================
  アルバムページ一覧の画面
========================================================================== --%>
<html>
	<head>
		<%-- HEADの共通部分
		--%>
		<jsp:include page="common/HeadCommon.jsp">
			<jsp:param name="pageName" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.name} - ${viewAlbumPageListDTO.albumPageListDTO.albumInfo.brief}" />
		</jsp:include>
	</head>

	<body>
		<%-- ページの見出し（アルバム名と簡易説明を表示する）
		--%>
		<jsp:include page="common/PageHeader.jsp">
			<jsp:param name="divID" value="PageHeader" />
			<jsp:param name="pageName" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.name}" />
			<jsp:param name="brief" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.brief}" />
		</jsp:include>

		<%-- 一覧を出力する
		--%>
		<div id="AlbumPageList">
			<table>
				<c:forEach var="albumPageInfo" items="${viewAlbumPageListDTO.albumPageListDTO.albumPageList}" varStatus="varStatus">
					<tr>
						<!-- Brief表示 -->
						<td>
							<a href="${pageContext.request.contextPath}/site/viewAlbumPage.do/${albumPageInfo.contentsGroupID}?pageFrom=${viewAlbumPageListDTO.page}">
								${albumPageInfo.name}
							</a>
						</td>
						<!-- 簡易説明表示 -->
						<td class="APVPageListTable">
							${albumPageInfo.brief}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<!-- ページ -->
		<p>
			${viewAlbumPageListDTO.page+1}ページ目を表示中<BR>
			<c:if test="${viewAlbumPageListDTO.prevPage!=null}" >
				<A href='${pageContext.request.contextPath}/site/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}?page=${viewAlbumPageListDTO.prevPage}'>
					&lt;&lt;前のページ
				</A>
				&nbsp;&nbsp;
			</c:if>
			<c:if test="${viewAlbumPageListDTO.nextPage!=null}" >
				<A href='${pageContext.request.contextPath}/site/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}?page=${viewAlbumPageListDTO.nextPage}'>
					&gt;&gt;次のページ
				</A>
			</c:if>
		</p>

		<!-- フォーム部分 -->
		<HR>
		<form name="fileUploadForm" method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/site/aas/uploadFileToDefaultAlbumPage.do">
			1. <input type="file" name="uploadFile1" size="30"/><BR/>
			2. <input type="file" name="uploadFile2" size="30"/><BR/>
			3. <input type="file" name="uploadFile3" size="30"/><BR/>
			4. <input type="file" name="uploadFile4" size="30"/><BR/>
			5. <input type="file" name="uploadFile5" size="30"/><BR/>
			<input type="hidden" name="albumID" value="${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="hidden" name="defaultAlbumPageName" value="${viewAlbumPageListDTO.defaultAlbumPageName}" />
			<input type="hidden" name="returnPath" value="${pageContext.request.contextPath}/site/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="submit" value="アップロード" />
		</form>
		<form:form commandName="createAlbumPageForm" action="${pageContext.request.contextPath}/site/aas/createAlbumPage.do" method="post" onsubmit="return FileNameCheck(createAlbumPageForm.name.value)">
			ページ名：<form:input path="name"/>
			<form:hidden path="albumID" />
			<input type="hidden" name="returnPath" value="${pageContext.request.contextPath}/site/viewAlbumPageList.do/${viewAlbumPageListDTO.albumPageListDTO.albumInfo.contentsGroupID}" />
			<input type="submit" value="アルバムページを作る" />
			<p><form:errors path="name" class="errorMessage"/></p>
		</form:form>
	</body>
</html>
