<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card-panel">
    <h6>Все карты</h6>
    <table class="table table-hover table-bordered table-condensed" data-toggle="table" data-sort-name="#"
           data-sort-order="acs">
        <tr>
            <th data-field="#" data-sortable="true">#</th>
            <th data-field="uuid" data-sortable="true">Card UUID</th>
            <th data-field="nfcuuid" data-sortable="true">NFC UUID</th>
            <th data-field="status" data-sortable="true">Статус</th>
            <th data-field="delivered" data-sortable="true">В компании</th>
        <tr>
            <c:forEach var="card" items="${cards}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${card.cardUUID}</td>
            <td>${card.nfcUUID}</td>
            <td>
                <c:choose>
                    <c:when test="${card.free}">
                        Свободна
                    </c:when>
                    <c:otherwise>
                        Зарегистрирована
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${empty card.deliveredToCompany ? "Нет" : "Да"}
            </td>
        </tr>
        </c:forEach>
    </table>
</div>