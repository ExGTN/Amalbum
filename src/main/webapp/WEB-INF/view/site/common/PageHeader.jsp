<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ページの見出し部分 -->
<%--
	[ Required Attributes ]
		name=divID, class=String : DIV要素のID
		name=pageName, class=String : ページ名
--%>

<%-- ページ名を見出し表示する
--%>
<div id="${param['divID']}">
	<H1>
		${param['pageName']}
		<c:if test="${param['brief']!=null and param['brief']!=''}">
			 - ${param['brief']}
		</c:if>
	</H1>
</div>
