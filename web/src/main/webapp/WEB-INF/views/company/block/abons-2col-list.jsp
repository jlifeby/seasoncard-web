<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card card-block">
    <div class="row horizontal-listing">
        <c:forEach var="abon" items="${abonnementsForList}">
            <div class="col-md-6">
                <div class="row hoverable ${abon.expiringAbonement ? 'orange lighten-4' : ''}" style="margin: 3px -12px;">
                    <div class="col-sm-4">
                        <img src="${contextPath}${abon.product.logoPath}" class="img-fluid">
                        <c:if test="${abon.checkIsFinished()}">
                            <div class="text-xs-center">
                                <span class="tag grey">Завершен</span>
                            </div>
                        </c:if>
                            <%--<c:if test="${abon.appliedPromotions.size() > 0}">--%>
                            <%--<i class="material-icons pull-right" data-toggle="tooltip"--%>
                            <%--data-placement="top" title=""--%>
                            <%--data-original-title="${abon.appliedPromotions.get(0).name}">money_off</i>--%>
                            <%--</c:if>--%>
                    </div>
                    <div class="col-sm-8">
                        <ul class="striped min">
                            <li><h4 class="ellipsis"><strong>${abon.product.name}</strong></h4></li>
                            <li><strong>${abon.unitNameAsString}:</strong>
                                <span class="${abon.expiringAbonementByAttendance ? 'red-text' : ''}">${abon.unitValueAsString}</span>
                            </li>
                            <li><strong>Действителен:</strong>
                                <span class="${abon.expiringAbonementByDayLeft ? 'red-text' : ''}">
                                   до <joda:format value="${abon.endDate}" pattern="d MMMM" dateTimeZone="Europe/Minsk"/>
                                </span>
                            </li>
                            <li><strong>Цена:</strong> <fmt:formatNumber value="${abon.product.price}" type="currency"
                                                                         minFractionDigits="2" maxFractionDigits="2"/>
                            </li>
                            <li><strong>Куплен:</strong> <joda:format value="${abon.purchaseDate}" style="F-"
                                                                      dateTimeZone="Europe/Minsk"/>
                            </li>
                        </ul>

                        <p class="list-light"><a
                                href="${contextPath}/company/cards/${card.cardUUID}/abonnements/${abon.id}"
                                class="btn btn-default btn-sm">Подробнее</a>
                            <c:if test="${abon.checkIsActual()}">
                                <button type="button"
                                        class="btn btn-default btn-sm waves-effect waves-light"
                                        onclick="selectAbonForAttendance({
                                                abonnementId:'${abon.id}',
                                                productName:'${abon.product.name}',
                                                abonnementType: '${abon.type}',
                                                availableCountOfUnits: '${abon.availableCountOfUnits}',
                                                unitName: '${abon.unitName}',
                                                trainerId: '${abon.trainerId}',
                                                startDate: new Date(${abon.startDate.year}, ${abon.startDate.monthOfYear - 1}, ${abon.startDate.dayOfMonth}),
                                                endDate: new Date(${abon.endDate.year}, ${abon.endDate.monthOfYear - 1}, ${abon.endDate.dayOfMonth})
                                                })"> Отметить посещение
                                </button>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
