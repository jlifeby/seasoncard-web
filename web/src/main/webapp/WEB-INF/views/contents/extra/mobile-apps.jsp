<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    section .btn {
        padding: 13px;
        margin: 0 5px;
    }

    section img.img-app-btn {
        display: inline-block;
        max-width: 8rem;
    }
</style>

<div class="container">

    <hr class="between-sections">

    <section class="section feature-box">

        <h1 class="section-heading">SeasonCard Business</h1>

        <div class="row features-small">

            <div class="col-md-3 mb-r center-on-small-only offset-md-1">
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app"
                   target="_blank">
                    <img src="${contextPath}/images/features/abons_business.png" alt="" class="z-depth-0">
                </a>
            </div>

            <div class="col-md-7">

                <p style="font-size: 1.5em;font-weight: 300;">
                    Мобильное приложение для операционной системы Android позволит Вам управлять Вашими
                    абонементами, быстро и просто контролировать посещения, создать клиентскую базу со всей необходимой
                    информацией, отслеживать различную статистику и быть всегда на связи с клиентами.</p>

                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app" target="_blank">
                    <img src="${contextPath}/images/icon/google-play-badge.png" alt="Get it on Google Play"
                         class="z-depth-0 img-fluid img-app-btn"/>
                </a>
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app"
                   class="btn btn-default waves-effect waves-light" target="_blank">
                    СКАЧАТЬ БЕСПЛАТНО
                </a>
            </div>
        </div>

    </section>

    <hr class="between-sections">

    <section class="section feature-box">

        <h1 class="section-heading">SeasonCard</h1>

        <div class="row features-small">

            <div class="col-sm-3 push-sm-7 mb-r center-on-small-only offset-sm-1">
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.client.app"
                   target="_blank">
                    <img src="${contextPath}/images/features/client_main.png" alt="" class="z-depth-0">
                </a>
            </div>

            <div class="col-sm-7 pull-sm-3 center-on-small">

                <p style="font-size: 1.5em;font-weight: 300;">
                    Мобильное приложение позволит Вам управлять Вашими абонементами, просматривать
                    количество оставшихся посещений, актуальные контакты ваших любимых заведений, а также содержит
                    электронную версию Вашей карты, которая будет всегда под рукой прямо в Вашем телефоне.</p>

                <div>
                    <a href="https://play.google.com/store/apps/details?id=by.kartka.android.client.app"
                       target="_blank">
                        <img src="${contextPath}/images/icon/google-play-badge.png" alt="Get it on Google Play"
                             class="z-depth-0 img-fluid img-app-btn"/>
                    </a>
                    <a href="https://play.google.com/store/apps/details?id=by.kartka.android.client.app"
                       class="btn btn-default light-blue btn-xlg waves-effect waves-light" target="_blank">
                        СКАЧАТЬ БЕСПЛАТНО
                    </a>
                </div>
                <div style="margin-top: 10px">
                    <a href="https://itunes.apple.com/ru/app/kartka/id1104274153"
                       target="_blank">
                        <img src="${contextPath}/images/icon/app-store-badge.svg" alt="Get it on App Store"
                             class="z-depth-0 img-fluid img-app-btn"/>
                    </a>
                    <a href="https://itunes.apple.com/ru/app/kartka/id1104274153"
                       class="btn btn-default light-blue btn-xlg waves-effect waves-light" target="_blank">
                        СКАЧАТЬ БЕСПЛАТНО
                    </a>
                </div>
            </div>
        </div>

    </section>

    <hr class="between-sections">

</div>