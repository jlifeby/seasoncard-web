<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="formUrl" value="/admin/profile/requisites"/>
    <c:set var="back" value="/admin/companies/${company.id}/profile"/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_COMPANY')">
    <c:set var="formUrl" value="/company/profile/requisites"/>
    <c:set var="back" value="/company/profile"/>
</sec:authorize>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}${back}" class="btn btn-default btn-sm">К Профилю</a>
        </div>
        <div class="col-col-xs-12 col-md-8">

            <div class="card-panel">
                <h4>Реквизиты</h4>
                <br/>
                <form:form action="${contextPath}${formUrl}" modelAttribute="companyRequisitesForm"
                           method="POST">

                    <form:input path="companyId" type="hidden"/>

                    <h5>Регистрационная форма:</h5>
                    <div class="form-group">
                        <label>Полное название организации: <span class="required">*</span></label>
                        <form:input path="companyFullName" type="text" required="required"/>
                        <form:errors path="companyFullName" cssClass="required"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>Юридический адрес: <span class="required">*</span></label>
                        <form:input path="legalAddress" type="text" required="required"/>
                        <form:errors path="legalAddress" cssClass="required"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>УНП: <span class="required">*</span></label>
                        <form:input path="unp" type="text" required="required"/>
                        <form:errors path="unp" cssClass="required"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>ФИО Директора: <span class="required">*</span></label>
                        <form:input path="directorName" type="text" required="required"/>
                        <form:errors path="directorName" cssClass="required"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>Форма работы директора: <span class="required">*</span></label>
                        <form:input path="directorWorkForm" type="text" required="required"/>
                        <form:errors path="directorWorkForm" cssClass="required"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>Контактный email (для получения информации и рассылки): </label>
                        <form:input path="contactEmail" type="email" required="required"/>
                        <form:errors path="contactEmail" cssClass="required"></form:errors>
                    </div>

                    <h5>Банковские реквизиты (для выставления счета):</h5>
                    <div class="form-group">
                        <label>Банк: <span class="required">*</span></label>
                        <form:input path="bank" type="text" required="required"/>
                        <form:errors path="bank" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Код банка: <span class="required">*</span></label>
                        <form:input path="bankCode" type="text" required="required"/>
                        <form:errors path="bankCode" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Адрес банка: <span class="required">*</span></label>
                        <form:input path="bankAddress" type="text" required="required"/>
                        <form:errors path="bankAddress" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Расчетный счет: <span class="required">*</span></label>
                        <form:input path="paymentAccount" type="text" required="required"/>
                        <form:errors path="paymentAccount" cssClass="required"></form:errors>
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

