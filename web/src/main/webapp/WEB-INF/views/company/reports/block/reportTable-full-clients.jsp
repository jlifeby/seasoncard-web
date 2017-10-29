<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:if test="${not empty report}">
    <div class="card-panel">
        <h5>${report.reportName}</h5>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <c:forEach var="headerCell" items="${report.reportHeader.pairs.entrySet()}">
                        <th class="text-xs-center">${headerCell.value}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="reportEntry" items="${report.reportEntries}">
                    <tr>
                        <c:forEach var="headerCell" items="${report.reportHeader.pairs.entrySet()}">
                            <td class="text-xs-center">${reportEntry.getValue(headerCell.key)}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                <c:if test="${report.reportSummary.pairs.entrySet().size() > 0}">
                    <tr>
                        <c:forEach var="summaryCell" items="${report.reportSummary.pairs.entrySet()}">
                            <th class="bold-500 text-xs-center">${summaryCell.value}</th>
                        </c:forEach>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
