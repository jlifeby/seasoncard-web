<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    footer img.img-app-btn {
        display: inline-block;
        max-width: 8rem;
    }
</style>

<footer class="page-footer center-on-small-only">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 offset-md-1 mobile">
                <h5>SeasonCard для Бизнеса</h5>

                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app" target="_blank">
                    <img src="${contextPath}/images/icon/google-play-badge.png" alt="Get it on Google Play"
                         class="z-depth-0 img-fluid img-app-btn"/>
                </a>

                <h5 style="margin-top: 10px;">SeasonCard для Посетителей</h5>
                <a href="https://play.google.com/store/apps/details?id=by.kartka.android.client.app"
                   target="_blank">
                    <img src="${contextPath}/images/icon/google-play-badge.png" alt="Get it on Google Play"
                         class="z-depth-0 img-fluid img-app-btn"/>
                </a>
                <a href="https://itunes.apple.com/ru/app/kartka/id1104274153"
                   target="_blank">
                    <img src="${contextPath}/images/icon/app-store-badge.svg" alt="Get it on App Store"
                         class="z-depth-0 img-fluid img-app-btn"/>
                </a>
            </div>

            <hr class="hidden-md-up">

            <div class="col-md-3">
                <h5 class="title">Меню</h5>
                <ul>
                    <li><a href="${contextPath}/about">О нас</a></li>
                    <li><a href="#" data-toggle="modal" data-target="#contact-form">
                        Связаться с нами</a></li>
                    <li><a href="${contextPath}/features">Возможности</a></li>
                    <li><a href="${contextPath}/tariffs">Тарифы</a></li>
                    <li><a href="${contextPath}/apps">Мобильное приложение</a></li>
                    <li><a href="${contextPath}/user-agreement">Пользовательское соглашение</a></li>
                </ul>
            </div>

            <hr class="hidden-md-up">

            <div class="col-md-3 text-xs-center">
                <h5 class="title">Мы в социальных сетях</h5>

                <div class="social-section">
                    <ul>
                        <li><a target="_blank" href="https://vk.com/seasoncard" class="btn-floating btn-small btn-vk"><i
                                class="fa fa-vk"> </i></a></li>
                        <li><a target="_blank" href="https://www.facebook.com/seasoncardclub"
                               class="btn-floating btn-small btn-fb"><i class="fa fa-facebook"> </i></a></li>
                        <li><a target="_blank" href="https://twitter.com/seasoncardclub"
                               class="btn-floating btn-small btn-tw"><i class="fa fa-twitter"> </i></a></li>
                        <li><a target="_blank" href="https://www.instagram.com/seasoncard"
                               class="btn-floating btn-small btn-ins"><i class="fa fa-instagram"> </i></a></li>
                    </ul>
                </div>

                <p>
                    +1234567 <br/>
                    info@test.com
                </p>
                <p></p>


            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container-fluid">
            © 2015 - 2017 Copyright <a href="http://seasoncard.by">SeasonCard.by</a>
            <span>Версия: @war_build_version@</span>
        </div>
    </div>
</footer>

<div class="clearfix"></div>


<!-- Modal Contact -->
<div class="modal fade modal-ext" id="contact-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Написать нам</h4>
            </div>
            <div class="modal-body">
                <div class="md-form">
                    <i class="fa fa-user prefix"></i>
                    <input id="contact-name" type="text" name="name" class="form-control validate" required>
                    <label for="contact-name">Ваше имя</label>
                </div>

                <div class="md-form">
                    <i class="fa fa-envelope prefix"></i>
                    <input id="contact-mail" type="email" name="email" class="form-control validate" required>
                    <label for="contact-mail">Ваш e-mail</label>
                </div>

                <div class="md-form">
                    <i class="fa fa-tag prefix"></i>
                    <input id="contact-subject" type="text" name="subject" class="form-control validate" required>
                    <label for="contact-subject">Тема</label>
                </div>

                <div class="md-form">
                    <i class="fa fa-pencil prefix"></i>
                    <textarea id="contact-text" class="md-textarea" name="message" required></textarea>
                    <label for="contact-text">Сообщение</label>
                </div>

                <div class="text-xs-center">
                    <button type="submit" class="btn btn-primary waves-effect waves-light">Отправить</button>
                    <div class="call">
                        <p>Адрес для связи: <span class="cf-phone"><i
                                class="fa fa-envelope"> info@test.com</i></span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
