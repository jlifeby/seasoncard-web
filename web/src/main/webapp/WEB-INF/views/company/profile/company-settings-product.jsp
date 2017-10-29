<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/company/settings/product" class="btn btn-default btn-block">Настройки абонементов</a>
            <a href="${contextPath}/company/settings/notification" class="btn btn-default btn-block">Оповещение</a>
        </div>
        <div class="col-sm-12 col-md-8">
            <div class="card-panel">

                <h4 class="card-title">Настройка абонемента по умолчанию</h4>

                <p class="card-text">Здесь вы можете задать изображения абонемента для вашей компании. Возможность
                    изменять изображение для каждого абонемента все равно будет доступна</p>


                <div class="row">
                    <div class="col-sm-8 offset-sm-2 col-md-4 offset-md-4" style="padding: 10px 15px">

                        <img id="logoImg" src="${contextPath}${company.defaultProductLogoPath}" class="img-fluid"
                             alt="">

                        <div class="file-field text-xs-center">
                            <span class="btn btn-default btn-sm waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1.586"
                                       accept="image/*"/>
                            </span>
                        </div>
                    </div>
                </div>

                <form action="${contextPath}/company/settings/products" method="POST">

                    <input type="hidden" id="logoPath" name="logoPath" value="${company.defaultProductLogoPath}"/>

                    <div class="text-xs-center">
                        <button type="submit" class="btn btn-default">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../components/image-crop-dialog.jsp"/>