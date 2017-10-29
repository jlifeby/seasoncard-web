<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:parseNumber var="pageInt" type="number" value="${param.page}"/>
<fmt:parseNumber var="totalPagesInt" type="number" value="${param.totalPages}"/>
<fmt:parseNumber var="sizeInt" type="number" value="${param.size}"/>

<nav ${sizeInt > 0 ? 'class="mb-5"' : ''}>
    <ul class="pagination">
        <c:if test="${pageInt le 1}">
            <li class="page-item disabled">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
        </c:if>
        <c:if test="${pageInt gt 1}">
            <li class="page-item">
                <a class="page-link" href="${param.urlPath}&size=${sizeInt}&page=${pageInt - 1}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
        </c:if>
        <c:forEach var="i"
                   begin="${pageInt > 3 ? pageInt - 3 : 1}"
                   end="${(totalPagesInt > (pageInt + 3) && totalPagesInt > 6) ? (pageInt > 3 ? pageInt + 3 : 7) : totalPagesInt}">
            <c:choose>
                <c:when test="${i == pageInt}">
                    <li class="page-item active">
                        <a class="page-link" href="${param.urlPath}&size=${sizeInt}&page=${i}">${i} <span
                                class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="${param.urlPath}&size=${sizeInt}&page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${pageInt ge totalPagesInt}">
            <li class="page-item disabled">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </c:if>
        <c:if test="${pageInt lt totalPagesInt}">
            <li class="page-item">
                <a class="page-link" href="${param.urlPath}&size=${sizeInt}&page=${pageInt + 1}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </c:if>
        <c:if test="${sizeInt > 0}">
            <li class="page-item">
                <div class="btn-group" style="margin: 7px">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
                            style="font-size: 1em; padding: 4px"
                            aria-haspopup="true" aria-expanded="false">${sizeInt}</button>
                    <div class="dropdown-menu">
                        <c:forEach var="pageSize" items="${[20, 50, 100]}">
                            <a class="dropdown-item"
                               href="${param.urlPath}&page=${pageInt}&size=${pageSize}">${pageSize}</a>
                        </c:forEach>
                    </div>
                </div>
            </li>
        </c:if>
    </ul>
</nav>

