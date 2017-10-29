<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-6 offset-md-3">
            <div class="card card-block">
                <h4>Восстановление пароля</h4>
                <p>Пожалуйста, введите свой телефон или ​​адрес электронной почты ниже.</p>
                <br>
                <p style="margin: 0 45px 30px">
                    <input name="selector" type="radio" id="selectorPhone" value="phone" checked/>
                    <label for="selectorPhone">Телефон</label>

                    <input name="selector" type="radio" id="selectorEmail" value="email"/>
                    <label for="selectorEmail">Email</label>
                </p>
                <form:form id="recoverForm" action="${contextPath}/recover-password-request" method="POST"
                           modelAttribute="recoveringRequestForm">

                    <div id="phonePanel">
                        <div class="md-form">
                            <i class="material-icons prefix">credit_card</i>
                            <form:input id="cardUUID" path="cardUUID" class="form-control validate" type="text"
                                        name="cardUUID"
                                        pattern="[0-9]{1,8}"
                                        placeholder="00000000"
                                        title="Введите цифровой номер карты (1-8 символов)"
                                        required="required"/>
                            <label for="phone">Номер карты: <span class="required">*</span></label>
                            <form:errors path="cardUUID" cssClass="required"></form:errors>
                        </div>

                        <div class="md-form" style="margin-top: 30px">
                            <i class="material-icons prefix">phone</i>
                            <form:input id="phone" path="phone" class="form-control validate" type="text"
                                        name="phone"
                                        pattern="((^375(25|29|33|44|17)[\d]{7}$)|(^(7)\d{3}[\d]{7,10}))$"
                                        title="Введите номер телефона в виде: 375291234567 или 79261234567"
                                        placeholder="375291234567 или 79261234567"
                                        required="required"/>
                            <label for="phone">Телефон: <span class="required">*</span></label>
                            <form:errors path="phone" cssClass="required"></form:errors>
                        </div>
                    </div>
                    <div id="emailPanel" class="md-form" style="display: none">
                        <i class="material-icons prefix">mail</i>
                        <form:input id="email" path="email" class="form-control validate" type="email"
                                    name="email"
                                    placeholder="example@seasoncard.by"
                                    title="Введите корректный email"/>
                        <label for="email">Email: <span class="required">*</span></label>
                        <form:errors path="email" cssClass="required"></form:errors>
                    </div>

                    <div class="text-xs-center" style="margin-top: 20px;">
                        <button type="submit" class="btn btn-default waves-effect waves-light">Восстановить</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script>
    jQuery(function ($) {
        $("#email").val('');
        $('input[type=radio][name=selector]').change(function () {
            if (this.value == 'phone') {
                $('#emailPanel').hide();
                $('#phonePanel').show();

                $("#phone").attr('required', 'required');
                $("#cardUUID").attr('required', 'required');
                $("#email").removeAttr('required');
                $("#email").val(null);
            } else if (this.value == 'email') {
                $('#phonePanel').hide();
                $('#emailPanel').show();

                $("#email").attr('required', 'required');
                $("#phone").removeAttr('required');
                $("#cardUUID").removeAttr('required');
                $("#phone").val(null);
                $("#cardUUID").val(null);
            }
        });
    });
</script>
