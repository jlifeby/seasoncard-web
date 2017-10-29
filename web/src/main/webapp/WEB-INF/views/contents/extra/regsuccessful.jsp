<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <div class="container">
        <div class="row">
            <article class="col-sm-12 col-md-12 content">
                <div class="my-account">
                    <div class="bottom-padding">

                        <c:if test="${status == 'successful'}">
                            <p>Успешная регистрации. Email адрес успешно подтвержден.</p>
                        </c:if>
                        <c:if test="${status == 'exist'}">
                            <p>Регистрация уже потверждена. Email адрес уже был успешно подтвержден.</p>
                        </c:if>

                        <div class="alert alert-info">
                            <ul class="list-unstyled">
                                <li>Email: <strong>***</strong></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </article>
        </div>
    </div>
</section>