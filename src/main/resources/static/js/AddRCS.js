$(document).ready(function() {

    $("#AddServiceForm").submit(function(e){
        e.preventDefault();
        var $ServiceName = $('#serviceName');
        var $ServiceAddress = $('#serviceAddress');
        var $ServiceDesc = $('#serviceDesc');

        var service = {
            adresa: $ServiceAddress.val(),
            naziv: $ServiceName.val(),
            opis: $ServiceDesc.val()
        };


        $.ajax({
            type: 'POST',
            url: '/RentACar/addRCS',
            contentType : 'application/json',
            dataType : "json",
            data : JSON.stringify(service),
            success: function(data){
                sessionStorage.setItem("user", "3");
                sessionStorage.setItem("service", data.naziv);
                document.location.href = "register.html";
            },
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })

    });


});