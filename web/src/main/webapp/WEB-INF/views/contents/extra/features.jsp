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

<header class="header-image light-blue">
    <div class="headline">
        <div class="container">
            <h1 style="font-size: 3.5em; color: #fff; font-weight: 400">SeasonCard</h1>

            <h2 style="font-size: 2.2em; color: #fff;">Новые возможности для развития вашего бизнеса</h2>
        </div>
    </div>
</header>

<div class="container">
    <section class="section feature-box">

        <h1 class="section-heading">Решение</h1>

        <div class="row features-small">

            <div class="col-sm-4 push-sm-6 mb-r center-on-small-only offset-sm-1">
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app"
                   class="pull-right" target="_blank">
                    <img src="${contextPath}/images/features/desktop.jpg" alt="" class="z-depth-0" style="margin-bottom: 10px">
                </a>
            </div>

            <div class="col-sm-6 pull-sm-4 center-on-small">
                <p class="lead">
                    Мы предлагаем доступную и современную онлайн-систему автоматизации и учёта деятельности организаций
                    услуг и сервиса включающую web-версию, <a href="${contextPath}/apps" target="_blank">android-приложение</a>,
                    <a href="${contextPath}/apps" target="_blank">iOS-приложение</a>, виртуальную и
                    <a href="${contextPath}/card-design" target="_blank">стильную пластиковую карту</a> с NFC-чипом.
                </p>
            </div>

        </div>

    </section>

    <hr class="between-sections">

    <section class="section feature-box">

        <h1 class="section-heading">Для вашего бизнеса</h1>

        <div class="row features-small">

            <div class="col-md-3 mb-r center-on-small-only offset-md-1">
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app"
                   target="_blank">
                    <img src="${contextPath}/images/features/abons_business.png" alt="" class="z-depth-0">
                </a>
            </div>

            <div class="col-md-7">

                <p class="lead">Простая и комфортная система автоматизации и учёта для вас, ваших сотрудников, и ваших
                    клиентов;</p>

                <p class="lead">Удобная клиентская база со всей необходимой информацией;</p>

                <p class="lead">Возможность дополнительной рекламы услуг через сайт и мобильное приложение;</p>

                <p class="lead">Экономия на полиграфии, отказавшись от бумажных и картонных абонементов;</p>

                <p class="lead">Отслеживание статистики (посещаемости и использования тех или иных услуг с
                    абонированием);</p>

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

    <section class="section feature-box" style="margin-bottom: 50px">

        <h1 class="section-heading">Для клиентов вашего бизнеса</h1>

        <div class="row features-small">

            <div class="col-sm-3 push-sm-7 mb-r center-on-small-only offset-sm-1">
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.client.app"
                   target="_blank">
                    <img src="${contextPath}/images/features/client_main.png" alt="" class="z-depth-0">
                </a>
            </div>

            <div class="col-sm-7 pull-sm-3 center-on-small">

                <p class="lead">Быстро и эффективно управлять своими абонементами;</p>

                <p class="lead">Обезопасить себя от потери абонемента (даже в случае
                    утраты карты вы всегда сможете использовать свой мобильный телефон);</p>

                <p class="lead">Online информация о количестве посещений (тренажерного
                    зала, студии, клуба, фитнес-центра, бассейна и тд., в мобильном приложении или на сайте);</p>

                <p class="lead">Использовать одну карту (приложение) для всех Ваших абонементов.</p>

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
</div>
