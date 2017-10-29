<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-md-9">

            </div>

            <div id="sidebar" class="sidebar col-sm-12 col-md-3">
                <aside class="widget">
                    <a href="${contextPath}/system/mailing/new" class="btn btn-sm btn-block btn-default">Добавить рассылку</a>
                </aside>
            </div>
        </div>
    </div>
</section>