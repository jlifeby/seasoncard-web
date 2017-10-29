<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/account" class="btn btn-default btn-block">Назад</a>
        </div>
        <div class="col-sm-12 col-md-6">
            <div class="card-panel">
                <h4>Настройки аккаунта</h4>

                <form:form action="${contextPath}/account/settings" modelAttribute="userSettingsForm" method="POST">

                    <div class="form-group">
                        <label>
                            <form:checkbox path="allowedEmailReceiving"
                                           cssStyle="position: initial; visibility: visible;"/>
                            Получать Email от посещаемых компаний
                        </label>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="allowedSmsReceiving"
                                           cssStyle="position: initial; visibility: visible;"/>
                            Получать SMS от посещаемых компаний
                        </label>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить настройки</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

