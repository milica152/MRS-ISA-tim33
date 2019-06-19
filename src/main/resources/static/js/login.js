$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: '/whoami',
        success: function (data) {
            if (data == undefined || data == "") {
                $('#logout-menu-item').hide();
                $('#login-menu-item').show();
            } else {
                $('#login-menu-item').hide();
                $('#logout-menu-item').show();
            }
        },
        async: false
    });

    $('#ajax-login-form').submit(function(e) {
        e.preventDefault();

        $.ajax({
            type: 'POST',
            url: '/login',
            data: $('#ajax-login-form').serialize(),
            success: function (data) {
                if (data.access_token == undefined) $('#error_text').text('Wrong credentials.');
                else location.reload();
            },
            error: function(data) {
                $('#error_text').text('Wrong credentials.');
            }
        });
    });

    $('#logout-menu-item').click(function() {
        $.ajax({
            type: 'POST',
            url: '/logout',
            success: function () {
                location.reload();
            }
        });
    });
});