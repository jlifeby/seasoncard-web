<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<script type="text/javascript" src="${contextPath}/widget/${company.id}/widget.js"></script>

<c:set var="appUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-3">
            <a href="${contextPath}/company/widget/design"
               class="btn btn-default btn-block ${fn:contains(url, '/design')? 'active' : ''}">Дизайн виджета</a>
            <a href="${contextPath}/company/widget/code"
               class="btn btn-default btn-block ${fn:contains(url, '/code')? 'active' : ''}">Код и установка</a>
        </div>
        <div class="col-sm-12 col-md-9">

            <div class="card">
                <h3 class="card-header white text-xs-center">HTML код</h3>
                <div class="card-block">

                    <p>Для установки кнопки на сайт используйте следующий код:</p>

                    <code style="font-size: 80%;">
                        &lt;script type="text/javascript" src="${appUrl}/ext/${company.id}/widget.js"
                        charset="UTF-8"&gt;&lt;/script&gt;
                    </code>


                </div>
            </div>

            <div class="card">
                <h3 class="card-header white text-xs-center">Настройки кнопки</h3>
                <div class="card-block">

                    <p>Для того, чтобы переопределить на отдельных страницах, заданные в системе настройки используйте
                        следующий шаблон. Код необходимо скопировать на страницы вашего сайта над основным кодом виджета.
                        Копируйте код только если полностью уверены в его необходимости.</p>

                    <code style="font-size: 80%;">
                        &lt;script&gt;<br/>
                        <span style="padding-left:2em">var scWidgetSettings = {</span><br/>
                        <span style="padding-left:4em">buttonColor : '#1c84c6',</span><br/>
                        <span style="padding-left:4em">buttonPosition : 'bottom right',</span><br/>
                        <span style="padding-left:4em">buttonAutoShow : true,</span><br/>
                        <span style="padding-left:4em">buttonText : 'Онлайн запись',</span><br/>
                        <span style="padding-left:4em">formPosition : 'right'</span><br/>
                        <span style="padding-left:2em">};</span><br/>
                        &lt;/script&gt;
                    </code>

                </div>
            </div>

        </div>
    </div>
</div>