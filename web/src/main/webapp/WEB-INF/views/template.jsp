<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru_RU" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">

    <title>SeasonCard</title>

    <meta name="verify-reformal" content="8e452704c81784a3ac2cc02d"/>

    <%--<meta name="keywords"--%>
          <%--content="Электронный абонемент, онлайн абонемент, карта, картка, картка бай, всё в одном, учёт посещений, Минск">--%>
    <%--<meta name="description" content="Электронная система абонементов «Картка.бай» объединяет в себе онлайн сервис и--%>
    <%--мобильное приложение учета посещений фитнес-клубов, спортивных залов, бассейнов, спа-салонов и многих других заведений.">--%>

    <meta name="keywords"
          content="Электронный абонемент, система управления абонементами, учёт посещений, CRM, автоматизация фитнес-клуба, онлайн абонемент, картка бай, сизонкард, сизон кард, seasoncard, season card">
    <meta name="description" content="Электронная система абонементов «SeasonCard» объединяет в себе онлайн сервис и
    мобильное приложение учета посещений фитнес-клубов, спортивных залов, бассейнов, спа-салонов и многих других заведений.">

    <%--<meta property="og:url" content="https://events.by/event/178479/">--%>
    <%--<meta property="og:type" content="activity">--%>
    <%--<meta property="og:title" content="${event.name} / События на 'site'">--%>
    <%--<meta property="og:image" content="https://events.by/.../poster_event_178479.png">--%>
    <%--<meta property="og:image:secure_url" content="https://events.by/.../poster_event_178479.png">--%>
    <%--<link rel="image_src" href="https://events.by/.../poster_event_178479.png">--%>
    <%--<meta property="og:description" content="${event.description}">--%>


    <meta name="author" content="UNDEADgtr">
    <meta class="viewport" name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Favicon -->
    <link rel="shortcut icon" href="${contextPath}/images/favicon.ico">

    <!-- Material Design Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Material Design Bootstrap -->
    <link href="${contextPath}/css/mdb4free/mdb.css" rel="stylesheet">
    <link href="${contextPath}/css/mdb4free/mdb-restyle.css" rel="stylesheet">

    <!-- Croppic -->
    <link href="${contextPath}/css/cropper.min.css" rel="stylesheet">

    <!-- Template style -->
    <link href="${contextPath}/css/features.css" rel="stylesheet">
    <link href="${contextPath}/css/style.css" rel="stylesheet">

    <!-- JQuery -->
    <script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
    <meta name="google-site-verification" content="YaH6bzq4dS8k8crjcyDzneioxra-79rpVHVamO7UnWg"/>

</head>

<body id="boxed-bg" class="boxed">
<a href="https://github.com/jlifeby/seasoncard-web"><img
        style="position: absolute; top: 0; left: 0; border: 0;z-index: 999"
        src="https://camo.githubusercontent.com/121cd7cbdc3e4855075ea8b558508b91ac463ac2/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f6c6566745f677265656e5f3030373230302e706e67"
        alt="Fork me on GitHub"
        data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_left_green_007200.png"></a>

<div class="page-box">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="main"/>
</div>

<tiles:insertAttribute name="footer"/>

<!-- SCRIPTS -->

<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>

<!-- Material Design Bootstrap -->
<script type="text/javascript" src="${contextPath}/js/mdb.js"></script>

<!-- Croppic -->
<script type="text/javascript" src="${contextPath}/js/cropper.min.js"></script>

<!-- Ellipsis -->
<script type="text/javascript" src="${contextPath}/js/autoellipsis.min.jquery.js"></script>

<!-- Main -->
<script type="text/javascript" src="${contextPath}/js/moment/moment.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/moment/ru.js"></script>
<script type="text/javascript" src="${contextPath}/js/main.js"></script>

<c:if test="${not empty flashMessage}">
    <script>showMessage('${flashMessage}');</script>
</c:if>

<c:if test="${not empty errorMessage}">
    <script>showError('${errorMessage}');</script>
</c:if>

<c:set var="isBeta" value="${'@isBeta@'}"/>

<c:if test="${isBeta == 'true'}">
    <jsp:include page="components/metric.jsp"/>
    <jsp:include page="components/copiny.jsp"/>
</c:if>

</body>
</html>