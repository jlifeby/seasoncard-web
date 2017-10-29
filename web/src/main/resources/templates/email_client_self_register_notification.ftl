<#import "template.ftl" as templ>
<@templ.override "title">Онлайн регистрация</@templ.override>
<@templ.override "body">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <div style="border: 1px solid #e1e1e1; overflow: hidden; padding: 30px;">
                <div style="float: left;">

                    <p style="margin: 0 0 15px;">
                        Пользователь выполнил самостоятельную регистрацию
                    </p>

                    <p style="margin: 0 0 15px;">
                        ФИО: <strong>${client.name} ${client.lastName}</strong> <br/>
                        Номер телефона: <strong>${client.phone}</strong> <br/>
                        Номер присвоенной карты: <strong>${client.cardUUID?c}</strong>
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>
</@templ.override>
<@templ.template />


