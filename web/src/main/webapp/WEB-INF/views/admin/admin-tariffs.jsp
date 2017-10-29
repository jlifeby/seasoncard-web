<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${promotions.size() == 0}">
                Список акций и скидок пуст.
            </c:if>
            <c:if test="${promotions.size() != 0}">
                <div class="card-panel">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Наименование</th>
                            <th>Цена</th>
                            <th>Max клиентов</th>
                            <th>Бесплатный</th>
                            <th>Активный</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="tariff" items="${tariffs}">
                            <tr>
                                <td style="max-width: 250px">
                                    <a href="${contextPath}/admin/tariffs/${tariff.id}/detail" class="ellipsis"
                                       style="display: block">
                                            ${tariff.name}
                                    </a>
                                </td>
                                <td>
                                    <c:forEach items="${tariff.localizedMonthPrice}" var="localizedMonthPrice"
                                               varStatus="status">
                                        ${localizedMonthPrice.key.localizedName}: ${localizedMonthPrice.value.value} ${localizedMonthPrice.value.currency.description}
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${tariff.maxClients}
                                </td>
                                <td class="text-xs-center">
                                    <c:if test="${tariff.free}">
                                        <i class="material-icons">check</i>
                                    </c:if>
                                </td>
                                <td class="text-xs-center">
                                    <c:if test="${tariff.active}">
                                        <i class="material-icons">check</i>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
        <div class="col-md-4">
            <div class="card-panel">
                <div class="row">
                    <div class="col-lg-12">

                        <h5>Работа с тарифами</h5>

                        <a href="${contextPath}/admin/tariffs/new" class="btn btn-default">Добавить тариф</a>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

