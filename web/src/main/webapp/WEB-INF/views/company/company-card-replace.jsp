<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <jsp:include page="block/client-header.jsp"/>

    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="card-panel">

                <form action="${contextPath}/company/cards/replace" method="POST">

                    <input type="hidden" name="userId" value="${card.user.id}"/>
                    <input type="hidden" name="cardUUID" value="${card.cardUUID}"/>

                    <div class="form-group">
                        <label>Номер карты: <span class="required">*</span></label>
                        <input type="text" id="newCardUUID" class="form-control" path="newCardUUID"
                               name="newCardUUID"
                               pattern="[0-9]{1,8}"
                               required="required"
                               title="Введите цифровой номер карты (1-8 символов)"/>
                    </div>

                    <div class="form-group">
                        <div class="text-xs-center">
                            <button type="submit" class="btn btn-default">Заменить карту</button>
                            <span class="required"><b>*</b> Обязательные поля</span>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>
</div>

