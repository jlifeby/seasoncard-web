<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="confirm-dialog" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="markAttendedForm" action="${contextPath}/company/mark-attended" method="POST">
                <input id="abonnementId" type="hidden" name="abonnementId"/>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Отметка посещения</h4>
                </div>
                <div class="modal-body">

                    <ul class="striped">
                        <li>Абонемент: <span id="productName" class="ellipsis bold-500"></span></li>
                        <li id="markUnits"> Сколько отметить (<span id="unitName"></span>):
                            <select id="markUnitsSelect" name="markUnits" class="browser-default"
                                    style="display: inline; padding: 0 5px;"></select>
                        </li>
                        <li>Дата посещения:
                            <input name="visitDate" class="readonly" type="text"
                                   style="width:80px; padding: 0 5px;"/>
                        </li>
                        <li>Тренер:
                            <select id="trainerSelect" name="trainerId" class="browser-default"
                                    style="display: inline; padding: 0 5px;">
                                <option value="">Без тренера</option>
                                <c:forEach var="trainer" items="${trainers}">
                                    <option value="${trainer.id}">${trainer.name} ${trainer.lastName}</option>
                                </c:forEach>
                            </select>
                        </li>
                        <li>
                            <fieldset class="form-group">
                                <input type="checkbox" id="skipped" name="skipped">
                                <label for="skipped"> Пропуск</label>
                            </fieldset>
                        </li>
                    </ul>
                </div>
                <div class="modal-footer text-right">
                    <button type="button" class="btn btn-default waves-effect waves-light" data-dismiss="modal">Нет
                    </button>
                    <button type="submit" class="btn btn-default waves-effect waves-light">Да</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function selectAbonForAttendance(abon) {
        $('#abonnementId').val(abon.abonnementId);
        $('#productName').text(abon.productName);

        var markUnitsSelect = $('#markUnits');
        markUnitsSelect.hide();
        $("#trainerSelect").val($("#trainerSelect option:first").val());
        $("#trainerSelect").val(abon.trainerId);

        if (abon.abonnementType == 'BY_UNIT') {
            $('#unitName').text(abon.unitName);
            $('#markUnitsSelect').empty();
            for (var i = 1; i <= abon.availableCountOfUnits; i++) {
                $('#markUnitsSelect').append('<option value="' + i + '"> ' + i + ' </option>');
            }
            markUnitsSelect.show();
        }

        var date = new Date();
        $('input[name=visitDate]').pickadate({
            selectMonths: true,
            selectYears: true,
            min: abon.startDate,
            max: abon.endDate,
            clear: '',
            onOpen: function () {
                $('.picker').appendTo('body');
            }
        }).pickadate('picker').set('select', [date.getFullYear(), date.getMonth(), date.getDate()]);

        $('#confirm-dialog').modal('show');
    }
</script>
