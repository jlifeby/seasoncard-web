<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:setLocale value="ru_RU" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">

    <title>SeasonCard</title>

    <meta name="verify-reformal" content="8e452704c81784a3ac2cc02d"/>

    <meta name="keywords"
          content="Электронный абонемент, система управления абонементами, учёт посещений, CRM, автоматизация фитнес-клуба, онлайн абонемент, картка бай, сизонкард, сизон кард, seasoncard, season card">
    <meta name="description" content="Электронная система абонементов «SeasonCard» объединяет в себе онлайн сервис и
    мобильное приложение учета посещений фитнес-клубов, спортивных залов, бассейнов, спа-салонов и многих других заведений.">

    <meta name="author" content="UNDEADgtr">
    <meta class="viewport" name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Favicon -->
    <link rel="shortcut icon" href="${contextPath}/images/favicon.ico">

    <!-- Material Design Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/css/mdb4free/bootstrap.min.css" rel="stylesheet">

    <!-- Material Design Bootstrap -->
    <link href="${contextPath}/css/mdb4free/mdb.css" rel="stylesheet">

    <!-- JQuery -->
    <script type="text/javascript" src="${contextPath}/js/mdb4free/jquery-3.2.1.min.js"></script>

</head>

<body class="hidden-sn ${widgetSetting.primaryPalette}">

<style>
    html {
        position: relative;
        min-height: 100%;
    }

    body {
        margin-bottom: 50px;
    }

    footer {
        position: absolute;
        bottom: 0;
        width: 100%;
    }
</style>

<header>

    <%--<ul id="slide-out" class="side-nav sn-bg-1 custom-scrollbar">--%>
    <%--<li>--%>
    <%--<div class="logo-wrapper waves-light">--%>
    <%--<a href="#"><img src="http://mdbootstrap.com/img/logo/mdb-transparent.png"--%>
    <%--class="img-fluid flex-center"></a>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<ul class="social">--%>
    <%--<li><a class="icons-sm fb-ic"><i class="fa fa-facebook"> </i></a></li>--%>
    <%--<li><a class="icons-sm pin-ic"><i class="fa fa-pinterest"> </i></a></li>--%>
    <%--<li><a class="icons-sm gplus-ic"><i class="fa fa-google-plus"> </i></a></li>--%>
    <%--<li><a class="icons-sm tw-ic"><i class="fa fa-twitter"> </i></a></li>--%>
    <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<ul class="collapsible collapsible-accordion">--%>
    <%--<li><a class="collapsible-header waves-effect arrow-r"><i class="fa fa-eye"></i> About<i--%>
    <%--class="fa fa-angle-down rotate-icon"></i></a>--%>
    <%--<div class="collapsible-body">--%>
    <%--<ul>--%>
    <%--<li><a href="#" class="waves-effect">Introduction</a>--%>
    <%--</li>--%>
    <%--<li><a href="#" class="waves-effect">Monthly meetings</a>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</li>--%>

    <%--<div class="sidenav-bg mask-strong"></div>--%>
    <%--</ul>--%>

    <nav class="navbar fixed-top navbar-toggleable-md navbar-dark scrolling-navbar double-nav">

        <%--<div class="float-left">--%>
        <%--<a href="#" data-activates="slide-out" class="button-collapse"><i class="fa fa-bars"></i></a>--%>
        <%--</div>--%>

        <div class="breadcrumb-dn mr-auto" style="display: block">
            <p>${widgetCompany.name}</p>
        </div>
        <%--<ul class="nav navbar-nav nav-flex-icons ml-auto">--%>
            <%--<li class="nav-item">--%>
                <%--<a class="nav-link"><i class="fa fa-long-arrow-left"></i></a>--%>
            <%--</li>--%>
        <%--</ul>--%>
    </nav>
</header>

