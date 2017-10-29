<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row m-2">

        <div class="col-md-8 offset-md-1">

            <div id="profile" class="mt-1">

                <h2 class="bold-500 mb-3">Видео справка</h2>

                <h4 class="bold-500">Администрирование и Профиль</h4>

                <hr>

                <p>
                    Видео рассказывает, как редактировать профиль компании, аккаунт администратора, настройки
                    абонементов по умолчанию.
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/SrYXWURmlks?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>

            <div id="abonnements" class="mt-3">

                <h4 class="bold-500">Абонементы</h4>

                <hr>

                <p>
                    Видео рассказывает, как создавать, редактировать и просматривать абонементы
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/0ZYwIYOorEE?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>

            <div id="clients" class="mt-3">

                <h4 class="bold-500">Клиенты</h4>

                <hr>

                <p>
                    Видео рассказывает, как создавать клиента, редактировать, зачислять ему абонемент и как осуществлять
                    поиск клиентов, а также как производить отметку посещения по абонементу
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/2-WYjTp6Huw?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>

            <div id="discounts" class="mt-3">

                <h4 class="bold-500">Скидки</h4>

                <hr>

                <p>
                    Видео рассказывает, как создавать, редактировать и просматривать скидки
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/zhWSmU61FMA?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>

            <div id="trainers" class="mt-3">

                <h4 class="bold-500">Тренеры</h4>

                <hr>

                <p>
                    Видео рассказывает, как создавать, редактировать и просматривать тренеров
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/_Fwz0ZIeCVY?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>

            <div id="reports" class="my-3">

                <h4 class="bold-500">Отчеты</h4>

                <hr>

                <p>
                    Видео рассказывает, как работать с отчетами
                </p>

                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item"
                            src="https://www.youtube.com/embed/tVz5RALTUYI?list=PLZKbPo65Fl7gSeEp1lQrAX35-Rd4lWYvt"
                            allowfullscreen></iframe>
                </div>

            </div>


        </div>

        <div class="col-md-2">
            <div>
                <ul class="nav nav-pills flex-column">
                    <li class="nav-item"><a class="nav-link" href="#profile">Администрирование и Профиль</a></li>
                    <li class="nav-item"><a class="nav-link" href="#abonnements">Абонементы</a></li>
                    <li class="nav-item"><a class="nav-link" href="#clients">Клиенты</a></li>
                    <li class="nav-item"><a class="nav-link" href="#discounts">Скидки</a></li>
                    <li class="nav-item"><a class="nav-link" href="#trainers">Тренеры</a></li>
                    <li class="nav-item"><a class="nav-link" href="#reports">Отчеты</a></li>
                </ul>
            </div>
            <div class="sticky-placeholder"></div>
        </div>
    </div>
</div>