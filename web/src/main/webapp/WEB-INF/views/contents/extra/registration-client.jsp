<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-6 col-md-offset-3">
            <div class="card-panel">
                <div class="row">
                    <form action="${contextPath}/registration/user/create" method="post" class="col-md-12">

                        <h5>Создание личного аккаунта</h5>
                        <h6>У меня есть <a href="${contextPath}/login">личный аккаунт</a> на сайте</h6>
                        <br>

                        <div class="form-group">
                            <label>Имя: <span class="required">*</span></label>
                            <input type="text" class="form-control" name="name" required="required"/>
                        </div>

                        <div class="form-group">
                            <label>Фамилия: <span class="required">*</span></label>
                            <input type="text" class="form-control" name="lastName"
                                   required="required"/>
                        </div>

                        <div class="form-group">
                            <label>Дата рождения: <span class="required">*</span></label>
                            <input type="text" class="form-control datepicker readonly" name="birthday"
                                   required="required"/>
                        </div>

                        <div class="form-group">
                            <label>Мобильный телефон: <span class="required">*</span></label>
                            <input type="text" class="form-control" name="phone" required="required"
                                   pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                   title="Введите номер телефона в виде: 375291234567"
                                   placeholder="375291234567"/>
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input class="form-control" type="email" name="email"
                                   placeholder="ivan@example.com"/>
                        </div>

                        <div class="input-field" style="margin-left: 45px;">

                            <input type="checkbox" name="userAgreement" required>
                                <span style="color: #9e9e9e;">Я прочитал
                                    <a href="${contextPath}/policy" target="_blank" style="text-decoration: underline;">соглашение</a>
                                об оказании услуг и согласен с ним. Пароль будет выслан Вам по СМС</span>

                        </div>
                        <div class="text-center" style="margin-top: 20px;">
                            <button type="submit" class="btn btn-default waves-effect waves-light">Зарегистрироваться
                            </button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
