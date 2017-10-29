<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="row">
    <div class="col-md-3">
        <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
    </div>
    <div class="col-md-5">
        <p>Название: <strong>${company.name}</strong></p>
        <p>Номер контракта: <strong>${company.contractId}</strong></p>
        <p>Абонементов: <strong>${amountOfProducts}</strong></p>
        <p>Клиентов: <strong>${amountOfClients}</strong></p>
        <p>Всех карт: <strong>${amountOfAllCards}</strong></p>
        <p>Физических карт: <strong>${amountOfNfcCards}</strong></p>
        <p>Свободных физических карт: <strong>${amountOfFreeNfcCards}</strong></p>
        <p>Дата регистрации: <strong><joda:format value="${company.createdDate}" pattern="d.MM.YYYY"
                                          dateTimeZone="Europe/Minsk"/></strong></p>
        <p>Дата активации: <strong><joda:format value="${company.contractDate}" pattern="d.MM.YYYY"
                                        dateTimeZone="Europe/Minsk"/></strong></p>
    </div>
    <div class="col-md-4">
        <%--<p>Тарифный план: </p>--%>
        <div>
            <a href="${contextPath}/admin/companies/${company.id}/profile" class="btn btn-default btn-sm btn-block">Редактировать</a>
            <a href="${contextPath}/admin/companies/${company.id}/balance" class="btn btn-default btn-sm btn-block">Баланс</a>
            <a href="${contextPath}/admin/companies/${company.id}/cards" class="btn btn-default btn-sm btn-block">Управление
                картами</a>
            <a href="${contextPath}/admin/companies/${company.id}/admins" class="btn btn-default btn-sm btn-block">Администраторы
                компании</a>
            <a href="${contextPath}/admin/companies/${company.id}/products" class="btn btn-default btn-sm btn-block">Абонементы</a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6" style="padding-right: 5px;">
        <h4>За сегодня:</h4>
        <span>Посещения (кол-во): <span class="bold-500">${currentStatistic.todayAttendances}</span></span><br/>
        <span>Проданные абонементы (кол-во, сумма): <span class="bold-500">${currentStatistic.todayAbonnements}</span> (<span class="bold-500">${currentStatistic.todayAbonnementsSum} р.</span>)</span>
    </div>
    <div class="col-md-6" style="padding-left: 5px; padding-right: 5px;">
        <h4>За последний час: </h4>
        <span>Посещения (кол-во): <span class="bold-500">${currentStatistic.hourAttendances}</span></span><br/>
        <span>Проданные абонементы (кол-во, сумма): <span class="bold-500">${currentStatistic.hourAbonnements}</span> (<span class="bold-500">${currentStatistic.hourAbonnementsSum} р.</span>)</span>
    </div>
</div>