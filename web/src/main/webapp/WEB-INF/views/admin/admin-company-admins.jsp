<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>
        </div>
        <div class="col-md-10">
            <div class="card-panel">
                <jsp:include page="block/company-header.jsp"/>
            </div>
            <h3>Администраторы компании: </h3>
            <div class="card-panel">

                <a href="${contextPath}/admin/companies/${company.id}/admins/new"
                   style="margin-bottom: 20px"
                   class="btn btn-default btn-sm pull-right">Добавить администратора</a>


                <table class="table table-hover table-bordered table-condensed" data-toggle="table" data-sort-name="#"
                       data-sort-order="acs">
                    <tr>
                        <th data-field="#" data-sortable="true">#</th>
                        <th data-field="name" data-sortable="true">Имя</th>
                        <th data-field="email" data-sortable="true">Email</th>
                        <th data-field="phone" data-sortable="true">Тел.</th>
                        <th data-field="edit" data-sortable="true"></th>
                    <tr>
                        <c:forEach var="user" items="${users}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${user.name} ${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>
                            <a href="${contextPath}/admin/companies/${company.id}/admins/${user.id}"
                               style="margin: 0px;"
                               class="btn btn-default btn-xs">Редактировать</a>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
