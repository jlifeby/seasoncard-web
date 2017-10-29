<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 offset-md-2 col-md-8">
            <div class="card-panel">

                <h4 class="text-xs-center">Управление тарифами</h4>

                <form:form action="${contextPath}/admin/tariffs/detail" modelAttribute="tariffForm" method="POST">

                    <c:if test="${tariffForm.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <div class="form-group">
                        <label>Название: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="name" name="name" required="required"
                                    placeholder="Пример: Малый бизнесс"/>
                    </div>

                    <%--<div class="form-group">--%>
                    <%--<label>Цена: <span class="required">*</span></label>--%>
                    <%--<form:input path="monthPrice" type="number" step="0.01" min="0.00" max="100000000"--%>
                    <%--class="form-control" placeholder="Пример: 50" required="required"/>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label>Максимальное количество клиентов: <span class="required">*</span></label>
                        <form:input path="maxClients" type="number" step="1" min="1" max="1000000"
                                    class="form-control" placeholder="Пример: 50" required="required"/>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="free" cssStyle="position: initial; visibility: visible;"/>
                            Бесплатный
                        </label>
                    </div>

                    <div class="form-group">
                        <label>
                            <form:checkbox path="active" cssStyle="position: initial; visibility: visible;"/>
                            Активный
                        </label>
                    </div>
                    <div class="form-group">
                        <table>
                            <tr>
                                <th>Страна</th>
                                <th>Цена</th>
                            </tr>
                            <c:forEach items="${tariffForm.localizedMonthPrice}" var="localizedMonthPrice"
                                       varStatus="status">
                                <tr>
                                    <td>${localizedMonthPrice.key}</td>
                                    <td><input name="localizedMonthPrice['${localizedMonthPrice.key}']"
                                               value="${localizedMonthPrice.value}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>

                    <div class="text-xs-center">
                        <input id="submitBtn" type="submit" class="btn btn-default" value="Сохранить тариф">
                        <span class="required"><b>*</b> Обязательные поля</span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>