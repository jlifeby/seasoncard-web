<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="offset-md-2 col-md-10">

            <p>Всего пользователей: <strong>${users.size()}</strong></p>

            <hr/>

            <c:forEach var="user" items="${users}">
                <div class="card-panel">
                    <div class="row">
                        <div class="col-md-2">
                            <img src="${contextPath}${user.logoPath}" class="img-fluid">
                        </div>
                        <div class="col-md-6">
                            <ul class="list-unstyled">
                                <li>Имя: <strong>${user.name}</strong></li>
                                <li>Фамилия: <strong>${user. lastName}</strong></li>
                                <li>Телефон: <strong>${user.phone}</strong></li>
                                <li>Email: <strong>${user.email}</strong></li>
                                <li>Роли:
                                    <c:if test="${user.roles.contains('ROLE_ADMIN')}">
                                        <span class="label label-danger">ADMIN</span>
                                    </c:if>
                                    <c:if test="${user.roles.contains('ROLE_COMPANY')}">
                                        <span class="label label-warning">COMPANY</span>
                                    </c:if>
                                    <c:if test="${user.roles.contains('ROLE_USER')}">
                                        <span class="label label-info">USER</span>
                                    </c:if>
                                </li>
                            </ul>
                        </div>
                        <div class="col-md-2">
                            <br/>
                            <a href="${contextPath}/admin/users/${user.id}" class="btn btn-default btn-sm">
                                Подробнее
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
