<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/account/change-email" class="btn btn-default btn-block">Изменить email</a>
            <a href="${contextPath}/account/change-password" class="btn btn-default btn-block">Изменить пароль</a>
            <sec:authorize access="hasRole('ROLE_USER')">
                <a href="${contextPath}/account/settings" class="btn btn-default btn-block">Настройки</a>
            </sec:authorize>
        </div>
        <div class="col-sm-12 col-md-6">
            <div class="card-panel">
                <h4>Информация об аккаунте</h4>

                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${accountForm.logoPath}" class="img-fluid" alt="">


                        <div class="file-field text-xs-center">
                            <span class="btn btn-default btn-sm waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1"
                                       accept="image/*"/>
                            </span>
                        </div>
                    </div>
                </div>

                <form:form action="${contextPath}/account/save" modelAttribute="accountForm" method="POST">

                    <form:input path="id" type="hidden"/>
                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <sec:authorize access="hasRole('ROLE_USER')">
                        <div class="text-xs-right">
                            <span class="ultra-bold-900-italic"
                                  style="font-size: larger">Номер карты: ${card.cardUUID}</span>
                        </div>
                    </sec:authorize>

                    <div class="form-group">
                        <label>Имя <span class="required">*</span></label>
                        <form:input path="name" class="form-control" type="text" name="name" placeholder="Иван"
                                    required="true"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Фамилия <span class="required">*</span></label>
                        <form:input path="lastName" class="form-control" type="text" name="lastName"
                                    placeholder="Иванов" required="true"/>
                        <form:errors path="lastName" cssClass="required"></form:errors>
                    </div>

                    <%--<div class="form-group">--%>
                    <%--<label>Email <span class="required">*</span></label>--%>
                    <%--<form:input path="email" class="form-control" type="email" name="email"--%>
                    <%--placeholder="ivan@example.com" readonly="true"/>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label>День Рождения <span class="required">*</span></label>
                        <form:input path="birthday" class="form-control datepicker readonly" type="text" name="birthday"
                                    placeholder="01.01.1986" required="required"/>
                        <form:errors path="birthday" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Телефон <span class="required">*</span></label>
                        <form:input path="phone" class="form-control readonly" type="text" name="phone"
                                    pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                    title="Введите номер телефона в виде: 375ХХХХХХХХХ или 7ХХХХХХХХХХ"
                                    placeholder="375ХХХХХХХХХ или 7ХХХХХХХХХХ"
                                    readonly="${not empty accountForm.phone}"/>
                        <form:errors path="phone" cssClass="required"></form:errors>
                    </div>

                    <%--<div class="form-group">--%>
                        <%--<label>Регистрационный номер автомобиля:</label>--%>
                        <%--<form:input type="text" class="form-control" path="vehicleRegistrationPlate"--%>
                                    <%--name="vehicleRegistrationPlate" placeholder="0000 МР-0"--%>
                        <%--/>--%>
                        <%--<form:errors path="vehicleRegistrationPlate"></form:errors>--%>
                    <%--</div>--%>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../components/image-crop-dialog.jsp"/>

