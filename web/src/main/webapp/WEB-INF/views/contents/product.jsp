<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.jlife.abon.util.PhoneUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-8">

            <h2 class="ellipsis bold-500">${product.name}</h2>

            <hr>

            <div class="row">
                <div class="col-sm-12 col-md-6">
                    <img class="img-fluid z-depth-1" src="${contextPath}${product.logoPath}" alt="">
                </div>
                <div class="col-sm-12 col-md-6">
                    <p><strong>${product.countOfAttendancesAsString}</strong></p>
                    <p><strong>Цена: <fmt:formatNumber value="${product.price}" type="currency" minFractionDigits="2"
                                                       maxFractionDigits="2"/></strong></p>

                    <p>${product.description}</p>
                </div>
            </div>

            <hr>

            ${product.htmlContent}

        </div>

        <div class="col-md-4">
            <c:set var="company" value="${product.company}" scope="request"/>
            <jsp:include page="blocks/company-card.jsp"/>
        </div>
    </div>

    <hr>

</div>

