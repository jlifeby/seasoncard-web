<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!doctype html>
<html class="full-height">
<head>

    <meta charset="utf-8">

    <title>Главная / Kartka.by</title>

    <meta name="keywords"
          content="Карточки, скидки, дисконт, Минск">
    <meta name="description" content="Онлайн-сервис по скидкам">

    <meta name="author" content="UNDEADgtr">
    <meta class="viewport" name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="images/coming-soon/favicon.ico">

    <!-- Font -->
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Arimo:400,700,400italic,700italic'>

    <!-- Plagins CSS -->
    <link rel="stylesheet" href="css/coming-soon/bootstrap.min.css">

    <!-- Theme CSS -->
    <link rel="stylesheet" href="css/coming-soon/style.css">

    <!-- IE Styles-->
    <link rel='stylesheet' href="css/coming-soon/ie/ie.css">

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <link rel='stylesheet' href="css/coming-soon/ie/ie8.css">
    <![endif]-->

</head>
<body class="body-bg-img">
<div class="page-box">

    <header class="header header-three">
        <div class="header-wrapper">
            <div class="container">
                <div class="row">
                    <div class="logo-box col-sm-12 col-md-12">
                        <div class="logo">
                            <a href="/">
                                <img src="images/coming-soon/logo.png" class="logo-img" alt="">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="text-center col-sm-12 col-md-12">
                    <h4>Мы в стадии разработки!</h4>

                    <p>
                        В настоящее время мы работаем над новым веб-сайтом и это не займет много времени. Пожалуйста, не
                        забудьте вернуться к нам позже!
                    </p>
                    <br>
                    <hr>
                    <br>
                </div>
            </div>
        </div>
    </section>
</div>

<footer id="footer" class="footer-two">
    <div class="footer-top">
        <div class="container">
            <div class="row">
                <div class="social col-sm-12 col-md-12">
                    <strong>Напишите нам:</strong>
                    <a href="mailto:info@kartka.by">info@kartka.by</a><br>
                </div>
                <div class="social col-sm-12 col-md-12" style="color:gainsboro">
                    <strong>Version: </strong> @war_build_version@
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- Yandex.Metrika counter -->
<script type="text/javascript">
    (function (d, w, c) {
        (w[c] = w[c] || []).push(function() {
            try {
                w.yaCounter33401228 = new Ya.Metrika({
                    id:33401228,
                    clickmap:true,
                    trackLinks:true,
                    accurateTrackBounce:true
                });
            } catch(e) { }
        });

        var n = d.getElementsByTagName("script")[0],
                s = d.createElement("script"),
                f = function () { n.parentNode.insertBefore(s, n); };
        s.type = "text/javascript";
        s.async = true;
        s.src = "https://mc.yandex.ru/metrika/watch.js";

        if (w.opera == "[object Opera]") {
            d.addEventListener("DOMContentLoaded", f, false);
        } else { f(); }
    })(document, window, "yandex_metrika_callbacks");
</script>
<noscript><div><img src="https://mc.yandex.ru/watch/33401228" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
<!-- /Yandex.Metrika counter -->

</body>
</html>