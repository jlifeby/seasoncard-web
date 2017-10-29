<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .popover {
        background-color: #006064;
    }

    .popover.bottom .arrow:after {
        border-bottom-color: #006064;
    }

    .popover-content {
        background-color: #006064;
        border-color: #006064;
    }

    .popover-content a:hover,
    .popover-content a:focus {
        color: #80deea;
    }

    .up {
        position: absolute;
        width: 0px;
        height: 0px;
        border-style: inset;
        border-width: 0 200px 200px 0;
        border-color: transparent #00bcd4 transparent transparent;
        /* float: right; */
        position: absolute;
        top: 0;
        right: 0;
        transform: rotate(360deg);
        -ms-transform: rotate(360deg);
        -moz-transform: rotate(360deg);
        -webkit-transform: rotate(360deg);
        -o-transform: rotate(360deg);
    }

    .up p {
        text-align: center;
        top: 70px;
        left: 120px;
        position: relative;
        width: 70px;
        font-size: 18px;
        color: #ffffff;
    }
</style>

<sec:authorize access="isAnonymous()">
    <!-- Google Code for Conversion Page
    In your html page, add the snippet and call
    goog_report_conversion when someone clicks on the
    chosen link or button. -->
    <script type="text/javascript">
        /* <![CDATA[ */
        goog_snippet_vars = function () {
            var w = window;
            w.google_conversion_id = 950399361;
            w.google_conversion_label = "tXI_CPmi7GoQgeOXxQM";
            w.google_remarketing_only = false;
        };
        // DO NOT CHANGE THE CODE BELOW.
        goog_report_conversion = function (url) {
            goog_snippet_vars();
            window.google_conversion_format = "3";
            var opt = {};
            opt.onload_callback = function () {
                if (typeof(url) != 'undefined') {
                    window.location = url;
                }
            };
            var conv_handler = window['google_trackConversion'];
            if (typeof(conv_handler) == 'function') {
                conv_handler(opt);
            }
        }
        /* ]]> */
    </script>
    <script type="text/javascript" src="//www.googleadservices.com/pagead/conversion_async.js"></script>
</sec:authorize>

<%--<div class="up">--%>
<%--<p>Бета Версия<p>--%>
<%--</div>--%>

<!-- Navigation -->
<nav class="navbar user-navbar z-depth-2 light-blue">
    <div class="container ">

        <sec:authorize access="isAnonymous()">
            <ul class="nav navbar-right navbar-nav">
                    <%--<li><a class="waves-effect waves-light" href="${contextPath}/registration">Зарегистрироваться--%>
                    <%--<i class="fa fa-lock after"></i></a></li>--%>
                <li><a class="waves-effect waves-light" href="${contextPath}/registration-company"
                       onclick="goog_report_conversion ('${contextPath}/registration-company')">
                    Зарегистрировать свою компанию
                        <%--<i class="fa fa- after"></i>--%>
                </a></li>

                <li><a class="waves-effect waves-light" href="${contextPath}/login">Вход для пользователей
                    <i class="fa fa-lock after"></i></a></li>
            </ul>
        </sec:authorize>

        <sec:authorize access="isAuthenticated()">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <sec:authorize access="hasRole('ROLE_COMPANY')">
                    <a id="btn-org" href="${contextPath}/company/profile"
                       class="navbar-brand waves-effect waves-light">${company.name}</a>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <a id="btn-org" href="${contextPath}/account"
                       class="navbar-brand waves-effect waves-light">${currentUser.name} ${currentUser.lastName}
                        <span class="label label-primary">${currentCard.cardUUID}</span></a>

                    <c:if test="${not currentUser.isChangedInitPassword()}">
                    <span style="display: inline-block; padding-top: 13px; cursor: pointer;"
                          data-container="body" data-toggle="popover" data-placement="bottom"
                          data-content="Рекомендуем изменить начальный пароль. <a href='/account/change-password'>Изменить</a>">
                        <i class="material-icons" style="color: red">security</i>
                    </span>
                    </c:if>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a id="btn-org" href="${contextPath}/account"
                       class="navbar-brand waves-effect waves-light">Администратор ${currentUser.name} ${currentUser.lastName}</a>
                </sec:authorize>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-right navbar-nav">
                        <%--<sec:authorize access="hasRole('ROLE_SYSTEM')">--%>
                        <%--<li><a href="${contextPath}/system">СИСТЕМ</a></li>--%>
                        <%--</sec:authorize>--%>
                        <%--<sec:authorize access="hasRole('ROLE_USER')">--%>
                        <%--<li><a class="waves-effect waves-light" href="${contextPath}/user/abonnements">Мои--%>
                        <%--абонементы</a></li>--%>
                        <%--</sec:authorize>--%>
                    <sec:authorize access="hasRole('ROLE_COMPANY')">
                        <li><a class="waves-effect waves-light" href="${contextPath}/company/profile">Профиль
                            компании</a></li>
                    </sec:authorize>
                    <li><a class="waves-effect waves-light" href="${contextPath}/account">Аккаунт</a></li>
                    <li><a class="waves-effect waves-light" href="${contextPath}/j_spring_security_logout">Выйти</a>
                    </li>
                </ul>
            </div>
        </sec:authorize>
    </div>
