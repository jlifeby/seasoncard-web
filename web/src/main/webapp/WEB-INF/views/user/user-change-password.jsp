<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/account" class="btn btn-default btn-block">Назад</a>
        </div>
        <div class="col-sm-12 col-md-6">
            <div class="card-panel">
                <h3 class="text-xs-center">Изменение пароля</h3>

                <c:if test="${!currentUser.hasAlreadyConfirmedEmail()}">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="page-header text-xs-center">Похоже на то, что вы еще не подтвердили email для своего
                                аккаута</h4>
                            <div class="alert alert-danger text-xs-center" role="alert">Для вашей безопасности мы требуем
                                обязательной
                                установки email для вашего аккаута и его подтверждения.<br/>
                                Изменение пароля возможно только после подтверждениея e-mail.
                            </div>
                        </div>
                    </div>

                    <c:if test="${currentUser.isWaitingToConfirmEmail()}">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="alert alert-warning" role="alert">В данный момент мы ожидаем от вас
                                    подтверждения email
                                    адреса <strong>${currentUser.newEmail}</strong></div>
                                <a href="/account/change-email" style="text-decoration: underline;">Изменить email для
                                    подтверждения</a>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${!currentUser.isWaitingToConfirmEmail()}">
                        <div class="row">
                            <div class="col-lg-12">
                                <a href="/account/change-email" style="text-decoration: underline;">Задать email</a>
                            </div>
                        </div>
                    </c:if>

                </c:if>

                <c:if test="${currentUser.hasAlreadyConfirmedEmail()}">

                    <form:form action="${contextPath}/account/change-password" method="POST"
                               modelAttribute="changingPasswordForm">

                        <div class="form-group">
                            <label>Ваш текущий пароль <span class="required">*</span></label>
                            <form:input path="currentPassword" class="form-control" type="password"
                                        name="currentPassword"
                                        readonly="false"/>
                            <form:errors path="currentPassword" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Новый пароль <span class="required">*</span></label>
                            <form:input path="newPassword" class="form-control" type="password" name="newPassword"
                                        readonly="false"/>
                            <form:errors path="newPassword" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Повторите новый пароль <span class="required">*</span></label>
                            <form:input path="confirmNewPassword" class="form-control" type="password"
                                        name="confirmNewPassword"
                                        readonly="false"/>
                            <form:errors path="" cssClass="required"></form:errors>
                        </div>
                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Изменить пароль</button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </form:form>
                </c:if>

            </div>
        </div>
    </div>
</div>