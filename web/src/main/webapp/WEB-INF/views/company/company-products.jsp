<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container margin-bottom-20">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <div class="z-depth-1 no-padding">
                <div class="btn-group" style="margin: 15px">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">${selectedProductSort.description}</button>

                    <div class="dropdown-menu">
                        <c:forEach var="productSort" items="${productSorts}">
                            <a class="dropdown-item"
                               href="${contextPath}/company/products?sort=${productSort.name()}">${productSort.description}</a>
                        </c:forEach>
                    </div>
                </div>

                <a href="${contextPath}/company/products/archive" class="btn-flat pull-right"
                   style="margin: 15px">Архивные</a>

                <c:if test="${products.size() == 0}">
                    <p class="p-2">Список абонементов пуст. Пожалуйста, создайте новый абонемент.</p>
                </c:if>
                <c:if test="${products.size() != 0}">
                    <div class="container-fluid horizontal-listing">
                        <c:forEach var="product" items="${products}">
                            <div class="row hoverable">
                                <div class="col-md-3">
                                    <a href="${contextPath}/company/products/${product.id}/detail">
                                        <img src="${contextPath}${product.logoPath}" class="img-fluid">
                                        <c:if test="${not product.published}">
                                            <span class="tag tag-danger"
                                                  style="font-size: 15px; position: absolute; top: 20px; right: 15px;">Скрыт</span>
                                        </c:if>
                                    </a>
                                </div>

                                <div class="col-md-9">
                                        <%--<a href="${contextPath}/company/products/${product.id}/detail">--%>
                                    <h4 class="ellipsis bold-500 title">${product.name}</h4>
                                        <%--</a>--%>

                                    <table class="table" style="margin-bottom: 0;">
                                        <tbody>
                                        <tr>
                                            <td>${product.countOfAttendancesAsString}</td>
                                            <td>Цена: <fmt:formatNumber value="${product.price}" type="currency"
                                                                        minFractionDigits="2"
                                                                        maxFractionDigits="2"/></td>
                                        </tr>
                                        </tbody>
                                    </table>

                                    <p class="pull-right"><a href="${contextPath}/company/products/${product.id}/detail"
                                                             class="btn btn-default btn-sm">Редактировать</a></p>
                                    <a href="${contextPath}/company/products/${product.id}/preview"
                                       style="margin-top: 6px"
                                       class="light-blue-text pull-right">Просмотр</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-block">

                <h5>Работа с абонементами</h5>

                <a href="${contextPath}/company/products/new" class="btn btn-default">Добавить абонемент</a>
                <br>
            </div>
        </div>
    </div>
</div>

