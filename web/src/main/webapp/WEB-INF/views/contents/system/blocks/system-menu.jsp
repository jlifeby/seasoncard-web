<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="breadcrumb-box">
    <div class="container">
        <div class="button-list">
            <a href="${contextPath}/system/mailing" class="btn btn-sm btn-default${pageName == 'admin-org' ? '' : ' btn-border'}"><i class="fa fa-plus-square"></i>&nbsp; Рассылка</a>
            <a href="${contextPath}/system/faq" class="btn btn-sm btn-default${pageName.startsWith('admin-event') ? '' : ' btn-border'}"><i class="fa fa-plus-square"></i>&nbsp; FAQ</a>
            <a href="${contextPath}/system/users" class="btn btn-sm btn-default${pageName.startsWith('admin-mail') ? '' : ' btn-border'}"><i class="fa fa-plus-square"></i>&nbsp; Пользователи</a>
            <a href="${contextPath}/system" class="btn btn-sm btn-default${pageName == 'admin-org-profile' ? '' : ' btn-border'}"><i class="fa fa-plus-square"></i>&nbsp; TOP</a>
            <a href="${contextPath}/system/blog" class="btn btn-sm btn-default${pageName == 'system-blog' ? '' : ' btn-border'}"><i class="fa fa-plus-square"></i>&nbsp; Блог</a>
        </div>
    </div>
</div>