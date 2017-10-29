<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <form action="${contextPath}/company/cards?tag=${tag}" method="GET">
        <div class="row">

            <div class="col-md-8">
                <div class="z-depth-1">
                    <div class="md-form input-group px-2 pt-2">
                        <input id="text" type="search" class="form-control" value="${text}" name="text"
                               placeholder="ФИО / Телефон / Номер карты / Внутренний номер"/>
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-default btn-lg">Найти</button>
                        </span>
                    </div>
                </div>

                <div class="text-xs-right">
                    <div class="switch">
                        <label>
                            Реальные
                            <input id="potentialMode" type="checkbox" name="potentialMode" value="POTENTIAL"
                                ${potentialMode == 'POTENTIAL' ? 'checked' : ''} onchange="this.form.submit()">
                            <span class="lever"></span>
                            Потенциальные клиенты
                        </label>
                    </div>
                </div>

                <c:if test="${clients.content.size() == 0 and empty text}">
                    <p class="my-2">Список клиентов пуст. Пожалуйста, добавьте нового клиента.</p>
                </c:if>
                <c:if test="${clients.content.size() == 0 and not empty text}">
                    <p class="my-2">Клиенты не найдены по запросу <strong>"${text}"</strong></p>
                </c:if>
                <c:if test="${clients.content.size() != 0}">
                    <div class="z-depth-1 container-fluid pt-1">
                        <div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <input id="select_all" type="checkbox">
                                    <label for="select_all">Все</label>
                                </div>
                                <div class="col-sm-10 text-xs-right">
                                    Клиентов найдено: <strong>${clients.totalElements}</strong>
                                </div>
                            </div>

                            <hr class="m-0"/>

                            <div class="horizontal-listing">
                                <c:forEach var="client" items="${clients.iterator()}" varStatus="loop">
                                    <div class="row hoverable">
                                        <div class="col-sm-2">
                                            <fieldset class="form-group" style="position: absolute;">
                                                    <%--<input type="checkbox" id="checkbox${loop.index}" class="client-checkbox"--%>
                                                    <%--data-id="${client.id}" data-name="${client.name} ${client.lastName}">--%>
                                                <c:if test="${client.user.allowedSmsReceiving}">
                                                    <input type="checkbox" id="checkbox${loop.index}"
                                                           class="client-checkbox"
                                                           data-uuid="${client.cardUUID}">
                                                    <label for="checkbox${loop.index}"></label>
                                                </c:if>
                                                <c:if test="${not client.user.allowedSmsReceiving}">
                                                    <i class="fa fa-warning opacity-5" aria-hidden="true"
                                                       data-toggle="tooltip" data-placement="top"
                                                       title="Клиент отписался от SMS рассылки"></i>
                                                </c:if>
                                            </fieldset>
                                            <a href="${contextPath}/company/cards/${client.cardUUID}">
                                                <img src="${contextPath}${client.logoPath}"
                                                     class="img-fluid rounded-circle">
                                            </a>
                                        </div>
                                        <div class="col-sm-10">

                                            <h5 style="margin: 5px 0;">
                                                <a href="${contextPath}/company/cards/${client.cardUUID}"
                                                   style="font-weight: bold">${client.name} ${client.lastName}</a>
                                                <c:if test="${client.internalId != null}">
                                                    (${client.internalId})
                                                </c:if>

                                                <span class="pull-right">
                                            <c:forEach var="iterTag" items="${client.tags}">
                                                <a href="${contextPath}/company/cards?tag=${iterTag}"
                                                   class="tag tag-default">${iterTag}</a>
                                            </c:forEach>
                                            <c:if test="${!client.active}">
                                                <span class="tag light-blue darken-4">Архивный</span>
                                            </c:if>
                                            <c:if test="${client.potential}">
                                                <span class="tag light-green darken-4">Потенциальный</span>
                                            </c:if>
                                        </span>
                                            </h5>
                                            <table class="table" style="width: 80%; margin-bottom: 0;">
                                                <tbody>
                                                <tr>
                                                    <td>Карта: ${client.cardUUID}</td>
                                                    <td>Тел: +${client.phone}</td>
                                                </tr>
                                                <c:if test="${not empty client.comment}">
                                                    <tr>
                                                        <td colspan="2">
                                                            Комментарий: <em> ${client.comment}</em>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                </tbody>
                                            </table>

                                            <c:if test="${not client.potential}">
                                                <div class="text-xs-right">

                                                    <a href="${contextPath}/company/cards/${client.cardUUID}/detail"
                                                       class="btn-floating light-blue" style="margin: 0">
                                                        <i class="material-icons md-18">mode_edit</i></a>

                                                    <a href="${contextPath}/company/cards/${client.cardUUID}/enroll"
                                                       class="btn btn-default btn-sm ${client.potential ? "disabled" : ""}">Зачислить </a>

                                                    <a href="${contextPath}/company/cards/${client.cardUUID}"
                                                       class="btn btn-default btn-sm">Aбонементы</a>

                                                </div>
                                            </c:if>
                                        </div>
                                    </div>

                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="col-md-4">
                <c:if test="${company.currentTariff.dead}">
                    <jsp:include page="../components/not-avalible-card.jsp"/>
                </c:if>
                <c:if test="${!company.currentTariff.dead}">

                    <div class="card">
                        <div class="card-block">

                            <a href="${contextPath}/company/reports/clients-full" class="btn btn-danger btn-block">
                                Отчет по всем клиентам</a>
                            <hr/>
                                <%--<c:if test="${potentialMode == 'REAL'}">--%>
                            <a href="${contextPath}/company/cards/new" class="btn btn-primary btn-block">
                                <i class="fa fa-user-plus left"></i> Добавить клиента</a>
                                <%--</c:if>--%>
                                <%--<c:if test="${potentialMode == 'POTENTIAL'}">--%>
                                <%--<a href="${contextPath}/company/cards/new" class="btn btn-primary btn-block">--%>
                                <%--<i class="fa fa-user-plus left"></i> Добавить потенциального клиента</a>--%>
                                <%--</c:if>--%>

                            <hr/>
                            <a href="${contextPath}/company/clients/birthday"
                               class="btn btn-default btn-sm btn-block">Ближайшие дни рождения</a>
                            <button id="showSmsDialogBtn" type="button" class="btn btn-default btn-sm btn-block"
                                    data-toggle="modal" data-target="#modalSms" disabled>Отправить SMS выбранным
                            </button>
                        </div>
                    </div>

                    <c:if test="${potentialMode != 'POTENTIAL'}">
                        <div class="card">
                            <div class="card-header white-text">
                                Фильтры
                            </div>
                            <div class="card-block">

                                <select class="mdb-select" name="selectedActiveMode">
                                    <option value="BOTH" ${selectedActiveMode == "BOTH" ? "selected" : ""}>Все клиенты</option>
                                    <option value="ONLY_ACTIVE" ${selectedActiveMode == "ONLY_ACTIVE" ? "selected" : ""}>Только активные клиенты</option>
                                    <option value="ONLY_NOT_ACTIVE" ${selectedActiveMode == "ONLY_NOT_ACTIVE" ? "selected" : ""}>Только архивные клиенты</option>
                                </select>

                                <select class="mdb-select" name="selectedProductId">
                                    <option value="" ${selectedProductId == null ? "selected" : ""}>Все типы абонемента</option>
                                    <c:forEach var="product" items="${products}">
                                        <option value="${product.id}" ${selectedProductId == product.id ? "selected" : ""}>${product.name}</option>
                                    </c:forEach>
                                </select>

                                <select class="mdb-select" name="selectedAbonnementMode">
                                    <option value="ALL" ${selectedAbonnementMode == "ALL" ? "selected" : ""}>Все абонементы</option>
                                    <option value="HAS_ACTIVE" ${selectedAbonnementMode == "HAS_ACTIVE" ? "selected" : ""}>Есть активные абонементы</option>
                                    <option value="HAS_ONLY_NOT_ACTIVE" ${selectedAbonnementMode == "HAS_ONLY_NOT_ACTIVE" ? "selected" : ""}>Есть только неактивные абонементы</option>
                                    <option value="HAS_ANY" ${selectedAbonnementMode == "HAS_ANY" ? "selected" : ""}>Есть хотя бы один абонемент</option>
                                    <option value="HAS_NONE" ${selectedAbonnementMode == "HAS_NONE" ? "selected" : ""}>Нет ни одного абонемента</option>
                                </select>

                                <fieldset class="form-group">
                                    <input type="checkbox" id="expiringCheckbox"
                                           name="expiringAbonnement" ${expiringAbonnement ? "value='true' checked='checked'" : ""}>
                                    <label for="expiringCheckbox">Заканчивается абонемент</label>
                                </fieldset>

                                <div class="text-xs-center">
                                    <button type="submit" class="btn btn-default">Показать</button>
                                </div>
                            </div>
                        </div>


                        <c:if test="${tags.size() != 0}">
                            <div class="card">
                                <div class="card-header white-text">
                                    Поиск по тегам
                                </div>
                                <div class="card-block">
                                    <c:forEach var="iterTag" items="${tags}">
                                        <c:if test="${iterTag == tag}">
                                            <a href="${contextPath}/company/cards?text=${text}&expiringAbonnement=${expiringAbonnement}&selectedProductId=${selectedProductId}&selectedActiveMode=${selectedActiveMode}&selectedAbonnementMode=${selectedAbonnementMode}"
                                               class="chip light-blue white-text">${iterTag}</a>
                                        </c:if>
                                        <c:if test="${iterTag != tag}">
                                            <a href="${contextPath}/company/cards?tag=${iterTag}&text=${text}&expiringAbonnement=${expiringAbonnement}&selectedProductId=${selectedProductId}&selectedActiveMode=${selectedActiveMode}&selectedAbonnementMode=${selectedAbonnementMode}"
                                               class="chip">${iterTag}</a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                </c:if>
            </div>
        </div>
    </form>
    <c:if test="${clients.content.size() != 0}">
        <div class="row">
            <div class="col-md-12">
                <jsp:include page="../contents/blocks/pagination.jsp">
                    <jsp:param name="urlPath"
                               value="${contextPath}/company/cards?tag=${tag}&text=${text}&expiringAbonnement=${expiringAbonnement}&selectedProductId=${selectedProductId}&selectedActiveMode=${selectedActiveMode}&selectedAbonnementMode=${selectedAbonnementMode}"/>
                    <jsp:param name="totalPages" value="${clients.totalPages}"/>
                    <jsp:param name="page" value="${clients.number + 1}"/>
                    <jsp:param name="size" value="${clients.size}"/>
                </jsp:include>
            </div>
        </div>
    </c:if>
