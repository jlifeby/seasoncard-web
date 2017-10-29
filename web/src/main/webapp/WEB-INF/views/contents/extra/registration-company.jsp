<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-6 offset-md-3">
            <div class="card-panel">

                <h3 class="text-xs-center">Регистрация новой компании</h3>
                <a href="${contextPath}/login" class="pull-right">Уже зарегистрированы</a>
                <br>
                <br>
                <jsp:include page="../blocks/registration-company-form.jsp"/>
                <p style="color: #9e9e9e; margin-top: 10px" class="text-xs-center">Данные для входа будут
                    высланы вам на почту. Информацию о компании вы сможете изменить в своем личном кабинете. Благодарим
                    за регистрацию!</p>
            </div>
        </div>
    </div>
</div>


