<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h4>Вся статистика по компаниям
                <div class="btn-group" style="margin: 15px">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">${selectedCompanySort.description}</button>

                    <div class="dropdown-menu">
                        <c:forEach var="companySort" items="${companySorts}">
                            <a class="dropdown-item"
                               href="${contextPath}/admin/companies/full-statistics?sort=${companySort.name()}">${companySort.description}</a>
                        </c:forEach>
                    </div>
                </div>
            </h4>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>№ Контракта</th>
                    <th>Компания</th>
                    <th>Тариф</th>
                    <th>Действие тарифа</th>
                    <th>Виртуальные(св/все)</th>
                    <th>Физические(св/все)</th>
                    <th>Абонементы, к-во</th>
                    <th>Клиенты(акт/все)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="company" items="${companies}" varStatus="loop">
                    <c:set var="currentTariff" value="${company.currentTariff}"/>
                    <tr class="${currentTariff.dead ? "danger-color" :
                        currentTariff.endingInLessThanFiveDays ? "warning-color" :
                        !currentTariff.free ? "success-color" : ""}">

                        <td>${company.contractId}</td>
                        <td><a href="${contextPath}/admin/companies/${company.id}">${company.name}</a></td>
                        <td>
                            <a href="${contextPath}/admin/tariffs/${currentTariff.id}/detail">${currentTariff.name}</a>
                        </td>

                        <td>
                            <joda:format value="${currentTariff.startDate}" pattern="d.MM.YYYY"
                                         dateTimeZone="Europe/Minsk"/>
                            -
                            <joda:format value="${currentTariff.endDate}" pattern="d.MM.YYYY"
                                         dateTimeZone="Europe/Minsk"/>
                        </td>

                        <td>${company.countOfFreeVirtualCards}/${company.countOfVirtualCards}</td>
                        <td>${company.countOfFreePhysicalCards}/${company.countOfPhysicalCards}</td>
                        <td>${company.countOfProducts}</td>
                        <td>${company.countOfActiveClients}/${company.countOfClients}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
