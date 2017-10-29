<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 offset-md-2 col-md-8">
            <div class="card-panel">

                <h4 class="text-xs-center">Управление скидкой или акцией</h4>

                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${promotionForm.logoPath}" class="img-fluid" alt="">

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

                <form:form action="${contextPath}/company/promotions/save" modelAttribute="promotionForm" method="POST">

                    <c:if test="${promotionForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Название: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="name" name="name" required="required"
                                    placeholder="Пример: Скидка новым клиентам"/>
                        <form:errors path="name" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Краткое описание: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" rows="3" maxlength="300"
                                       placeholder="Пример: Скидка всем новым абонементам до конца лета"/>
                        <form:errors path="description" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Тип скидки: <span class="required">*</span></label>
                        <br>
                        <ul>
                            <form:radiobuttons path="promotionType"
                                               items="${promotionTypeValues}"
                                               itemLabel="description"
                                               disabled="${!promotionForm.checkIsNew()?'true': ''}"
                                               element="li"/>
                        </ul>
                    </div>

                    <div class="form-group">
                        <label>Размер скидки: <span class="required">*</span></label>
                        <form:input path="promotionValue" type="number" step="0.01" min="0.01" max="100000000"
                                    class="form-control" placeholder="Пример: 50" required="required"/>
                        <form:errors path="promotionValue" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="termless" cssStyle="position: initial; visibility: visible;"/>
                            Бессрочная (не имеет даты окончания)
                        </label>
                    </div>

                    <div class="form-group">
                        <label>Дата окончания акции:</label>
                        <form:input path="endDate" class="form-control datepicker readonly" type="text" name="endDate"
                                    required="required"/>
                        <form:errors path="endDate" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="published" cssStyle="position: initial; visibility: visible;"/>
                            Публичная скидка (будет отображаться на странице компании и в каталоге абонементов)
                        </label>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="active" cssStyle="position: initial; visibility: visible;"/>
                            Активная скидка (будет доступна для абонементов)
                        </label>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить акцию</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../components/image-crop-dialog.jsp"/>

<script>
    $(document).ready(function () {
        $("input[name=termless]").change(function () {
            onTermlessChange();
        });

        $("input[name=promotionType]").click(function () {
            onClick();
        });
        function onClick() {
            var value = $("input[name=promotionType]:checked").val();
            if (value == 'PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION') {
                $("input[name=promotionValue]").attr('max', '100');
            } else {
                $("input[name=promotionValue]").attr('max', '100000000');
            }
        }

        function onTermlessChange(){
            var termless = $('input[name=termless]').prop('checked');
            if(termless){
                $("input[name=endDate]").removeAttr('required');
                $("input[name=endDate]").val('');
                $("input[name=endDate]").attr('disabled', 'disabled');
            } else {
                $('input[name=endDate]').attr('required', 'required');
                $("input[name=endDate]").removeAttr('disabled');
                $("input[name=endDate]").removeAttr('readonly');
            }
        }

        onClick();
        onTermlessChange();
    });
</script>