<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row m-2">

        <div class="col-md-8 offset-md-1">

            <div id="start" class="mt-1">

                <h2 class="bold-500 mb-3">Часто задаваемые вопросы</h2>

                <h4 class="bold-500">О сервисе. Начало работы</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseWhatIsService" aria-expanded="false"
                       aria-controls="collapseExample">1. Для чего нужен сервис SeasonCard?</a>
                </p>
                <div id="collapseWhatIsService" class="collapse">
                    <div class="card card-block">
                        Онлайн-сервис SeasonCard помогает решать ряд важных задач для оптимизации времени работы
                        персонала по учету посетителей и абонементов, контроля платежей, ведения и анализа клиентской
                        базы, управленческого учёта и многого другого.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseAdvantagesService" aria-expanded="false"
                       aria-controls="collapseExample">2. Какие преимущества дает сервис SeasonCard?</a>
                </p>
                <div id="collapseAdvantagesService" class="collapse">
                    <div class="card card-block">
                        С онлайн-сервисом Season Card вы сможете легко управлять всеми процессами дистанционно, получать
                        актуальные отчеты о деятельности вашей компании, контролировать эффективность работы персонала,
                        сформировать актуальную клиентскую базу и многое другое.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseWichCompanyService" aria-expanded="false"
                       aria-controls="collapseExample">3. Каким компаниям подходит сервис SeasonCard?</a>
                </p>
                <div id="collapseWichCompanyService" class="collapse">
                    <div class="card card-block">
                        Онлайн-сервис SeasonCard специально разработан для работы с системой абонементов фитнес-клубов,
                        танцевальных школ, тренажерных залов, детских центров, спортивных школ, йога-студий, школ
                        единоборств и др.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEqipment" aria-expanded="false"
                       aria-controls="collapseExample">4. Какое оборудование необходимо для работы с сервисом
                        SeasonCard?</a>
                </p>
                <div id="collapseEqipment" class="collapse">
                    <div class="card card-block">
                        Для работы с сервисом требуется компьютер с доступом к сети интернет или мобильный телефон с
                        системой Android. Смартфон с системой Android используется для ускорения процесса регистрации
                        клиентов в сервисе путем распознавания бесконтактной карты клиента. Для этого в телефоне
                        необходим модуль NFC, а также установленное на смартфоне мобильное приложение SeasonCard. Данное
                        мобильное приложение также позволяет считывать информацию по штрих-коду и QR-коду.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowStart" aria-expanded="false"
                       aria-controls="collapseExample">5. Как стать пользователем сервиса?</a>
                </p>
                <div id="collapseHowStart" class="collapse">
                    <div class="card card-block">
                        Стать пользователем сервиса SeasonCard очень просто – заполните регистрационную форму на сайте
                        seasoncard.by, после этого на указанный электронный адрес вам придет письмо с логином и паролем
                        для входа в личный кабинет. Попробуйте сервис абсолютно бесплатно!
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowPopulate" aria-expanded="false"
                       aria-controls="collapseExample">6. Как заполнить данные о компании и для чего это нужно?</a>
                </p>
                <div id="collapseHowPopulate" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Профиль компании». Здесь внесите данные о вашей компании, загрузите логотип,
                        контактную информацию. Так вас смогут увидеть все пользователи системы. Также ниже укажите ваши
                        банковские реквизиты – это закрытая информация, которая доступна только сервису SeasonCard и
                        будет использована для выставления счетов на оплату.
                    </div>
                </div>
            </div>

            <div id="clients" class="mt-3">

                <h4 class="bold-500">Работа с клиентской базой</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseHowCreateBase" aria-expanded="false"
                       aria-controls="collapseExample">1. Как создать автоматизированную клиентскую базу?</a>
                </p>
                <div id="collapseHowCreateBase" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Клиенты», который находится в основном меню. Здесь будет отображаться список
                        всех ваших клиентов. Чтобы добавить нового клиента, справа нажмите на кнопку «Добавить клиента».
                        Внесите необходимую информацию, нажмите «Сохранить». Таким образом, внесите данные по всем
                        клиентам. Теперь вы сможете осуществить поиск клиента по ФИО, номеру телефона, номеру карты и
                        тегам, зачислить клиенту абонемент, просмотреть список всех его абонементов, а также отправить
                        смс или e-mail уведомление.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowEditClientInfo" aria-expanded="false"
                       aria-controls="collapseExample">2. Как редактировать информацию о клиентах?</a>
                </p>
                <div id="collapseHowEditClientInfo" class="collapse">
                    <div class="card card-block">
                        Найдите необходимого вам клиента в разделе «Клиенты», который находится в основном меню, или в
                        форме поиска на главной странице. Слева от кнопки «зачислить» находится кнопка «карандаш». При
                        нажатии на эту кнопку, появится форма редактирования данных о клиенте.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseUniqClientNumber" aria-expanded="false"
                       aria-controls="collapseExample">3. Можно ли присвоить свой уникальный внутренний номер
                        клиенту?</a>
                </p>
                <div id="collapseUniqClientNumber" class="collapse">
                    <div class="card card-block">
                        Каждому клиенту можно присвоить уникальный внутренний номер, соответствующий номеру вашего
                        договора. Для этого при заполнении или редактировании информации о клиенте заполните поле
                        «Внутренний номер в рамках компании». По данному внутреннему номеру доступен поиск клиентов.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowFindClient" aria-expanded="false"
                       aria-controls="collapseExample">4. Как найти клиента в базе сервиса SeasonCard?</a>
                </p>
                <div id="collapseHowFindClient" class="collapse">
                    <div class="card card-block">
                        Поиск клиентов может осуществляться в разделе «Клиенты» или на главной странице. Вы можете найти
                        клиента по ФИО, номеру телефона, номеру карты, внутреннему индивидуальному номеру, тегам или
                        комментариям указанным при регистрации данного клиента.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowSendSMSEmail" aria-expanded="false"
                       aria-controls="collapseExample">5. Как отправить клиенту смс и/или e-mail уведомление через
                        сервис SeasonCard?</a>
                </p>
                <div id="collapseHowSendSMSEmail" class="collapse">
                    <div class="card card-block">
                        Чтобы осуществить массовую рассылку смс перейдите в раздел «Клиенты», выберите необходимое
                        количество клиентов и справа нажмите на кнопку «отправить смс выбранным». Если необходимо
                        отправить смс определенному клиенту – найдите его через поиск, нажмите на его ФИО. Откроется
                        личная страница клиента, на которой есть разделы «смс - рассылка» и «e-mail рассылка». Выберите
                        необходимый раздел и отправьте сообщение.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowSeeSMSEmail" aria-expanded="false"
                       aria-controls="collapseExample">6. Как посмотреть историю отправки смс и e-mail уведомлений
                        клиенту?</a>
                </p>
                <div id="collapseHowSeeSMSEmail" class="collapse">
                    <div class="card card-block">
                        Найдите клиента через поиск, нажмите на его ФИО. Откроется личная страница клиента, на которой
                        есть разделы «смс - рассылка» и «e-mail рассылка». Выберите необходимый раздел и посмотрите
                        историю отправки сообщений.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseBirthday" aria-expanded="false"
                       aria-controls="collapseExample">7. Как узнать у кого из клиентов скоро День рождения?</a>
                </p>
                <div id="collapseBirthday" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Клиенты», который находится в основном меню. Над списком всех клиентов
                        справа находится кнопка «Дни Рождения», нажав на которую, вы можете посмотреть список клиентов,
                        у кого День Рождения сегодня, на текущей неделе или в текущем месяце.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEnrollAbon" aria-expanded="false"
                       aria-controls="collapseExample">8. Как зачислить абонемент клиенту?</a>
                </p>
                <div id="collapseEnrollAbon" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите «Зачислить». После этого откроется страница с указанием клиента и списком всех
                        абонементов вашей компании.
                        Напротив каждого наименования абонемента есть кнопка «Зачислить абонемент». Выберите необходимый
                        абонемент и нажмите на эту кнопку. Откроется диалоговое окно для подтверждения зачисления
                        выбранного абонемента.
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты», и нажмите кнопку
                        «Зачислить абонемент».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseMarkAttendance" aria-expanded="false"
                       aria-controls="collapseExample">9. Как отметить клиенту посещение по абонементу?</a>
                </p>
                <div id="collapseMarkAttendance" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите кнопку «Отметить». После этого откроется страница с указанием клиента и списком его
                        абонементов. Выберите необходимый абонемент и нажмите кнопку «Отметить посещение».
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты». Откройте личную страницу
                        клиента, выберите необходимый абонемент и нажмите кнопку «Отметить посещение».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseMarkSkipAttendance" aria-expanded="false"
                       aria-controls="collapseExample">10. Как отметить пропуск по абонементу?</a>
                </p>
                <div id="collapseMarkSkipAttendance" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите кнопку «Отметить». После этого откроется страница с указанием клиента и списком его
                        абонементов. Выберите необходимый абонемент и нажмите кнопку «Отметить посещение». В появившемся
                        диалоговом окне поставьте галочку в графе «Пропуск».
                        Если номер карты не известен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты». Откройте личную страницу
                        клиента, выберите необходимый абонемент и нажмите кнопку «Отметить посещение». В появившемся
                        диалоговом окне поставьте галочку в графе «Пропуск».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseMarkWithoutAbon" aria-expanded="false"
                       aria-controls="collapseExample">11. Как отметить посещение без абонемента?</a>
                </p>
                <div id="collapseMarkWithoutAbon" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Нажмите на кнопку «Разовое посещение». В появившемся диалоговом
                        окне внесите необходимые данные.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseLoseCard" aria-expanded="false"
                       aria-controls="collapseExample">12. Клиент потерял карту, что делать?</a>
                </p>
                <div id="collapseLoseCard" class="collapse">
                    <div class="card card-block">
                        Найдите клиента по номеру карты, индивидуальному внутреннему номеру, ФИО или номеру телефона.
                        Откройте его личную страницу. Справа от номера карты клиента нажмите на кнопку «Заменить карту».
                        При этом все абонементы с утерянной карты автоматически будут перезачислены на новую.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseClientAbons" aria-expanded="false"
                       aria-controls="collapseExample">13. Как посмотреть список всех абонементов клиента?</a>
                </p>
                <div id="collapseClientAbons" class="collapse">
                    <div class="card card-block">
                        Найдите клиента по номеру карты, индивидуальному внутреннему номеру, ФИО или номеру телефона.
                        Нажмите на кнопку «Абонементы». Откроется его личная страница, здесь находится список всех его
                        абонементов.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseClientAbonsHistory" aria-expanded="false"
                       aria-controls="collapseExample">14. Как посмотреть историю посещений по абонементу?</a>
                </p>
                <div id="collapseClientAbonsHistory" class="collapse">
                    <div class="card card-block">
                        Найдите клиента по номеру карты, индивидуальному внутреннему номеру, ФИО или номеру телефона.
                        Нажмите на кнопку «Абонементы» выберите необходимый абонемент, нажмите на кнопку «Подробнее».
                        Справа вы увидите историю посещений по данному абонементу.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEnrollWithDiscount" aria-expanded="false"
                       aria-controls="collapseExample">15. Как зачислить абонемент со скидкой?</a>
                </p>
                <div id="collapseEnrollWithDiscount" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите «Зачислить». После этого откроется страница с указанием клиента и списком всех
                        абонементов вашей компании.
                        Напротив каждого наименования абонемента есть кнопка «Зачислить абонемент». Выберите необходимый
                        абонемент и нажмите на эту кнопку. Откроется диалоговое окно для подтверждения зачисления
                        выбранного абонемента. В соответствующем поле выберите необходимую скидку, цена будет
                        пересчитана автоматически.
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты», и нажмите кнопку
                        «Зачислить абонемент».
                    </div>
                </div>
            </div>


            <div id="abonnements" class="mt-3">

                <h4 class="bold-500">Управление абонементами</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseCreateAbon" aria-expanded="false"
                       aria-controls="collapseExample">1. Как внести информацию об абонементах компании?</a>
                </p>
                <div id="collapseCreateAbon" class="collapse">
                    <div class="card card-block">
                        В главном меню перейдите в раздел «Абонементы». Нажмите на кнопку «Добавить абонемент». Появится
                        форма для создания абонемента. Введите необходимые данные.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEditAbon" aria-expanded="false"
                       aria-controls="collapseExample">2. Как отредактировать информацию об абонементе?</a>
                </p>
                <div id="collapseEditAbon" class="collapse">
                    <div class="card card-block">
                        В главном меню перейдите в раздел «Абонементы». Найдите необходимый абонемент и нажмите на
                        кнопку «Редактировать».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseAddAbonImage" aria-expanded="false"
                       aria-controls="collapseExample">3. Как добавить изображение для каждого абонемента?</a>
                </p>
                <div id="collapseAddAbonImage" class="collapse">
                    <div class="card card-block">
                        В главном меню перейдите в раздел «Абонементы». Найдите необходимый абонемент и нажмите на
                        кнопку «Редактировать». Загрузите изображение.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEnroll" aria-expanded="false"
                       aria-controls="collapseExample">4. Как зачислить абонемент клиенту?</a>
                </p>
                <div id="collapseEnroll" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите «Зачислить». После этого откроется страница с указанием клиента и списком всех
                        абонементов вашей компании.
                        Напротив каждого наименования абонемента есть кнопка «Зачислить абонемент». Выберите необходимый
                        абонемент и нажмите на эту кнопку. Откроется диалоговое окно для подтверждения зачисления
                        выбранного абонемента.
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты», и нажмите кнопку
                        «Зачислить абонемент».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseMarkAttendanceAbon" aria-expanded="false"
                       aria-controls="collapseExample">5. Как отметить клиенту посещение по абонементу?</a>
                </p>
                <div id="collapseMarkAttendanceAbon" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите кнопку «Отметить». После этого откроется страница с указанием клиента и списком его
                        абонементов. Выберите необходимый абонемент и нажмите кнопку «Отметить посещение».
                        Если номер карты не известен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты». Откройте личную страницу
                        клиента, выберите необходимый абонемент и нажмите кнопку «Отметить посещение».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseMarkSkipAttendanceAbon" aria-expanded="false"
                       aria-controls="collapseExample">6. Как отметить пропуск по абонементу?</a>
                </p>
                <div id="collapseMarkSkipAttendanceAbon" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите кнопку «Отметить». После этого откроется страница с указанием клиента и списком его
                        абонементов. Выберите необходимый абонемент и нажмите кнопку «Отметить посещение». В появившемся
                        диалоговом окне поставьте галочку в графе «Пропуск».
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты». Откройте личную страницу
                        клиента, выберите необходимый абонемент и нажмите кнопку «Отметить посещение». В появившемся
                        диалоговом окне поставьте галочку в графе «Пропуск».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseClientAllAbons" aria-expanded="false"
                       aria-controls="collapseExample">7. Как посмотреть список всех абонементов клиента?</a>
                </p>
                <div id="collapseClientAllAbons" class="collapse">
                    <div class="card card-block">
                        Найдите клиента по номеру карты, индивидуальному внутреннему номеру, ФИО или номеру телефона.
                        Нажмите на кнопку «Абонементы». Откроется его личная страница, здесь находится список всех его
                        абонементов.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseClientAbonHistory" aria-expanded="false"
                       aria-controls="collapseExample">8. Как посмотреть историю посещений по абонементу?</a>
                </p>
                <div id="collapseClientAbonHistory" class="collapse">
                    <div class="card card-block">
                        Найдите клиента по номеру карты, индивидуальному внутреннему номеру, ФИО или номеру телефона.
                        Нажмите на кнопку «Абонементы» выберите необходимый абонемент, нажмите на кнопку «Подробнее».
                        Справа вы увидите историю посещений по данному абонементу.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEnrollAbonDiscount" aria-expanded="false"
                       aria-controls="collapseExample">9. Как зачислить абонемент со скидкой?</a>
                </p>
                <div id="collapseEnrollAbonDiscount" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите «Зачислить». После этого откроется страница с указанием клиента и списком всех
                        абонементов вашей компании.
                        Напротив каждого наименования абонемента есть кнопка «Зачислить абонемент». Выберите необходимый
                        абонемент и нажмите на эту кнопку. Откроется диалоговое окно для подтверждения зачисления
                        выбранного абонемента. В соответствующем поле выберите необходимую скидку, цена будет
                        пересчитана автоматически.
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты», и нажмите кнопку
                        «Зачислить абонемент».
                    </div>
                </div>

            </div>


            <div id="discounts" class="mt-3">

                <h4 class="bold-500">Создание скидок и акций</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseHowCreateDiscount" aria-expanded="false"
                       aria-controls="collapseExample">1. Как создать акцию или скидку?</a>
                </p>
                <div id="collapseHowCreateDiscount" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Скидки и Акции». Нажмите на кнопку «Добавить акцию», заполните необходимые
                        поля.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowEditDiscount" aria-expanded="false"
                       aria-controls="collapseExample">2. Как изменить акцию или скидку?</a>
                </p>
                <div id="collapseHowEditDiscount" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Скидки и Акции». Найдите необходимую акцию и нажмите на её название –
                        откроется окно редактирования данных.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseEnrollDiscount" aria-expanded="false"
                       aria-controls="collapseExample">3. Как зачислить абонемент со скидкой?</a>
                </p>
                <div id="collapseEnrollDiscount" class="collapse">
                    <div class="card card-block">
                        Перейдите на главную страницу. Если вы знаете номер карты - введите его в соответствующее поле и
                        нажмите «Зачислить». После этого откроется страница с указанием клиента и списком всех
                        абонементов вашей компании.
                        Напротив каждого наименования абонемента есть кнопка «Зачислить абонемент». Выберите необходимый
                        абонемент и нажмите на эту кнопку. Откроется диалоговое окно для подтверждения зачисления
                        выбранного абонемента. В соответствующем поле выберите необходимую скидку, цена будет
                        пересчитана автоматически.
                        Если номер карты неизвестен - найдите клиента по ФИО, уникальному внутреннему номеру или номеру
                        телефона. Это можно сделать на главной странице и в разделе «Клиенты», и нажмите кнопку
                        «Зачислить абонемент».
                    </div>
                </div>
            </div>

            <div id="trainers" class="mt-3">

                <h4 class="bold-500">Внесение информации о тренерах</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseHowCreateTrainer" aria-expanded="false"
                       aria-controls="collapseExample">1. Как вносить информацию о тренерах?</a>
                </p>
                <div id="collapseHowCreateTrainer" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Тренеры». Нажмите на кнопку «Добавить тренера». Заполните необходимые поля.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowChangeTrainer" aria-expanded="false"
                       aria-controls="collapseExample">2. Как изменить информацию о тренере?</a>
                </p>
                <div id="collapseHowChangeTrainer" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Тренеры». Выберите необходимого тренера, нажмите на его ФИО, откроется
                        страница редактирования данных.
                    </div>
                </div>

            </div>

            <div id="reports" class="mt-3">

                <h4 class="bold-500">Формирование отчетов</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseHowGenerateReport" aria-expanded="false"
                       aria-controls="collapseExample">1. Какие отчеты можно сформировать через сервис SeasonCard?</a>
                </p>
                <div id="collapseHowGenerateReport" class="collapse">
                    <div class="card card-block">
                        В системе SeasonCard предусмотрена возможность формирования отчетов по всем абонементам, по
                        проданным абонементам, по разовым посещениям, по новым посетителям, по посещениям по времени, по
                        тренерам (информация по проданным абонементам к каждому тренеру), а также по дням рождения
                        посетителей. Каждый отчет может быть сформирован за текущий день, месяц или любой другой период
                        времени.
                    </div>
                </div>

            </div>

            <div id="balance" class="my-3">

                <h4 class="bold-500">Баланс</h4>

                <hr>

                <p>
                    <a data-toggle="collapse" href="#collapseHowShowBalance" aria-expanded="false"
                       aria-controls="collapseExample">1. Как посмотреть баланс своего счета?</a>
                </p>
                <div id="collapseHowShowBalance" class="collapse">
                    <div class="card card-block">
                        Чтобы посмотреть свой баланс, текущий тариф и срок его действия, а также историю операций по
                        счету, перейдите в раздел «Баланс».
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowFillBalance" aria-expanded="false"
                       aria-controls="collapseExample">2. Как пополнить баланс?</a>
                </p>
                <div id="collapseHowFillBalance" class="collapse">
                    <div class="card card-block">
                        Запросите счет на необходимую сумму у администратора сервиса SeasonCard. После его оплаты ваш
                        баланс будет пополнен.
                    </div>
                </div>

                <p>
                    <a data-toggle="collapse" href="#collapseHowShowBalanceHistory" aria-expanded="false"
                       aria-controls="collapseExample">3. Как посмотреть историю операций по счету?</a>
                </p>
                <div id="collapseHowShowBalanceHistory" class="collapse">
                    <div class="card card-block">
                        Перейдите в раздел «Баланс».
                    </div>
                </div>

            </div>

        </div>

        <div class="col-md-3">
            <div>
                <ul class="nav nav-pills flex-column">
                    <li class="nav-item"><a class="nav-link" href="#start">О сервисе. Начало работы</a></li>
                    <li class="nav-item"><a class="nav-link" href="#clients">Работа с клиентской базой</a></li>
                    <li class="nav-item"><a class="nav-link" href="#abonnements">Управление абонементами</a></li>
                    <li class="nav-item"><a class="nav-link" href="#discounts">Создание скидок и акций</a></li>
                    <li class="nav-item"><a class="nav-link" href="#trainers">Внесение информации о тренерах</a></li>
                    <li class="nav-item"><a class="nav-link" href="#reports">Формирование отчетов</a></li>
                    <li class="nav-item"><a class="nav-link" href="#balance">Баланс</a></li>
                </ul>
            </div>
            <div class="sticky-placeholder"></div>
        </div>
    </div>
</div>