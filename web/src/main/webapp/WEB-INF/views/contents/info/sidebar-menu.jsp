<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .sidebar .btn-flat {
        margin: 0;
        padding: 10px 0;
    }

    .sidebar hr {
        margin: 1px 0;
        padding: 0;
    }

    .sidebar a.active {
        color: #5a95f5 !important;
    }
</style>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="sidebar card-panel">
    <a href="${contextPath}/about" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/about')? 'active' : ''}">О нас</a><br/>
    <hr/>
    <a href="${contextPath}/contacts" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/contacts')? 'active' : ''}">Контакты</a><br/>
    <hr/>
    <a href="${contextPath}/card-design" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/card-design')? 'active' : ''}">Памятка по картам</a><br/>
    <hr/>
    <a href="${contextPath}/video-help" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/video-help')? 'active' : ''}">Видео справка</a><br/>
    <hr/>
    <a href="${contextPath}/faq" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/faq')? 'active' : ''}">Часто задаваемые вопросы</a><br/>
    <hr/>
    <a href="${contextPath}/user-agreement" class="btn-flat waves-effect waves-teal ${fn:contains(url, '/user-agreement')? 'active' : ''}">Пользовательское соглашение</a><br/>
    <hr/>
    <a href="${contextPath}/public-offer.pdf" class="btn-flat waves-effect waves-teal" target="_blank">Публичная оферта</a><br/>
</div>