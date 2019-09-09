$flightID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
//$airlineID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);

$airlineID0 = window.location.href.slice(0,window.location.href.lastIndexOf('/') + 1);
$airlineID = $airlineID0.slice(0,$airlineID0.lastIndexOf('/'));
$airlineID1 = $airlineID.slice(0,$airlineID.lastIndexOf('/'));
$airlineID2 = $airlineID1.substring($airlineID1.lastIndexOf('/') + 1);

var reservationsMade = [];
var fastResForFlight = [];

$(document).ready(function(){



    var isLogged = false;
    var isCorrectAdmin = false;


    $.ajax({
        type: 'GET',
        url: '/whoami',
        success: function (data) {
            if (data == undefined || data == "") {
                isLogged = false;
                hideAll(isCorrectAdmin, isLogged);

            } else {
                isLogged = true;
                for (var i = 0; i < data.authorities.length; i++) {
                    if (data.authorities[i].authority == 'SISTEM_ADMIN' || data.authorities[i].authority == 'ADMIN_AK') {
                        isCorrectAdmin = true;
                        break;
                    }
                }
                if (!isCorrectAdmin) {
                    hideAll( isCorrectAdmin, isLogged);
                }
            }

        },
        async: false
    });





    function hideAll(isCorrectAdmin, isLogged){
        if(!isCorrectAdmin){
            document.getElementById('addRowBtn').style.visibility = 'hidden';
        }
        if(!isLogged){
            document.getElementById('doneBtn').style.display = 'none';
        }

    }





    function includes(elem) {
        for(var e in fastResForFlight){
            if(fastResForFlight[e] === elem){
                alert(elem);

                return true;
            }
        }
        return false;

    }

    showSeats();

    function showSeats() {
        $.ajax({      //dobih sedista iz brzih rez
            type:'GET',
            url:'/FastFlightReservation/seatsFromFlight/' + $flightID ,
            dataType:"json",
            success:[function(data){
                for(var d in data){

                    fastResForFlight.push(data[d].id.toString());
                }


            }]
        });

        $(document).on('click', '#backBtn', function(e){
            e.preventDefault();
            window.location = "/Aviocompany/" + $airlineID2;
        });



        $.ajax({
            type:'GET',
            url:'/Seats/fromPlane/' + $flightID ,
            data:{},
            success:[function(data){
                var row;
                var column;
                var hidden_counter = 0;
                var rows = data.length;
                var columns = data[0].length;

                if(!(document.getElementById("seats").childNodes.length === rows+1)){

                    for(row = 1; row<=rows;row++){

                        var column_counter= 0;
                        var row_elem = document.createElement("div");
                        row_elem.setAttribute("id", row.toString());
                        row_elem.setAttribute("class", "row row1");

                        for(column = 1; column <= columns; column ++){


                            if(data[row-1][column-1]===null){
                                hidden_counter++;
                                var hidden_btn = document.createElement("button");
                                hidden_btn.setAttribute("id", hidden_counter.toString());
                                hidden_btn.style.visibility = 'hidden';
                                row_elem.appendChild(hidden_btn);

                            }else{
                                column_counter++;

                                if(data[row-1][column-1].reserved || includes(data[row-1][column-1].id.toString())){                       // POGLEDAJ JE L SA OVOM CRTICOM ILI BEZ
                                    var btn_r = document.createElement("button");
                                    var id_r = row.toString() + "-" + column_counter.toString();
                                    btn_r.setAttribute("id", id_r);
                                    btn_r.setAttribute("class", "reservedBtn");
                                    btn_r.disabled = true;
                                    row_elem.appendChild(btn_r);

                                }else{
                                    var btn = document.createElement("button");
                                    var id = row.toString() + "-" + column_counter.toString();
                                    btn.setAttribute("id", id);
                                    btn.setAttribute("class", "button5");
                                    btn.onclick= function() {seatFunction(this, isCorrectAdmin, isLogged);};
                                    row_elem.appendChild(btn);

                                }
                            }
                        }
                        document.getElementById("seats").appendChild(row_elem);

                    }
                }

            }],
            error:function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        });




    }


    $(document).on("click" , "#addRowBtn", function(e) {
        e.preventDefault();
        var conBox = confirm("Are you sure?");


        if(conBox) {
            $.ajax({
                type : 'POST',
                url : '/Seats/addRow/' + $flightID,
                dataType : "json",
                success : [function (data) {

                    if(data.length !== 0){
                        var row = data[0].numberOfRow;
                        var column;
                        var hidden_counter = 0;
                        var columns = data.length;
                        var column_counter= 0;
                        var row_elem = document.createElement("div");

                        row_elem.setAttribute("id", row.toString());
                        row_elem.setAttribute("class", "row row1");

                        for(column = 1; column <= columns; column ++) {


                            if (data[column - 1] === null) {
                                hidden_counter++;
                                var hidden_btn = document.createElement("button");
                                hidden_btn.setAttribute("id", hidden_counter.toString());
                                hidden_btn.style.visibility = 'hidden';
                                row_elem.appendChild(hidden_btn);

                            } else {
                                column_counter++;

                                    var btn = document.createElement("button");
                                    var id = row.toString() + "-" + column_counter.toString();
                                    btn.setAttribute("id", id);
                                    btn.setAttribute("class", "button5");
                                    btn.onclick = function () {
                                        seatFunction(this, isCorrectAdmin, isLogged);
                                    };
                                    row_elem.appendChild(btn);

                            }


                        }
                        document.getElementById("seats").appendChild(row_elem);

                        alert("Row added successfully");
                    }else {
                        alert(data);
                    }
                }],
                error : function (xhr, status, error) {
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            });


        }
    });


    $(document).on("click" , "#enterBtn", function(e){
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

        var writing = "Name: " + $name + "\nSurname: " + $surname + "\nPassport: " + $passport;
        document.getElementById(clicked_button_id).title = writing;
        document.getElementById(clicked_button_id).style.backgroundColor = "yellow";
        document.getElementById('reservationInfo').style.display = 'none';

        var date = new Date().getTime();
        var reservation = JSON.stringify({
            "name" : $name,
            "surname" : $surname,
            "passport" : $passport,
            "flightId" : $flightID,
            "seatId" : clicked_button_id,
            "date" : date
          });
        reservationsMade.push(reservation);
    });


    $(document).on("click", "#doneBtn", function(e){
        e.preventDefault();

        var len = reservationsMade.length;
        if(len === 0){
            return;
        }
        for (var i = 0; i < len; i++) {
            addReservation(reservationsMade[i]);
        }




    });
});
function seatFunction(button, isCorrectAdmin, isLogged){
    if(isCorrectAdmin){
        var buttonToDelete;
        var conBox = confirm("Are you sure you want to delete this seat?");



        if(conBox) {

            $.ajax({
                method : 'POST',
                url : "/Seats/rowAndColumn/" + button.id + "/" + $flightID,
                dataType : "json",
                success : [function(data){
                    data.reserved = true;
                    buttonToDelete =JSON.stringify(data);

                    changeSeatToInactive(buttonToDelete);
                }],
                error : function(xhr, status, code){
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            });




        }
        return;
    }
    if(!isLogged){
        return;
    }


    clicked_button_id = button.id;
    document.getElementById('nameInput').value = "";
    document.getElementById('surnameInput').value = "";
    document.getElementById('passport').value = "";
    document.getElementById('reservationInfo').style.display = 'contents';

}

function changeSeatToInactive(buttonToDelete) {
    $.ajax({
        method : 'PUT',
        url : "/Seats/update",
        contentType : 'application/json',
        dataType : "json",
        data : buttonToDelete,
        success : [function(data){
            alert('successfully change seat to inactive!');
            window.location.reload();
        }],
        error : function(xhr, status, code){
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    });

}


function addReservation(reservation){
    $.ajax({
        method : 'POST',
        url : "/FlightReservationController",
        contentType : 'application/json',
        dataType : "json",
        data : reservation,
        success : [function(data){
            alert('You successfully reserved this flight!');
            window.location = "/Aviocompany";
        }],
        error : function(xhr, status, code){
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    });
}