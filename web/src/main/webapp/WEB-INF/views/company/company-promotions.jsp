<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${promotions.size() == 0}">
                Список акций и скидок пуст.
            </c:if>
            <c:if test="${promotions.size() != 0}">
                <div class="card-panel">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Карт.</th>
                                <th>Наименование</th>
                                    <%--<th>Описание</th>--%>
                                <th>Размер</th>
                                <th>Оконч.</th>
                                <th>Публ.</th>
                                <th>Актив.</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="promotion" items="${promotions}">
                                <tr class="${promotion.active ? 'table-success' : ''}">
                                    <td>
                                        <a href="${contextPath}/company/promotions/${promotion.id}/detail">
                                            <img src="${contextPath}${promotion.logoPath}" style="height: 30px;">
                                        </a>
                                    </td>
                                    <td style="max-width: 250px">
                                        <a href="${contextPath}/company/promotions/${promotion.id}/detail">
                                            <p class="ellipsis"
                                               style="margin: 0; font-size: 1.1rem">${promotion.name}</p>
                                        </a>
                                    </td>
                                        <%--<td class="ellipsis" style="max-width: 200px">${promotion.description}</td>--%>
                                    <td>
                                            ${promotion.promotionValueAsString}
                                    </td>
                                    <td>
                                        <joda:format value="${promotion.endDate}" style="M-"
                                                     dateTimeZone="Europe/Minsk"/>
                                    </td>
                                    <td class="text-xs-center">
                                        <c:if test="${promotion.published}">
                                            <i class="material-icons">check</i>
                                        </c:if>
                                    </td>
                                    <td class="text-xs-center">
                                        <c:if test="${promotion.active}">
                                            <i class="material-icons">check</i>
                                        </c:if>
                                    </td>
                                    <td><a href="${contextPath}/company/promotions/${promotion.id}/detail"
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
                        Работа со скидками и акциями
                    </div>
                    <div class="card-block text-xs-center">
                        <a href="${contextPath}/company/promotions/new" class="btn btn-default">Добавить акцию</a>
                        <br>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

