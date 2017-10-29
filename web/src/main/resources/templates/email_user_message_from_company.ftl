<#import "template.ftl" as templ>
<@templ.override "title">Сообщение от компании ${company.name}</@templ.override>
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
                    ${content}
                    </p>

                    <p style="margin: 0 0 15px;">
                        С уважением, ${company.name}
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>

<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;padding: 20px 0;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <hr style="margin: 0; border: 0; border-bottom: 0 none; border-top: 1px solid #e1e1e1;">
        </td>
    </tr>
</table>

<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto; padding: 20px 0;">
    <tr style="margin: 0;">
        <td style="text-align: left; vertical-align: top; width: 100px">
            <a href="${appUrl}/companies/${company.id}"><img src="${appUrl}${company.logoPath}" alt="Logo"
                                                             style="max-width: 100px;"></a>
        </td>
        <td style="width: 14px; text-align: left; vertical-align: top;"></td>
        <td style="text-align: left; vertical-align: top;">
            <h3 style="font-size: 16px; font-weight: bold; line-height: 1.3; margin: 0 0 12px; margin-bottom: 7px;"><a
                    href="${appUrl}/companies/${company.id}"
                    style="color: #1e1e1e; text-decoration: underline;">${company.name}</a></h3>
            <p style="margin: 0 0 12px; font-size: 12px;">${company.description}</p>
            <a href="${appUrl}/companies/${company.id}"
               style="color: #fff; text-decoration: none; font-size: 10px; padding: 2px 10px; -moz-border-radius: 3px; -webkit-border-radius: 3px; background: #1e1e1e; border: 0 none; border-radius: 3px; display: inline-block;">Подробнее</a>
        </td>
        <td style="width: 14px; text-align: left; vertical-align: top;"></td>
        <td style="text-align: left; vertical-align: top; width: 150px">
            <p style="margin: 0 0 12px; font-size: 12px;">${company.email}</p>
            <#list company.phones as phone>
                <p style="margin: 0 0 2px; font-size: 12px;">${phone}</p>
            </#list>
        </td>
    </tr>
</table>

</@templ.override>
<@templ.template />