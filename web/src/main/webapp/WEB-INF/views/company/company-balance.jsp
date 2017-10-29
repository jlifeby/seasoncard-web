<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="formProlongationUrl" value="${contextPath}/admin/companies/${company.id}/tariff/prolongation"/>
    <c:set var="paginationUrl" value="${contextPath}/admin/companies/${company.id}/balance?text="/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_COMPANY')">
    <c:set var="formProlongationUrl" value="/company/tariff/prolongation"/>
    <c:set var="paginationUrl" value="${contextPath}/company/balance?text="/>
</sec:authorize>

<div class="container">

    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <section class="section">
            <h2>Компания: ${company.name}</h2>
        </section>
    </sec:authorize>

    <section class="section">
        <div class="row">
            <div class="col-md-4">
                <div class="card card-cascade cascading-admin-card">
                    <div class="admin-up">
                        <i class="fa fa-money blue darken-3"></i>
                        <div class="data">
                            <p>Баланс (${company.country.currency.name()})</p>
                            <h3>${account.balance} ${account.currency.description}</h3>
                        </div>
                    </div>
                    <div class="card-block">
                        <%--<p class="card-text">Better than last week (25%)</p>--%>
                        <%--<a href="#" class="btn btn-primary btn-sm m-0 pull-right">Пополнить</a>--%>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <div class="text-right">
                                <a href="${contextPath}/admin/companies/${company.id}/profile/add-funds">
                                    <i class="material-icons md-18">mode_edit</i> Пополнить счет</a>
                            </div>
                        </sec:authorize>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card card-cascade cascading-admin-card">

                    <div class="admin-up">
                        <i class="fa fa-table deep-purple darken-4"></i>
                        <div class="data">
                            <p>Текущий тариф (${company.monthPrice.value} ${company.monthPrice.currency.description}/мес)</p>
                            <h3>${company.currentTariff.name}</h3>
                        </div>
                    </div>
                    <div class="card-block">
                        <div class="row">
                            <div class="col-xs-5 pr-0">
                                <span>До: <b><joda:format value="${company.currentTariff.endDate}" pattern="d.MM.YYYY"
                                                          dateTimeZone="Europe/Minsk"/></b></span>
                            </div>
                            <div class="col-xs-7 pl-0">
                                <button class="btn btn-primary btn-sm m-0 pull-right" data-toggle="modal"
                                        data-target="#modalTariffProlongation">Изменить/Продлить
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card card-cascade cascading-admin-card">
                    <div class="admin-up">
                        <i class="fa fa-users indigo"></i>
                        <div class="data">
                            <p>Активных посетителей (${previousMonthName})</p>
                            <h3> ${countUniqueClientsInPreviousMonth}
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="${contextPath}/admin/companies/${company.id}/activity" style="font-size: 1rem">(Подробнее)</a>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_COMPANY')">
                                    <a href="${contextPath}/company/activity" style="font-size: 1rem">(Подробнее)</a>
                                </sec:authorize>
                            </h3>
                        </div>
                    </div>
                    <div class="card-block">
                        <p class="card-text" style="font-size: 12px;">Посетитель, который купил хотя бы один абонемент
                            или посетил зал хотя бы один раз</p>
                    </div>
                </div>
            </div>
            <%--<div class="col-md-3">--%>
            <%--<div class="card card-cascade cascading-admin-card">--%>
            <%--<div class="admin-up">--%>
            <%--<i class="fa fa-bar-chart purple"></i>--%>
            <%--<div class="data">--%>
            <%--<p>TODAY'S ORGANIC TRAFFIC</p>--%>
            <%--<h3>2000</h3>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="card-block">--%>
            <%--<progress class="progress progress-success" value="25" max="100">25%</progress>--%>
            <%--<p class="card-text">Better than last week (25%)</p>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </section>
    <section class="section">
        <div class="row mb-1">
            <div class="col-xs-12 col-sm-4">
                <h5 class="h5-responsive"><span
                        class="tag big-tag info-color">${smsPrice.value} ${smsPrice.currency.description}</span> за одну
                    СМС</h5>
            </div>
        </div>
    </section>
    <section class="section mb-0">
        <div class="row">
            <div class="col-md-12">
                <div class="card ">
                    <div class="card-block">
                        <h3 class="mb-2">Операции по счету</h3>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Дата</th>
                                    <th>Тип</th>
                                    <th>Сумма</th>
                                    <th>Детали</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="transaction" items="${transactions.iterator()}">
                                    <tr>
                                        <td><joda:format value="${transaction.createdDate}"
                                                         pattern="d MMM yyyy HH:mm"
                                                         dateTimeZone="Europe/Minsk"/></td>
                                        <td><spring:message code="TransactionType.${transaction.type.name()}"
                                                            text="-"/></td>
                                        <td>${transaction.value} ${transaction.currency.description}</td>
                                        <td>${transaction.details.comment}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="row mb-2">
        <div class="col-md-12">
            <jsp:include page="../contents/blocks/pagination.jsp">
                <jsp:param name="urlPath" value="${paginationUrl}"/>
                <jsp:param name="totalPages" value="${transactions.totalPages}"/>
                <jsp:param name="page" value="${transactions.number + 1}"/>
            </jsp:include>
        </div>
    </div>
</div>


