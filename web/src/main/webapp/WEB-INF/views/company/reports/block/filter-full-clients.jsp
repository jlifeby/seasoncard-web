<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card-panel">
    <h3 style="display: inline-block;">Описание</h3>
    <h5>Для формирования таблицы всех клиентов с контактными данными используйте кнопку <b>"Сформировать отчет"</b>.
    </h5>
    <h5>Чтобы скачать Excel файл с клиентами используйте кнопку <b>"Экспорт в Excel"</b></h5>

    <form:form action="${param.urlPath}" modelAttribute="reportFilterForm" method="POST">


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
