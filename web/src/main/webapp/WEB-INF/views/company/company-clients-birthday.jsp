<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="z-depth-1 no-padding">

                <div class="btn-group" style="margin: 15px">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">${period.description}</button>

                    <div class="dropdown-menu">
                        <c:forEach var="periodIter" items="${periods}">
                            <a class="dropdown-item"
                               href="${contextPath}/company/clients/birthday?period=${periodIter.name()}">${periodIter.description}</a>
                        </c:forEach>
                    </div>
                </div>

                <c:if test="${empty clients}">
                    <p style="padding: 15px">Клиенты не найдены</p>
                </c:if>

                <c:if test="${not empty clients}">
                    <div class="container-fluid horizontal-listing">

                        <c:forEach var="client" items="${clients}">
                            <div class="row hoverable">
                                <div class="col-sm-1">
                                    <a href="${contextPath}/company/cards/${client.cardUUID}">
                                        <img src="${contextPath}${client.logoPath}"
                                             class="img-fluid rounded-circle">
                                    </a>
                                </div>
                                <div class="col-sm-11">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <h5 style="margin: 5px 0;">
                                                <a href="${contextPath}/company/cards/${client.cardUUID}"
                                                   style="font-weight: bold; margin-left: 0;">${client.name} ${client.lastName}</a>

                                            </h5>
                                        </div>
                                        <div class="col-sm-4">
                                            Карта: ${client.cardUUID}
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8">
                                            Дата рождения: <span class="birthday"><joda:format value="${client.birthday}"
                                                                                               pattern="d MMMM YYYY"/></span>
                                        </div>
                                        <div class="col-sm-4">
                                            Тел: +${client.phone}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="block/birthday.jsp"/>