</nav>

<style>
    .main-menu .navbar-nav > li > a,
    .main-menu .navbar-nav > li > .dropdown > a {
        padding: 10px 15px;
        background-color: #03a9f4;
        line-height: 20px;
    }

    .main-menu .navbar-nav > li > a:hover,
    .main-menu .navbar-nav > li > a:focus,
    .main-menu .navbar-nav > li > .dropdown > a:hover,
    .main-menu .navbar-nav > li > .dropdown > a:focus {
        background-color: #03a9f4;
    }

    .main-menu.navbar .navbar-nav > .active > a,
    .main-menu.navbar .navbar-nav > .active > a:hover,
    .main-menu.navbar .navbar-nav > .active > a:focus,
    .main-menu.navbar .navbar-nav > .active > .dropdown > a,
    .main-menu.navbar .navbar-nav > .active > .dropdown > a:hover,
    .main-menu.navbar .navbar-nav > .active > .dropdown > a:focus {
        background-color: #5a95f5;
    }

    .main-menu.navbar .navbar-toggle {
        border-color: #03a9f4;
    }

    .main-menu.navbar .navbar-toggle .icon-bar {
        background-color: #03a9f4;
    }

    .main-menu.navbar .mobile-icon {
        display: inline-block;
        vertical-align: middle;
        line-height: 2pc;
    }

    .main-menu.navbar .mobile-icon .material-icons {
        font-size: 36px;
    }

    .main-menu.navbar .mobile-text {
        display: inline-block;
        vertical-align: middle;
        margin-left: 5px;
        line-height: 15px;
    }

    .dropdown:hover .dropdown-menu {
        display: block;
        margin-top: 0;
    / / remove the gap so it doesn 't close
    }

</style>


