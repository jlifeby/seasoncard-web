<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 offset-md-2 col-md-8">
            <div class="card card-block">
                <c:if test="${productForm.id != null}">
                    <a class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#archive-dialog">
                        В архив
                    </a>
                </c:if>
                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${productForm.logoPath}" class="img-fluid" alt="">

                        <div class="file-field text-xs-center">
                            <span class="btn btn-default btn-sm waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1.586"
                                       accept="image/*"/>
                            </span>
                        </div>
                    </div>
                </div>

                <form:form action="${contextPath}/company/products/detail/save" modelAttribute="productForm"
                           method="POST">

                    <input id="productId" name="productId" type="hidden" value="${productForm.id}"/>
                    <c:if test="${productForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Заголовок: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="name" name="name" required="required"
                                    placeholder="Пример: Кроссфит с тренером"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Краткое описание: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" rows="3" maxlength="300"
                                       required="required"
                                       placeholder="Пример: Групповые занятия по 6 человек в будние дни и выходные дни"/>
                        <form:errors path="description" cssClass="required"></form:errors>
                        <p class="exposition">Краткое описание будет доступно в нашем каталоге, а также на сайтах
                            партнеров. Постарайтесь уложиться в 300 символов.</p>
                    </div>

                    <div class="form-group">
                        <label>Подробное описание: </label>

                        <form:textarea id="edit" path="htmlContent" class="form-control" cols="30" rows="5"
                                       placeholder="Пример: Кроссфит - это система всесторонней физической подготовки, основанная на постоянно чередующихся функциональных движениях, выполняемых с высокой интенсивностью. В настоящее время, данная программа адаптирована для всех, система подходит любому человеку вне зависимости от его опыта, навыков и возраста."/>
                        <form:errors path="htmlContent" cssClass="required"></form:errors>
                        <p class="exposition">Подробное описание будет использовано на странице абонемента.</p>
                    </div>

                    <div class="form-group">
                        <label>Тип абонемента: <span class="required">*</span></label>
                        <br>
                        <ul>
                            <form:radiobuttons path="abonnementType"
                                               items="${abonnementTypeValues}" itemLabel="description"
                                               disabled="${!productForm.checkIsNew()?'true': ''}"
                                               element="li"/>
                            <form:input path="abonnementType" type="hidden"/>
                        </ul>
                    </div>

                    <div class="form-group" id="countOfAttendancesLabel">
                        <label>Количество посещений: <span class="required">*</span></label>
                        <form:input path="countOfAttendances" type="number" min="1" max="1000" class="form-control"
                                    placeholder="Пример: 8" required="required"/>
                        <form:errors path="countOfAttendances" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group" id="countOfUnitsLabel">
                        <label>Количество единиц: <span class="required">*</span></label>
                        <form:input path="countOfUnits" type="number" min="1" max="1000" class="form-control"
                                    placeholder="Пример: 30" required="required"/>
                        <form:errors path="countOfUnits" cssClass="required"></form:errors>
                    </div>


                    <div class="form-group" id="unitNameLabel">
                        <label>Название единицы: <span class="required">*</span></label>
                        <form:input path="UnitName" class="form-control"
                                    placeholder="Пример: Минуты" required="required"/>
                        <form:errors path="unitName" cssClass="required"></form:errors>
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
                        <form:input path="price" type="number" step="0.01" min="0.01" max="100000000"
                                    class="form-control" placeholder="Пример: 100" required="required"/>
                        <form:errors path="price" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="published" cssStyle="position: initial; visibility: visible;"/>
                            Публичный (будет отображаться в каталоге абонементов)
                        </label>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить абонемент</button>
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
            <form action="${contextPath}/company/products/archive" method="POST">
                <input type="hidden" name="productId" value="${productForm.id}">
                <div class="modal-body">
                    <p>Вы действительно хотите переместить абонемент <strong>${productForm.name}</strong> в архив?</p>
                </div>
                <div class="modal-footer text-right">
                    <button type="submit" class="btn btn-default pull-right" style="margin-left: 10px;">В архив</button>
                    <button type="button" class="btn btn-inverse pull-right" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>

    $("input[name=abonnementType]:radio").change(function () {
        checkAbonnementType($(this).val());
    });

    $(document).ready(function () {
        if ($("input[name=abonnementType]:radio")) {
            checkAbonnementType($("input[name=abonnementType]:checked").val());
        }
    });

    function checkAbonnementType(val) {
        if (val === 'BY_ATTENDANCE') {
            $("#countOfAttendancesLabel").show();
            $("#countOfUnitsLabel").hide();
            $("#unitNameLabel").hide();
            $("#countOfUnitsLabel input").removeAttr('required');
            $("#countOfAttendancesLabel input").attr('required', 'required');
        } else if (val === 'BY_TIME') {
            $("#countOfAttendancesLabel").hide();
            $("#countOfUnitsLabel").hide();
            $("#unitNameLabel").hide();
            $("#countOfUnitsLabel input").removeAttr('required');
            $("#countOfAttendancesLabel input").removeAttr('required');
            $("#countOfAttendancesLabel input").val('1');
        } else if (val === 'BY_UNIT') {
            $("#countOfUnitsLabel").show();
            $("#unitNameLabel").show();
            $("#countOfAttendancesLabel").hide();
            $("#countOfAttendancesLabel input").val('1');
            $("#countOfUnitsLabel input").attr('required', 'required');
            $("#countOfAttendancesLabel input").removeAttr('required');
        }
    }
</script>