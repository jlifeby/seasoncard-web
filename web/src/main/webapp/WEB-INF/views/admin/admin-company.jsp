<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>
        </div>
        <div class="col-md-10">
            <div class="card-panel">
                <jsp:include page="block/company-header.jsp"/>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="block/cards-table.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
