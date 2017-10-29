<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .report-flat {
        margin: 0;
        padding: 5px 10px;
    }

    .report-flat.active {
        color: #5a95f5;
    }
</style>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="card-panel">
    <h5>Виды отчетов</h5>
    <a href="${contextPath}/company/reports/products" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/products')? 'active' : ''}">по всем абонементам</a><br/>
    <a href="${contextPath}/company/reports/sold-products" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/sold-products')? 'active' : ''}">по проданным абонементам</a><br/>
    <a href="${contextPath}/company/reports/single-attendance" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/single-attendance')? 'active' : ''}">по разовым посещениям</a><br/>
    <a href="${contextPath}/company/reports/clients" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/clients')? 'active' : ''}">по новым посетителям</a><br/>
    <a href="${contextPath}/company/reports/visits-by-time" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/visits-by-time')? 'active' : ''}">по посещениям по времени</a><br/>
    <a href="${contextPath}/company/reports/trainer" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/trainer')? 'active' : ''}">по тренерам</a><br/>
    <a href="${contextPath}/company/reports/birthday" class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/birthday')? 'active' : ''}">по Дням Рождения посетителей</a><br/>
    <a href="${contextPath}/company/reports/clients-full"
       class="btn-flat waves-effect waves-teal report-flat ${fn:contains(url, '/clients-full')? 'active' : ''}">по всем
        клиентам</a><br/>
</div>
