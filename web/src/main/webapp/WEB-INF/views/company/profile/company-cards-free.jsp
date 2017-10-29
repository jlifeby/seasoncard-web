<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-10 offset-md-1">
            <div class="row">
                <div class="col-md-12">
                    <div class="card-panel">
                        <h3>Статистика по картам</h3>
                        <p> Всего карт: ${countOfAllCard} </p>
                        <p> Свободных: ${countOfFreeCard} </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="../../admin/block/cards-table.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
