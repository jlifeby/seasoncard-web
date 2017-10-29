<#import "template.ftl" as templ>
<@templ.override "title">Сообщение от пользователя</@templ.override>
<@templ.override "body">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <div style="border: 1px solid #e1e1e1; overflow: hidden; padding: 30px;">
                <div style="float: left;">

                    <p style="margin: 0 0 15px;">
                        Письмо от пользователя: ${user_name}
                    </p>

                    <p style="margin: 0 0 15px;">
                        Пользователь ${user_name} задал вопрос:
                        <pre>
                        ${user_message}
                        </pre>
                        Ответить пользователю
                        <a href="mailto:${user_email}?subject=Re:Сообщение от пользователя ${user_name}">${user_email}</a><br/>
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>
</@templ.override>
<@templ.template />


