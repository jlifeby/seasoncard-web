<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>--%>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>

<%--<c:set var="contextPath" value="${pageContext.request.contextPath}"/>--%>

<%--<div class="container">--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-2">--%>
            <%--<a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>--%>
        <%--</div>--%>
        <%--<div class="col-md-10">--%>
            <%--<h3>Редактирование тарифа для компании: </h3>--%>
            <%--<div class="card-panel">--%>
                <%--<div class="row">--%>
                    <%--<div class="col-md-1">--%>
                        <%--<img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">--%>
                    <%--</div>--%>
                    <%--<div class="col-md-11">--%>
                        <%--<h4>${company.name}</h4>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="card-panel">--%>

                <%--<div class="row">--%>
                    <%--<div class="col-md-8 offset-md-2">--%>

                        <%--<h5>Заполните данные: </h5>--%>

                        <%--<br/>--%>

                        <%--<form action="${contextPath}/admin/companies/${company.id}/profile/tariff"--%>
                              <%--modelAttribute="tariffForm" method="POST">--%>

                            <%--<c:if test="${tariff.id != null}">--%>
                                <%--<input name="id" type="hidden" value="${tariff.id}"/>--%>
                            <%--</c:if>--%>

                            <%--<div class="form-group">--%>
                                <%--<label>Дата начала <span class="required">*</span></label>--%>
                                <%--<input name="startDate" class="form-control" type="text" required="required"/>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label>Дата окончания <span class="required">*</span></label>--%>
                                <%--<input name="endDate" class="form-control" type="text" required="required"/>--%>
                            <%--</div>--%>

                            <%--<div class="text-xs-center">--%>
                                <%--<button id="submitBtn" type="submit" class="btn btn-default">Обновить тариф</button>--%>
                                <%--<span class="required"><b>*</b> Обязательные поля</span>--%>
                            <%--</div>--%>
                        <%--</form>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<%--<script>--%>
    <%--$(document).ready(function () {--%>
        <%--var startDate = ${tariff.startDate.getMillis()};--%>
        <%--var endDate = ${tariff.endDate.getMillis()};--%>

        <%--$('input[name=startDate]').pickadate({--%>
            <%--selectMonths: true,--%>
            <%--selectYears: 10,--%>
            <%--clear: '',--%>
            <%--onClose: function () {--%>
                <%--$(document.activeElement).blur();--%>
            <%--}--%>
        <%--}).pickadate('picker').set('select', startDate);--%>

        <%--$('input[name=endDate]').pickadate({--%>
            <%--selectMonths: true,--%>
            <%--selectYears: 10,--%>
            <%--clear: '',--%>
            <%--onClose: function () {--%>
                <%--$(document.activeElement).blur();--%>
            <%--}--%>
        <%--}).pickadate('picker').set('select', endDate);--%>

    <%--});--%>
<%--</script>--%>