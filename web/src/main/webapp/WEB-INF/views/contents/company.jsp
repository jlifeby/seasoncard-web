<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">

    <header class="card-panel">
        <div class="row">
            <div class="col-md-3">
                <img class="img-fluid z-depth-1" src="${contextPath}${company.logoPath}" alt="">
            </div>
            <div class="col-md-9">

                <h2 class="ellipsis bold-500" style="margin: 5px 0">${company.name}</h2>

                <%--<hr style="margin: 5px 0">--%>

                <div class="row">
                    <div class="col-md-7">
                        <p style="font-weight: 300;">${company.description}</p>
                    </div>
                    <div class="col-md-5">
                        <table class="table table-striped">
                            <tr>
                                <td>Сайт</td>
                                <td><a href="${company.site}" target="_blank">
                                    <div class="ellipsis" style="max-width: 220px">${company.site}</div>
                                </a></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><a href="mailto:${company.email}">${company.email}</a></td>
                            </tr>
                            <tr>
                                <td>Адрес</td>
                                <td>${company.address.toString()}</td>
                            </tr>
                            <tr>
                                <td>Телефоны</td>
                                <td>
                                    <span class="phone-format">${company.phones[0]}</span>
                                    ${not empty company.phones[1] ? "<br/>" : ""}
                                    <span class="phone-format">${company.phones[1]}</span>
                                    ${not empty company.phones[2] ? "<br/>" : ""}
                                    <span class="phone-format">${company.phones[2]}</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <c:if test="${not empty promotions}">
        <h3>Акции и скидки</h3>
        <hr>
        <div class="row">
            <c:forEach var="promotion" items="${promotions}">
                <div class="col-md-6">
                    <c:set var="promotion" value="${promotion}" scope="request"/>
                    <jsp:include page="blocks/promotion-card.jsp"/>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <h3>Абонементы</h3>
    <hr>
    <div class="row">
        <c:forEach var="product" items="${company.products}">
            <div class="col-md-4">
                <div class="card">
                    <img class="img-fluid" src="${contextPath}${product.logoPath}" alt="Card image cap" style="min-height: 230px;">
                    <div class="card-block">
                        <a href="${contextPath}/products/${product.id}">
                            <h4 class="card-title ellipsis bold-500" title="${product.name}">${product.name}</h4>
                        </a>
                            <%--<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>--%>
                        <span class="lead">${product.countOfAttendancesAsString}</span><br/>
                        <span class="lead">Цена: <fmt:formatNumber value="${product.price}" type="currency"
                                                                   minFractionDigits="2" maxFractionDigits="2"/></span>
                        <br/>
                        <a href="${contextPath}/products/${product.id}"
                           class="btn btn-primary btn-sm waves-effect waves-light pull-right">Подробнее</a>
                    </div>

                </div>
                <%--<div class="card hoverable">--%>
                    <%--<div class="card-image" style="min-height: 230px;">--%>
                        <%--<img src="${contextPath}${product.logoPath}" class="img-fluid">--%>
                    <%--</div>--%>
                    <%--<div class="card-content">--%>

                        <%--<a href="${contextPath}/products/${product.id}"--%>
                           <%--style="font-size: 20px; text-decoration: underline">--%>
                            <%--<h4 class="ellipsis bold-500" title="${product.name}">${product.name}</h4>--%>
                        <%--</a>--%>


                    <%--</div>--%>
                    <%--<div class="card-action">--%>
                        <%--<a href="${contextPath}/products/${product.id}">--%>
                            <%--<button type="button" class="btn btn-info waves-effect waves-light">Подробнее</button>--%>
                        <%--</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </c:forEach>
    </div>
</div>

