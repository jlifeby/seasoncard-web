<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <header class="page-header">
        <div class="container">
            <h1 class="title">Блог</h1>
        </div>
    </header>
    <div class="container">
        <div class="row">
            <div class="content blog col-sm-9 col-md-9">
                <c:forEach var="article" items="${articles}">
                    <article class="post">
                        <h2 class="entry-title"><a href="${contextPath}/system/blog/${article.id}">${article.title}</a></h2>

                        <div class="work-one">
                            <div class="work-image">
                                <img src="${contextPath}/uploaded/images/${article.logo}">
                            </div>
                        </div>

                        <div class="entry-content">${article.description}</div>

                        <div class="text-right">
                            <a href="${contextPath}/blog/${article.id}">Просмотр</a>
                        </div>

                        <footer class="entry-meta">
                            <span class="autor-name">${article.author}</span>,
                            <span class="time"><joda:format value="${article.createdDate}" style="F-"/></span>
                            <%--<span class="separator">|</span>--%>
                            <%--<span class="meta">Тег: <a href="#">${event.category}</a></span>--%>
                        </footer>
                    </article>
                </c:forEach>
            </div>
            <div id="sidebar" class="sidebar col-sm-3 col-md-3">
                <aside class="widget">
                    <a href="${contextPath}/system/blog/new" class="btn btn-sm btn-block btn-default">
                        Добавить статью</a>
                </aside>
                <aside class="widget">
                    <header>
                        <h3 class="title">Боковая панель</h3>
                    </header>
                </aside>
            </div>
        </div>
    </div>
</section>