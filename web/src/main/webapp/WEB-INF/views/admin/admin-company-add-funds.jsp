<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>
        </div>
        <div class="col-md-6">
            <h3>Пополнение счета для компании: </h3>
            <div class="card-panel">
                <div class="row">
                    <div class="col-md-2">
                        <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
                    </div>
                    <div class="col-md-8">
                        <h4>${company.name}</h4>
                        <p>Страна: ${company.country.localizedName}</p>
                        <p>Валюта: ${company.country.currency.name()} (${company.country.currency.description})</p>
                    </div>
                </div>
            </div>
            <div class="card card-block">
                <form action="${contextPath}/admin/companies/${company.id}/profile/add-funds" method="POST">

                    <%--<div class="form-group">--%>
                    <%--<label>Сейчас на счете:</label>--%>
                    <%--<input disabled class="form-control" value="10"/>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label>Сумма пополнения: <span class="required">*</span></label>
                        <input type="number" name="amount" class="form-control" required="required"/>
                    </div>

                    <div class="form-group">
                        <label>Комментарий: </label>
                        <textarea name="comment" class="form-control" rows="3" maxlength="300"
                                  placeholder="Пример: Пополнение счета"></textarea>
                    </div>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Пополнить</button>
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
