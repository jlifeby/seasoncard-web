<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <header class="page-header">
        <div class="container">
            <h1 class="title">Все пользователи</h1>
        </div>
    </header>
    <div class="container">
        <div class="row">
            <div class="content blog col-sm-9 col-md-9">
                <c:forEach var="user" items="${users}">
                    <div class="product">
                        <h4 class="product-name" style="margin:0">
                            <a href>${user.name} ${user.lastName}</a>
                        </h4>

                        <ul style="list-style: none; margin: 0; padding: 0">
                            <li>Email: ${user.email}</li>
                            <li>Статус: ${user.status}</li>
                            <li>Организации:
                                <c:forEach var="userOrg" items="${user.orgs}">
                                    <a href="${contextPath}/org/${userOrg.id}">${userOrg.name}</a><br/>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                </c:forEach>
            </div>
            <div id="sidebar" class="sidebar col-sm-3 col-md-3">
                <aside class="widget">
                    <header>
                        <h3 class="title">Боковая панель</h3>
                    </header>
                </aside>
            </div>
        </div>
    </div>
</section>
