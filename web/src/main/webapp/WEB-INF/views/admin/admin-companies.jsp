<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <h6>Действия</h6>
            <%--<a href="${contextPath}/admin/companies/new" class="btn btn-default btn-sm" style="margin-left: 0">Добавить компанию</a>--%>
            <a href="${contextPath}/admin/companies/full-statistics" class="btn btn-default btn-sm" style="margin-left: 0">Вся статистика</a>
            <a href="${contextPath}/admin/companies/contacts" class="btn btn-default btn-sm" style="margin-left: 0">Контакты</a>
        </div>
        <div class="col-md-10">
            <h6>Компании</h6>
            <div class="row">
                <div class="col-md-12">
                    <c:forEach var="company" items="${companies}">
                        <div class="card-panel">
                            <div class="row">
                                <div class="col-md-2">
                                    <a href="${contextPath}/admin/companies/${company.id}">
                                        <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
                                    </a>
                                </div>
                                <div class="col-md-6">
                                    <p>Название: <a
                                            href="${contextPath}/admin/companies/${company.id}">${company.name}</a></p>
                                    <p>Дата регистрации: </p>
                                    <p>Абонементов: ${company.products.size()}</p>
                                </div>
                                <div class="col-md-4">
                                    <p>Тарифный план:</p>
                                    <div style="margin-top: 5px;">
                                        <a href="${contextPath}/admin/companies/${company.id}">
                                            <button type="button" class="btn btn-default btn-sm">Подробнее</button>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
