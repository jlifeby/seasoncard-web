<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-6 offset-md-3">
            <div class="card-panel">
                <div class="row">
                    <form action="${contextPath}/j_spring_security_check" method="post" class="col-md-12">
                        <h4 class="text-xs-center" style="margin-bottom: 20px">Войти с логином и паролем</h4>
                        <%--<h6>Если ещё не создан личный аккаунт, вы можете--%>
                        <%--<a href="${contextPath}/registration">зарегистрироваться</a></h6>--%>
                        <%--<br>--%>

                        <%--<div class="form-group">--%>
                        <%--<label>Имя <span class="required">*</span></label>--%>
                        <%--<form:input path="name" type="text" name="name" placeholder="Иван"--%>
                        <%--required="true"/>--%>
                        <%--</div>--%>

                        <div class="form-group">
                            <label for="email">Email или номер карты: <span class="required">*</span></label>
                            <input id="email" type="text" name="j_username" class="form-control"
                                   pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}|[0-9]{1,8}"
                                   autofocus required
                                   title="Введите корректный email или цифровой номер карты(1-8 символов)">

                        </div>
                        <div class="form-group">
                            <label for="password">Пароль: <span class="required">*</span></label>
                            <input id="password" type="password" name="j_password" class="form-control" required>

                        </div>
                        <div class="form-group text-xs-center">
                            <input id="rememberme" type="checkbox" name="_spring_security_remember_me"/>
                            <label for="rememberme">Запомнить меня</label>
                        </div>
                        <c:if test="${error}">
                            <div class="form-group">
                                <p class="error-message normal-400">Неверное имя пользователя или пароль</p>
                            </div>
                        </c:if>
                        <div class="text-xs-center" style="margin-top: 20px;">
                            <button type="submit" class="btn btn-default waves-effect waves-light">Войти</button>
                            <a href="${contextPath}/recover-password-request">Забыли пароль?</a>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

