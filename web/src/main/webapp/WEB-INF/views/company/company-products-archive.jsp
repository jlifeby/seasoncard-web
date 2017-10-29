<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${products.size() == 0}">
                <p style="margin-top: 30px">Список архивных абонементов пуст.</p>
            </c:if>
            <c:if test="${products.size() != 0}">
                <div class="z-depth-1 no-padding">
                    <div class="container-fluid horizontal-listing">
                        <c:forEach var="product" items="${products}">
                            <div class="row hoverable">
                                <div class="col-sm-3">
                                    <a href="${contextPath}/company/products/${product.id}/preview">
                                        <img src="${contextPath}${product.logoPath}" class="img-fluid">
                                        <span class="tag grey"
                                              style="font-size: 15px; position: absolute; top: 20px; right: 15px;">В архиве</span>
                                    </a>
                                </div>

                                <div class="col-sm-8">
                                        <%--<a href="${contextPath}/company/products/${product.id}/detail">--%>
                                    <h5 class="ellipsis bold-500">${product.name}</h5>
                                        <%--</a>--%>
                                    <a href="${contextPath}/company/products/${product.id}/preview"
                                       class="light-blue-text">Предварительный
                                        просмотр</a>
                                        <%--<p class="ellipsis-2" style="max-height: 60px; line-height: 1.3; margin-top: 5px">${product.description}</p>--%>
                                    <p style="margin: 0"><strong>${product.countOfAttendancesAsString}</strong>
                                    </p>
                                    <p><strong>Цена: <fmt:formatNumber value="${product.price}" type="currency"
                                                                       minFractionDigits="2"
                                                                       maxFractionDigits="2"/></strong></p>
                                    <p class="pull-right"><a
                                            href="${contextPath}/company/products/${product.id}/restore"
                                            class="btn btn-default btn-sm">Восстановить</a></p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="col-md-4">
            <div class="card card-block">
                <h4>Архивные абонементы</h4>
                <p>Архивные абонементы Вы всегда сможете восстановить нажав кнопку "Восстановить" на
                    соответствующем абонементе.</p>
                <br>
            </div>
        </div>
    </div>
</div>

