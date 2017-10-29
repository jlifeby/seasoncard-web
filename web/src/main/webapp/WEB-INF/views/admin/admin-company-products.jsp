<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="card-panel">
        <jsp:include page="block/company-header.jsp"/>
    </div>
    <h3>Абонементы компании: </h3>
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <c:if test="${company.products.size() == 0}">
                Список абонементов пуст. Пожалуйста, создайте новый абонемент.
            </c:if>
            <c:if test="${company.products.size() != 0}">
                <div class="card-panel no-padding">
                    <div style="text-align: right">
                        <a href="${contextPath}/admin/companies/${company.id}/products/archive" class="btn btn-default">Архивные</a>
                    </div>
                    <div class="horizontal-listing container-fluid">
                        <c:forEach var="product" items="${company.products}">
                            <div class="row hoverable">
                                <div class="col-sm-4">
                                    <a href="${contextPath}/company/products/${product.id}/preview">
                                        <img src="${contextPath}${product.logoPath}" class="img-fluid">
                                        <c:if test="${not product.published}">
                                            <span class="label label-danger"
                                                  style="font-size: 15px; position: absolute; top: 20px; right: 15px;">Скрыт</span>
                                        </c:if>
                                    </a>
                                </div>

                                <div class="col-sm-8">
                                        <%--<a href="${contextPath}/company/products/${product.id}/detail">--%>
                                    <h5 class="ellipsis bold-500">${product.name}</h5>
                                        <%--</a>--%>
                                        <%--<p class="ellipsis-2" style="max-height: 60px; line-height: 1.3; margin-top: 5px">${product.description}</p>--%>
                                    <p style="margin: 0"><strong>${product.countOfAttendancesAsString}</strong>
                                    </p>
                                    <p><strong>Цена: <fmt:formatNumber value="${product.price}" type="currency" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
                                    <p class="pull-right"><a
                                            href="${contextPath}/admin/companies/${company.id}/products/${product.id}/detail"
                                            class="btn btn-default btn-sm">Редактировать</a></p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="col-md-4">
            <div class="card-panel">
                <div class="row">
                    <div class="col-lg-12">

                        <h5>Работа с абонементами</h5>

                        <a href="${contextPath}/admin/companies/${company.id}/products/new" class="btn btn-default">Добавить
                            абонемент</a>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

