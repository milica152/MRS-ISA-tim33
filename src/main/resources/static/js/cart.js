function removeRoomFromCart(data) {
    var room = data.cells[0].children[0].children[1].children[0].innerHTML;
    var roomSplit = room.split("Room");
    var roomId = roomSplit[1];
    var $reservationId = ($.parseJSON(sessionStorage.getItem("HotelReservation"))).id;
    $.ajax({
        type: 'POST',
        url: '/HotelReservation/deleteRoomCart/' + roomId + '/' + $reservationId,
        contentType: 'application/json',
        dataType: "json",
        data: {},
        success: function (data) {
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
            updateCart(data);
            setCart();
        },
        error: function (xhr, status, error) {
            if (xhr.responseText !== 'true') {
                alert(xhr.responseText);
            }
        }
    })
}

function removeVehicleFromCart(data) {
    var vehicle = data.cells[0].children[0].children[1].children[0].innerHTML;
    var vSplit = vehicle.split("Vehicle");
    var vId = vSplit[1];
    var $reservationId = ($.parseJSON(sessionStorage.getItem("RCSReservation"))).id;
    $.ajax({
        type: 'POST',
        url: '/VehicleReservation/deleteVehicleCart/' + vId + '/' + $reservationId,
        contentType: 'application/json',
        dataType: "json",
        data: {},
        success: function (data) {
            sessionStorage.setItem("RCSReservation", JSON.stringify(data));
            updateCart(data);
            setCart();
        },
        error: function (xhr, status, error) {
            if (xhr.responseText !== 'true') {
                alert(xhr.responseText);
            }
        }
    })
}

function setCart() {
    var $airlineReservation = $.parseJSON(sessionStorage.getItem("AirlineReservation"));
    var $hotelReservation = $.parseJSON(sessionStorage.getItem("HotelReservation"));
    var $rcsReservation = $.parseJSON(sessionStorage.getItem("RCSReservation"));
    var sumTotal = 0.00;
    var cartEl = 0;

    if ($airlineReservation != null) {
        document.getElementById('flightResNo').innerHTML = $airlineReservation.id;
        document.getElementById('FlightsTotal').innerHTML = $airlineReservation.price;
        sumTotal = sumTotal + parseFloat($airlineReservation.price);
        //TODO dodajte ovde za vas deo
        //cartEl = cartEl + $airlineReservation.flights.length;
    }
    if ($hotelReservation != null) {
        document.getElementById('hotelResNo').innerHTML = $hotelReservation.id;
        document.getElementById('RoomsTotal').innerHTML = $hotelReservation.price;
        if ($hotelReservation.room != null) {
            cartEl = cartEl + $hotelReservation.room.length;
        }
        sumTotal = sumTotal + parseFloat($hotelReservation.price);

    }
    if ($rcsReservation != null) {
        document.getElementById('rcsResNo').innerHTML = $rcsReservation.id;
        document.getElementById('VehiclesTotal').innerHTML = $rcsReservation.price;
        if ($rcsReservation.vozila != null) {
            cartEl = cartEl + $rcsReservation.vozila.length;
        }
        sumTotal = sumTotal + parseFloat($rcsReservation.price);
    }
    document.getElementById("SumTotal").innerHTML = sumTotal + "€";
    document.getElementById('noOfRes').innerHTML = cartEl;
}

