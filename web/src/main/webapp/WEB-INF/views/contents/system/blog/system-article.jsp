<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <div class="container">
        <div class="row">
            <div class="content blog blog-post col-sm-9 col-md-9">
                <form:form class="form-box register-form form-validator"
                           action="${contextPath}/system/blog/article/save" modelAttribute="article"
                           method="POST" enctype="multipart/form-data">

                    <c:if test="${article.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <div class="form-group">
                        <label>Заголовок: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="title" name="title"
                                    data-bv-trigger="keyup" required="required"
                                    data-bv-notempty-message="Заголовок не может быть пустым."/>
                    </div>

                    <div class="form-group">
                        <label>Автор: <span class="required">*</span></label>
                        <form:input type="text" class="form-control" path="author" name="title"
                                    data-bv-trigger="keyup" required="required"
                                    data-bv-notempty-message="Автор не может быть пустым."/>
                    </div>

                    <div class="form-group">
                        <label>Краткое описание: <span class="required">*</span></label>
                        <form:textarea path="description" class="form-control" cols="30"
                                       rows="10" maxlength="500" data-bv-trigger="keyup" required="required"
                                       data-bv-notempty-message="Краткое описание не может быть пустым."/>
                    </div>
                    <p>Постарайтесь уложиться в 500 символов.</p>

                    <div class="form-group">
                        <label>Подробное описание: <span class="required">*</span></label>
                        <form:textarea id="edit" path="htmlContent" class="form-control"></form:textarea>
                    </div>

                    <div class="form-group">
                        <label>Афиша статьи: <span class="required">*</span></label>
                        <input type="file" name="logoFile">
                        <c:if test="${article.logo != null}">
                            <div class="row">
                                <div class="col-sm-5 col-md-5" style="padding: 10px 15px">
                                    <img src="${contextPath}/uploaded/images/${article.logo}" width="770"
                                         height="270" alt="">
                                </div>
                            </div>
                        </c:if>
                    </div>

                    <div class="buttons-box clearfix">
                        <input id="submitBtn" type="submit" class="btn btn-default"
                               value="Сохранить статью">
                        <span class="required"><b>*</b> Обязательные поля</span>
                        <c:if test="${article.id != null}">
                            <button class="btn btn-danger pull-right" data-toggle="modal" data-target="#remove-dialog">
                                Удалить
                            </button>
                        </c:if>
                    </div>

                </form:form>
            </div>

            <div id="sidebar" class="sidebar col-sm-3 col-md-3">
                <aside class="widget list">
                    <header>
                        <h3 class="title">Боковая панель</h3>
                    </header>
                </aside>
            </div>
        </div>
    </div>
</section>