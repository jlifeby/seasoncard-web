<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<footer class="page-footer light-blue" style="padding-top: 15px;">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-1">
                <p class="white-text center-on-small-only">SeasonCard</p>

                <p class="white-text center-on-small-only">Это новое решение в управлении Вашими абонементами.</p>
            </div>
            <hr class="hidden-lg hidden-md">

            <div class="col-md-3">
                <p class="white-text center-on-small-only">Меню</p>
                <ul>
                    <li><a href="${contextPath}/about" class="grey-text text-lighten-3">О нас</a></li>
                    <li><a href="#" class="grey-text text-lighten-3" data-toggle="modal" data-target="#contact-form">
                        Связаться с нами</a></li>
                    <li><a href="${contextPath}/features" class="grey-text text-lighten-3">Возможности</a></li>
                    <li><a href="${contextPath}/tariffs" class="grey-text text-lighten-3">Тарифы</a></li>
                    <li><a href="${contextPath}/apps" class="grey-text text-lighten-3">Мобильное приложение</a></li>
                    <li><a href="${contextPath}/user-agreement" class="grey-text text-lighten-3">Пользовательское соглашение</a></li>
                </ul>
            </div>

            <hr class="hidden-lg hidden-md">

            <div class="col-md-4 text-center">
                <p class="white-text text-center">Мы в социальных сетях</p>
                <a target="_blank" href="https://vk.com/seasoncard"
                   class="btn-floating btn-large vk-bg waves-effect waves-light"><i class="fa fa-vk"></i></a>
                <a target="_blank" href="https://www.facebook.com/seasoncardclub"
                   class="btn-floating btn-large fb-bg waves-effect waves-light"><i class="fa fa-facebook"></i></a>
                <a target="_blank" href="https://twitter.com/seasoncardclub"
                   class="btn-floating btn-large tw-bg waves-effect waves-light"><i class="fa fa-twitter"></i></a>
                <a target="_blank" href="https://www.instagram.com/seasoncard"
                   class="btn-floating btn-large tw-bg waves-effect waves-light"><i class="fa fa-instagram"></i></a>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            <div class="text-center"> © 2015 - 2017 Copyright <a href="http://seasoncard.by">SeasonCard.by</a> <span>Версия: @war_build_version@</span></div>
        </div>
    </div>
</footer>
<div class="clearfix"></div>

<!-- Modal: Contact form -->
<div class="modal fade" id="contact-form" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="padding: 5px;">
                <h4 class="modal-title text-center">Написать нам</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form action="${contextPath}/feedback/send" method="POST" class="col-md-12">
                        <div class="input-field">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="contact-name" type="text" name="name" class="validate" required>
                            <label for="contact-name">Ваше имя</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">mail</i>
                            <input id="contact-mail" type="email" name="email" class="validate" required>
                            <label for="contact-mail">Ваш e-mail</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">label</i>
                            <input id="contact-subject" type="text" name="subject" class="validate" required>
                            <label for="contact-subject">Тема</label>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">mode_edit</i>
                            <textarea id="contact-text" class="materialize-textarea" name="message" required></textarea>
                            <label for="contact-text">Сообщение</label>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-default waves-effect waves-light">Отправить</button>
                        </div>
                    </form>
                    <hr style="margin: 0">
                    <div class="col-md-12">
                        <div class="call">
                            <p>Или просто написать нам? <span class="cf-phone"><i class="fa fa-envelope">
                                info@test.com</i></span></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer" style="padding: 5px;">
                <button type="button" class="btn btn-default" data-dismiss="modal">X</button>
            </div>
        </div>

    </div>
</div>