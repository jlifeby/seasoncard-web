<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container min-height">
    <h2>Ваши абонементы</h2>
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${abons.size() == 0}">
                Список абонементов пуст.
            </c:if>
            <c:if test="${abons.size() != 0}">
                <div class="z-depth-1 no-padding">
                    <div class="container-fluid horizontal-listing no-padding">
                        <div class="container-fluid">
                            <c:forEach var="abon" items="${abons}">
                                <div class="row hoverable">
                                    <div class="col-sm-4">
                                        <a href="${contextPath}/user/abonnements/${abon.id}">
                                            <img src="${contextPath}${abon.product.logoPath}" class="img-fluid">
                                            <c:if test="${abon.checkIsFinished()}">
                                            <span class="tag grey"
                                                  style="font-size: 15px; position: absolute; top: 20px; right: 15px;">Завершен</span>
                                            </c:if>
                                            <c:if test="${abon.appliedPromotions.size() > 0}">
                                                <i class="material-icons" data-toggle="tooltip"
                                                   data-placement="top" title=""
                                                   data-original-title="${abon.appliedPromotions.get(0).name}">money_off</i>
                                            </c:if>
                                        </a>
                                    </div>
                                    <div class="col-sm-8">
                                        <a href="${contextPath}/user/abonnements/${abon.id}">
                                            <h5 class="ellipsis bold-500">${abon.product.name}</h5>
                                        </a>

                                        <p class="list">
                                            <strong>${abon.unitNameAsString}: ${abon.unitValueAsString}</strong></p>
                                        <p class="list"><strong>Действителен до:
                                            <joda:format value="${abon.endDate}" pattern="d MMMM"
                                                         dateTimeZone="Europe/Minsk"/></strong></p>
                                        <p class="list-light">
                                            <a href="${contextPath}/companies/${abon.product.companyId}"
                                               class="ellipsis light-blue-text bold-500"
                                               style="display: block;">${abon.product.company.name}</a>
                                        </p>
                                        <p class="list-light"><i class="fa fa-clock-o"> Куплен
                                            <joda:format value="${abon.purchaseDate}" style="F-"
                                                         dateTimeZone="Europe/Minsk"/></i></p>
                                        <p class="list text-xs-right">
                                            <a class="light-blue-text bold-500"
                                               href="${contextPath}/user/abonnements/${abon.id}">
                                                Посмотреть историю</a></p>

                                            <%--<p>${abon.product.description}</p>--%>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="col-md-4">

            <div class="card-panel">
                <div class="row">
                    <div class="col-md-12">
                        <h5><strong>История абонементов</strong></h5>

                        <p class="normal-400" style="font-size: 1.05rem; margin-bottom: 5px">
                            На этой странице вы можите увидеть все купленные вами абонементы,
                            просмотреть по ним более детальную информацию, увидеть историю посещений,
                            а также остатоток по каждому из них.</p>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

