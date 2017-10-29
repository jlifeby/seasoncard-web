<#import "template.ftl" as templ>
<@templ.override "title">Восстановление пароля</@templ.override>
<@templ.override "body">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <div style="border: 1px solid #e1e1e1; overflow: hidden; padding: 30px;">
                <div style="float: left;">

                    <p style="margin: 0 0 15px;">
                        Здравствуйте, ${user.name}.
                    </p>

                    <p style="margin: 0 0 15px;">
                        Вы сделали запрос на восстановление доступа для ${appUrl}
                    </p>

                    <p style="margin: 0 0 15px;">
                        Чтобы установить новый пароль для вашего аккаута перейдите по ссылке
                    </p>

                    <p style="margin: 0 0 15px;">
                        <a href="${appUrl}${relativeRecoveringLink}" target="_blank">${appUrl}${relativeRecoveringLink}</a>
                    </p>

                    <p style="margin: 0 0 15px;">
                        С уважением, команда SeasonCard
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>
</@templ.override>
<@templ.template />


