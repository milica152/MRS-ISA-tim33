$airlineId0 = window.location.href.slice(0,window.location.href.lastIndexOf('/'));
$airlineId = $airlineId0.substring(window.location.href.lastIndexOf('/') + -1);
var clicked_button_id = "";


$(document).ready(function(){




    var $table = $('#table');


    //$(function() {
        $table.bootstrapTable();
    ///});


    $(document).on('click', '#backBtn', function(e){
        e.preventDefault();
        window.location = "/Aviocompany/" + $airlineId;
    });

    getRes();

    $(document).on('click', '.btn-primary', function (e) {
        reserve(this);
    });

    $(document).on("click" , "#enterBtn", function(e){   //kad popuni podatke:
        e.preventDefault();


        var $name = $('#nameInput').val();
        var $surname = $('#surnameInput').val();
        var $passport = $('#passport').val();

        if($name === null || $name===""){
            alert('Enter name!');
            return;
        }
        else if($surname === null || $surname===""){
            alert('Enter surname!');
            return;
        }
        else if($passport === null || $passport===""){
            alert('Enter passport number!');
            return;
        }

        document.getElementById('reservationInfo').style.display = 'none';

        var info = JSON.stringify({
            "name" : $name,
            "surname" : $surname,
            "passNum" : $passport
        });
        alert(info);

        //posalji ajaxom ovde podatke na url
        //ajax za rezervaciju
        $.ajax({
            type : 'POST',
            url: "/FastFlightReservation/reserve/" + clicked_button_id,
            data : info,
            dataType : 'text',
            contentType : 'application/json',
            success: function(data) {

                if(data === "true"){
                    alert('You successfully reserved this flight!');
                    getRes();
                }else{
                    alert(data);
                }



            }
        });
        alert("preskocio ajax");

    });



});

function showRes(data){
    $("#table tr").remove();

    if(data.length !== 0){
        for(var d in data){
            var price = data[d].price - data[d].price*data[d].discount;
            var klasa = data[d].klasa;
            var depCity = data[d].depCity;
            var depCountry = data[d].depCountry;
            var arrCity = data[d].arrCity;
            var arrCountry = data[d].arrCountry;
            var depTime = data[d].depTime;
            var arrTime = data[d].arrTime;
            var newRow=document.getElementById('table').insertRow();

            newRow.innerHTML  = "<td> <a class=\"list-group-item list-group-item-action flex-column align-items-start\"> <div class=\"d-flex w-100 justify-content-between\">" +
                "<h5 class=\"mb-1\">" + depTime.toString() + "-" + arrTime.toString() +" </h5> <small><b>" + price.toString() + "$</b></small>" +
                "</div> <p class=\"mb-1\"> From: <b>" + depCity  + "</b>, " + depCountry + "  to: <b>" + arrCity + "</b>, " + arrCountry + "<button id=\" " + data[d].id.toString()  + " \" class='btn btn-primary' style='float: right;'>Reserve</button>  </p><small>" + klasa.toString() + "</small></a> </td>";


        }

    }else {
        alert('No quick reservations left!');
    }

}



function reserve(button){
    clicked_button_id = button.id.toString();
    document.getElementById('nameInput').value = "";
    document.getElementById('surnameInput').value = "";
    document.getElementById('passport').value = "";
    document.getElementById('reservationInfo').style.display = 'contents';



}

function getRes(){

    $.ajax({
        type : 'GET',
        url: "/FastFlightReservation/fromAirline/" + $airlineId,
        dataType : "json",
        success: function(data) {
            showRes(data);
        }
    });

}

/*function btn_display(a) {
    var aId = a.id;
    alert(aId);
    var divId = aId.replace('a', 'div');
    alert(divId);
    document.getElementById('' + divId).style.display = 'contents';
}
function formatDate(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    var month = date.getMonth() + 1;
    return date.getDate() + "." + month + "." + date.getFullYear() + ".  " + strTime;
}


*/