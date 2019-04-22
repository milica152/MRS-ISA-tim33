$(document).ready(function(){

    $(document).on('submit', "#regForm", function(e){
        var $firstName = $('#f_name');
        var $lastName = $('#l_name');
        var $email = $('#email_address');
        var $username = $('#user_name');
        var $password = $('#password');
        var $password2 = $('#password2');

        if($password.val() !== $password2.val()){
            alert("Different passwords!");
            return false;
        }
        e.preventDefault();
        var user = JSON.stringify({
            "ime": $firstName.val(),
            "prezime": $lastName.val(),
            "email": $email.val(),
            "username": $username.val(),
            "password": $password.val(),
            "tip_korisnika": 4
        });

        $.ajax({
            type: 'POST',
            url: 'registerUser',
            contentType : 'application/json',
            dataType : "text",
            data : user,
            success: [function(newUser){
                document.location.href = "logged.html"
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })


    })


});