<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <c:set var="requiredAcceptingAgreement"
           value="${!currentUser.isAcceptedAgreement() and currentUser.hasUserRole()}"/>

    <c:if test="${requiredAcceptingAgreement}">
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header text-xs-center">Похоже на то, что вы еще не задали email для своего аккаута</h3>
                <div class="blockquote bq-danger text-xs-center" role="alert">Для вашей безопасности мы требуем
                    обязательной
                    установки email для вашего аккаута и его подтверждения.<br/>
                    Это позволит в дальнейшем восстановить доступ в случае проблем.
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${currentUser.isWaitingToConfirmEmail()}">
        <div class="row">
            <div class="col-lg-12">
                <div class="blockquote bq-warning" role="alert">В данный момент мы ожидаем от вас подтверждения email
                    адреса <strong>${currentUser.newEmail}</strong></div>
            </div>
        </div>
    </c:if>

    <div class="row">

        <div class="col-sm-12 col-md-3">
            <c:if test="${not requiredAcceptingAgreement}">
                <a href="${contextPath}/account" class="btn btn-default btn-block">Назад</a>
            </c:if>
        </div>
        <div class="col-sm-12 col-md-6">
            <div class="card-panel">
                <h4 class="text-xs-center">Изменение email адреса</h4>

                <c:set var="changeEmailUrl"
                       value="${requiredAcceptingAgreement? '/account/change-email-with-agreement':'/account/change-email'}"/>

                <form:form action="${contextPath}${changeEmailUrl}" method="POST"
                           modelAttribute="changingEmailForm">

                    <c:if test="${not empty currentUser.email}">
                        <div class="form-group">
                            <label>Ваш текущий email</label>
                                ${currentUser.email}
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label>Новый email <span class="required">*</span></label>
                        <form:input path="newEmail" class="form-control" type="email" name="newEmail"
                                    placeholder="ivan.ivanov@gmail.com" required="true"
                                    title="Введите корректный email"/>
                        <form:errors path="newEmail" cssClass="required"></form:errors>
                    </div>


                    <c:if test="${requiredAcceptingAgreement}">
                        <div class="form-group text-xs-center">

                            <input id="acceptedAgreement" type="checkbox" name="acceptedAgreement"
                                   required="required"/>
                            <label for="acceptedAgreement">
                                Я прочитал
                                <a href="${contextPath}/user-agreement" target="_blank"
                                   style="text-decoration: underline;">соглашение</a>
                                об оказании услуг и согласен с ним.
                            </label>
                        </div>

                    </c:if>

                    <div class="text-xs-center">
                        <button id="submitBtn" type="submit"
                                class="btn btn-default" ${requiredAcceptingAgreement? "disabled": ""}>
                            Изменить email
                        </button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>