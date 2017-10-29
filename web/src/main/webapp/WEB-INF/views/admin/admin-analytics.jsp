<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div class="card-panel">
                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item" src="/analytics/admin/main.html"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>

