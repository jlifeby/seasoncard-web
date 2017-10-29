<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>
        </div>
        <div class="col-md-10">
            <h3>Создание администратора для компании: </h3>
            <div class="card-panel">
                <div class="row">
                    <div class="col-md-1">
                        <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
                    </div>
                    <div class="col-md-11">
                        <h4>${company.name}</h4>
                    </div>
                </div>
            </div>
            <div class="card-panel">

                <c:if test="${companyAdminForm.id != null}">
                    <a href="${contextPath}/admin/companies/${company.id}/admins/${companyAdminForm.id}/send-admin-info"
                       style="margin-top: 0px; padding-right: 0px"
                       class="btn-flat pull-right">Отправить администратору данные на Email</a>
                </c:if>

                <div class="row">
                    <div class="col-md-8 offset-md-2">

                        <h5>Заполните данные администратора: </h5>

                        <br/>

                        <form:form action="${contextPath}/admin/companies/${company.id}/admins/save"
                                   modelAttribute="companyAdminForm" method="POST">

                            <c:if test="${companyAdminForm.id != null}">
                                <form:input path="id" type="hidden"/>
                            </c:if>

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

                            <div class="form-group">
                                <label>Email <span class="required">*</span></label>
                                <form:input path="email" class="form-control" type="email" name="email"
                                            placeholder="example@gmail.com" required="true"/>
                                <form:errors path="email" cssClass="required"></form:errors>
                            </div>

                            <div class="text-xs-center">
                                <input type="submit" class="btn btn-default"
                                       value="${empty companyAdminForm.id ? "Создать" : "Сохранить"}">
                                <span class="required"><b>*</b> Обязательные поля</span>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