</div>

<div id="modalSms" class="modal fade modal-ext" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header white-text light-blue">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Отправить SMS</h4>
            </div>
            <div class="modal-body">
                <h5 class="bold-500">Будет отправлено клиентам:
                    <span id="clientCount" class="light-blue-text">0</span>
                </h5>
                <h5 class="h5-responsive mb-2">
                    <small>На вашем балансе:</small>
                    <strong class="mr-2 blue-text">${account.balance} ${account.currency.description}</strong>
                    <small>Цена 1 SMS:</small>
                    <strong class="blue-text">${smsPrice.value} ${smsPrice.currency.description}</strong>
                </h5>

                <form id="smsForm" action="${contextPath}/company/cards/send-sms" method="POST">
                    <div class="form-group">

                        <label for="message">Текст SMS:</label>
                        <textarea id="message" rows="3" required
                                  name="message"
                                  length="350"
                                  maxlength="350"
                                  class="form-control p-1"
                                  placeholder="Пример: Вы получили скидку на посещение нашего тренажерного зала"></textarea>
                        <span id="smsPerMessageWrap" style="font-size: 12px; height: 1px; float: left">Войдет в <span
                                id="smsPerMessage">1</span> СМС</span>
                    </div>

                    <%--<p>Пример сообщения для отправки:</p>--%>
                    <%--<blockquote class="blockquote bq-success">--%>
                    <%--<p class="m-0 mb-0">--%>
                    <%--<span id="previewMessage">Текст сообщения...</span><br/>--%>
                    <%--${company.name}--%>
                    <%--</p>--%>
                    <%--</blockquote>--%>
                    <h5 class="h5-responsive mt-2">
                        <small>Количество СМС:</small>
                        <strong id="smsCount" class="mr-2 blue-text">0</strong>
                        <small>Итоговая сумма:</small>
                        <strong id="totalPrice" class="blue-text">0 руб</strong>
                    </h5>
                    <p id="smsErrorMessage" class="red-text" style="display: none">Стоимость СМС превышает остаток
                        средств на балансе</p>
                    <div class="text-xs-center mt-3">
                        <button id="submitBtn" type="submit" class="btn btn-primary" disabled>Отправить SMS</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    var smsPrice = ${smsPrice.value};
    var balance = ${account.balance};

    $(document).ready(function () {
        $('#message').keyup(function () {
            prepareMessage();
        });
        $('#message').change(function () {
            prepareMessage()
        });
    });

    function prepareMessage() {
        var textarea = $('#message');
        var clients = $('.client-checkbox:checked');
        var length = textarea.val().length;
        var smsCountPerClient = Math.floor((length - 1) / 70) + 1;
        var smsCount = clients.size() * smsCountPerClient;
        var totalPrice = (smsCount * smsPrice).toFixed(2);

        $('#smsPerMessageWrap').toggle(smsCountPerClient > 0);
        $('#smsPerMessage').text(smsCountPerClient);
        $('#smsCount').text(smsCount);
        $('#totalPrice').text(totalPrice + ' ${smsPrice.currency.description}');

        if (totalPrice < balance) {
            $('#submitBtn').prop('disabled', length == 0);
            $('#smsErrorMessage').hide();
        } else {
            $('#smsErrorMessage').show();
            $('#submitBtn').prop('disabled', true);
        }
    }

    $('#modalSms').on('show.bs.modal', function () {
        //var clients = $('.client-checkbox:checked');
        $('#clientCount').text($('.client-checkbox:checked').size());
        $('#message').val(null);
        prepareMessage();
        //$('#smsCount').text(clients.size());
        //$('#totalPrice').text((smsPrice * clients.size()) + ' ${smsPrice.currency.description}');
    });

    $('#select_all').change(function () {
        var checkboxes = $(".client-checkbox");
        if ($(this).is(':checked')) {
            checkboxes.prop('checked', true);
        } else {
            checkboxes.prop('checked', false);
        }
    });

    $(".client-checkbox, #select_all").change(function () {
        $('#showSmsDialogBtn').prop('disabled', $('.client-checkbox:checked').size() == 0)
    });

    $("#smsForm").submit(function (event) {
        var uuds = [];
        $('.client-checkbox:checked').each(function (index) {
            uuds.push($(this).data('uuid'));
        });
        $('<input />').attr('type', 'hidden')
            .attr('name', "uuids")
            .attr('value', uuds.join(','))
            .appendTo('#smsForm');
        return true;
    });
</script>

