
<#import "template.ftl" as templ>
<@templ.override "title">Регистрация в системе</@templ.override>
<@templ.override "body">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <div style="border: 1px solid #e1e1e1; overflow: hidden; padding: 30px;">
                <div style="float: left;">

                    <p style="margin: 0 0 15px;">
                        Здравствуйте, ${user.name}!
                    </p>

                    <p style="margin: 0 0 15px;">
                        Благодарим вас за проявленный интерес к сервису SeasonCard.
                    </p>

                    <p style="margin: 0 0 15px;">
                        Вы успешно зарегистрированы в системе SeasonCard как администратор компании ${company.name}.
                    </p>


                    <p style="margin: 0 0 15px;">
                        Для входа в профиль вашей компании вам необходимо ввести следующие параметры на сайте <a
                            href="https://seasoncard.by">SeasonCard.by</a>
                    </p>

                    <p style="margin: 0 0 15px;">
                        Логин: <strong>${user.email} </strong>
                        <br/>
                        Пароль:<strong> ${password} </strong>
                    </p>

                    <p style="margin: 0 0 15px;">
                        После входа в систему вы сможете редактировать профиль компании, профиль пользователя, изменить
                        текущий пароль для входа в систему.
                    </p>

                    <p style="margin: 0 0 15px;">
                        Приложение для Android-смартфона вы сможете установить по ссылке:
                        <br/>
                        <a href="https://play.google.com/store/apps/details?id=by.kartka.android.business.app">
                            https://play.google.com/store/apps/details?id=by.kartka.android.business.app
                        </a>
                        <br/>
                        Параметры для входа в Android-приложении совпадают с параметрами для входа через сайт. Они
                        следующие:
                    </p>


                    <p style="margin: 0 0 15px;">
                        Логин: <strong>${user.email} </strong>
                        <br/>
                        Пароль:<strong> ${password} </strong>
                    </p>

                    <p>
                        Если вам потребуются дополнительные сотрудники для выполнения роли администратора в системе, вы
                        можете выслать их e-mail адреса на
                        <a href="mailto:support@seasoncard.by">support@seasoncard.by</a> и мы добавим им данные права.
                        Мы настоятельно рекомендуем для каждого сотрудника вашей компании,
                        который будет работать с системой SeasonCard иметь отдельный аккаунт.
                    </p>

                    <p style="margin: 0 0 15px;">
                        Если у вас возникли дополнительные вопросы или предложения, пожалуйста, присылайте их на адрес <a href="mailto:support@seasoncard.by">support@seasoncard.by</a>
                    </p>

                    <p style="margin: 0 0 15px;">
                        С уважением,
                        <br/>
                        SeasonCard Team
                        <br/>
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>
</@templ.override>
<@templ.template />


