<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .pricing-card {
        margin-bottom: 1em;
    }

    .pricing-card .price h5 {
        font-size: 1.1rem;
    }

    .pricing-card .price h2 {
        font-size: 2rem;
        padding: 1.5rem;
        margin-bottom: 0;
    }

    .pricing-card .price h2:after {
        content: "$";
        font-size: 20px;
        position: absolute;
        margin-top: 0.7rem;
    }

    .col-md-2 {
        padding-right: 5px;
        padding-left: 5px;
    }

    .col-sm-4 {
        padding-right: 5px;
        padding-left: 5px;
    }

    .col-xs-6 {
        padding-right: 5px;
        padding-left: 5px;
    }

    .lang a.btn {
        font-size: 14px;
        text-transform: none;
        padding: 0.2rem 1rem;
    }

    .lang a img {
        max-width: 24px;
        display: inline-block;
        box-shadow: none;
    }

    .card-block .btn {
        margin-left: 2px;
        margin-top: 0;
    }

    p.footnote {
        font-size: 12px;
        margin-bottom: 0;
    }
</style>

<div class="container">

    <section class="section">

        <h2 class="text-xs-center margin-bottom-20">Стоимость ежемесячного обслуживания</h2>

        <hr>

        <div class="row" style="margin-bottom: 10px">
            <div class="col-md-12" style="padding-left: 5px;">
                <div class="btn-group lang">
                    <a href="${contextPath}/tariffs" class="btn btn-default light-blue">.руб <img
                            src="${contextPath}/images/features/flag_by.png"
                            alt=""/></a>
                    <a href="${contextPath}/tariffs/rub" class="btn btn-default light-blue">.руб <img
                            src="${contextPath}/images/features/flag_ru.png"/></a>
                    <a href="#" class="btn btn-default light-blue lighten-2">$ <img
                            src="${contextPath}/images/features/flag_us.png"/></a>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Пробный **</h5>
                        </div>
                        <h2>0</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>0</b> до <b>50</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Старт 30</h5>
                        </div>
                        <h2>5</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>0</b> до <b>30</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Стандарт 50</h5>
                        </div>
                        <h2>10</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>31</b> до <b>50</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Стандарт 100</h5>
                        </div>
                        <h2>15</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>51</b> до <b>100</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Стандарт 150</h5>
                        </div>
                        <h2>20</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>101</b> до <b>150</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Стандарт 200</h5>
                        </div>
                        <h2>25</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>151</b> до <b>200</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
        </div>
        <p class="footnote"> * - Количество активных посетителей при пользовании системой. Активным посетителем системы
            считается тот посетитель, который купил хотя бы один абонемент или посетил зал хотя бы один раз за отчетный
            период.</p>
        <p class="footnote"> ** - <b>Пробный (бесплатный) тариф</b> пользования системой – составляет <b>1 месяц</b>.
            Предоставляется  при регистрации в системе. Датой начала тестового периода  считается момент активации
            регистрации в системе SeasonCard.
        </p>

        <h2 class="text-xs-center" style="margin: 20px 0">Стоимость ежемесячного обслуживания</h2>
        <hr>
        <div class="row" style="margin-bottom: 10px">
            <div class="col-md-12" style="padding-left: 5px;">
                <div class="btn-group lang">
                    <a href="${contextPath}/tariffs" class="btn btn-default light-blue">.руб <img
                            src="${contextPath}/images/features/flag_by.png"
                            alt=""/></a>
                    <a href="${contextPath}/tariffs/rub" class="btn btn-default light-blue">.руб <img
                            src="${contextPath}/images/features/flag_ru.png"/></a>
                    <a href="#" class="btn btn-default light-blue lighten-2">$ <img
                            src="${contextPath}/images/features/flag_us.png"/></a>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Оптима 300</h5>
                        </div>
                        <h2>30</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>201</b> до <b>300</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Оптима 400</h5>
                        </div>
                        <h2>35</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>301</b> до <b>400</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Оптима 500</h5>
                        </div>
                        <h2>40</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>401</b> до <b>500</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Премиум 1000</h5>
                        </div>
                        <h2>50</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>501</b> до <b>1000</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>Премиум 2000</h5>
                        </div>
                        <h2>70</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p>от <b>1001</b> до <b>2000</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-2">
                <div class="card pricing-card">
                    <div class="price header light-blue">
                        <div class="version">
                            <h5>VIP</h5>
                        </div>
                        <h2>100</h2>
                    </div>
                    <div class="card-block striped">
                        <ul>
                            <li>
                                <p> более <b>2000</b> * посетителей в месяц</p>
                            </li>
                            <li>
                                <p><i class="fa fa-check green-text"></i> Все функции <b>SeasonCard</b></p>
                            </li>
                        </ul>
                        <%--<a href="${contextPath}/registration-company"--%>
                        <%--class="btn light-blue btn-rounded">Регистрация</a>--%>
                    </div>
                </div>
            </div>
        </div>
        <p class="footnote"> * - Количество активных посетителей при пользовании системой. Активным посетителем системы
            считается тот посетитель, который купил хотя бы один абонемент или посетил зал хотя бы один раз за отчетный
            период.</p>

    </section>

    <hr class="between-sections">

    <section class="section">

        <style>
            .card .card-price {
                float: right;
                padding: 1rem 1rem 0;
                margin-top: 3px;
                font-size: 1.2rem;
                color: #000;
            }
        </style>

        <h1 class="section-heading">Пакеты карт</h1>

        <hr>

        <div class="row">
            <div class="col-xs-12 col-sm-6 col-lg-4 offset-lg-2">
                <div class="card">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/card_example_sc.png" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block">
                        <h4 class="card-title" style="margin-bottom: 0;">Карта SeasonCard</h4>
                        <%--<hr>--%>
                        <%--<p class="card-text" style="display: inline-block;">Изготовление от 100 шт за 1 день</p>--%>
                    </div>
                </div>
            </div>
            <div class="col-xs-12 col-sm-6 col-lg-4">
                <div class="card">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/card_example_comp.png" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block">
                        <h4 class="card-title" style="margin-bottom: 0;">Дизайнерская</h4>
                        <%--<hr>--%>
                        <%--<p class="card-text" style="display: inline-block;">Изготовление от 100 шт за 1день</p>--%>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-lg-8 offset-lg-2">
                <p class="footnote"> * - Итоговая стоимость изготовления карты зависит от объема заказа и сложности дизайна карты.</p>
            </div>
        </div>
    </section>

    <hr class="between-sections">

    <section class="section">

        <h1 class="section-heading">Скидки и акционные предложения</h1>

        <hr>

        <h3 class="text-xs-center"></h3>

        <blockquote class="blockquote bq-primary">
            <p class="bq-title">Скидки на оплату</p>
            <p>Скидка <b>5%</b> – при единоразовой оплате за 3 месяца. <br/>
                Скидка <b>10%</b> – при единоразовой оплате за 6 месяцев. <br/>
                Скидка <b>20%</b> – при единоразовой оплате за 12 месяцев.
            </p>
        </blockquote>

        <blockquote class="blockquote bq-warning">
            <p class="bq-title">Акции</p>
            <p>Скидка <b>10%</b> – по акции "<b>Рекомендую SeasonCard</b>" <br/>
                Скидка <b>10%</b> – при переходе на SeasonCard  с другой системы автоматизации и учета
            </p>
        </blockquote>

        <p class="footnote"> * - <b>Все скидки суммируются!</b> Суммарная скидка одному партнёру не может превышать 50%.
        </p>

        <h3 class="text-xs-center red-text" style="margin: 30px 0 20px"><b>Акция "Рекомендую SeasonCard"</b></h3>

        <blockquote class="blockquote bq-danger">
            <%--<p class="bq-title">Акция "Рекомендую SeasonCard"</p>--%>
            <p>Компания, которой порекомендовали сервис SeasonCard, получает скидку 10% на ежемесячную оплату
                пользования сервисом SeasonCard в течение <b>6-ти месяцев</b> после перехода на платный тариф.</p>
            <p>Компания, которая порекомендовала сервис SeasonCard, получает накопительный бонус в размере 10% от оплат
                приведенной компании-партнёра  в течение <b>6-ти месяцев</b>.

            </p>
        </blockquote>

        <h3 class="text-xs-center red-text" style="margin: 30px 0 20px"><b>Скидка при переходе на SeasonCard с другой
            системы автоматизации и учета</b></h3>

        <blockquote class="blockquote bq-danger">
            <%--<p class="bq-title">Акция "Рекомендую SeasonCard"</p>--%>
            <p>Ежемесячная скидка в размере <b>10%</b> предоставляется сроком на <b>6 месяцев</b> после оплаты первого
                месяца работы в
                системе. Скидка предоставляется только при документальном подтверждении перехода с другой системы
                автоматизации.
                В системы автоматизации не входят “бумажный учет ”, учет в таблицах Excel, Google-инструментах, а также
                учёт при использовании любых бесплатных электронных программ и систем CRM.
            </p>
        </blockquote>

    </section>

    <blockquote class="blockquote bq-primary">
        <%--<p class="bq-title">Info notification</p>--%>
        <p>Сервис SeasonCard доступен во всех регионах. Тарифный план фиксируется на месяц, с возможной его
            пролонгацией. Сервисом SeasonCard предусмотрены региональные спецпредложения и скидки для наших партнёров.
            Чтобы узнать о специальных условиях сотрудничества, просим обращаться в службу
            поддержки(<a href="mailto:info@test.com">info@test.com</a>).</p>
    </blockquote>
</div>
