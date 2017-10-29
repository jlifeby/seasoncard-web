<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-8 offset-md-2">
            <div class="card-panel">
                <h4 class="text-xs-center">Подтвердите согласие на использование сервиса</h4>
                <br/>

                <p class="text-xs-right" style="font-size: 0.8rem;">*Данные Вы сможете изменить в вашем кабинете</p>
                <table class="table table-striped">
                    <tr>
                        <td>Наименование:</td>
                        <td><b>${company.name}</b></td>
                    </tr>
                    <tr>
                        <td>Номер компании в системе:</td>
                        <td><b>${company.contractId}</b></td>
                    </tr>
                    <c:if test="${not empty company.logoPath and !company.logoPath.contains('company_profile.png')}">
                        <tr>
                            <td>Логотип:</td>
                            <td><img src="${contextPath}${company.logoPath}"
                                     style="max-height: 100px; max-height: 100px">
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty company.site}">
                        <tr>
                            <td>Сайт:</td>
                            <td>${company.site}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty company.email}">
                        <tr>
                            <td>Email:</td>
                            <td>${company.email}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty company.address.toString()}">
                        <tr>
                            <td>Адрес:</td>
                            <td>${company.address.toString()}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty company.phones}">
                        <tr>
                            <td>Телефоны:</td>
                            <td>
                                <span class="phone-format">${company.phones[0]}</span>
                                    ${not empty company.phones[1] ? "<br/>" : ""}
                                <span class="phone-format">${company.phones[1]}</span>
                                    ${not empty company.phones[2] ? "<br/>" : ""}
                                <span class="phone-format">${company.phones[2]}</span>
                            </td>
                        </tr>
                    </c:if>
                </table>

                <form action="${contextPath}/company/accept" method="POST">
                    <div class="input-field text-xs-center" style="margin-bottom: 20px">
                        <span style="color: #9e9e9e;">Продолжая работу Вы подверждаете согласие с
                            <a href="${contextPath}/user-agreement"
                               target="_blank" style="text-decoration: underline;">пользовательским соглашением</a><br/>
                            и договором
                            <a href="${contextPath}/public-offer.pdf"
                               target="_blank" style="text-decoration: underline;">публичной оферты</a>.</span>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default brn-sm">Продолжить</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
