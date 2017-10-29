<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="editContactDetails" value="/admin/companies/${company.id}/profile/contact-details"/>
    <c:set var="editRequisites" value="/admin/companies/${company.id}/profile/requisites"/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_COMPANY')">
    <c:set var="editContactDetails" value="/company/profile/contact-details"/>
    <c:set var="editRequisites" value="/company/profile/requisites"/>
</sec:authorize>

<div class="container">
    <div class="row">
        <div class="col-col-xs-12 col-md-8">
            <div class="card-panel">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Информация о компании</h4>
                        <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">
                            <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
                        </div>
                    </div>
                </div>

                <h5>Контактные данные (информация доступна пользователям)</h5>
                <table class="table table-striped">
                    <tr>
                        <td>Наименование:</td>
                        <td style="max-width: 450px">
                            <p class="ellipsis bold-500">${company.name}</p>
                        </td>
                    </tr>
                    <tr>
                        <td>Номер компании в системе:</td>
                        <td><b>${company.contractId}</b></td>
                    </tr>
                    <tr>
                        <td>Краткое описание:</td>
                        <td>${company.description}</td>
                    </tr>
                    <tr>
                        <td>Сайт:</td>
                        <td>${company.site}</td>
                    </tr>
                    <tr>
                        <td>Контактный email:</td>
                        <td>${company.email}</td>
                    </tr>
                    <tr>
                        <td>Адрес:</td>
                        <td>${company.address.toString()}</td>
                    </tr>
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
                    <tr>
                        <td></td>
                        <td>

                            <p><a href="${contextPath}${editContactDetails}">Редактировать</a></p>
                        </td>
                    </tr>
                </table>

                <c:set var="requiesites" value="${company.companyRequisites}"/>

                <h5>Реквизиты (информация скрыта)</h5>
                <table class="table table-striped">
                    <tr>
                        <td>Полное название организации::</td>
                        <td><b>${requiesites.companyFullName}</b></td>
                    </tr>
                    <tr>
                        <td>Юридический адрес:</td>
                        <td><b>${requiesites.legalAddress}</b></td>
                    </tr>
                    <tr>
                        <td>УНП:</td>
                        <td><b>${requiesites.unp}</b></td>
                    </tr>
                    <tr>
                        <td>ФИО Директора:</td>
                        <td><b>${requiesites.directorName}</b></td>
                    </tr>
                    <tr>
                        <td>Контактный email:</td>
                        <td><b>${requiesites.contactEmail}</b></td>
                    </tr>
                    <tr>
                        <td>Банк:</td>
                        <td><b>${requiesites.bank}</b></td>
                    </tr>
                    <tr>
                        <td>Код банка:</td>
                        <td><b>${requiesites.bankCode}</b></td>
                    </tr>
                    <tr>
                        <td>Адрес банка:</td>
                        <td><b>${requiesites.bankAddress}</b></td>
                    </tr>
                    <tr>
                        <td>Расчетный счет:</td>
                        <td><b>${requiesites.paymentAccount}</b></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <p><a href="${contextPath}${editRequisites}">Редактировать</a></p>
                        </td>
                    </tr>
                </table>
                <span>* Реквизиты компании необходимы для выставления счета при оплате.</span>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card-panel">
                <%--<c:if test="${company.currentTariff.trial}">--%>
                    <%--<p class="text-right"><a href="${contextPath}/tariffs" target="_blank">--%>
                        <%--<span class="badge red">ПРОБНЫЙ ПЕРИОД</span></a>--%>
                    <%--</p>--%>
                <%--</c:if>--%>
                <%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
                    <%--<div class="text-right">--%>
                        <%--<a href="${contextPath}/admin/companies/${company.id}/profile/tariff">--%>
                            <%--<i class="material-icons md-18">mode_edit</i> Редактировать</a>--%>
                    <%--</div>--%>
                <%--</sec:authorize>--%>
                <%--<h5>Тарифный план: </h5>--%>
                <%--<p><b><a href="${contextPath}/tariffs" target="_blank">${company.currentTariff.name}</a></b></p>--%>

                <%--<span>Начало действия: <joda:format value="${company.currentTariff.startDate}" pattern="d.MM.YYYY"--%>
                                                    <%--dateTimeZone="Europe/Minsk"/></span>--%>
                <%--<br/>--%>
                <%--<span>Окончание действия: <joda:format value="${company.currentTariff.endDate}" pattern="d.MM.YYYY"--%>
                                                       <%--dateTimeZone="Europe/Minsk"/></span>--%>
                <%--<br/>--%>
                <%--<span>Тариф: ${company.currentTariff.monthPrice} руб/мес</span>--%>

                <%--<h5>Номер компании в системе: </h5>--%>
                <%--<p><b>${company.contractId}</b></p>--%>

                <%--<h5>Количество уникальных посетителей (${previousMonthName}): </h5>--%>
                <%--<p><b>${countUniqueClientsInPreviousMonth}</b></p>--%>

                <h5>Публичная оферта: </h5>
                <p><b><a href="${contextPath}/public-offer.pdf" target="_blank">Скачать</a></b></p>

                <br/>
                <a href="${contextPath}/company/profile/cards/free" class="btn btn-default btn-sm">Свободные карты</a>
                <br/>
                <%--<a href="${contextPath}/company/profile/products/settings" class="btn btn-default btn-sm">Настройки--%>
                    <%--абонементов</a>--%>
                <%--<br/>--%>
            </div>
        </div>
    </div>
</div>

