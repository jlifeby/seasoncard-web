<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">

            <h6>Действия</h6>

            <div>
                <button type="button" class="btn btn-default btn-sm btn-block" style="margin-left: 0;">Добавить карты</button>
            </div>
        </div>
        <div class="col-md-10">
            <div class="row">
                <div class="col-md-12">
                    <div class="card-panel">
                        <h6> Статистика </h6>
                        <p> Всего карт: ${countOfAllCard} </p>
                        <p> Свободных: ${countOfFreeCard} </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="block/cards-table.jsp"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="../contents/blocks/pagination.jsp">
                        <jsp:param name="urlPath" value="${contextPath}/admin/cards?sort=free"/>
                        <jsp:param name="totalPages" value="${totalPages}"/>
                        <jsp:param name="page" value="${page}"/>
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
</div>
