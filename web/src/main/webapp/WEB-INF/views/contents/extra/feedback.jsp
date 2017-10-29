<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<section id="main" class="small-padding">
    <div class="container">
        <div class="row">
            <div class="content col-sm-12 col-md-12">
                <div class="row filter-elements">
                    <div class="col-xs-12 col-sm-6 col-md-6 box">

                        <h3 class="title">Есть вопрос?</h3>

                        <p>Вы можете позвонить нам или написать письмо. Мы с удовольствием ответим на ваши вопросы.</p>

                        <form class="form-validator" style="margin-bottom: 0"
                              action="${contextPath}/feedback/send" method="POST">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Ваше имя: <span class="required">*</span></label>
                                    <input type="text" class="form-control" name="name"
                                           data-bv-trigger="blur" required
                                           data-bv-notempty-message="Обязательное поле"/>
                                </div>
                                <div class="form-group">
                                    <label>Ваш e-mail: <span class="required">*</span></label>
                                    <input type="email" class="form-control" name="email"
                                           data-bv-trigger="blur" required
                                           data-bv-notempty-message="Обязательное поле"
                                           data-bv-emailaddress-message="Неправильный email"/>
                                </div>
                                <div class="form-group">
                                    <label>Ваш телефон: <span class="required">*</span></label>
                                    <input type="tel" class="form-control" name="phone"
                                           data-bv-trigger="blur" required
                                           data-bv-notempty-message="Обязательное поле"/>
                                </div>
                                <div class="form-group">
                                    <label>Текст сообщения: <span class="required">*</span></label>
                                    <textarea class="form-control" name="message"
                                              data-bv-trigger="blur" required
                                              data-bv-notempty-message="Обязательное поле"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="submit" class="btn btn-default" value="Отправить">
                            </div>
                        </form>

                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 box">
                        <div class="info-box">
                            <h3 class="title">Может уже есть ответ?</h3>

                            <p class="descriptions" style="margin-bottom: 10px">Загляните в наш <a>справочник</a>
                                и найдите там ответы на часто задаваемые вопросы:. </p>

                            <ul>
                                <li><a href="#"></a>Вопрос?</li>
                                <li><a href="#"></a>Вопрос?</li>
                                <li><a href="#"></a>Вопрос?</li>
                            </ul>

                            <a href="${contextPath}/faq" class="btn btn-default">Перейти к справочнику</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>