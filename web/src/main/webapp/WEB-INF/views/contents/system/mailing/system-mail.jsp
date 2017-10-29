<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    angular.module('app', [])
            .controller('SystemMailCtrl', function ($scope) {
                <c:forEach var="mailEvent" items="${mail.events}">
                $scope.selectEvents.push({
                    "id": "${mailEvent.id}",
                    "name": "${mailEvent.name}"
                });
                </c:forEach>
            });
</script>

<section id="main" class="small-padding" ng-controller="SystemMailCtrl">
    <div class="container">
        <div class="row">
            <article class="content-block bg pull-right col-sm-12 col-md-9">

                <form:form class="form-box form-validator" modelAttribute="event"
                           action="${contextPath}/system/mailing/save" method="POST" enctype="multipart/form-data">

                    <c:if test="${event.id != null}">
                        <form:input path="id" type="hidden"/>
                    </c:if>

                    <div class="form-group">
                        <label>Тема: <span class="required">*</span></label>
                        <form:input path="theme" type="text" class="form-control" name="theme"
                                    data-bv-trigger="keyup" required="required"
                                    data-bv-notempty-message="Заголовок не может быть пустым."/>
                    </div>

                    <div class="form-group">
                        <label>События для рассылки: <span class="required">*</span></label>

                        <div class="row">
                            <div class="col-sm-12 col-md-8 col-lg-8">
                                <select path="event" class="form-control">
                                    <c:forEach var="eventSelect" items="${events}">
                                        <option value="${eventSelect.id}">${eventSelect.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div ng-repeat="selectEvent in selectEvents">
                            <input type="hidden" name="events" value="{{selectEvent}}"/>
                            <a href="${contextPath}/events/{{selectEvent.id}}">{{selectEvent.name}}</a>
                            <a href ng-click="removeEvent(selectEvent)">Удалить</a>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Предпросмотр:</label>

                        <c:if test="${event.id != null}">
                            <p>Письмо</p>
                        </c:if>
                    </div>

                    <div class="buttons-box clearfix">
                        <input id="submitBtn" type="submit" class="btn btn-default"
                               value="Сохранить">
                        <span class="required"><b>*</b> Обязательные поля</span>

                        <c:if test="${event.id != null}">
                            <button class="btn btn-default pull-right" data-toggle="modal" data-target="#remove-dialog">
                                Отправить
                            </button>
                        </c:if>
                    </div>

                </form:form>
            </article>

            <div id="sidebar" class="sidebar col-sm-12 col-md-3">

            </div>
        </div>
    </div>
</section>