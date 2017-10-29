<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>

    .card .card-title {
        font-size: 1rem;
        min-height: 3.3em;
    }

    .carousel-inner .col-xs-2 {
        padding-left: 3px;
        padding-right: 3px;
    }

    @media (min-width: 576px) {
        .card .card-title {
            font-size: 1.1rem;
            min-height: 2.1em;
        }

        .carousel-inner .col-xs-2 {
            padding-left: 10px;
            padding-right: 10px;
        }
    }

    @media (min-width: 992px) {
        .card .card-title {
            font-size: 1.3rem;
            min-height: 2.1em;
        }

        .carousel-inner .col-xs-2 {
            padding-left: 15px;
            padding-right: 15px;
        }
    }

    @media (min-width: 1200px) {
        .card .card-title {
            font-size: 1.5rem;
            min-height: 2.1em;
        }
    }

</style>

<%--<div class="container">--%>
    <%--<div class="row mt-3">--%>
        <%--<div class="col-sm-7">--%>

            <%--<div class="embed-responsive embed-responsive-16by9">--%>
                <%--<iframe class="embed-responsive-item" src="https://www.youtube.com/embed/vYT-epBnYN4"--%>
                        <%--allowfullscreen></iframe>--%>
            <%--</div>--%>

        <%--</div>--%>
        <%--<div class="col-sm-5">--%>
            <%--<p class="text-xs-center mt-3" style="font-size: 1.05rem;">Наступает самый волшебный, самый долгожданный, самый замечательный праздник –--%>
                <%--Новый год! <br/>--%>
                <%--Команда SeasonCard желает, чтобы для вас этот год стал действительно чудесным. Пусть ваша--%>
                <%--жизнь течёт широкой рекой, пусть она будет наполнена яркими событиями, приятными встречами, позитивными--%>
                <%--впечатлениями. Пусть сбудутся все желания, которые вы успеете загадать под бой курантов.</p>--%>
            <%--<p class="text-xs-center" style="color: #818a91; font-weight: 500;">Команда SeasonCard</p>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<div id="main-banner-carousel" class="carousel slide carousel-fade" data-ride="carousel" style="margin-top: -20px;">
    <ol class="carousel-indicators">
        <li data-target="#main-banner-carousel" data-slide-to="0" class="active"></li>
        <li data-target="#main-banner-carousel" data-slide-to="1"></li>
        <li data-target="#main-banner-carousel" data-slide-to="2"></li>
        <%--<li data-target="#main-banner-carousel" data-slide-to="3"></li>--%>
    </ol>
    <div class="carousel-inner" role="listbox">
        <%--<div class="carousel-item active">--%>
            <%--<a href="${contextPath}/tariffs">--%>
                <%--<img src="${contextPath}/images/banner/banner-ny-1-min.png" alt="Посмотреть тарифы" style="width: 100%">--%>
            <%--</a>--%>
        <%--</div>--%>
        <div class="carousel-item active">
            <a href="${contextPath}/tariffs">
                <img src="${contextPath}/images/banner/banner-2-min.png" alt="Посмотреть тарифы" style="width: 100%">
            </a>
        </div>
        <div class="carousel-item">
            <a href="${contextPath}/tariffs">
                <img src="${contextPath}/images/banner/banner-1-min.png" alt="Посмотреть тарифы" style="width: 100%">
            </a>
        </div>
        <div class="carousel-item">
            <a href="${contextPath}/tariffs">
                <img src="${contextPath}/images/banner/banner-3-min.png" alt="Посмотреть тарифы" style="width: 100%">
            </a>
        </div>
    </div>
    <a class="left carousel-control" href="#main-banner-carousel" role="button" data-slide="prev">
        <span class="icon-prev" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#main-banner-carousel" role="button" data-slide="next">
        <span class="icon-next" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<div class="container">


    <%--<div class="row intro-row" style="margin-top: 40px">--%>
    <%--<div class="col-md-8">--%>

    <%--<div id="carousel-example-1" class="carousel slide carousel-fade" data-ride="carousel">--%>
    <%--<ol class="carousel-indicators">--%>
    <%--<li data-target="#carousel-example-1" data-slide-to="0" class="active"></li>--%>
    <%--<li data-target="#carousel-example-1" data-slide-to="1"></li>--%>
    <%--</ol>--%>
    <%--<div class="carousel-inner" role="listbox">--%>
    <%--<div class="carousel-item active">--%>
    <%--<a href="${contextPath}/tariffs">--%>
    <%--<img src="${contextPath}/images/banner/banner-2.png" alt="First slide">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--<div class="carousel-item">--%>
    <%--<a href="${contextPath}/tariffs">--%>
    <%--<img src="${contextPath}/images/banner/banner-1.png" alt="Second slide">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--<div class="carousel-item">--%>
    <%--<a href="${contextPath}/tariffs">--%>
    <%--<img src="${contextPath}/images/banner/banner-3.png" alt="Second slide">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<a class="left carousel-control" href="#carousel-example-1" role="button" data-slide="prev">--%>
    <%--<span class="icon-prev" aria-hidden="true"></span>--%>
    <%--<span class="sr-only">Previous</span>--%>
    <%--</a>--%>
    <%--<a class="right carousel-control" href="#carousel-example-1" role="button" data-slide="next">--%>
    <%--<span class="icon-next" aria-hidden="true"></span>--%>
    <%--<span class="sr-only">Next</span>--%>
    <%--</a>--%>
    <%--</div>--%>

    <%--</div>--%>
    <%--<div class="col-md-4">--%>
    <%--<div style="height: 7em"></div>--%>
    <%--<h4 class="text-xs-center">SeasonCard - мы создаём новые возможности для развития вашего бизнеса</h4>--%>
    <%--<div class="text-xs-center">--%>
    <%--<sec:authorize access="isAnonymous()">--%>
    <%--<a href="${contextPath}/registration-company"--%>
    <%--class="btn btn-default waves-effect waves-light ">Регистрация</a>--%>
    <%--</sec:authorize>--%>
    <%--<sec:authorize access="hasRole('ROLE_USER')">--%>
    <%--<a href="${contextPath}/user/abonnements" class="btn btn-default waves-effect waves-light ">--%>
    <%--Перейти к моим абонементам</a>--%>
    <%--</sec:authorize>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <hr class="between-sections">

    <section class="section feature-box">

        <h1 class="section-heading">Seasoncard - мы создаём новые возможности для развития вашего бизнеса</h1>
        <p class="section-description lead" style="margin: 50px 0px;">Онлайн-сервис SeasonCard помогает решать ряд
            важных задач для оптимизации времени работы персонала по учету посетителей и абонементов, контроля платежей,
            ведения и анализа клиентской базы, управленческого учёта и многого другого.<br/>
            Smart-сервис SeasonCard - это простое и современное решение сложных вопросов автоматизации и учёта.
        </p>

        <div class="row features-big">
            <div class="col-md-4 mb-r">
                <i class="fa fa-users blue-text"></i>
                <h4 class="feature-title">Автоматизация клиентской базы</h4>
                <p class="grey-text">Автоматизация учёта и регистрация любой информации о клиентах</p>
            </div>

            <div class="col-md-4 mb-r">
                <i class="fa fa-credit-card-alt green-text"></i>
                <h4 class="feature-title">Система абонентских карточек</h4>
                <p class="grey-text">Виртуальные карты и бесконтактные пластиковые карты с NFC-чипом и уникальным кодом
                    - инновационная альтернатива традиционным абонементам</p>
            </div>

            <div class="col-md-4 mb-r">
                <i class="fa fa-percent red-text"></i>
                <h4 class="feature-title">Программы лояльности</h4><br/>
                <p class="grey-text">Мгновенное получение информации о предстоящих акциях, скидках и других программах
                    лояльности</p>
            </div>
        </div>

    </section>

    <hr class="between-sections">

    <section class="section">
        <h1 class="section-heading">Для кого система SeasonCard</h1>
        <div class="row">
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_fitness_club-min.jpg" class="img-fluid" alt="">
                        <%--<a>--%>
                        <%--<div class="mask"></div>--%>
                        <%--</a>--%>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Фитнес-клубы</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_dance_studio_2-min.jpg" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Танцевальные школы и студии</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_yoga_studio-min.jpg" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Йога-студии<br/><br/></h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_fighting_school-min.jpg" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Школы боевых искусств</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_early_learning_school-min.jpg" class="img-fluid"
                             alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Детские центры</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_jum-min.jpg" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Тренажёрные залы</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_training_centers-min.jpg" class="img-fluid" alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Тренинговые центры</h4>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-lg-3">
                <div class="card wow fadeInUp">
                    <div class="view overlay hm-white-slight">
                        <img src="${contextPath}/images/features/cat_language_school_2-min.jpg" class="img-fluid"
                             alt="">
                        <a>
                            <div class="mask"></div>
                        </a>
                    </div>
                    <div class="card-block text-xs-center">
                        <h4 class="card-title">Языковые школы</h4>
                    </div>
                </div>
            </div>
        </div>

    </section>

    <hr class="between-sections">

    <section class="section extra-margins">

        <h1 class="section-heading">Почему выбирают SeasonCard</h1>

        <%--<p class="section-description lead">Seasoncard вдохновляет собственников на дальнейшее динамичное развитие--%>
        <%--бизнесов внедрением лучших мировых практик, позволяющих управлять и контролировать процесс получив--%>
        <%--онлайн-доступ к системе не зная границ и расстояний и новую степень свободы в любой точке земного шара.</p>--%>

        <div class="row">
            <div class="col-md-6 mb-r">
                <img src="${contextPath}/images/features/info_stat.png" alt="" class="img-fluid z-depth-0">
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div class="col-xs-1 mr-1">
                        <i class="fa fa-mobile fa-2x cyan-text"></i>
                    </div>
                    <div class="col-xs-10">
                        <h4 class="feature-title">SeasonCard - это удобно</h4>
                        <p class="grey-text">Лёгкость освоения и пользования системой, возможность связи с клиентом
                            посредством SMS, Push-уведомления, электронной почты, телефонного звонка, поддержка
                            WEB-версии, Android-приложения,
                            iOS-приложения, возможность оффлайн-доступа.
                        </p>
                    </div>
                </div>

                <div style="height:20px"></div>

                <div class="row">
                    <div class="col-xs-1 mr-1">
                        <i class="fa fa-bar-chart fa-2x indigo-text"></i>
                    </div>
                    <div class="col-xs-10">
                        <h4 class="feature-title">SeasonCard - это информативно</h4>
                        <p class="grey-text">Структурированное ведение клиентской базы, гибкое управление абонементами,
                            ведение полной статистики и формирование отчётов.</p>
                    </div>
                </div>

                <div style="height:20px"></div>

                <div class="row">
                    <div class="col-xs-1 mr-1">
                        <i class="fa fa-lock fa-2x red-text"></i>
                    </div>
                    <div class="col-xs-10">
                        <h4 class="feature-title">SeasonCard - это надежно</h4>
                        <p class="grey-text">Надёжное хранение всех данных клиентов. Базы хранятся на защищённых
                            удаленных серверах. Передача данных надежно шифруется. Производится регулярное сохранение
                            резервных копий.</p>
                    </div>
                </div>
            </div>
        </div>

        <div style="height:70px">
            <hr class="hidden-md-up">
        </div>

        <div class="row">
            <div class="col-md-6 mb-r">

                <!--First row-->
                <div class="row">
                    <div class="col-xs-1 mr-1">
                        <i class="fa fa-money fa-2x light-green-text"></i>
                    </div>
                    <div class="col-xs-10">
                        <h4 class="feature-title">SeasonCard - это выгодно</h4>
                        <p class="grey-text">Доступная стоимость системы, экономия на разработке мобильных приложений,
                            онлайн-продажа ваших абонементов, отсутствие расходов на полиграфические услуги, учёт и
                            отслеживание неактивных клиентов, маркетинговые мероприятия для привлечения новых и
                            удержания
                            постоянных клиентов, актуальная статистика и финансовые отчёты, повышение качества
                            обслуживания клиентов.</p>
                    </div>
                </div>

                <div style="height:20px"></div>

                <div class="row">
                    <div class="col-xs-1 mr-1">
                        <i class="fa fa-shopping-cart fa-2x pink-text"></i>
                    </div>
                    <div class="col-xs-10">
                        <h4 class="feature-title">SeasonCard - это дополнительный эффективный канал продаж</h4>
                        <p class="grey-text">Удобный доступ к информации о ваших услугах (цены, специальные предложения
                            и акции, с возможностью оперативного изменения информации, онлайн-продажи ваших услуг).</p>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-2">
                <img src="${contextPath}/images/features/control_panel.png" alt="" class="img-fluid z-depth-0">
            </div>
        </div>
    </section>

    <%--<hr class="between-sections">--%>

    <%--<section class="section feature-box">--%>

    <%--<h1 class="section-heading">ПОЛЕЗНЫЕ ФУНКЦИИ</h1>--%>
    <%--<p class="section-description lead">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Fugit, error amet--%>
    <%--numquam iure provident voluptate esse quasi, veritatis totam voluptas nostrum quisquam eum porro a pariatur--%>
    <%--accusamus veniam. Quia, minima?</p>--%>

    <%--<div class="row features-small">--%>
    <%--<div class="col-md-4">--%>
    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-flag-checkered indigo-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">International</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-flask blue-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">Experimental</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-glass cyan-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">Relaxing</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="col-md-4 mb-r center-on-small-only">--%>
    <%--<img src="${contextPath}/images/features/client_main.png" alt="" class="z-depth-0"--%>
    <%--style="margin: 0 auto;">--%>
    <%--</div>--%>

    <%--<div class="col-md-4">--%>
    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-heart red-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">Beloved</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-flash orange-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">Rapid</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
    <%--<div class="col-xs-2">--%>
    <%--<i class="fa fa-magic lime-text"></i>--%>
    <%--</div>--%>
    <%--<div class="col-xs-10">--%>
    <%--<h4 class="feature-title">Magical</h4>--%>
    <%--<p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit--%>
    <%--maiores nam, aperiam minima assumenda deleniti hic.</p>--%>
    <%--<div style="height:30px"></div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</section>--%>

    <%--<hr class="between-sections">--%>

    <%--<section class="section">--%>

        <%--<h1 class="section-heading">О нас говорят</h1>--%>

        <%--<div id="testimonials" class="carousel testimonial-carousel slide carousel-fade" data-ride="carousel"--%>
             <%--data-interval="false">--%>

            <%--<div class="carousel-inner" role="listbox">--%>
                <%--<div class="carousel-item active">--%>
                    <%--<div class="testimonial">--%>
                        <%--<div class="avatar">--%>
                            <%--<img src="${contextPath}/images/features/crossmax.jpg" class="rounded img-fluid">--%>
                        <%--</div>--%>
                        <%--<p><i class="fa fa-quote-left"></i> Системой автоматизации «SeasonCard»--%>
                            <%--пользуемся с июля 2016 г. Долго выбирали CRM-систему, рассматривали множество вариантов и--%>
                            <%--остановились на «SeasonCard»! Есть весь необходимый функционал для комфортной работы нашего--%>
                            <%--клуба и самое главное, что для полноценного управления достаточно мобильного телефона.--%>
                            <%--Команда "SeasonCard" регулярно добавляет новые возможности и совершенствует свой сервис. Нам--%>
                            <%--нравится модель оплаты - абонентская плата. Альтернативные сервисы предлагают оплатить--%>
                            <%--единоразово внушительную сумму за систему, а некоторые системы требуют приобретения--%>
                            <%--дополнительного оборудования. Сотрудничеством с командой "SeasonCard" очень довольны.--%>
                            <%--Рекомендуем «SeasonCard» как самую удобную и простую систему автоматизации управления--%>
                            <%--клубом!</p>--%>

                        <%--<h4>CrossMax Family Fitness Club</h4>--%>
                        <%--<h5>Максим Тимошенко</h5>--%>

                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="carousel-item">--%>
                    <%--<div class="testimonial">--%>
                        <%--<div class="avatar">--%>
                            <%--<img src="${contextPath}/images/features/fiteffect.jpg" class="rounded img-fluid">--%>
                        <%--</div>--%>
                        <%--<p><i class="fa fa-quote-left"></i> Система автоматизации и учёта для нас--%>
                            <%--важный управленческий ресурс. Я хочу рассказать об опыте использования сервиса "SeasonCard".--%>
                            <%--После принятия решения о внедрении в сеть наших клубов CRM-системы возник вопрос о выборе--%>
                            <%--оптимального готового решения, отвечающего всем нашим требованиям. Используя в своих клубах--%>
                            <%--новейшие технологии из мира фитнеса наш поиск был проведён исключительно среди лучших--%>
                            <%--инновационных и удобных систем представленных на рынке. После изучения всех предложений,--%>
                            <%--выбор был сделан в пользу решения от «SeasonCard». Готовое решение от "SeasonCard",--%>
                            <%--сэкономило нам значительные средства, и позволило нашим клиентам получить самую актуальную--%>
                            <%--удобную и инновационную систему.--%>
                            <%--&lt;%&ndash;Система полностью соответствует нашим ожиданиям.&ndash;%&gt;--%>
                            <%--Всем нашим--%>
                            <%--прогрессивным клиентам очень нравится новый формат работы клубов. Каждый клиент получил--%>
                            <%--уникальную клубную карту, представляющую из себя умное высокотехнологичное устройство.--%>
                            <%--Впечатляет динамичное развитие "SeasonCard", её регулярные обновления и новые возможности.--%>
                            <%--Рекомендуем приобщиться к уникальным решениям предлагаемым нашим партнёром "SeasonCard"!</p>--%>

                        <%--<h4>Fiteffect</h4>--%>
                        <%--<h5>Андрей Дворкин</h5>--%>

                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="carousel-item">--%>
                    <%--<div class="testimonial">--%>
                        <%--<div class="avatar">--%>
                            <%--<img src="${contextPath}/images/features/orangeclub.jpg" class="rounded img-fluid">--%>
                        <%--</div>--%>
                        <%--<p><i class="fa fa-quote-left"></i> Наш клуб начал использовать сервис--%>
                            <%--«Seasoncard» с июня 2016 года. В данный момент в систему добавилось много нового,--%>
                            <%--актуального потребностям нашего бизнеса функционала, ребята делают систему доступной и--%>
                            <%--максимально адаптированной для услуг в сфере здорового образа жизни. Сервис позволил нам--%>
                            <%--перенести наш бизнес в онлайн, вести учёт клиентов, коммуницировать с ними через мобильное--%>
                            <%--приложение и получать актуальную статистику по всей деятельности клуба. Очень удобна функция--%>
                            <%--отметки посещений с помощью пластиковой карты и мобильного телефона. Скорость работы--%>
                            <%--администратора повысилась в разы, клиенты в восторге, а руководитель может видеть работу--%>
                            <%--своего клуба в любой момент времени, из любого места! Благодарим за взаимовыгодное--%>
                            <%--сотрудничество и желаем дальнейшего развития.--%>
                        <%--</p>--%>

                        <%--<h4>Orange Club</h4>--%>
                        <%--<h5>Александр Швайбович</h5>--%>
                    <%--</div>--%>

                <%--</div>--%>
            <%--</div>--%>
            <%--<a class="left carousel-control" href="#testimonials" role="button" data-slide="prev">--%>
                <%--<span class="icon-prev" aria-hidden="true"></span>--%>
                <%--<span class="sr-only">Previous</span>--%>
            <%--</a>--%>
            <%--<a class="right carousel-control" href="#testimonials" role="button" data-slide="next">--%>
                <%--<span class="icon-next" aria-hidden="true"></span>--%>
                <%--<span class="sr-only">Next</span>--%>
            <%--</a>--%>
        <%--</div>--%>
    <%--</section>--%>

    <%--<hr class="between-sections">--%>

    <%--<section class="section">--%>

        <%--<h1 class="section-heading">Наши партнёры</h1>--%>

        <%--<div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">--%>

            <%--<div class="controls-top">--%>
                <%--<a class="btn-floating btn-small primary-color" href="#multi-item-example" data-slide="prev"><i--%>
                        <%--class="fa fa-chevron-left"></i></a>--%>
                <%--<a class="btn-floating btn-small primary-color" href="#multi-item-example" data-slide="next"><i--%>
                        <%--class="fa fa-chevron-right"></i></a>--%>
            <%--</div>--%>

            <%--<c:set var="countPerPage" value="${4}"/>--%>

            <%--<ol class="carousel-indicators">--%>
                <%--<li class="primary-color" data-target="#multi-item-example" data-slide-to="0" class="active"></li>--%>
                <%--<c:forEach var="i" begin="1" end="${companies.size() div countPerPage}" varStatus="loop">--%>
                    <%--<li class="primary-color" data-target="#multi-item-example" data-slide-to="i"></li>--%>
                <%--</c:forEach>--%>
            <%--</ol>--%>

            <%--<div class="carousel-inner" role="listbox">--%>
                <%--<c:forEach var="i" begin="0" end="${companies.size() div countPerPage}" varStatus="loop">--%>
                    <%--<div class="carousel-item ${i == 0 ? 'active' : ''}">--%>
                        <%--<c:forEach var="j" begin="0" end="${countPerPage - 1}" varStatus="loop">--%>

                            <%--<c:set var="index" value="${i * countPerPage + j}"/>--%>

                            <%--<c:if test="${companies.size() > index}">--%>
                                <%--<div class="col-xs-3">--%>
                                    <%--<div class="collection-card hoverable view overlay hm-zoom z-depth-1">--%>
                                        <%--<a href="${contextPath}/companies/${companies.get(index).id}">--%>
                                            <%--<img src="${companies.get(index).logoPath}" class="img-fluid">--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</c:if>--%>
                        <%--</c:forEach>--%>
                    <%--</div>--%>
                <%--</c:forEach>--%>
            <%--</div>--%>
        <%--</div>--%>

    <%--</section>--%>

</div>

