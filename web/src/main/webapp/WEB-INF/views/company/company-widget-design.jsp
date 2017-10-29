<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<script type="text/javascript" src="${contextPath}/ext/${company.id}/widget.js"></script>

<style>
    .select-dropdown li img {
        height: 30px;
        width: 30px;
    }

    p.white-skin {
        background: #F9F9F9;
    }

    p.black-skin {
        background-color: #222;
    }

    p.cyan-skin {
        background-color: #1e5252;
    }

    p.deep-purple-skin {
        background-color: #383048;
    }

    p.navy-blue-skin {
        background-color: #152032;
    }

    p.pink-skin {
        background-color: #673348;
    }

    p.indigo-skin {
        background-color: #26294e;
    }

    p.light-blue-skin {
        background-color: #318594;
    }

    p.grey-skin {
        background-color: #a5a4a4;
    }

    #primaryPalettePreview {
        height: 25px;
        width: 40px;
        margin-top: 1.2em;
        border: 1px solid #a2a2a2;
    }
</style>


<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/company/widget/design"
               class="btn btn-default btn-block ${fn:contains(url, '/design')? 'active' : ''}">Дизайн виджета</a>
            <a href="${contextPath}/company/widget/code"
               class="btn btn-default btn-block ${fn:contains(url, '/code')? 'active' : ''}">Код и установка</a>
        </div>
        <div class="col-sm-12 col-md-7">

            <form:form action="${contextPath}/company/widget/design" modelAttribute="widgetSettingForm"
                       method="POST">

                <c:if test="${widgetSettingForm.widgetSettingId != null}">
                    <form:input path="widgetSettingId" type="hidden"/>
                </c:if>

                <div class="card">
                    <h3 class="card-header white text-xs-center">Основные настройки</h3>
                    <div class="card-block">

                        <div class="form-group">
                            <label>Заголовок: <span class="required">*</span></label>
                            <form:input type="text" class="form-control" path="title" name="title" required="required"
                                        placeholder="Пример: Форма компании"/>
                            <form:errors path="title" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Описание: <span class="required">*</span></label>
                            <form:textarea path="description" class="form-control" cols="30" rows="3" maxlength="300"
                                        placeholder="Пример: После заполнения формы наш администратор перезвонит Вам и подберёт удобное для Вас время!"/>
                            <form:errors path="description" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Основной цвет: <span class="required">*</span></label>
                            <div class="row">
                                <div class="col-sm-5">
                                    <form:select path="primaryPalette" required="required" id="primaryPalette">
                                        <form:option value="white-skin" label="Белый"/>
                                        <form:option value="black-skin" label="Черный"/>
                                        <form:option value="cyan-skin" label="Циан"/>
                                        <form:option value="deep-purple-skin" label="Темно-фиолетовый"/>
                                        <form:option value="navy-blue-skin" label="Темно-синий"/>
                                        <form:option value="pink-skin" label="Розовый"/>
                                        <form:option value="indigo-skin" label="Индиго"/>
                                        <form:option value="light-blue-skin" label="Светло-синий"/>
                                        <form:option value="grey-skin" label="Серый"/>
                                    </form:select>
                                </div>
                                <div class="col-sm-1">
                                    <p id="primaryPalettePreview" class="${widgetSettingForm.primaryPalette}"></p>
                                </div>
                            </div>
                        </div>

                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Сохранить</button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h3 class="card-header white text-xs-center">Настройки кнопки</h3>
                    <div class="card-block">

                        <div class="form-group">
                            <label>Расположение панели: <span class="required">*</span></label>
                            <form:select path="formPosition" required="required">
                                <form:option value="left" label="Слева"/>
                                <form:option value="right" label="Справа"/>
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label>Текст кнопки: <span class="required">*</span></label>
                            <form:input type="text" class="form-control" path="buttonText" name="buttonText"
                                        required="required"
                                        placeholder="Пример: Онлайн запись"/>
                            <form:errors path="buttonText" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>Расположение кнопки: <span class="required">*</span></label>
                            <form:select path="buttonPosition" required="required">
                                <form:option value="bottom right" label="Правый нижний угол"/>
                                <form:option value="top right" label="Правый верхний угол"/>
                                <form:option value="bottom left" label="Левый нижний угол"/>
                                <form:option value="top left" label="Левый верхний угол"/>
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label>Цвет кнопки: <span class="required">*</span></label>
                            <form:input type="color" class="form-control" path="buttonColor" name="buttonColor"
                                        required="required" cssStyle="padding: 0"/>
                            <form:errors path="buttonColor" cssClass="required"></form:errors>
                        </div>

                        <div class="form-group">
                            <label>
                                <form:checkbox path="buttonAnimation"
                                               cssStyle="position: initial; visibility: visible;"/>
                                Показывать анимацию
                            </label>
                        </div>

                        <div class="form-group">
                            <label>
                                <form:checkbox path="showButton" cssStyle="position: initial; visibility: visible;"/>
                                Включить кнопку
                            </label>
                        </div>

                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Сохранить</button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h3 class="card-header white text-xs-center">Оповещения</h3>
                    <div class="card-block">

                        <div class="form-group">
                            <label>Email</label>
                            <form:input path="notificationEmail" class="form-control" type="email"
                                        name="notificationEmail"                                        placeholder="ivan@example.com"/>
                        </div>

                        <div class="form-group">
                            <label>
                                <form:checkbox path="newClientEmailNotification"
                                               cssStyle="position: initial; visibility: visible;"/>
                                Отсылать Email при каждой новой записи
                            </label>
                        </div>

                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Сохранить</button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>

                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script>
    $('#primaryPalette').change(function () {
        $('#primaryPalettePreview').removeClass();
        $('#primaryPalettePreview').addClass(this.options[this.selectedIndex].value)
    })
</script>