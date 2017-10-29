<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-6 offset-md-3">
            <div class="card-panel">

                <c:if test="${bindClientForm.id != null}">
                    <c:if test="${bindClientForm.active}">
                        <a class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#archive-dialog">
                            В архив
                        </a>
                    </c:if>
                    <c:if test="${!bindClientForm.active}">
                        <a href="${contextPath}/company/clients/${bindClientForm.cardUUID}/restore"
                           class="btn btn-primary btn-sm pull-right">Восстановить</a>
                    </c:if>
                </c:if>

                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${bindClientForm.logoPath}" class="img-fluid" alt="">

                        <div class="file-field text-xs-center">
                            <span class="btn btn-default waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1"
                                       accept="image/*"/>
                            </span>
                        </div>
                    </div>
                </div>

                <form:form id="clientForm" action="${contextPath}/company/cards/save" modelAttribute="bindClientForm"
                           method="POST">

                    <c:if test="${bindClientForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>
                    <form:input type="hidden" path="userId" name="userId"/>
                    <form:input type="hidden" id="logoPath" path="logoPath" name="logoPath"/>
                    <form:input type="hidden" id="logoMediaId" path="logoMediaId" name="logoMediaId"/>

                    <div class="form-group">
                        <label>Номер карты: <span class="required">*</span></label>
                        <form:input type="text" id="cardUUID" class="form-control" path="cardUUID" name="cardUUID"
                                    pattern="[0-9]{1,8}"
                                    required="required"
                                    readonly="${!bindClientForm.checkIsNew()}"
                                    title="Введите цифровой номер карты (1-8 символов)"/>
                        <form:errors path="cardUUID" cssClass="required"></form:errors>
                        <c:if test="${bindClientForm.id == null}">
                            <div class="text-xs-right">
                                <button id="insertUUIDBtn" type="button"
                                        class="btn-flat light-blue-text waves-effect waves-light"
                                        style="padding: 0; margin: 0;">
                                    Использовать виртуальную карту
                                </button>
                            </div>
                        </c:if>
                    </div>

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
                        <label>Дата рождения: <span class="required">*</span></label>
                        <form:input type="text" class="form-control datepicker readonly" path="birthday" name="birthday"
                                    required="required"/>
                        <form:errors path="birthday" cssClass="required"></form:errors>
                    </div>

                    <%--<div class="form-group">--%>
                    <%--<label>Страна: <span class="required">*</span></label>--%>
                    <%--<form:select path="country" required="required">--%>
                    <%--<form:options items="${countries}" itemLabel="localizedName"/>--%>
                    <%--</form:select>--%>
                    <%--<form:errors path="country" cssClass="required"></form:errors>--%>
                    <%--</div>--%>

                    <div class="input-group form-group">
                        <label>Мобильный телефон: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="phone" name="phone" required="required"
                                    pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                    title="Введите номер телефона в виде: 375291234567 или 79261234567"
                                    readonly="${!bindClientForm.checkIsNew()}"
                                    placeholder="375291234567 или 79261234567"/>
                        <c:if test="${bindClientForm.id != null}">
                            <span class="input-group-btn">
                                <a class="btn btn-primary btn-sm" style="margin-bottom: -40px" data-toggle="modal"
                                   data-target="#change-phone-dialog">Изменить</a>
                            </span>
                        </c:if>
                        <form:errors path="phone" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Внутренний номер в рамках компании: </label>
                        <form:input type="text" class="form-control" path="internalId" name="internalId"/>
                        <form:errors path="internalId" cssClass="required"></form:errors>
                    </div>

                    <div class="form-group">
                        <label>Теги: </label>
                        <div class="chips chips-initial"></div>
                        <form:input type="hidden" path="tagsAsString" name="tagsAsString"/>
                    </div>

                    <%--<div class="form-group">--%>
                    <%--<label>Email </label>--%>
                    <%--<form:input path="email" class="form-control" type="email" name="email"--%>
                    <%--placeholder="ivan@example.com"/>--%>
                    <%--<form:errors path="phone" cssClass="required"></form:errors>--%>
                    <%--</div>--%>

                    <%--<div class="form-group">--%>
                    <%--<label>Регистрационный номер автомобиля:</label>--%>
                    <%--<form:input type="text" class="form-control" path="vehicleRegistrationPlate"--%>
                    <%--name="vehicleRegistrationPlate" placeholder="0000 МР-0"/>--%>
                    <%--<form:errors path="vehicleRegistrationPlate"></form:errors>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label>Комментарий: </label>
                        <form:textarea path="comment" class="form-control" cols="30" rows="2" maxlength="300"/>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit"
                                class="btn btn-default">${bindClientForm.checkIsNew() ? "Создать пользователя с картой" : "Сохранить"}</button>
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
                <h4 class="modal-title">Перемещение клиента в архив</h4>
            </div>
            <form action="${contextPath}/company/clients/archive" method="POST">
                <input type="hidden" name="cardUUID" value="${bindClientForm.cardUUID}">
                <div class="modal-body">
                    <p>Вы действительно хотите переместить клиента
                        <strong>${bindClientForm.name} ${bindClientForm.lastName}</strong> в архив?</p>
                </div>
                <div class="modal-footer text-right">
                    <button type="submit" class="btn btn-default pull-right" style="margin-left: 10px;">В архив</button>
                    <button type="button" class="btn btn-inverse pull-right" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="change-phone-dialog" class="modal modal-center fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel-1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Изменение номера</h4>
            </div>
            <form action="${contextPath}/company/clients/change-phone" method="POST" modelAttribute="phoneForm">
                <input type="hidden" name="cardUUID" value="${bindClientForm.cardUUID}">
                <div class="modal-body">
                    <p>При изменении телефонного номера клиенту будет отправлено SMS с новым паролем для входа.
                        Использование старого пароля для входа в систему станет недоступным.</p>

                    <div class="form-group">
                        <label>Новый номер телефона: <span class="required">*</span></label>
                        <input type="text" class="form-control" name="phone" required="required"
                               pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                               title="Введите номер телефона в виде: 375291234567 или 79261234567"
                               placeholder="375291234567 или 79261234567"/>
                    </div>
                </div>
                <div class="modal-footer text-right">
                    <button type="submit" class="btn btn-default pull-right" style="margin-left: 10px;">Изменить
                    </button>
                    <button type="button" class="btn btn-inverse pull-right" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/image-crop-dialog.jsp"/>

<script>
    $("#insertUUIDBtn").click(function () {
        $.ajax('/api/company/cards/next-free-virtual', {
            method: "GET",
            success: function (data) {
                $('#cardUUID').val(data.cardUUID);
            },
            error: function (e) {
                var responseText = e.responseText;
                try {
                    var apiError = JSON.parse(responseText);
                    var errorMsg = apiError.localizedMsg ? apiError.localizedMsg : apiError.msg;
                    showError(errorMsg);
                } catch (ex) {
                    showError('Ошибка получения карты!')
                }
            }
        });
    });

    $(document).ready(function () {
        var data = [];
        var tagsAsString = '${bindClientForm.tagsAsString}';
        if (tagsAsString) {
            tagsAsString.split(',').forEach(function (tag) {
                data.push({tag: tag});
            });
        }
        $('.chips-initial').material_chip({
            data: data,
            placeholder: 'Enter для ввода',
            secondaryPlaceholder: 'Enter для ввода'
        });
    });

    $('#clientForm').submit(function () {
        var tags = [];
        $('.chips-initial').material_chip('data').forEach(function (tag) {
            tags.push(tag.tag);
        });
        $("input[name='tagsAsString']").val(tags.join(","));
        return true; // return false to cancel form action
    });


</script>