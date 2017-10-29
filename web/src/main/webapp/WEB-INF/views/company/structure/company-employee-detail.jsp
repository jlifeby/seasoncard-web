<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card-panel">
                <div>
                    <h3 style="display:inline-block;">Редактирование сотрудника</h3>
                    <a href="${contextPath}/company/employees" class="btn-flat waves-effect waves-teal pull-right">К списку сотрудников</a><br/>
                </div>

                <ul class="nav nav-tabs tabs-3" style="margin-top: 20px">
                    <li class="active"><a data-toggle="tab" href="#profile">Анкета сотрудника</a></li>
                    <li><a data-toggle="tab" href="#instructor">Преподаватель</a></li>
                    <li><a data-toggle="tab" href="#access">Доступ к системе</a></li>
                </ul>

                <div class="tab-content card-panel">
                    <div id="profile" class="tab-pane fade in active">
                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4" style="padding: 10px 15px">

                                <img id="logoImg" src="${contextPath}${accountForm.logoPath}" class="img-responsive" alt="">


                                <div class="file-field text-center">
                            <span class="btn btn-default waves-effect waves-light"
                                  style="float: inherit;line-height: inherit;">
                                <span>Изменить</span>
                                <input id="uploadLogoFile" type="file" name="uploadLogoFile" data-aspect-ratio="1"
                                       accept="image/*"/>
                            </span>
                                </div>
                            </div>
                        </div>

                        <jsp:include page="../../components/image-crop-dialog.jsp"/>
                    </div>
                    <div id="instructor" class="tab-pane fade">
                        <br>
                        <h5>Menu 1</h5>
                        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                            commodo consequat.</p>
                    </div>
                    <div id="access" class="tab-pane fade">
                        <br>
                        <h5>Menu 2</h5>
                        <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
                            laudantium, totam rem aperiam.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

