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

        if(sessionStorage.getItem("user") ==="1"){
            var $tipKorisnika = 1;
            var urlReg = "registerAdmin/"+sessionStorage.getItem("service");
        }
        else if(sessionStorage.getItem("user") ==="2"){
            $tipKorisnika = 2;
            urlReg = "registerAdmin/"+sessionStorage.getItem("service");
        }
        else if(sessionStorage.getItem("user") ==="3"){
            $tipKorisnika = 3;
            urlReg = "registerAdmin/"+sessionStorage.getItem("service");
        }
        else{
            $tipKorisnika = 4;
            urlReg = "registerUser";
        }
        e.preventDefault();

        var user = JSON.stringify({
            ime: $firstName.val(),
            prezime: $lastName.val(),
            email: $email.val(),
            username: $username.val(),
            password: $password.val(),
            tip_korisnika: $tipKorisnika
        });


        $.ajax({
            type: 'POST',
            url: urlReg,
            contentType : 'application/json',
            dataType : "json",
            data : user,
            success: [function(newUser){
                alery("You have registered successfully.");
                document.location.href = "/";
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })


    })


});