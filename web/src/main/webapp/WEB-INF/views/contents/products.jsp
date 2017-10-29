<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <div class="dropdown">
                <button class="btn btn-default btn-sm dropdown-toggle" type="button"
                        style="padding: 2px 8px;" data-toggle="dropdown">
                    ${sort.description} <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <c:forEach var="productSort" items="${productSorts}">
                        <li>
                            <a href="${contextPath}/products?sort=${productSort.name()}">${productSort.description}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <jsp:include page="blocks/list-product.jsp"/>

            <jsp:include page="blocks/pagination.jsp">
                <jsp:param name="urlPath" value="${contextPath}/products?sort=${sort.name()}"/>
            </jsp:include>

        </div>
        <div class="col-md-4">
            <div class="row">
                <div class="col-md-12">
                    <h4>SeasonCard</h4>
                    <p>На сегодняшний день SeasonCard – это самый удобный способ использовать, покупать и продавать
                        абонементы.</p>
                </div>
            </div>
        </div>
    </div>
</div>

