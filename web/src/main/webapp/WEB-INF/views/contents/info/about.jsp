<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar-menu.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="card-panel">
                <h1 class="page-header">О нас</h1>

                <p>SeasonCard - команда увлеченных профессионалов, которые видят свою основную задачу в том, чтобы
                    сделать вашу жизнь проще. Мы искренне убеждены, что SeasonCard сможет многое изменить в системах
                    автоматизации и учёта, и обязательно найдёт продолжение в других сферах управления бизнесом.</p>
                <p>Мы создали этот продукт, используя лучшие технические решения, сочетая их с инновационными
                    технологиями, уникальным принципом построения системы, оригинальным дизайном карт.</p>
                <p>В нашей работе мы всегда учитываем потребности пользователя, стремимся создать интуитивно понятный
                    интерфейс, реализовать максимально простые, передовые решения.</p>
                <p>Команда SeasonCard - создаёт новые возможности для развития вашего бизнеса.</p>

                <div style="margin-bottom: 30px;"></div>
            </div>
        </div>
    </div>
</div>
