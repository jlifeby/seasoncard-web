<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card-panel">
                <div>
                    <h3 style="display:inline-block;">Сотрудники компании</h3>
                    <a href="${contextPath}/company/employees/new" class="btn waves-effect waves-teal pull-right">Добавить сотрудника</a><br/>
                </div>

                <table class="table table-hover table-models">
                    <thead>
                    <tr>
                        <th>№</th>
                        <th>Имя</th>
                        <th>Телефон, E-mail</th>
                        <th>Должность</th>
                        <th>Доступ к системе (логин)</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Мишурин Дмитрий Олегович</td>
                        <td>79261234567, ambr_gt@mail.ru</td>
                        <td>Преподаватель</td>
                        <td>Есть (ambr_gt@mail.ru)</td>
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

