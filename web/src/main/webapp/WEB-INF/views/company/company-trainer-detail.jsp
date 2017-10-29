<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-6 offset-md-3">
            <div class="card-panel">
                <c:if test="${trainerForm.id != null}">
                    <a class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#archive-dialog">
                        В архив
                    </a>
                </c:if>
                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${trainerForm.logoPath}" class="img-fluid" alt="">

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

                <form:form action="${contextPath}/company/trainers/save" modelAttribute="trainerForm" method="POST">

                    <c:if test="${trainerForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>
                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Имя: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="name" name="name" required="required"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Фамилия: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="lastName" name="lastName"
                                    required="required"/>
                        <form:errors path="lastName" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Отчество: </label>
                        <form:input type="text" class="form-control" path="middleName" name="middleName"/>
                    </div>

                    <div class="form-group">
                        <label>Дата рождения: <span class="required">*</span></label>
                        <form:input type="text" class="form-control datepicker readonly" path="birthday" name="birthday"
                                    required="required"/>
                        <form:errors path="birthday" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Страна: <span class="required">*</span></label>
                        <form:select path="country" required="required">
                            <form:options items="${countries}" itemLabel="localizedName"/>
                        </form:select>
                        <form:errors path="country" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Мобильный телефон: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="phone" name="phone" required="required"
                                    pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                    title="Введите номер телефона в виде: 375291234567 или 79261234567"
                                    placeholder="375291234567 или 79261234567"/>
                        <form:errors path="phone" cssClass="required"></form:errors>
                    </div>

                    <%--<div class="form-group">--%>
                    <%--<label>Email </label>--%>
                    <%--<form:input path="email" class="form-control" type="email" name="email"--%>
                    <%--placeholder="ivan@example.com"/>--%>
                    <%--<form:errors path="phone" cssClass="required"></form:errors>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label>Краткое описание направления тренера: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" rows="3" maxlength="300"
                                       required="required"
                                       placeholder="Пример: Тренер по Кроссфиту"/>
                        <form:errors path="description" cssClass="required"></form:errors>
                        <p class="exposition">Краткое описание будет доступно в нашем каталоге, а также на сайтах
                            партнеров. Постарайтесь уложиться в 300 символов.</p>
                    </div>

                    <div class="form-group">
                        <label>Подробное описание опыта тренера: </label>

                        <form:textarea id="edit" path="htmlContent" class="form-control" cols="30" rows="5"
                                       placeholder="Пример:  Инструктор тренажерного зала и персональный тренер. Имеет высшее образование. Член федерации бодибилдинга и фитнеса."/>
                        <form:errors path="htmlContent" cssClass="required"></form:errors>
                        <p class="exposition">Подробное описание будет использовано на странице тренера.</p>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="published" cssStyle="position: initial; visibility: visible;"/>
                            Публичный (тренер будет отображаться на странице компании)
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

<div id="archive-dialog" class="modal modal-center fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel-1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Перемещение тренера в архив</h4>
            </div>
            <form action="${contextPath}/company/trainers/archive" method="POST">
                <input type="hidden" name="trainerId" value="${trainerForm.id}">
                <div class="modal-body">
                    <p>Вы действительно хотите переместить тренера <strong>${trainerForm.name} ${trainerForm.lastName}</strong> в архив?</p>
                </div>
                <div class="modal-footer text-right">
                    <button type="submit" class="btn btn-default pull-right" style="margin-left: 10px;">Удалить</button>
                    <button type="button" class="btn btn-inverse pull-right" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/image-crop-dialog.jsp"/>