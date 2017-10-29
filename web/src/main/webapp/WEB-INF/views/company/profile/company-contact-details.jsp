<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="formURL" value="/admin/companies/profile/contact-details"/>
    <c:set var="back" value="admin/companies/${company.id}/profile"/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_COMPANY')">
    <c:set var="formURL" value="/company/profile/contact-details"/>
    <c:set var="back" value="/company/profile"/>
</sec:authorize>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}${back}" class="btn btn-default btn-sm">К Профилю</a>
        </div>
        <div class="col-xs-12 col-md-8">

            <div class="card-panel">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Контактные данные</h4>
                        <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                            <img id="logoImg" src="${contextPath}${companyProfileForm.logoPath}" class="img-fluid"
                                 alt="">

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
                </div>

                <form:form action="${contextPath}${formURL}" modelAttribute="companyProfileForm" method="POST">

                    <c:if test="${companyProfileForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>
                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Название: <span class="required">*</span></label>
                        <form:input path="name" type="text" required="required"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Краткое описание: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" cols="30" rows="5"
                                       maxlength="300" required="required"/>
                        <form:errors path="description" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Страна: <span class="required">*</span></label>
                        <form:select path="country" required="required" disabled="true">
                            <form:options items="${countries}" itemLabel="localizedName"/>
                        </form:select>
                        <form:errors path="country" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Контактный email (для ваших клиентов): </label>
                                <form:input path="email" type="email"/>
                                <form:errors path="email" cssClass="required"></form:errors>
                            </div>
                            <div class="col-md-6">
                                <label>Сайт: </label>
                                <form:input path="site" type="text"/>
                                <form:errors path="site" cssClass="required"></form:errors>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-3">
                                <label>Город: </label>
                                <form:input path="address.city" type="text"/>
                                <form:errors path="address.city" cssClass="required"></form:errors>
                            </div>
                            <div class="col-md-3">
                                <label>Улица: </label>
                                <form:input path="address.street" type="text"/>
                                <form:errors path="address.street" cssClass="required"></form:errors>
                            </div>
                            <div class="col-md-2">
                                <label>Дом: </label>
                                <form:input path="address.building" type="text"/>
                                <form:errors path="address.building" cssClass="required"></form:errors>
                            </div>
                            <div class="col-md-2">
                                <label>Офис: </label>
                                <form:input path="address.room" type="text"/>
                                <form:errors path="address.room" cssClass="required"></form:errors>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Телефоны: </label>
                        <div class="row">
                            <div class="col-md-6">
                                <form:input path="phone1" type="text" cssStyle="margin: 0;"
                                            pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                            title="Введите номер телефона в виде: 375291234567 или 79261234567"/>
                                <form:errors path="phone1" cssClass="required"></form:errors>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <form:input path="phone2" type="text" cssStyle="margin: 0;"
                                            pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                            title="Введите номер телефона в виде: 375291234567 или 79261234567"/>
                                <form:errors path="phone2" cssClass="required"></form:errors>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <form:input path="phone3" type="text"
                                            pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                            title="Введите номер телефона в виде: 375291234567 или 79261234567"/>
                                <form:errors path="phone3" cssClass="required"></form:errors>
                            </div>
                        </div>
                    </div>

                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="form-group">
                            <label>
                                <form:checkbox path="published" cssStyle="position: initial; visibility: visible;"/>
                                Публичная (будет отображаться в каталоге)
                            </label>
                        </div>
                    </sec:authorize>

                    <div class="text-xs-center">
                        <button type="submit"
                                class="btn btn-default brn-sm">${companyProfileForm.checkIsNew() ? "Создать компанию" : "Сохранить"}</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../components/image-crop-dialog.jsp"/>

