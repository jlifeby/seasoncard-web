<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${trainers.size() == 0}">
                Список тренеров пуст.
            </c:if>
            <c:if test="${trainers.size() != 0}">
                <div class="card-panel">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>ФИО</th>
                                <th>Дата рождения</th>
                                <th>Телефон/Email</th>
                                <th>Публ.</th>
                                <th>Актив.</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="trainer" items="${trainers}" varStatus="loop">
                                <tr class="${trainer.active ? 'table-success' : ''}">
                                    <td>${loop.index + 1}</td>
                                    <td style="max-width: 250px">
                                        <a href="${contextPath}/company/trainers/${trainer.id}/detail">
                                                ${trainer.name} ${trainer.lastName}
                                        </a>
                                    </td>
                                    <td>
                                        <joda:format value="${trainer.birthday}" style="M-"
                                                     dateTimeZone="Europe/Minsk"/>
                                    </td>
                                    <td>
                                            ${trainer.phone}
                                    </td>
                                    <td class="text-xs-center">
                                        <c:if test="${trainer.published}">
                                            <i class="material-icons">check</i>
                                        </c:if>
                                    </td>
                                    <td class="text-xs-center">
                                        <c:if test="${trainer.active}">
                                            <i class="material-icons">check</i>
                                        </c:if>
                                    </td>
                                    <td><a href="${contextPath}/company/trainers/${trainer.id}/detail"
                                           class="secondary-content"><i class="material-icons">send</i></a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="col-md-4">
            <c:if test="${company.currentTariff.dead}">
                <jsp:include page="../components/not-avalible-card.jsp"/>
            </c:if>
            <c:if test="${!company.currentTariff.dead}">
                <div class="card">
                    <div class="card-header white-text">
                        Работа с тренерами
                    </div>
                    <div class="card-block text-xs-center">
                        <%--<h5>Работа с тренерами</h5>--%>

                        <a href="${contextPath}/company/trainers/new" class="btn btn-default">Добавить тренера</a>
                        <br>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
