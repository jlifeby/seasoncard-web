<#import "template.ftl" as templ>
<@templ.override "title">Подтверждение email</@templ.override>
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
                        Чтобы подтвердить действительность электронного адреса ${newEmail}, указанного для Вашей учетной
                        записи SeasonCard, нажмите:
                    </p>
                    <p style="margin: 0 0 15px;">
                        <a style="display:inline-block;width:100%;background-color:#00bcd4;text-decoration:none;color:#ffffff;font-size:18px;font-weight:bold;text-align:center;padding:15px 0px 15px 0px;-webkit-border-radius:2px;-moz-border-radius:2px;border-radius:2px"
                           href="${appUrl}${relativeConfirmationLink}" target="_blank">Подтвердить</a>
                    </p>
                    <p style="margin: 0 0 15px;">
                        Или перейдите по ссылке:
                    </p>
                    <p style="margin: 0 0 15px;">
                        <a href="${appUrl}${relativeConfirmationLink}"
                           target="_blank">${appUrl}${relativeConfirmationLink}</a>
                    </p>

                    <p style="margin: 0 0 15px;">
                        После этого Вы сможете пользоваться всеми возможностями SeasonCard, где требуется авторизация:
                        например, быстро и эффективно управлять своими абонементами, получать Online информацию о
                        количестве посещений и актуальные контакты Ваших любимых заведений, а также доступ к электронной
                        версии Вашей карты.
                        Эта проверка, кроме того, — дополнительная гарантия безопасности Вашей учетной записи!
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


