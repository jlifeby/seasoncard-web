<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <a href="${contextPath}/admin/companies" class="btn btn-default btn-sm">Все компании</a>
        </div>
        <div class="col-md-10">
            <h3>Добавление карт в компанию: </h3>
            <div class="card-panel">
                <div class="row">
                    <div class="col-md-1">
                        <img src="${contextPath}${company.logoPath}" class="img-fluid" alt="">
                    </div>
                    <div class="col-md-11">
                        <h4>${company.name}</h4>
                    </div>
                </div>
            </div>
            <div class="card-panel">

                <ul class="nav nav-tabs tabs-4">
                    <li class="active"><a data-toggle="tab" href="#add-real">Добавить реальные</a></li>
                    <li><a data-toggle="tab" href="#add-virtual">Добавить виртуальнае</a></li>
                    <li><a data-toggle="tab" href="#remove-real">Удалить реальные</a></li>
                    <li><a data-toggle="tab" href="#remove-virtual">Удалить виртуальнае</a></li>
                </ul>

                <div class="tab-content card-panel blue-grey lighten-5">
                    <div id="add-real" class="tab-pane fade in active">
                        <div class="row">
                            <div class="col-md-6 offset-md-3">
                                <p>Добавление NFC карт: </p>
                                <form action="${contextPath}/admin/companies/${company.id}/cards/nfc" method="POST">

                                    <input name="companyId" type="hidden" value="${company.id}"/>

                                    <div class="form-group">
                                        <label>С: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="fromCard" required="required"/>
                                    </div>

                                    <div class="form-group">
                                        <label>До: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="toCard" required="required"/>
                                    </div>

                                    <div class="text-xs-center">
                                        <input type="submit" class="btn btn-default" value="Добавить карты">
                                        <span class="required"><b>*</b> Обязательные поля</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div id="add-virtual" class="tab-pane fade">
                        <div class="row">
                            <div class="col-md-6 offset-md-3">
                                <p>Добавление виртуальных карт: </p>
                                <form action="${contextPath}/admin/companies/${company.id}/cards/virtual"
                                      method="POST">

                                    <input name="companyId" type="hidden" value="${company.id}"/>

                                    <div class="form-group">
                                        <label>Количество карт: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="count" required="required"/>
                                    </div>

                                    <div class="text-xs-center">
                                        <input type="submit" class="btn btn-default" value="Добавить карты">
                                        <span class="required"><b>*</b> Обязательные поля</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div id="remove-real" class="tab-pane fade">
                        <div class="row">
                            <div class="col-md-6 offset-md-3">
                                <p>Удаление NFC карт: </p>
                                <form action="${contextPath}/admin/companies/${company.id}/cards/nfc/remove"
                                      method="POST">

                                    <input name="companyId" type="hidden" value="${company.id}"/>

                                    <div class="form-group">
                                        <label>С: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="fromCard" required="required"/>
                                    </div>

                                    <div class="form-group">
                                        <label>До: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="toCard" required="required"/>
                                    </div>

                                    <div class="text-xs-center">
                                        <input type="submit" class="btn btn-default" value="Удалить реальны карты">
                                        <span class="required"><b>*</b> Обязательные поля</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div id="remove-virtual" class="tab-pane fade">
                        <div class="row">
                            <div class="col-md-6 offset-md-3">
                                <p>Удаление виртуальных карт: </p>
                                <form action="${contextPath}/admin/companies/${company.id}/cards/virtual/remove"
                                      method="POST">

                                    <input name="companyId" type="hidden" value="${company.id}"/>

                                    <div class="form-group">
                                        <label>С: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="fromCard" required="required"/>
                                    </div>

                                    <div class="form-group">
                                        <label>До: <span class="required">*</span></label>
                                        <input type="number" class="form-control" name="toCard" required="required"/>
                                    </div>

                                    <div class="text-xs-center">
                                        <input type="submit" class="btn btn-default" value="Удалить виртуальные карты">
                                        <span class="required"><b>*</b> Обязательные поля</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
