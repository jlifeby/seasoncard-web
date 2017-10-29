<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="row card-panel">
    <div class="col-md-2">
        <img class="img-fluid rounded-circle" src="${contextPath}${card.user.logoPath}" alt="">
    </div>
    <div class="col-md-7">
        <h2 style="margin: 0"><a
                href="${contextPath}/company/cards/${card.cardUUID}">${card.user.name} ${card.user.lastName}</a>
            <a href="${contextPath}/company/cards/${card.client.cardUUID}/detail">
                <i class="material-icons md-18 light-blue-text">mode_edit</i></a></h2>

        <table class="table" style="margin-bottom: 0;">
            <tbody>
            <tr>
                <%--<td>Карта: ${card.cardUUID}</td>--%>
                <td>Тел: +${card.user.phone}</td>
                <td>Дата рождения: <span class="birthday"><joda:format value="${card.user.birthday}"
                                                                       pattern="d MMMM YYYY"/></span>
                </td>
            </tr>
            <c:if test="${not empty card.comment}">
                <tr>
                    <td colspan="2">
                            ${card.comment}
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
        <div style="margin-top: 10px;">
            <c:forEach var="iterTag" items="${card.client.tags}">
                <a href="${contextPath}/company/cards?tag=${iterTag}" class="chip">${iterTag}</a>
            </c:forEach>
            <c:if test="${!card.client.active}">
                <span class="tag light-blue darken-4">Архивный</span>
            </c:if>
        </div>

        <%--<hr style="margin: 10px 0">--%>
        <%--<p class="list"><b>Дата рождения: </b>--%>
        <%----%>

        <%--<p class="list"><b>Телефон: </b>${PhoneUtil.formatPhone(card.user.phone)}</p>--%>
    </div>
    <div class="col-md-3">
        <h5>Номер карты: <span class="tag light-blue">${card.cardUUID}</span>
            <c:if test="${!card.client.potential}">
                <a href="${contextPath}/company/cards/${card.cardUUID}/replace"><i
                        class="fa fa-refresh light-blue-text align-middle" aria-hidden="true"></i></a>
            </c:if>
        </h5>
        <c:if test="${card.client.internalId != null}">
            <h5>Внутренний номер: <span class="tag secondary-color">${card.client.internalId}</span></h5>
        </c:if>
    </div>
</div>