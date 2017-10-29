<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    $(document).ready(function () {
        $('.birthday').each(function(index) {
            var birthdayNow = moment($(this).text(), 'D MMMM YYYY').year(moment().year());
            if (moment().startOf('day').isSame(birthdayNow)) {
                $(this).append('<span class="red-text"> (День Рождения!)</span>');
                //showMessage('Сегодня у пользователя День Рождения!');
            } else if (birthdayNow.isAfter(moment()) && birthdayNow.isBefore(moment().month(moment().month() + 2))) {
                birthdayNow = birthdayNow.add(1, 'day');
                $(this).append('<span class="red-text"> (' + birthdayNow.fromNow() + ')</span>');
            }
        });
    });
</script>