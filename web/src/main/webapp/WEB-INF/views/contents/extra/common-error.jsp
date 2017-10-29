<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="statusCode" value="${pageContext.errorData.statusCode}"/>

<style>
    .navbar.main-menu {
        background-color: #ffffff;
    }
</style>

<nav class="navbar navbar-dark" style="height: 40px"></nav>

<nav class="navbar main-menu" style="margin-bottom: 20px">

    <button class="navbar-toggler hidden-sm-up" type="button" data-toggle="collapse" data-target="#collapseEx2">
        <i class="fa fa-bars"></i>
    </button>

    <div class="container-flued">
        <div class="collapse navbar-toggleable-xs" id="collapseEx2">
            <div class="navbar-brand" style="margin: 0px 30px">
                <a class="waves-effect waves-light" href="${contextPath}/">
                    <img src="${contextPath}/images/kartka_logo.png" class="img-fluid"
                         style="max-width: 200px; max-height: 50px;" alt="">
                </a>
            </div>
        </div>
    </div>
</nav>

<div class="container" style="margin-top: 5rem">
    <div class="row">
        <div class="col-md-6 offset-md-1">
            <h1 style="font-size: 100px; font-weight: bold">${statusCode}</h1>

            <c:if test="${statusCode == 404}">
                <h3>Страница не найдена!</h3>
            </c:if>
            <c:if test="${statusCode == 403}">
                <h3>Доступ запрещен!</h3>
            </c:if>
            <c:if test="${statusCode != 404 and statusCode != 403}">
                <h3>Хм.. Что-то пошло не так!</h3>
            </c:if>
            <a href="/" style="font-size: 20px" class="btn btn-default">На главную</a>

        </div>
        <div class="col-md-4">
            <img class="img-fluid pull-right" src="${contextPath}/images/errorstop.png">
        </div>
    </div>
</div>



