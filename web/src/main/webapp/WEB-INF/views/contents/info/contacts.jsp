<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar-menu.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="card-panel">

                <h3>Контакты</h3>

                <p>Моб. тел (Velcom): ***</p>
                <p>Email: <a href="mailto:info@test.com">info@test.com</a></p>
                <p>Сайт: <a href="https://site.com">https://site.com</a></p>

                <hr>

                <h3>Социальные сети</h3>

                <p><a href="*">vk</a></p>
                <p><a href="*">facebook</a>
                <p><a href="*">instagram</a></p>
                <p><a href="*">twitter</a></p>

                <hr>

                <h3>Юридическая информация</h3>
                <p class="ng-scope">ООО</p>
                <p class="ng-scope">г. Минск</p>
                <p class="ng-scope">УНП</p>
                <p class="ng-scope">Расчетный счет</p><br class="ng-scope">

                <div style="margin-bottom: 30px;"></div>
            </div>
        </div>
    </div>
</div>