<nav class="navbar main-menu">
    <div class="container-fluid">
        <div class="navbar-header col-md-2">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="waves-effect waves-light" href="${contextPath}/">
                <img src="${contextPath}/images/kartka_logo.png" class="img-responsive"
                     style="max-width: 200px; max-height: 50px;" alt="">
            </a>
        </div>
        <div class="col-md-1"></div>
        <div class="collapse navbar-collapse col-md-offset-2" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/' ? 'class="active"' : ''}>
                        <a href="${contextPath}/" class="btn btn-default waves-effect waves-light">Главная</a>
                    </li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/companies') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/companies" class="btn waves-effect waves-light">Компании</a></li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/cards') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/cards" class="btn waves-effect waves-light">Карты</a></li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/users') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/users" class="btn waves-effect waves-light">Пользователи</a></li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/sms') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/sms" class="btn waves-effect waves-light">SMS рассылка</a></li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/tariffs') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/tariffs" class="btn waves-effect waves-light">Тарифы</a></li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/admin/analytics') ? 'class="active"' : ''}>
                        <a href="${contextPath}/admin/analytics" class="btn waves-effect waves-light">Аналитика</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_COMPANY')">
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/' ? 'class="active"' : ''}>
                        <a href="${contextPath}/" class="btn btn-default waves-effect waves-light">Главная</a>
                    </li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/products') ? 'class="active"' : ''}>
                        <a href="${contextPath}/company/products" class="btn waves-effect waves-light">Абонементы</a>
                    </li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/promotions') ? 'class="active"' : ''}>
                        <a href="${contextPath}/company/promotions" class="btn waves-effect waves-light">Скидки и
                            акции</a>
                    </li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/trainers') ? 'class="active"' : ''}>
                        <a href="${contextPath}/company/trainers" class="btn waves-effect waves-light">Тренеры</a>
                    </li>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/cards') ? 'class="active"' : ''}>
                        <a href="${contextPath}/company/cards" class="btn waves-effect waves-light">Клиенты</a>
                    </li>
                    <%--<li class="dropdown ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/info') ? 'active' : ''}">--%>
                    <%--<a href="${contextPath}/company/info" class="btn dropdown-toggle waves-effect waves-light">Компания <span class="caret"></span></a>--%>
                    <%--<a href="#" class="btn dropdown-toggle waves-effect waves-light">Компания <span class="caret"></span></a>--%>
                    <%--<ul class="dropdown-menu" role="menu">--%>
                    <%--<li><a href="${contextPath}/company/offices">Филиалы</a></li>--%>
                    <%--<li><a href="${contextPath}/company/directions">Направления</a></li>--%>
                    <%--<li><a href="${contextPath}/company/groups">Группы</a></li>--%>
                    <%--<li><a href="${contextPath}/company/employees">Сотрудники</a></li>--%>
                    <%--</ul>--%>
                    <%--</li>--%>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/company/reports') ? 'class="active"' : ''}>
                        <a href="${contextPath}/company/reports/products"
                           class="btn waves-effect waves-light">Отчеты</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/' ? 'class="active"' : ''}>
                        <a href="${contextPath}/" class="btn btn-default waves-effect waves-light">Главная</a>
                    </li>
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/features' ? 'class="active"' : ''}>
                        <a href="${contextPath}/features" class="btn waves-effect waves-light">Решение</a></li>
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/tariffs' ? 'class="active"' : ''}>
                        <a href="${contextPath}/tariffs" class="btn waves-effect waves-light">Тарифы</a></li>
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/about' ? 'class="active"' : ''}>
                        <a href="${contextPath}/about" class="btn waves-effect waves-light">О Нас</a></li>
                    <li>
                        <a href="${contextPath}/apps" class="light-blue-text"
                           style="background-color: transparent; padding: 7px 15px;">
                            <span class="mobile-icon"><i class="material-icons">phone_android</i></span>
                            <span class="mobile-text">Скачать мобильное<br>приложение</span>
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/' ? 'class="active"' : ''}>
                        <a href="${contextPath}/" class="btn btn-default waves-effect waves-light">Мои абонементы</a>
                    </li>
                    <%--<li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/products') ? 'class="active"' : ''}>--%>
                    <%--<a href="${contextPath}/products" class="btn waves-effect waves-light">Каталог</a></li>--%>
                    <li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/user/company') ? 'class="active"' : ''}>
                        <a href="${contextPath}/user/company" class="btn waves-effect waves-light">Абонементы
                            компании</a></li>
                    <%--<li ${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/companies') ? 'class="active"' : ''}>--%>
                    <%--<a href="${contextPath}/companies" class="btn waves-effect waves-light">О компании</a></li>--%>
                    <li ${requestScope['javax.servlet.forward.request_uri'] == '/about' ? 'class="active"' : ''}>
                        <a href="${contextPath}/about" class="btn waves-effect waves-light">О Нас</a></li>
                    <li>
                        <a href="${contextPath}/apps" class="light-blue-text"
                           style="background-color: transparent; padding: 7px 15px;">
                            <span class="mobile-icon"><i class="material-icons">phone_android</i></span>
                            <span class="mobile-text">Скачать мобильное<br>приложение</span>
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>