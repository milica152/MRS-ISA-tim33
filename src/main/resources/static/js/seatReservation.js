$flightID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
//$airlineID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
//$airlineID0 = window.location.href.slice(0,window.location.href.lastIndexOf('/') + 1);
//$airlineID = $airlineID0.slice(0,window.location.href.lastIndexOf('/') + 1);

//alert($airlineID);
var reservationsMade = [];
var fastResForFlight = [];

$(document).ready(function(){


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
                                    btn.onclick= function() {seatFunction(this);};
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
function seatFunction(button){
    clicked_button_id = button.id;
    document.getElementById('nameInput').value = "";
    document.getElementById('surnameInput').value = "";
    document.getElementById('passport').value = "";
    document.getElementById('reservationInfo').style.display = 'contents';

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