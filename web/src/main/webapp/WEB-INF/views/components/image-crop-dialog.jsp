<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .cropper-container {
        margin: 0 auto;
    }
</style>

<div id="imageCropDialog" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Выберите миниатюру</h4>
            </div>
            <div class="modal-body">
                <div class="row" style="min-height: 200px;">
                    <div style="text-align: center">
                        <div id="imageProgress" class="preloader-wrapper big active" style="margin-top: 70px">
                            <div class="spinner-layer spinner-blue-only">
                                <div class="circle-clipper left">
                                    <div class="circle"></div>
                                </div><div class="gap-patch">
                                <div class="circle"></div>
                            </div><div class="circle-clipper right">
                                <div class="circle"></div>
                            </div>
                            </div>
                        </div>
                        <img id="imageCrop" class="img-fluid" src="" alt="Picture">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <%--<a id="rotateLeft" class="btn-floating btn-small waves-effect waves-light light-blue pull-left">--%>
                    <%--<i class="material-icons">rotate_left</i></a>--%>
                <%--<a id="rotateRight" class="btn-floating btn-small waves-effect waves-light light-blue pull-left">--%>
                    <%--<i class="material-icons">rotate_right</i></a>--%>

                <button type="button" class="btn waves-effect waves-light" data-dismiss="modal"
                        style="margin: 10px 5px;">Отмена
                </button>
                <button id="confirmCrop" type="button" class="btn btn-default waves-effect waves-light"
                        style="margin: 10px 5px;">Ок
                </button>
            </div>
        </div>
    </div>
</div>