<main class="pt-3 pb-3">
    <div class="container-fluid">
        <div class="m-3">

            <div class="text-center">

                <img src="${contextPath}${company.logoPath}" class="img-fluid z-depth-2 rounded-circle"
                     alt="${company.name}"
                     style="display: initial; max-width: 100px;">

                <h3 class="mt-1">${widgetSetting.title}</h3>
                <hr class="mt-1 mb-2">
            </div>

            <p class="text-center">${widgetSetting.description}</p>

            <br>

            <c:if test="${not empty flashMessage}">
                <blockquote class="blockquote bq-success">
                    <p class="bq-title text-center"><i class="fa fa-smile-o"></i></p>
                    <p class="text-center">Ваша заявка отправлена!</p>
                </blockquote>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <blockquote class="blockquote bq-danger">
                    <p class="bq-title text-center"><i class="fa fa-smile-o"></i></p>
                    <p class="text-center">Ошибка заполнения! ${errorMessage}</p>
                </blockquote>
            </c:if>

            <form:form action="${contextPath}/ext/widget/enroll" method="POST" modelAttribute="potentialClientForm">

                <input type="hidden" name="companyId" value="${widgetCompany.id}"/>

                <div class="md-form">
                    <i class="fa fa-user prefix"></i>
                    <form:input type="text" id="name" name="name" path="name" class="form-control" required="true"/>
                    <label for="name">Ваше имя</label>
                    <form:errors path="name" cssClass="required"></form:errors>
                </div>

                <div class="md-form">
                    <i class="fa fa-user prefix"></i>
                    <form:input type="text" id="lastName" name="lastName" path="lastName"  class="form-control"/>
                    <label for="lastName">Фамилия: </label>
                    <form:errors path="lastName" cssClass="required"></form:errors>
                </div>

                <div class="md-form">
                    <i class="fa fa-phone prefix"></i>
                    <form:input type="text" id="phone" name="phone" path="phone" class="form-control" required="true"
                           pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                           title="Введите номер телефона в виде: 375291234567 или 79261234567"/>
                    <label for="phone">Мобильный телефон</label>
                    <form:errors path="phone" cssClass="required"></form:errors>
                </div>

                <div class="md-form">
                    <i class="fa fa-envelope prefix"></i>
                    <form:input type="email" id="email" name="email" path="email" class="form-control"/>
                    <label for="email">Email</label>
                    <form:errors path="email" cssClass="required"></form:errors>
                </div>

                <div class="md-form">
                    <i class="fa fa-pencil prefix"></i>
                    <form:textarea type="text" id="comment" name="comment" path="comment" cols="30" rows="2" maxlength="300"
                              class="md-textarea"></form:textarea>
                    <label for="comment">Комментарий</label>
                    <form:errors path="email" cssClass="required"></form:errors>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-default">Записаться</button>
                    <div class="call mt-2">
                        <p>
                            <span class="cf-phone">${widgetCompany.address.toString()}</span><br/>
                            <c:if test="${not empty widgetCompany.email}">
                                <span class="cf-phone">${widgetCompany.email}</span>
                            </c:if>
                            <c:if test="${not empty widgetCompany.phones[0]}">
                                <span class="cf-phone">+${widgetCompany.phones[0]}</span>
                            </c:if>
                        </p>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</main>

<footer class="page-footer center-on-small-only" style="padding-top: 0;">
    <div class="footer-copyright">
        <div class="container-fluid">
            Работает на <a href="https://seasoncard.by/" target="_blank"> <strong>SeasonCard</strong> </a>
        </div>
    </div>
</footer>


<!-- SCRIPTS -->

<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="${contextPath}/js/mdb4free/tether.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/mdb4free/bootstrap.min.js"></script>

<!-- Material Design Bootstrap -->
<script type="text/javascript" src="${contextPath}/js/mdb4free/mdb.js"></script>

<script type="text/javascript">

    // SideNav init
    $(".button-collapse").sideNav();

    // Custom scrollbar init
    var el = document.querySelector('.custom-scrollbar');
    Ps.initialize(el);

</script>

</body>
</html>