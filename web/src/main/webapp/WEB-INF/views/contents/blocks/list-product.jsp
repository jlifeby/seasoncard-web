<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card-panel horizontal-listing no-padding">
    <div class="container-fluid">
        <c:forEach var="product" items="${products}">
            <div class="row hoverable">
                <div class="col-sm-4">
                    <a href="${contextPath}/products/${product.id}">
                        <img src="${contextPath}${product.logoPath}" class="img-responsive">
                    </a>
                </div>

                <div class="col-sm-8">
                    <a href="${contextPath}/products/${product.id}"><h5 class="ellipsis bold-500">${product.name}</h5></a>
                        <%--<ul class="list-inline item-details">--%>
                        <%--<li><i class="fa fa-clock-o"> 05/10/2015</i>--%>
                        <%--&lt;%&ndash;<joda:format value="${event.startDate}" style="F-"/>&ndash;%&gt;--%>
                        <%--<li><a href="#"><i class="fa fa-comments-o"></i> 12</a></li>--%>
                        <%--<li><a href="#"><i class="fa fa-facebook"> </i> 21</a></li>--%>
                        <%--<li><a href="#"><i class="fa fa-twitter"> </i> 5</a></li>--%>
                        <%--</ul>--%>
                    <p class="list-light">
                        <a href="${contextPath}/companies/${product.companyId}" class="light-blue-text ellipsis bold-500" style="display: block">
                            От: ${product.company.name}
                        </a>
                    </p>
                    <p class="list-light">Количество посещений: <strong>${product.abonnementType == 'BY_TIME' ? 'неограниченно' : product.countOfAttendances}</strong></p>
                    <p class="list-light">Цена: <strong><fmt:formatNumber value="${product.price}" type="currency" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
                    <p class="ellipsis">${product.description}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>