<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <div class="container">
        <div class="row">
            <article class="col-sm-12 col-md-12 content">
                <div class="my-account">
                    <div class="bottom-padding">
                        <p>На ваш email было выслано письмо с новым паролем.</p>
                        <div class="alert alert-info">
                            <ul class="list-unstyled">
                                <li>Email: <strong>***</strong></li>
                            </ul>
                        </div>
                        <p>Если email не пришло, проверьте папку "Спам" или попробуте
                            <a href="#">получить письмо</a> снова.</p>
                    </div>
                </div>
            </article>
        </div>
    </div>
</section>