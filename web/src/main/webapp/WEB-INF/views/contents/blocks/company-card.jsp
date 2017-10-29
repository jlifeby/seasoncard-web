<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card card-cascade narrower">
    <div class="text-xs-center" style="margin-top: -20px;">
        <a href="${contextPath}/companies/${company.id}">
            <img src="${contextPath}${company.logoPath}" class="img-fluid z-depth-2" alt="${company.name}"
                 style="display: initial;">
        </a>
    </div>
    <div class="card-block">
        <%--<h5 class="red-text"><i class="fa fa-money"></i> Business</h5>--%>
        <h4 class="card-title">${company.name}</h4>
        <p class="card-text">${company.description}</p>

        <table class="table table-striped">
            <tr>
                <td>Сайт</td>
                <td><a href="${company.site}" target="_blank">${company.site}</a></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><a href="mailto:${company.email}">${company.email}</a></td>
            </tr>
            <tr>
                <td>Адрес</td>
                <td>${company.address.toString()}</td>
            </tr>
            <tr>
                <td>Телефоны</td>
                <td>
                    <span class="phone-format">${company.phones[0]}</span>
                    ${not empty company.phones[1] ? "<br/>" : ""}
                    <span class="phone-format">${company.phones[1]}</span>
                    ${not empty company.phones[2] ? "<br/>" : ""}
                    <span class="phone-format">${company.phones[2]}</span>
                </td>
            </tr>
        </table>
        <div class="text-xs-center">
            <a href="${contextPath}/companies/${company.id}" class="btn btn-primary">Подробнее о компании</a>
        </div>
    </div>
</div>
