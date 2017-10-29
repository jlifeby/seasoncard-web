<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">


    <section class="section">
        <div class="row">
            <div class="col-md-6">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <h2 class="mt-2">Компания: ${company.name}</h2>
                </sec:authorize>
            </div>
            <div class="col-md-6">
                <div class="card card-cascade cascading-admin-card">
                    <div class="admin-up">
                        <i class="fa fa-users indigo"></i>
                        <div class="data">
                            <p>Активных посетителей c
                                <b><joda:format value="${startDate}" pattern="d.MM.YYYY"
                                                dateTimeZone="Europe/Minsk"/></b>
                                по
                                <b><joda:format value="${endDate}" pattern="d.MM.YYYY"
                                                dateTimeZone="Europe/Minsk"/></b>
                                (включительно)
                            </p>
                            <h3>${clientActivities.size()}</h3>
                        </div>
                    </div>
                    <div class="card-block">
                        <p class="card-text" style="font-size: 12px;">Посетитель, который купил хотя бы один абонемент
                            или посетил зал хотя бы один раз</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="card mb-r">
        <div class="card-header white-text">
            Клиенты, по которым были активности
        </div>
        <div class="card-block">

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>№ п/п</th>
                        <th>Card UUID</th>
                        <th>Имя клиента</th>
                        <th>Отмечено посещений</th>
                        <th>Продано абонементов</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="clientActivity" items="${clientActivities}" varStatus="orderNumber">
                        <tr>
                            <td>${orderNumber.index+1}</td>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    ${clientActivity.cardUUID}
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_COMPANY')">
                                    <a href="/company/cards/${clientActivity.cardUUID}">${clientActivity.cardUUID}</a>
                                </sec:authorize>
                            </td>
                            <td>${clientActivity.client.name} ${clientActivity.client.lastName}</td>
                            <td>${clientActivity.attendanceDates.size()}</td>
                            <td>${clientActivity.purchaseDates.size()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

