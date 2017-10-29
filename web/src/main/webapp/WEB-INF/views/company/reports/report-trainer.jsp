<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <h3>Отчет по тренерам</h3>
    <div class="row">
        <div class="col-sm-12 col-md-8">

            <jsp:include page="block/filter.jsp">
                <jsp:param name="urlPath" value="${contextPath}/company/reports/trainer"/>
                <jsp:param name="showParam" value="trainerId,reportTrainerType"/>
            </jsp:include>

            <jsp:include page="block/reportTable.jsp"/>

        </div>
        <div class="col-md-4">
            <jsp:include page="block/sidebar-right.jsp"/>
        </div>
    </div>
</div>
