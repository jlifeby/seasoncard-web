<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row">
        <div class="col-md-2">
            <div class="card-panel">
                <h5>Действия</h5>
            </div>
        </div>
        <div class="col-md-10">
            <div class="card-panel">
                <div class="row">
                    <div class="col-md-6 offset-md-3">
                        <form action="${contextPath}/admin/sms" method="POST">
                            <div class="form-group">
                                <label for="input_text">Номер телефона</label>
                                <input id="input_text" type="text" length="12" name="phone" placeholder="375291234567"
                                       required>
                            </div>

                            <div class="form-group">
                                <label for="textarea1">Сообщения</label>
                                <textarea id="textarea1" class="materialize-textarea" length="120" name="textMessage"
                                          required></textarea>
                            </div>

                            <div class="text-xs-center">
                                <input type="submit" class="btn btn-default" value="Отправить SMS">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
