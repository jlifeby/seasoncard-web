<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<form:form modelAttribute="companySelfRegistrationForm" action="${contextPath}/registration-company" method="post">

    <div class="form-group">
        <label>Название компании: <span class="required">*</span></label>
        <form:input path="name" type="text" required="required" cssClass="form-control"/>
        <form:errors path="name" cssClass="required"></form:errors>
    </div>

    <%--<div class="form-group">--%>
    <%--<label>Краткое описание: </label>--%>
    <%--<textarea path="description" class="form-control" cols="30" rows="2"--%>
    <%--maxlength="300" required="required"></textarea>--%>
    <%--</div>--%>

    <div class="form-group">
        <label>Страна: <span class="required">*</span></label>
        <form:select path="country" required="required">
            <form:options items="${countries}" itemLabel="localizedName"/>
        </form:select>
        <form:errors path="country" cssClass="required"></form:errors>
    </div>

    <%--<br/>--%>
    <%--<h5>Заполните данные администратора:</h5>--%>
    <%--<br/>--%>

    <div class="form-group">
        <label>Имя <span class="required">*</span></label>
        <form:input path="adminName" type="text" required="required" cssClass="form-control"
                    placeholder="Иван"/>
        <form:errors path="adminName" cssClass="required"></form:errors>
    </div>

    <div class="form-group">
        <label>Фамилия <span class="required">*</span></label>
        <form:input path="adminLastName" type="text" required="required" cssClass="form-control"
                    placeholder="Иванов"/>
        <form:errors path="adminLastName" cssClass="required"></form:errors>
    </div>

    <div class="form-group">
        <label>Телефон <span class="required">*</span></label>
        <div class="md-form input-group">
            <span class="input-group-addon" id="basic-addon1">+</span>
            <form:input path="adminPhone" class="form-control readonly" type="text" name="phone"
                        pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                        title="Введите номер телефона в виде: 375ХХХХХХХХХ или 7ХХХХХХХХХХ"
                        placeholder="375ХХХХХХХХХ или 7ХХХХХХХХХХ"
                        required="required"
                        aria-describedby="basic-addon1"/>
            <form:errors path="adminPhone" cssClass="required"></form:errors>
        </div>
    </div>

    <div class="form-group">
        <label>Email <span class="required">*</span></label>
        <form:input path="adminEmail" cssClass="form-control" type="email" name="email"
                    placeholder="example@gmail.com" required="required"/>
        <form:errors path="adminEmail" cssClass="required"></form:errors>
    </div>

    <div class="text-xs-center" style="margin-top: 20px;">
        <button type="submit" class="btn btn-default">Зарегистрироваться</button>
        <%--<span class="required"><b>*</b> Обязательные поля</span>--%>
    </div>
</form:form>



