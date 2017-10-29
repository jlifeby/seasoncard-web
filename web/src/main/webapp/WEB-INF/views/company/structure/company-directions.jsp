<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card-panel">
                <div>
                    <h3 style="display:inline-block;">Направления организации</h3>
                    <a href="${contextPath}/company/directions/new" class="btn waves-effect waves-teal pull-right">Добавить направление</a><br/>
                </div>

                <div class="row">
                    <div class="col-sm-12 col-md-6 col-md-offset-3">
                        <table class="table table-hover table-models">
                            <thead>
                            <tr>
                                <th>Название</th>
                                 <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Название направления</td>
                                <td class="edit-cell">
                                    <a class="btn-floating light-blue"><i class="material-icons md-18">mode_edit</i></a>
                                </td>
                            </tr>
                            <tr>
                                <td>Название направления</td>
                                <td class="edit-cell">
                                    <a class="btn-floating light-blue"><i class="material-icons md-18">mode_edit</i></a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>