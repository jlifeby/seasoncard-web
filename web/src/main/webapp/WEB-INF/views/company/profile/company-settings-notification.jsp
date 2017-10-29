<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/company/settings/product" class="btn btn-default btn-block">Настройки абонементов</a>
            <a href="${contextPath}/company/settings/notification" class="btn btn-default btn-block">Оповещение</a>
        </div>
        <div class="col-sm-12 col-md-6">
            <div class="card-panel">
                <h4>Настройки оповещение</h4>
                <p>Введите email для получения оповещений.</p>
                <form:form action="${contextPath}/company/settings/notification" modelAttribute="companySettingsForm" method="POST">

                    <div class="form-group">
                        <label>Email <span class="required">*</span></label>
                        <form:input path="notificationEmail" class="form-control" type="email" name="notificationEmail"
                                    placeholder="ivan@example.com"/>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="abonSoldEmailNotification" cssStyle="position: initial; visibility: visible;"/>
                            Отсылать Email при каждой продаже абонемента
                        </label>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

