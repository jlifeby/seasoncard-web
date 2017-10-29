<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-8">
            <div class="card-panel horizontal-listing no-padding">
                <div class="container-fluid">
                    <c:forEach var="company" items="${companies}">

                        <div class="row hoverable">
                            <a href="${contextPath}/companies/${company.id}">
                                <div class="col-sm-3">
                                    <img src="${contextPath}${company.logoPath}"
                                         class="img-responsive z-depth-1">
                                </div>
                                <div class="col-sm-9">
                                    <h5 class="title ellipsis bold-500">${company.name}</h5>
                                    <ul class="list-inline item-details">
                                        <li><i class="fa fa-compass"> ${company.address.city} </i></li>
                                    </ul>
                                    <p class="ellipsis-2" style="max-height: 60px;">${company.description}</p>
                                </div>
                            </a>
                        </div>

                    </c:forEach>
                </div>
            </div>
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

