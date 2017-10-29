<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="card">
    <div class="card-header danger-color-dark white-text">
        Сервис недоступен
    </div>
    <div class="card-block text-xs-center">
        <h4 class="font-bold">Доступ к системе приостановлен</h4>
        <p>Для возобновления доступа к системе необходимо погасить задолженность.</p>
        <%--<a href="/" class="btn btn-primary">Пополнить баланс</a>--%>
        <hr>
    </div>
</div>