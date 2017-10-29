<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card-panel">
                <div>
                    <h3 style="display:inline-block;">Офисы</h3>
                    <a href="#" class="btn waves-effect waves-teal pull-right">Добавить офис</a><br/>
                </div>

                <table class="table table-hover table-models">
                    <thead>
                    <tr>
                        <th>Название филиала</th>
                        <th>Телефон</th>
                        <th>E-mail</th>
                        <th>Адрес</th>
                        <th>Время работы</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Название филиала</td>
                        <td>Телефон</td>
                        <td>E-mail</td>
                        <td>Адрес</td>
                        <td>Время работы</td>
                        <td class="edit-cell">
                            <a class="btn-floating light-blue"><i class="material-icons md-18">mode_edit</i></a>
                        </td>
                    </tr>
                    <tr>
                        <td>Название филиала</td>
                        <td>Телефон</td>
                        <td>E-mail</td>
                        <td>Адрес</td>
                        <td>Время работы</td>
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

