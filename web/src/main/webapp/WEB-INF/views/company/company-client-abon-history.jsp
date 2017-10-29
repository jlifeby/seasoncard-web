<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <jsp:include page="block/client-header.jsp"/>

    <h3>История: <span class="bold-500">${consolidatedAbonnementData.product.name}</span></h3>

    <c:if test="${consolidatedAbonnementData.allAbonnements.size() == 0}">
        Список абонементов пуст.
    </c:if>
    <c:if test="${consolidatedAbonnementData.allAbonnements.size() != 0}">

        <c:set var="abonnementsForList" value="${consolidatedAbonnementData.allAbonnements}" scope="request"/>
        <jsp:include page="block/abons-2col-list.jsp"/>

    </c:if>

</div>