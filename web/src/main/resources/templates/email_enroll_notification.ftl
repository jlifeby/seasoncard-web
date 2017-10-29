<#import "template.ftl" as templ>
<@templ.override "title">Продажа абонемента</@templ.override>
<@templ.override "body">
<table width="600" cellspacing="0" cellpadding="0" border="0" style="margin: 0 auto;">
    <tr>
        <td style="text-align: left; vertical-align: top;">
            <div style="border: 1px solid #e1e1e1; overflow: hidden; padding: 30px;">
                <div style="float: left;">

                    <p style="margin: 0 0 15px;">
                        Абонемент был реализован пользователю:
                    </p>

                    <p style="margin: 0 0 15px;">
                        ФИО: <strong>${client.name} ${client.lastName}</strong> <br/>
                        Номер: <strong>${client.cardUUID}</strong>
                    </p>
                    <p style="margin: 0 0 15px;">
                        Информация по абонементу:
                    </p>
                    <p style="margin: 0 0 15px;">
                        Наименование: <strong>${abon.product.name}</strong> <br/>
                        Дата начала: <strong>${abon.startDate.toString('dd.MM.yyyy')}</strong> <br/>
                        Дата окончания: <strong>${abon.endDate.toString('dd.MM.yyyy')}</strong> <br/>
                        Единицы: <strong>${abon.product.countOfAttendancesAsString}</strong>
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>
</@templ.override>
<@templ.template />


