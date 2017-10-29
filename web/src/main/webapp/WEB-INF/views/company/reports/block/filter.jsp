<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card-panel">
    <h5 style="display: inline-block;">Фильтр</h5>
    <a href="${param.urlPath}" class="btn btn-default btn-xs pull-right">Сбросить фильтры</a>

    <form:form action="${param.urlPath}" modelAttribute="reportFilterForm" method="POST">

        <c:if test="${fn:contains(param.showParam, 'trainerId')}">
            <div class="form-group">
                <label>Тренер: </label>
                <form:select path="trainerId" required="required" cssClass="browser-default"
                             cssStyle="display: inline; margin: 5px 10px;">
                    <c:forEach items="${trainers}" var="trainer">
                        <form:option value="${trainer.id}">${trainer.name} ${trainer.lastName}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </c:if>

        <c:if test="${fn:contains(param.showParam, 'reportTrainerType')}">
            <div class="form-group">
                <label>Тип: </label>
                <form:select path="reportTrainerType" required="required" cssClass="browser-default"
                             cssStyle="display: inline; margin: 5px 10px;">
                    <form:options items="${reportTrainerTypes}" itemLabel="description"/>
                </form:select>
            </div>
        </c:if>

        <ul>
            <form:radiobuttons path="reportFilterType"
                               items="${filterTypeValues}" itemLabel="description"
                               element="li"/>
        </ul>

        <div class="row">
            <label class="col-md-1" style="padding-right: 0">C:</label>
            <div class="col-sm-4 col-md-2" style="padding: 0">
                <form:input path="startDate" type="text" name="startDate" readonly="true"
                            cssClass="datepicker" cssStyle="margin: 0; height: 1.1rem;"/>
            </div>
            <label class="col-md-1" style="padding-right: 0">ПО:</label>
            <div class="col-sm-4 col-md-2" style="padding: 0">
                <form:input path="endDate" type="text" name="startDate" readonly="true" cssClass="datepicker"
                            cssStyle="margin: 0; height: 1.1rem;"/>
            </div>
        </div>

        <div class="text-xs-center">
            <button type="submit" class="btn btn-default" onclick="this.form.action = '${param.urlPath}'">
                Сформировать отчет
            </button>
            <button type="submit" class="btn btn-default" onclick="this.form.action = '${param.urlPath}/export'">
                <i class="fa fa-file-excel-o left"></i> Экспорт в Excel
            </button>
        </div>
    </form:form>
</div>

<script>
    $(document).ready(function () {
        $("input[name=reportFilterType]").click(function () {
            onClick();
        });
        function onClick() {
            var value = $("input[name=reportFilterType]:checked").val();
            if (value == 'BY_CUSTOM') {
                $("input[name=startDate]").removeAttr('disabled');
                $("input[name=endDate]").removeAttr('disabled');
                if (!$("input[name=startDate]").val() || !$("input[name=endDate]").val()) {
                    $('input[name=startDate]').pickadate('picker').set('select', moment().startOf('month').toDate());
                    $('input[name=endDate]').pickadate('picker').set('select', moment().toDate());
                }
            } else {
                $("input[name=startDate]").attr('disabled', 'disabled');
                $("input[name=endDate]").attr('disabled', 'disabled');
            }
        }

        onClick();
    });
</script>
