<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="blocks/system-menu.jsp"/>

<section id="main" class="small-padding">

    <tiles:insertAttribute name="system-main"/>

</section>
