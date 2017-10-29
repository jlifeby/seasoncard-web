<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">


    <jsp:include page="block/client-header.jsp"/>

    <h3>Абонемент: <span class="bold-500">${abon.product.name}</span>
        <a href="${contextPath}/company/cards/${card.cardUUID}/abonnements/${abon.product.id}/history"
           class="btn btn-default btn-sm pull-right" style="margin-top: 0;">Вся история по абонементу</a>
    </h3>

    <div class="row">
        <div class="col-md-6">
            <div class="card-panel">
                <form action="${contextPath}/company/cards/${card.cardUUID}/abonnements/${abon.id}" method="POST">
                    <div class="row">
                        <div class="col-md-9">
                            <h5 style="margin-top: 20px">Информация по абонементу</h5>
                        </div>
                        <div class="col-md-3">
                            <img src="${contextPath}${abon.product.logoPath}" class="img-fluid z-depth-1"
                                 style="margin: 5px">
                        </div>
                    </div>
                    <table class="table table-hover">
                        <caption>

                        </caption>
                        <tbody>
                        <tr>
                            <td>Дата начала</td>
                            <td><joda:format value="${abon.startDate}" pattern="d MMMM yyyy"
                                             dateTimeZone="Europe/Minsk"/></td>
                        </tr>
                        <tr>
                            <td>Дата окончания</td>
                            <td>
                                <div class="row">
                                    <div class="col-md-5">
                                        <span id="endDateLabel"><joda:format value="${abon.endDate}"
                                                                             pattern="d MMMM yyyy"
                                                                             dateTimeZone="Europe/Minsk"/></span>
                                        <input type="hidden" name="endDate" style="margin: 0; height: 1.6rem;"/>

                                    </div>
                                    <div class="col-md-7">
                                        <div id="endDateInfoPanel" style="display: none">
                                            <label>Комментарий: </label>
                                            <textarea id="endDateInfo" name="endDateInfo" rows="2"></textarea>
                                        </div>
                                        <c:if test="${abon.isActive()}">
                                            <button id="changeEndDate" type="button" class="btn btn-default btn-sm"
                                                    style="margin: 0"
                                                    onclick="editEndDate(${abon.endDate.getMillis()})">
                                                Изменить
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>${abon.unitNameAsString}</td>
                            <td>${abon.unitValueAsString}</td>
                        </tr>
                        <tr>
                            <td>Цена</td>
                            <td><fmt:formatNumber value="${abon.product.price}" type="currency" minFractionDigits="2"
                                                  maxFractionDigits="2"/></td>
                        </tr>
                        <tr>
                            <td>Куплен</td>
                            <td><joda:format value="${abon.purchaseDate}" style="F-"/></td>
                        </tr>
                        <c:if test="${not empty abon.appliedPromotions}">
                            <tr>
                                <td>Скидка</td>
                                <td>
                                    <c:forEach var="promotion" items="${abon.appliedPromotions}">
                                        <span class="red-text">-${promotion.promotionValueAsString} </span> / ${promotion.name}
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty abon.trainer}">
                            <tr>
                                <td>Тренер</td>
                                <td>
                                        ${abon.trainer.lastName} ${abon.trainer.name}
                                </td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>

                    <div class="text-xs-center">
                        <button id="submitBtn" type="submit" class="btn btn-default btn-sm" disabled>Сохранить</button>
                    </div>
                </form>

            </div>
        </div>
        <div class="col-md-6">
            <div class="card-panel">
                <h5 style="display: inline-block">История посещений</h5>
                <c:if test="${abon.checkIsFinished()}">
                    <span class="tag grey pull-right">Завершен</span>
                </c:if>
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
                                <joda:format value="${attendance.visitDate}" style="FS" dateTimeZone="Europe/Minsk"/>
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
                <c:if test="${abon.checkIsActual()}">
                    <div class="text-xs-center">
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
                                        })">Отметить посещение
                        </button>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card-panel horizontal-listing comments-section">
                <h5 class="title">Комментарии к абонементу
                    <span class="tag light-blue">${abon.comments.size()}</span></h5>
                <hr>
                <div class="container-fluid">
                    <c:forEach var="comment" items="${abon.comments}">
                        <div class="row hoverable">
                            <div class="col-md-10">
                                <p class="thin-100" style="font-size: 1rem;"><i class="fa fa-clock-o"> <joda:format
                                        value="${comment.createdDate}" style="FS"
                                        dateTimeZone="Europe/Minsk"/></i></p>
                                <span>${comment.content}</span>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="text-xs-center">
                        <button type="button"
                                class="btn btn-default btn-sm waves-effect waves-light"
                                data-toggle="modal" data-target="#add-comment-dialog">Добавить комментарий
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="block/mark-dialog.jsp"/>

<script>
    function editEndDate() {
        var endDate = ${abon.endDate.getMillis()};

        $("#endDateLabel").hide();
        $("#changeEndDate").hide();
        $("#endDateInfoPanel").show();

        $('input[name=endDate]').pickadate({
            selectMonths: true,
            selectYears: true,
            clear: '',
            onClose: function () {
                $(document.activeElement).blur();
                $('#submitBtn').removeAttr("disabled");
            }
        }).pickadate('picker').set('select', endDate).open();

        event.stopPropagation();
    }
</script>

<div id="add-comment-dialog" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Добавление комментария</h4>
            </div>
            <div class="modal-body">
                <form id="addCommentForm"
                      action="${contextPath}/company/cards/${card.cardUUID}/abonnements/${abon.id}/add-comment"
                      method="POST">
                    <textarea id="comment" name="comment" rows="4"></textarea>
                </form>
            </div>
            <div class="modal-footer text-right">
                <button type="button" class="btn btn-default waves-effect waves-light" data-dismiss="modal">Отмена
                </button>
                <button type="button" class="btn btn-default waves-effect waves-light"
                        onclick="$('#addCommentForm').submit()">Добавить
                </button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="block/birthday.jsp"/>