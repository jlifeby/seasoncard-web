<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h4>Контакты компаний</h4>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>№ Контракта</th>
                    <th>Дата контракта</th>
                    <th>Название</th>
                    <th>Email</th>
                    <th>Администратор</th>
                    <th>Телефон Адм.</th>
                    <th>Email Адм.</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="company" items="${companies}" varStatus="loop">
                    <tr>
                        <td>${company.contractId}</td>
                        <td><joda:format value="${company.contractDate}" pattern="d.MM.YYYY"
                                         dateTimeZone="Europe/Minsk"/></td>
                        <td><a href="${contextPath}/admin/companies/${company.id}">${company.name}</a></td>
                        <td>${company.email}</td>
                        <c:if test="${company.firstAdmin != null}">
                            <td>${company.firstAdmin.fullName}</td>
                            <td>${company.firstAdmin.phone}</td>
                            <td>${company.firstAdmin.email}</td>
                        </c:if>
                        <c:if test="${company.firstAdmin == null}">
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
