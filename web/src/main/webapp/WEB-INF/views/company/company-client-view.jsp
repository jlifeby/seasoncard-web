<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.jlife.abon.util.PhoneUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <jsp:include page="block/client-header.jsp"/>
    <jsp:include page="block/birthday.jsp"/>

    <c:if test="${!originUser.potential}">

        <!-- Nav tabs -->
        <div class="tabs-wrapper">
            <ul class="nav classic-tabs light-blue" role="tablist">
                <li class="nav-item">
                    <a class="nav-link waves-light active" data-toggle="tab" href="#abon" role="tab">Абонементы</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link waves-light" data-toggle="tab" href="#SMSSend" role="tab">СМС-рассылка</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link waves-light" data-toggle="tab" href="#emailSend" role="tab">Email-рассылка</a>
                </li>
            </ul>
        </div>

        <div class="tab-content card" style="min-height: 200px;">
            <div class="tab-pane fade in active" id="abon" role="tabpanel">
                <h3>Абонементы клиента: <a href="${contextPath}/company/cards/${card.cardUUID}/enroll"
                                           class="btn btn-default btn-sm pull-right">Зачислить абонемент</a></h3>

                <c:set var="abonnements" value="${card.lastAbonnements}"/>
                <c:if test="${abonnements.size() == 0}">
                    Список абонементов пуст.
                </c:if>
                <c:if test="${abonnements.size() != 0}">

                    <c:set var="abonnementsForList" value="${abonnements}" scope="request"/>
                    <jsp:include page="block/abons-2col-list.jsp"/>

                </c:if>

            </div>

            <div class="tab-pane fade" id="SMSSend" role="tabpanel">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card card-block">
                            <c:if test="${not canSendSms}">
                                <p class="text-danger">Недостаточно средств на счете для отправки СМС</p>
                            </c:if>
                            <c:if test="${canSendSms}">
                                <c:if test="${not originUser.allowedSmsReceiving}">
                                    <p class="text-danger">${card.user.name} ${card.user.lastName} отписался от SMS
                                        рассылок.</p>
                                </c:if>

                                <c:if test="${originUser.allowedSmsReceiving}">
                                    <h5>Номер телефона пользователя: ${card.user.phone}</h5>

                                    <form action="${contextPath}/company/cards/${card.cardUUID}/send-sms" method="POST">
                                        <div class="form-group">

                                            <label for="message">Текст сообщения пользователю:</label>

                                            <textarea id="message" rows="4" required
                                                      name="message"
                                                      length="350"
                                                      maxlength="350"
                                                      class="form-control p-1"
                                                      placeholder="Пример: Вы получили скидку на посещение нашего тренажерного зала"></textarea>
                                            <span id="smsPerMessageWrap"
                                                  style="font-size: 12px; height: 1px; float: left">Войдет в <span
                                                    id="smsPerMessage">1</span> СМС</span>
                                        </div>
                                        <h5 class="h5-responsive mt-2">
                                            <small>Количество СМС:</small>
                                            <strong id="smsCount" class="mr-2 blue-text">0</strong>
                                            <small>Итоговая сумма:</small>
                                            <strong id="totalPrice" class="blue-text">0 руб</strong>
                                        </h5>
                                        <div class="text-xs-center">
                                            <button id="submitSmsBtn" type="submit" class="btn btn-default" disabled>
                                                Отправить SMS
                                            </button>
                                        </div>
                                    </form>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card card-block horizontal-listing">
                            <h5 class="title">Отправленные SMS
                                <span class="tag light-blue">${smsMessages.totalElements}</span></h5>
                            <hr>
                            <div class="container-fluid">
                                <c:forEach var="smsMessage" items="${smsMessages.iterator()}">
                                    <div class="row hoverable">
                                        <div class="col-md-10">
                                            <p class="thin-100" style="font-size: 1rem;"><i class="fa fa-clock-o">
                                                <joda:format
                                                        value="${smsMessage.sentDate}" style="FS"
                                                        dateTimeZone="Europe/Minsk"/></i></p>
                                            <span>${smsMessage.text}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="tab-pane fade" id="emailSend" role="tabpanel">
                <div class="row">
                    <div class="col-md-6 offset-md-3">
                        <c:if test="${company.canSendEmail()}">
                            <c:set var="canReceiveEmail"
                                   value="${originUser.allowedEmailReceiving and originUser.hasAnyEmail()}"/>
                            <c:if test="${not originUser.allowedEmailReceiving}">
                                <p class="text-danger">${card.user.name} ${card.user.lastName} отписался от Email
                                    рассылок.</p>
                            </c:if>
                            <c:if test="${not originUser.hasAnyEmail()}">
                                <p class="text-danger">Email пользователя не задан.</p>
                            </c:if>
                            <%--<p class="text-danger">В день можно отправить только 1 email одному клиенту.</p>--%>
                            <%--<h5>Email пользователя: {card.user.email}</h5>--%>

                            <c:if test="${canReceiveEmail}">
                                <form action="${contextPath}/company/cards/${card.cardUUID}/send-email" method="POST">
                                    <div class="form-group">
                                        <label for="content">Текст сообщения пользователю:</label>
                                        <textarea id="content" name="content" rows="5" class="form-control" required
                                                  placeholder="Пример: Вы получили скидку на посещение нашего тренажерного зала. За скидкой обратиться к администратору"></textarea>
                                    </div>

                                    <div class="text-xs-center">
                                        <button type="submit" class="btn btn-default">Отправить Email</button>
                                    </div>
                                </form>
                            </c:if>
                        </c:if>
                        <c:if test="${not company.canSendEmail()}">
                            <p class="text-danger">На пробном тарифе e-mail рассылка недоступна</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="block/mark-dialog.jsp"/>

        <script>
            $(document).ready(function () {
                $('#message').keyup(function () {
                    prepareMessage();
                });
                $('#message').change(function () {
                    prepareMessage()
                });

                prepareMessage();
            });

            function prepareMessage() {
                var smsPrice = ${smsPrice.value};
                var textarea = $('#message');
                var length = textarea.val().length;
                var smsCountPerClient = Math.floor((length - 1) / 70) + 1;
                var totalPrice = (smsCountPerClient * smsPrice).toFixed(2);
                ;

                $('#smsPerMessageWrap').toggle(smsCountPerClient > 0);
                $('#smsPerMessage').text(smsCountPerClient);
                $('#smsCount').text(smsCountPerClient);
                $('#totalPrice').text(totalPrice + ' ${smsPrice.currency.description}');

                $('#submitSmsBtn').prop('disabled', length == 0);
            }
        </script>

    </c:if>
    <c:if test="${originUser.potential}">

        <div class="card card-block text-xs-center" style="min-height: 200px;">

            <h3 class="light-blue-text"> Потенциальный клиент </h3>

            <p>Для потенциальног клиента не доступна возможность зачисления абонемента.
                При переводе потенциального клиента в реальное он получет SMS с номерам его
                карты и паролем для входа в систему</p>

            <button class="btn btn-primary" data-toggle="modal" data-target="#potentialToRealModal">
                Перевести в раздел ваших клиентов
            </button>
        </div>


        <div id="potentialToRealModal" class="modal fade modal-ext" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header white-text light-blue">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">Перевести в раздел ваших клиентов</h4>
                    </div>
                    <div class="modal-body">
                        <h4 class="">
                            <small>Номер виртуальной карты:</small>
                            <strong class="mr-2 blue-text">${card.cardUUID}</strong>
                        </h4>
                        <p class="mb-2">Клиент получит SMS с номером карты и паролем</p>

                        <form id="smsForm" action="${contextPath}/company/cards/make-client-real" method="POST">
                            <input type="hidden" name="cardUUID" value="${card.cardUUID}"/>
                            <div class="form-group">
                                <label>Номер карты:</label>
                                <input type="text" class="form-control" name="newCardUUID"
                                       pattern="[0-9]{1,8}"
                                       title="Введите цифровой номер карты (1-8 символов)"/>
                            </div>

                            <p class="text-xs-center">Вы можите указать номер пластиковой карты,
                                если хотите передать ее клиенту</p>
                            <div class="text-xs-center mt-3">
                                <button type="submit" class="btn btn-primary">Перевести</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>