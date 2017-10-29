<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th class="text-xs-center">За сегодня:</th>
                        <th class="text-xs-center">За последний час:</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Посещения (кол-во):</td>
                        <td class="text-xs-center">${currentStatistic.todayAttendances}</td>
                        <td class="text-xs-center">${currentStatistic.hourAttendances}</td>
                    </tr>
                    <tr>
                        <td>Проданные абонементы (кол-во, сумма):</td>
                        <td class="text-xs-center">
                            <span class="bold-500">${currentStatistic.todayAbonnements}</span>
                            (<span class="bold-500">${currentStatistic.todayAbonnementsSum} р.</span>)
                        </td>
                        <td class="text-xs-center">
                            <span class="bold-500">${currentStatistic.hourAbonnements}</span>
                            (<span class="bold-500">${currentStatistic.hourAbonnementsSum} р.</span>)
                        </td>
                    </tr>
                    <tr>
                        <td>Разовые посещения (кол-во, сумма):</td>
                        <td class="text-xs-center">
                            <span class="bold-500">${currentStatistic.todaySingleAttendances}</span>
                            (<span class="bold-500">${currentStatistic.todaySingleAttendancesSum} р.</span>)
                        </td>
                        <td class="text-xs-center">
                            <span class="bold-500">${currentStatistic.hourSingleAttendances}</span>
                            (<span class="bold-500">${currentStatistic.hourSingleAttendancesSum} р.</span>)
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <div class="col-md-7">
                    <div class="card">
                        <div class="card-header white-text">
                            Зачислить абонемент / Отметить посещение
                        </div>
                        <div class="card-block">
                            <form id="markForm" action="${contextPath}/company/card-search" method="POST">
                                <div class="md-form">
                                    <i class="material-icons prefix">credit_card</i>
                                    <input id="markCardUUID" type="text" name="cardUUID" pattern="[0-9]{1,8}"
                                           class="form-control"
                                           required title="Введите цифровой номер карты (1-8 символов)"/>
                                    <label for="markCardUUID">Номер карты:</label>
                                </div>
                                <div>
                                    <button type="submit" class="btn btn-default"
                                            onclick="$('#markForm').attr('action', '${contextPath}/company/cards/enroll');">
                                        Зачислить
                                    </button>
                                    <button type="submit" class="btn btn-default pull-right"
                                            onclick="$('#markForm').attr('action', '${contextPath}/company/card-search');">
                                        Отметить
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="card">
                        <div class="card-header white-text">
                            Разовое посещение
                        </div>
                        <div class="card-block">
                            <p>Разовое посещение отмечается без карты клиента</p>
                            <button type="button" class="btn btn-default" data-toggle="modal"
                                    data-target="#confirm-dialog"> Разовое посещение
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <c:if test="${company.currentTariff.dead}">
                <jsp:include page="../components/not-avalible-card.jsp"/>
            </c:if>
            <c:if test="${!company.currentTariff.dead}">
                <div class="card-panel">
                    <a href="${contextPath}/company/cards/new" class="btn btn-default btn-block">Добавить клиента</a>
                    <br>
                    <form action="${contextPath}/company/cards" method="GET">

                        <div class="form-group">
                            <label for="text">Поиск: ФИО / Телефон / Номер карты <i class="fa fa-info-circle"
                                                                                    data-toggle="tooltip"
                                                                                    data-placement="top"
                                                                                    title="Поиск клиентов по ФИО, Телефону, Номеру карты и Внутреннему номеру"></i>
                            </label>                            <input id="text" type="text" class="form-control" value="${text}" name="text"/>
                        </div>

                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Найти</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>

<div id="confirm-dialog" class="modal fade" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Разовое посещение</h4>
            </div>
            <form action="${contextPath}/company/single-attendance" method="POST">
                <div class="modal-body">

                    <div class="form-group">
                        <label>Цена:</label>
                        <input id="price" type="number" step="0.01" min="0.01" max="100000000"
                               class="form-control" name="price" required/>
                    </div>

                    <div class="form-group">
                        <label>Тренер:</label>
                        <select name="trainerId" class="browser-default">
                            <option value="">Без тренера</option>
                            <c:forEach var="trainer" items="${trainers}">
                                <option value="${trainer.id}">${trainer.name} ${trainer.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Комментарий:</label>
                        <textarea name="comment" rows="2"></textarea>
                    </div>
                </div>
                <div class="modal-footer text-right">
                    <button type="button" class="btn btn-default waves-effect waves-light" data-dismiss="modal">Отмена
                    </button>
                    <button type="submit" class="btn btn-default waves-effect waves-light">Отметить
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