function updateCart(reservation) {
    if (reservation.room != null) {
        var rooms = reservation.room;
        var rowCount = $('#RoomTable tbody tr').length;
        var toDelete = rowCount - 3;
        $("#RoomTable").find("tbody tr:lt(" + toDelete + ")").remove();
        rooms.forEach(function (room) {
            var cartRows = $('#rowSubtotal');
            var newEntry = '<tr>\n' +
                '<td>\n' +
                '<div class="media">\n' +
                '<a class="thumbnail pull-left roomImg" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" > </a>\n' +
                '<div class="media-body">\n' +
                '<h6 class="media-heading roomTypeTable">Room ' + room.id + '</h6>\n' +
                '<p class="roomFloorInfo">Floor: ' + room.roomFloor + ' </p>\n' +
                '<p class="roomNoInfo">Number: ' + room.roomNo + '</p>\n' +
                '</div>\n' +
                '</div>\n' +
                '</td>\n' +
                '<td class="text-center"><strong>' + room.cena_nocenja + '€</strong></td>\n' +
                '<td class="text-center">\n' +
                '<button type="button" onclick="removeRoomFromCart(this.parentNode.parentNode)" class="btn-md btn-danger">\n' +
                '<span class="fas fa-trash"></span>\n' +
                '</button>\n' +
                '</td>\n' +
                '</tr>';
            cartRows.before(newEntry);
        });

        var discount = 0.00;
        if (reservation.discount == null) {
            discount = 0.00;
        } else {
            discount = reservation.discount;
        }
        var priceNoDisc = reservation.price + discount;
        document.getElementById('subtotal').innerHTML = priceNoDisc + "€";
        document.getElementById('discount').innerHTML = discount + "€";
        document.getElementById('totalAll').innerHTML = reservation.price + "€";
        document.getElementById('RoomsTotal').innerHTML = reservation.price;
    }

    if (reservation.vozila != null) {
        var vozila = reservation.vozila;
        var rowCount2 = $('#VehicleTable tbody tr').length;
        var toDelete2 = rowCount2 - 3;
        $("#VehicleTable").find("tbody tr:lt(" + toDelete2 + ")").remove();
        vozila.forEach(function (vehicle) {
            var cartRows = $('#rc-rowSubtotal');
            var newEntry = '<tr>\n' +
                '<td>\n' +
                '<div class="media">\n' +
                '<a class="thumbnail pull-left roomImg" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" > </a>\n' +
                '<div class="media-body">\n' +
                '<h6 class="media-heading roomTypeTable">Vehicle ' + vehicle.id + '</h6>\n' +
                '<p class="roomFloorInfo">Name: ' + vehicle.naziv + ' </p>\n' +
                '<p class="roomNoInfo">Manufacturer: ' + vehicle.marka + '</p>\n' +
                '</div>\n' +
                '</div>\n' +
                '</td>\n' +
                '<td class="text-center"><strong>' + vehicle.cena + '€</strong></td>\n' +
                '<td class="text-center">\n' +
                '<button type="button" onclick="removeVehicleFromCart(this.parentNode.parentNode)" class="btn-md btn-danger">\n' +
                '<span class="fas fa-trash"></span>\n' +
                '</button>\n' +
                '</td>\n' +
                '</tr>';
            cartRows.before(newEntry);
        });

        document.getElementById('rc-subtotal').innerHTML = reservation.price + "€";
        document.getElementById('rc-totalAll').innerHTML = reservation.price + "€";
        document.getElementById('VehiclesTotal').innerHTML = reservation.price;
    }
}

setCart();
var $hotelReservation = $.parseJSON(sessionStorage.getItem("HotelReservation"));
if($hotelReservation!=null){
    updateCart($hotelReservation);
}

var $rcsReservation = $.parseJSON(sessionStorage.getItem("RCSReservation"));
if($rcsReservation!=null){
    updateCart($rcsReservation);
}

var panels = $('.service-infos');
var panelsButton = $('.dropdown-service');
panels.hide();

//Click dropdown
panelsButton.click(function() {
    //get data-for attribute
    var dataFor = $(this).attr('data-for');
    var idFor = $(dataFor);

    //current button
    var currentButton = $(this);
    idFor.slideToggle(400, function() {
        //Completed slidetoggle
        if(idFor.is(':visible'))
        {
            currentButton.html('<i class="fas fa-chevron-up text-muted float-right"></i>');
        }
        else
        {
            currentButton.html('<i class="fas fa-chevron-down text-muted float-right"></i>');
        }
    })
});