<div id="modalTariffProlongation" class="modal fade modal-ext" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header white-text light-blue">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Продлить тарифный план</h4>
            </div>
            <!--Body-->
            <div class="modal-body">

                <ul class="striped min">
                    <li>Баланс: <span class="light-blue-text">${account.balance} ${account.currency.description}</span>
                    </li>
                    <li>Окончание текущего тарифа: <span class="light-blue-text">
                        <joda:format value="${company.currentTariff.endDate}" pattern="d MMMM YYYY"
                                     dateTimeZone="Europe/Minsk"/></span>
                    </li>
                    <li>Активных посетителей (${previousMonthName}): <span
                            class="light-blue-text">${countUniqueClientsInPreviousMonth}</span>
                    </li>
                </ul>
                <br/>
                <br/>

                <form action="${formProlongationUrl}" method="POST">
                    <input type="hidden" name="companyId" value="${company.id}">
                    <div class="md-form">
                        <select id="tariffSelect" class="mdb-select" name="tariffId">
                            <%--<option value="" disabled selected>Choose your option</option>--%>
                            <c:forEach var="tariff" items="${tariffs}">
                                <option value="${tariff.id}">${tariff.name} - до ${tariff.maxClients} клиентов: ${tariff.localTotalPrice.value} ${tariff.localTotalPrice.currency.description}</option>
                            </c:forEach>
                        </select>
                        <label>Выберите тариф</label>
                    </div>
                    <div class="md-form">
                        <select id="periodSelect" class="mdb-select" name="countOfMonth">
                            <%--class="mdb-select"--%>
                            <c:forEach var="paymentPeriod" items="${paymentPeriods}">
                                <option value="${paymentPeriod.periodValue}">${paymentPeriod.description}</option>
                            </c:forEach>
                        </select>
                        <label>Выберите период оплаты</label>
                    </div>

                    <h5 class="h5-responsive mb-2">
                        <small>Тариф будет действовать до:</small>
                        <strong><span id="paymentPeriod" class="blue-text">-</span></strong>
                    </h5>

                    <h4 class="h4-responsive mb-2">
                        <small>Скидка:</small>
                        <strong><span id="discount" class="blue-text">-</span></strong>
                    </h4>

                    <h3 class="h3-responsive">
                        <small>Итоговая сумма:</small>
                        <strong><span id="totalPrice" class="blue-text">-</span></strong>
                    </h3>

                    <p id="errorMessage" class="red-text" style="display: none">
                        Сумма к оплате больше суммы на вашем балансе
                    </p>

                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="form-group">
                            <label>Другая цена: </label>
                            <input id="changedPriceInput" name="changedPrice" class="form-control" type="text"
                                   value="0"/>
                        </div>

                        <script>
                            $(document).ready(function () {
                                $('#changedPriceInput').change(function () {
                                    $('#submitBtn').prop("disabled", false);
                                });
                            });
                        </script>

                        <div class="form-group">
                            <label>Дата начала: </label>
                            <input id="startDate" name="startDate" type="text" class="form-control datepicker"/>
                        </div>

                        <div class="form-group">
                            <label>Дата окончания: </label>
                            <input id="endDate" name="endDate" type="text" class="form-control datepicker"/>
                        </div>
                    </sec:authorize>

                    <div class="text-xs-center mt-4">
                        <button id="submitBtn" type="submit" class="btn btn-primary">Оплатить тариф</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>

    $(document).ready(function () {
    });

    $('#modalTariffProlongation').on('shown.bs.modal', function () {
        calculatePrice();
    });

    $("select#tariffSelect").change(function () {
        calculatePrice();
    });

    $("select#periodSelect").change(function () {
        calculatePrice();
    });

    function calculatePrice() {
        var companyId = '${company.id}';
        var tariffId = $("select#tariffSelect option:selected").val();
        var countOfMonth = $("select#periodSelect option:selected").val();
        var balance = ${account.balance};

        var url = '/api/company/tariffs/calculate';
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        url = '/api/admin/tariffs/calculate';
        </sec:authorize>

        $.ajax(url + '?companyId=' + companyId +
            '&countOfMonth=' + countOfMonth +
            '&tariffId=' + tariffId, {
            method: "GET",
            success: function (data) {

                $('#paymentPeriod').text(moment(data.endDate).format('D MMMM YYYY'));
                $('#totalPrice').text(data.localTotalPrice.value + ' ' + Currency[data.localTotalPrice.currency]);

                if (data.appliedPromotionValue) {
                    var discountText = data.appliedPromotionValue + ' ' + Currency[data.localTotalPrice.currency];
                    var discountPersantage = 0;
                    data.appliedPromotions.forEach(function (promotion) {
                        discountPersantage += promotion.promotionValue;
                    });
                    discountText += ' (' + discountPersantage + '%)';
                    $('#discount').text(discountText);
                } else {
                    $('#discount').text('-');
                }

                var invalidBalance = data.localTotalPrice.value > balance;
                invalidBalance ? $('#errorMessage').show() : $('#errorMessage').hide();
                $('#submitBtn').prop("disabled", invalidBalance);

                //Admin
                if ($('#startDate') && $('#endDate')) {
                    $('input#changedPriceInput').val(data.localTotalPrice.value);
                    $('input#startDate').pickadate('picker').set('select', data.startDate);
                    $('input#endDate').pickadate('picker').set('select', data.endDate);
                }
            },
            error: function (e) {
                var responseText = e.responseText;
                try {
                    var apiError = JSON.parse(responseText);
                    var errorMsg = apiError.localizedMsg ? apiError.localizedMsg : apiError.msg;
                    showError(errorMsg);
                } catch (ex) {
                    showError('Ошибка расчета платежа!')
                }
            }
        });
    }
</script>