<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <jsp:include page="block/client-header.jsp"/>

    <div class="row">
        <div class="col-sm-12 col-md-8 offset-md-2">

            <form id="enrollForm" action="${contextPath}/company/cards/enroll/save" method="POST">

                <input id="cardUUID" type="hidden" name="cardUUID" value="${cardUUID}"/>
                <input id="productId" type="hidden" name="productId"/>

                <c:if test="${empty products}">
                    Доступные абонементы отсутствуют.
                </c:if>

                <c:if test="${not empty products}">
                <div class="z-depth-1 no-padding">
                    <div class="container-fluid horizontal-listing">
                            <c:forEach var="product" items="${products}" varStatus="loop">
                                <div class="row hoverable">
                                    <div class="col-sm-3">
                                        <img src="${contextPath}${product.logoPath}" alt="" title=""
                                             class="img-fluid">
                                    </div>
                                    <div class="col-sm-9">
                                        <h4 class="ellipsis bold-500">${product.name}</h4>

                                        <ul style="list-style: none; margin: 0; padding: 0">
                                            <li><span class="bold-500">${product.countOfAttendancesAsString}</span></li>
                                            <li><i class="fa fa-money"></i> Цена: <fmt:formatNumber
                                                    value="${product.price}" type="currency" minFractionDigits="2"
                                                    maxFractionDigits="2"/></li>
                                        </ul>

                                        <button type="button"
                                                onclick="$('#productId').val('${product.id}');
                                                        setDialogDate('${product.preferredStartingDate}',
                                                        '${product.abonnementType}', ${product.countOfAttendances}, ${product.price}, ${product.countOfUnits}, '${product.unitName}')"
                                                class="btn btn-default btn-sm pull-right"
                                                data-toggle="modal" data-target="#confirm-dialog">Зачислить абонемент
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </c:if>

                <div id="confirm-dialog" class="modal fade" role="dialog" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Зачисление абонемента</h4>
                            </div>
                            <div class="modal-body">
                                <p>Абонемент будет зачислен <strong>${card.user.name} ${card.user.lastName}</strong>?
                                </p>

                                <div class="form-group" id="attendanceLabel">
                                    <label>Количество посещений:</label>
                                    <input id="attendances" type="number" class="form-control" name="attendances"
                                           min="1" max="999" required/>
                                    <p id="unlimited" style="display: none" style="font-size: 1.1rem;">НЕОГРАНИЧЕНО</p>
                                </div>

                                <div class="form-group" id="unitLabel">
                                    <label>Количество единиц:</label>
                                    <input id="units" type="number" class="form-control" name="units"
                                           min="1" max="999" required/>
                                    <label>Название единицы:</label>
                                    <p id="unitName" style="" style="font-size: 1.1rem;"></p>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label>Цена:</label>
                                            <input id="price" type="number" step="0.01" min="0.01" max="100000000"
                                                   class="form-control" name="price" required/>
                                        </div>
                                        <div class="col-md-8">
                                            <label>Скидка:</label>
                                            <select name="promotionId" class="browser-default"
                                                    style="margin-top: 10px; max-width: 300px;">
                                                <option value="">Выберите скидку</option>
                                                <c:forEach var="promotion" items="${promotions}">
                                                    <option value="${promotion.id}">${promotion.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Дата действия абонемента:</label>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <input class="form-control readonly" type="text" name="startDate" readonly/>
                                        </div>
                                        <div class="col-md-1" style="padding-top: 12px;">
                                            <span>по</span>
                                        </div>
                                        <div class="col-md-3">
                                            <input class="form-control readonly" type="text" name="endDate" readonly/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Тренер:</label>
                                    <select name="trainerId" class="browser-default">
                                        <option value="">Без тренера</option>
                                        <c:forEach var="trainer" items="${trainers}">
                                            <option value="${trainer.id}">${trainer.name} ${trainer.lastName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Комментарий:</label>
                                    <textarea name="message" rows="2"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer text-right">
                                <button type="button" class="btn btn-default waves-effect waves-light"
                                        data-dismiss="modal">Отмена
                                </button>
                                <button type="submit" class="btn btn-default waves-effect waves-light">Зачислить
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    var basePrice;
    function setDialogDate(preferred, type, attendances, price, units, unitName) {
        basePrice = price;
        if (type === 'BY_TIME') {
            $('#unitLabel').hide();
            $('#attendanceLabel').show();
            $('#unlimited').show();
            $("input#units").removeAttr('required');
            $("input#attendances").attr('required', 'required');
            $('#attendances').hide();
            $('#attendances').val(999);
        } else if (type === 'BY_ATTENDANCE') {
            $('#attendanceLabel').show();
            $('#unlimited').hide();
            $('#unitLabel').hide();
            $("input#units").removeAttr('required');
            $("input#attendances").attr('required', 'required');
            $('#attendances').show();
            $('#attendances').val(attendances);
        } else if (type === 'BY_UNIT') {
            $('#attendanceLabel').hide();
            $('#unitLabel').show();
            $("input#attendances").removeAttr('required');
            $("input#units").attr('required', 'required');
            $('#units').val(units);
            $('#unitName').text(unitName);
        }
        $('#price').val(price);

        var startDate;
        var endDate;
        if (preferred === 'PURCHASE_DATE') {
            startDate = moment();
            endDate = moment().add(1, 'months');
        } else if (preferred === 'MONTH_START') {
            if (moment().date() < 10) {
                startDate = moment().startOf('month');
                endDate = moment().endOf('month');
            } else {
                startDate = moment().add(1, 'months').startOf('month');
                endDate = moment().add(1, 'months').endOf('month');
            }
        }
        $('input[name=startDate]').pickadate({
            selectMonths: true,
            selectYears: true,
            clear: '',
            onOpen: function() {
                $('.picker').appendTo('body');
            }
        }).pickadate('picker').set('select', startDate.toDate());

        $('input[name=endDate]').pickadate({
            selectMonths: true,
            selectYears: true,
            clear: '',
            onOpen: function() {
                $('.picker').appendTo('body');
            }
        }).pickadate('picker').set('select', endDate.toDate());
    }


    $("select[name=promotionId]").change(function () {
        var value = $("select[name=promotionId] option:selected").val();
        if (value && value.length > 0) {
            var formData = new FormData();
            formData.append('productId', $('#productId').val());
            formData.append('promotionId', value);
            $.ajax('/company/cards/${card.cardUUID}/calculate-new-price', {
                method: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    $('#price').val(data);
                    $('#price').attr('readonly', 'readonly');
                },
                error: function (e) {
                    showError('Ошибка формирования цены со скидкой!')
                }
            });
        } else {
            $('#price').val(basePrice);
            $("#price").removeAttr('readonly');
        }
    });

</script>

<jsp:include page="block/birthday.jsp"/>