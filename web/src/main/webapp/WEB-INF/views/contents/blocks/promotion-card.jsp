<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card">
    <div class="card-block">
        <div class="row">
            <div class="col-md-2">
                <img src="${contextPath}${promotion.logoPath}" class="img-fluid"
                     alt="${promotion.name}">
            </div>
            <div class="col-md-10">
                <h4 class="card-title" style="margin-top: 10px"><strong>${promotion.name}</strong></h4>
            </div>

        </div>

        <p class="card-text text-xs-center">${promotion.description}</p>

        <div class="card-footer">
            <span class="tag red">${promotion.promotionValueAsString}</span>
            <c:if test="${not empty promotion.endDate}">
                                    <span class="right tag green">
                                        До <joda:format value="${promotion.endDate}" style="F-"/>
                                    </span>
            </c:if>
        </div>

    </div>
</div>
