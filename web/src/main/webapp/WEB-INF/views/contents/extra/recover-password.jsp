<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-offset-3 col-md-6">
            <div class="card-panel">
                <h4>Создание нового пароля</h4>

                <div class="row">


                    <form:form action="${contextPath}/recover-password" method="POST"
                               modelAttribute="recoveringPasswordForm">

                        <div class="form-group">
                            <form:input path="recoveringId" class="form-control" type="hidden"
                                        name="recoveringId"
                                        readonly="false"/>
                        </div>

                        <div class="form-group">
                            <label>Новый пароль <span class="required">*</span></label>
                            <form:input path="newPassword" class="form-control" type="password" name="newPassword"
                                        readonly="false" required="required"/>
                            <form:errors path="newPassword" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Повторите новый пароль <span class="required">*</span></label>
                            <form:input path="confirmNewPassword" class="form-control" type="password"
                                        name="confirmNewPassword"
                                        readonly="false" required="required"/>
                            <form:errors path="" cssClass="required"></form:errors>
                        </div>
                        <div class="text-center">
                            <input type="submit" class="btn btn-default" value="Изменить пароль">
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
