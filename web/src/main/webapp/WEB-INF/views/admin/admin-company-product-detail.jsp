<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 offset-md-2 col-md-8">
            <div class="card-panel">
                <c:if test="${companyProductForm.id != null}">
                    <a class="btn pull-right" data-toggle="modal" data-target="#archive-dialog">
                        В архив
                    </a>
                </c:if>
                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${companyProductForm.logoPath}" class="img-fluid" alt="">

                        <div class="file-field text-xs-center">
                            <span class="btn btn-default waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1.586"
                                       accept="image/*"/>
                            </span>
                        </div>
                    </div>
                </div>

                <form:form action="${contextPath}/admin/companies/${company.id}/products/save" modelAttribute="companyProductForm"
                           method="POST">

                    <input id="productId" name="productId" type="hidden" value="${companyProductForm.id}"/>
                    <c:if test="${companyProductForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Заголовок: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="name" name="name" required="required"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Краткое описание: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" rows="4" maxlength="300"
                                       required="required"/>
                        <form:errors path="description" cssClass="required"></form:errors>
                        <p class="exposition">Краткое описание будет доступно в нашем каталоге, а также на сайтах
                            партнеров. Постарайтесь уложиться в 300 символов.</p>
                    </div>

                    <div class="form-group">
                        <label>Подробное описание: <span class="required">*</span></label>

                        <form:textarea id="edit" path="htmlContent" class="form-control" cols="30" rows="10"/>
                        <form:errors path="htmlContent" cssClass="required"></form:errors>
                        <p class="exposition">Подробное описание будет использовано на странице абонемента.</p>
                    </div>

                    <div class="form-group">
                        <label>Тип абонемента: <span class="required">*</span></label>
                        <br>
                        <ul>
                            <form:radiobuttons path="abonnementType"
                                               items="${abonnementTypeValues}" itemLabel="description"
                                               disabled="${!companyProductForm.checkIsNew()?'true': ''}"
                                               element="li"/>
                        </ul>
                    </div>

                    <div class="form-group" id="countOfAttendancesLabel">
                        <label>Количество посещений: <span class="required">*</span></label>
                        <form:input path="countOfAttendances" type="number" min="1" max="1000" class="form-control"
                                    required="required"/>
                        <form:errors path="countOfAttendances" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Период действия(возможно изменение во время покупки): <span
                                class="required">*</span></label>
                        <br>
                        <ul>
                            <form:radiobuttons path="preferredStartingDate"
                                               items="${preferredStartingDateValues}" itemLabel="description"
                                               element="li"/>
                        </ul>
                    </div>

                    <div class="form-group">
                        <label>Стоимость: <span class="required">*</span></label>
                        <form:input path="price" type="number" min="1" max="100000000" class="form-control"
                                    required="required"/>
                        <form:errors path="price" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="published" cssStyle="position: initial; visibility: visible;"/>
                            Публичный (будет отображаться в каталоге абонементов)
                        </label>
                    </div>

                    <div class="text-xs-center">
                        <input id="submitBtn" type="submit" class="btn btn-default" value="Сохранить абонемент">
                        <span class="required"><b>*</b> Обязательные поля</span>

                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../components/image-crop-dialog.jsp"/>

<div id="archive-dialog" class="modal modal-center fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel-1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Перемещение в архив</h4>
            </div>
            <form action="${contextPath}/admin/companies/${company.id}/products/archive" method="POST">
                <input type="hidden" name="productId" value="${companyProductForm.id}">
                <div class="modal-body">
                    <p>Вы действительно хотите переместить абонемент <strong>${companyProductForm.name}</strong> в архив?</p>
                </div>
                <div class="modal-footer text-right">
                    <input type="submit" class="btn btn-danger pull-right" style="margin-left: 10px;" value="В архив">
                    <button type="button" class="btn btn-inverse pull-right" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>