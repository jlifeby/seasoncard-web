<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-8">

            <h2 class="ellipsis bold-500">${abon.product.name}</h2>

            <hr>

            <div class="row">
                <div class="col-sm-12 col-md-5">
                    <img class="img-fluid z-depth-1" src="${contextPath}${abon.product.logoPath}" alt="">
                </div>
                <div class="col-sm-12 col-md-7">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <td><p style="margin: 0"><strong>${abon.unitNameAsString}</strong></p></td>
                            <td><p style="margin: 0"><strong>${abon.unitValueAsString}</strong>
                            </p></td>
                        </tr>
                        <tr>
                            <td><p style="margin: 0"><strong>Действителен:</strong></p></td>
                            <td><p style="margin: 0"><strong>c
                                <joda:format value="${abon.startDate}" pattern="d.MM.YYYY" dateTimeZone="Europe/Minsk"/>
                                по
                                <joda:format value="${abon.endDate}" pattern="d.MM.YYYY"
                                             dateTimeZone="Europe/Minsk"/></strong></p></td>
                        </tr>
                        <tr>
                            <td><p style="margin: 0">Цена:</p></td>
                            <td><p style="margin: 0"><fmt:formatNumber value="${abon.product.price}" type="currency"
                                                                       minFractionDigits="2" maxFractionDigits="2"/>
                                <c:if test="${abon.appliedPromotions.size() > 0}">
                                    <i class="material-icons" data-toggle="tooltip"
                                       data-placement="top" title=""
                                       data-original-title="${abon.appliedPromotions.get(0).name}">money_off</i>
                                </c:if></p></td>
                        </tr>
                        <tr>
                            <td><p style="margin: 0">Куплен:</p></td>
                            <td><p style="margin: 0"><joda:format value="${abon.purchaseDate}"
                                                                  style="F-" dateTimeZone="Europe/Minsk"/></p></td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </div>
            <p class="normal-400" style="margin-top: 10px">
                ${abon.product.description}
            </p>
            <hr>

            <div class="text-xs-right">
                <a href="${contextPath}/user/abonnements/${abon.product.id}/history"
                   class="btn btn-default btn-sm" style="margin-top: 0;">Вся история по абонементу</a>
            </div>

            <div class="card-panel">
                <h5 class="text-xs-center title">История посещений <c:if test="${abon.checkIsFinished()}">
                    <span class="tag grey pull-right">Завершен</span>
                </c:if></h5>
                <hr>
                <c:if test="${empty abon.attendances}">
                    <p>Пока нет посещений</p>
                </c:if>
                <c:if test="${not empty abon.attendances}">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Дата посещения</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="attendance" items="${abon.attendances}">
                            <tr>
                                <td> ${attendance.id} </td>
                                <td>
                                    <joda:format value="${attendance.visitDate}" style="FS"
                                                 dateTimeZone="Europe/Minsk"/>
                                    <c:if test="${abon.type.name() == 'BY_UNIT'}">
                                        <span class="light-blue-text"> (${attendance.unitName}: ${attendance.markUnits})</span>
                                    </c:if>
                                    <c:if test="${attendance.skipped}">
                                        <span class="red-text"> (Пропуск)</span>
                                    </c:if>
                                    <c:if test="${attendance.trainer != null}">
                                        <br/>Тренер: ${attendance.trainer.lastName} ${attendance.trainer.name}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
            <div class="card-panel">
                <h5 class="text-xs-center title">Комментарии <span class="tag light-blue">${abon.comments.size()}</span>
                </h5>
                <hr>
                <c:if test="${empty abon.comments}">
                    <p>Пока нет комментариев</p>
                </c:if>

                <c:if test="${not empty abon.comments}">
                    <div class="horizontal-listing comments-section">
                        <div class="container-fluid">
                            <c:forEach var="comment" items="${abon.comments}">
                                <div class="row hoverable">
                                    <div class="col-md-10">
                                        <i class="fa fa-clock-o"> <joda:format value="${comment.createdDate}"
                                                                               style="FS"
                                                                               dateTimeZone="Europe/Minsk"/></i>
                                        <p>${comment.content}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>

            <hr>

        </div>
        <div class="col-md-4">

            <c:set var="company" value="${abon.product.company}" scope="request"/>
            <jsp:include page="../contents/blocks/company-card.jsp"/>

            <c:if test="${not empty abon.appliedPromotions}">
                <c:forEach var="promotion" items="${abon.appliedPromotions}">
                    <div class="row card-panel">
                        <div class="col-sm-4">
                            <h5 class="card-label"
                                style="position: relative; margin-bottom: -35px; margin-left: 5px;">
                                                <span class="label rgba-red-strong"
                                                      style="padding: .2em .4em .3em; font-weight: 400; background-color: #FF2525;">-${promotion.promotionValueAsString}</span>
                            </h5>
                            <img src="${promotion.logoPath}" class="img-fluid">
                        </div>
                        <div class="col-sm-8">
                            <h5 class="title ellipsis" style="margin-bottom: 0">${promotion.name}</h5>
                            <a href="${contextPath}/companies/${promotion.company.id}"
                               class="ellipsis light-blue-text bold-500"
                               style="display: block">${promotion.company.name}</a>
                            <p class="light-300 ellipsis"
                               style="font-size: small">${promotion.description}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>

    <hr>

</div>

