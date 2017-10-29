<#global html_block = {}>
<#macro override name>
    <#local  html_tmp><#nested></#local>
    <#local  html_tmp_inherited=html_block[name]!""/>
    <#global html_block = html_block + {name:(html_tmp+html_tmp_inherited)}>
</#macro>
<#macro block name>
${html_block[name]!""}
</#macro>
<#macro inherit page>
    <#import page as inh>
</#macro>
<#macro template>
<!doctype html>
<html>
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
</head>
<body style="background-color: #ffffff; color: #1e1e1e; font-family: 'Arimo', sans-serif; font-size: 14px; line-height: 22px; margin: 0; padding: 0;">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto; height: 105px;">
    <tr>
        <td style="text-align: left; vertical-align: top; padding-top: 25px;">
            <a href="https://seasoncard.by"><img src="${appUrl}/images/email/logo.png" alt="Logo" style="max-width: 170px;"></a>
        </td>
        <td style="padding: 11px 0; text-align: right; vertical-align: top;">
            <h1 style="font-size: 18px; font-weight: bold; line-height: 1.3; margin: 40px 0 10px;"><@block "title"/></h1>
        </td>
    </tr>
</table>

<table cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto; width: 100%;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <hr style="border: 0; border-bottom: 0 none; border-top: 1px solid #e1e1e1; margin: 0 0 35px;">
        </td>
    </tr>
</table>

    <@block "body"/>

<div style="background: #f7f7f7; border-top: 1px solid #e1e1e1; color: #7f7f7f; font-size: 11px; line-height: 20px; padding: 20px 0; margin: 40px 0;">
    <table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
        <tr>
            <td style="text-align: left; vertical-align: top;">
                <br>Copyright © <a href="https://seasoncard.by">SeasonCard</a>, 2015 - 2017</span>
            </td>
            <td style="text-align: right; vertical-align: top; width: 50px;">
                <a href="https://vk.com/seasoncard" target="_blank"
                   style="background: #3b57a9; -webkit-border-radius: 2px; -moz-border-radius: 2px; border-radius: 2px; display: block; height: 40px; line-height: 46px; margin: 0 0 0 10px; text-align: center; width: 40px; text-decoration: none;">
                    <img src="${appUrl}/images/email/vkontakte.png" alt="vkontakte"
                         style="max-width: 100%;">
                </a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
</#macro>