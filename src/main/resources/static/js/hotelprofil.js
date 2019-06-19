$profileID = document.location.href.substring(document.location.href.lastIndexOf('/') + 1);
var isGroupSel = false;
var roomTypesNeeded=[];
var noOfReser=0;
var clicks=0;
var roomsTable;

$(document).ready(function() {

    if(sessionStorage.getItem("HotelReservation")==null){
        createInitialReservation();
    }
    //updateCart();
    /*if(sessionStorage.getItem("DateFromHotelRes")==null || sessionStorage.getItem("DateToHotelRes")==null){
        //disejbluj rezervacije
    }
    if(($.parseJSON(sessionStorage.getItem("HotelReservation"))).dateFrom!==sessionStorage.getItem("DateFromHotelRes")){
        //updateReservationDate();
    }else if(($.parseJSON(sessionStorage.getItem("HotelReservation"))).dateTo!==sessionStorage.getItem("DateToHotelRes")){
        //updateReservationDate();
    }*/


    $(".controls").hide();

    var panels = $('.service-infos');
    var panelsButton = $('.dropdown-service');
    panels.hide();

    //#region Click dropdown
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
    //#endregion

    //#region Inicijalizacija tabele
    $('#alert').hide();
    roomsTable = $('#table-rooms').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: true,
        compact: true,
        columns: [
            { data: 'roomNo', title: 'Number' },
            { data: 'roomFloor', title: 'Floor' },
            { data: 'tip_sobe', title: 'Room type' },
            { data: 'cena_nocenja', title: 'Price per night' },
            { data: 'ocena', title: 'Rating'},
            { data: null, defaultContent:'<button class="btn btn-success reserveBtn" onClick = "Reserve(this)" type="button"><i class="fa fa-cart-plus"></i></button>', title:'Select'}
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    setTable();
    setPage();
    setModal();
    if($.parseJSON(sessionStorage.getItem("HotelReservation"))!=null){
        updateCart($.parseJSON(sessionStorage.getItem("HotelReservation")));
    }
    setCart();


    //#region Checkbox-ovi za dodavanje usluga
    $('#isAirportTransfer').change(function () {
        $("#AirportTransfer").prop("disabled", !this.checked);
    });
    $('#isParkingLot').change(function () {
        $("#parkingLot").prop("disabled", !this.checked);
    });
    $('#isPool').change(function () {
        $("#pool").prop("disabled", !this.checked);
    });
    $('#isRestaurant').change(function () {
        $("#restaurant").prop("disabled", !this.checked);
    });
    $('#isRoomService').change(function () {
        $("#roomService").prop("disabled", !this.checked);
    });
    $('#isWellness').change(function () {
        $("#wellness").prop("disabled", !this.checked);
    });
    $('#isSpa').change(function () {
        $("#spa").prop("disabled", !this.checked);
    });
    $('#isWiFi').change(function () {
        $("#wiFi").prop("disabled", !this.checked);
    });
    //#endregion

    //#region Dodavanje grupa soba
    $(document).on('click', '.btn-add', function(e)
    {
        e.preventDefault();

        var controlForm = $('.controls form:first'),
            currentEntry = $(this).parents('.entry:first');
        var newEntry = $(currentEntry.clone());
        newEntry.find('input').val('');
        currentEntry.before(newEntry);
        controlForm.find('.entry:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<i class="fa fa-minus-circle" id="btnMinus"></i>');
    }).on('click', '.btn-remove', function(e)
    {
        $(this).parents('.entry:first').remove();

        e.preventDefault();
        return false;
    });
    //#endregion

    //#region Dodavanje usluga Hotelu
    $(document).on('click', '#AddServiceBtn', function(e){
        e.preventDefault();
        var isValid = testCbx();
        if(isValid){
            var $AirportTransferPrice = $('#AirportTransfer').val();
            var $ParkingLotPrice = $('#parkingLot').val();
            var $PoolPrice = $('#pool').val();
            var $RestaurantPrice = $('#restaurant').val();
            var $RoomServicePrice = $('#roomService').val();
            var $WellnessPrice = $('#wellness').val();
            var $SpaPrice = $('#spa').val();
            var $WiFiPrice = $('#wiFi').val();
            if(!$('#isAirportTransfer').prop('checked')){
                if($AirportTransferPrice!==''){
                    $AirportTransferPrice=-1;
                }else{
                    $AirportTransferPrice = null;
                }
            }
            if(!$('#isParkingLot').prop('checked')){
                if($ParkingLotPrice!==''){
                    $ParkingLotPrice=-1;
                }else{
                    $ParkingLotPrice = null;
                }
            }
            if(!$('#isPool').prop('checked')){
                if($PoolPrice!==''){
                    $PoolPrice=-1;
                }else{
                    $PoolPrice = null;
                }
            }
            if(!$('#isRestaurant').prop('checked')){
                if($RestaurantPrice!==''){
                    $RestaurantPrice=-1;
                }else{
                    $RestaurantPrice = null;
                }
            }
            if(!$('#isRoomService').prop('checked')){
                if($RoomServicePrice!==''){
                    $RoomServicePrice=-1;
                }else{
                    $RoomServicePrice = null;
                }
            }
            if(!$('#isWellness').prop('checked')){
                if($WellnessPrice!==''){
                    $WellnessPrice=-1;
                }else{
                    $WellnessPrice = null;
                }
            }
            if(!$('#isSpa').prop('checked')){
                if($SpaPrice!==''){
                    $SpaPrice=-1;
                }else{
                    $SpaPrice = null;
                }
            }
            if(!$('#isWiFi').prop('checked')){
                if($WiFiPrice!==''){
                    $WiFiPrice = -1;
                }else{
                    $WiFiPrice = null;
                }

            }

            var Services = {
                airportTransferPrice: $AirportTransferPrice,
                parkingLotPrice: $ParkingLotPrice,
                poolPrice: $PoolPrice,
                restaurantPrice: $RestaurantPrice,
                roomServicePrice: $RoomServicePrice,
                wellnessPrice: $WellnessPrice,
                spaPrice: $SpaPrice,
                wiFiPrice: $WiFiPrice
            };
            $.ajax({
                type: 'POST',
                url: '/HotelServices/add/'+$profileID,
                contentType : 'application/json',
                dataType : "json",
                data : JSON.stringify(Services),
                success: function(data){
                    $('#addServiceModal').modal('hide');
                    //TODO alert
                },
                error: function(xhr, status, error) {
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            })
        }

    });
    //#endregion

    //#region Dodavanje nove sobe
    $('#AddRoomBtn').on("click",function(e) {
        e.preventDefault();
        var isValid = test();
        if(isValid){
            var $RoomNo = $('#RoomNo');
            var $RoomFloor = $('#roomFloor');
            var $RoomType = $('#RoomType');
            var $PricePerNight = $('#pricePerNight');

            var Room = {
                roomNo: $RoomNo.val(),
                roomFloor: $RoomFloor.val(),
                cena_nocenja: $PricePerNight.val(),
                ocena: 0,
                tip_sobe: $RoomType.val()
            };
            $.ajax({
                type: 'POST',
                url: '/Rooms/addRoom/'+$profileID,
                contentType : 'application/json',
                dataType : "json",
                data : JSON.stringify(Room),
                success: function(data){
                    $('#addRoomModal').modal('hide');
                    setPage();
                    setTable();
                },
                error: function(xhr, status, error) {
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            })
        }

    });
    //#endregion

    //#region Ponistavanje filtera
    $(document).on('click', '#cancelFilter', function(e){

        e.preventDefault();
        setTable();

    });
    //#endregion

    //#region Praznjenje inputa za dodavanje sobe
    $(".modal").on("hidden.bs.modal", function(){
        setModal();
        $('.mod').val('');
        $('#RoomType').val('JEDNOKREVETNA');
    });
    //#endregion

    //#region Filtriranje soba
    $(document).on('click', '#filterRooms', function(e){

        e.preventDefault();
        roomTypesNeeded=[];

        if(isGroupSel){
            var controlForm = $('.controls');
            var list=controlForm.find('.selected_room');
            list.each(function () {
                var that = $(this);
                roomType = that.find('.room_type_sel').val();
                roomTypesNeeded.push(roomType);

            });
        }else{
            if(document.getElementById("selRoomBtn").value==="1-1"){
                roomTypesNeeded.push('JEDNOKREVETNA');
            }else{
                roomTypesNeeded.push('DVOKREVETNA');
            }
        }
        doFilter();

    });
    //#endregion


});

function setModal(){
    $('#isAirportTransfer').prop("checked", false);
    $('#isParkingLot').prop("checked", false);
    $('#isPool').prop("checked", false);
    $('#isRestaurant').prop("checked", false);
    $('#isRoomService').prop("checked", false);
    $('#isWellness').prop("checked", false);
    $('#isSpa').prop("checked", false);
    $('#isWiFi').prop("checked", false);
    $('#AirportTransfer').prop("disabled", true);
    $('#parkingLot').prop("disabled", true);
    $('#pool').prop("disabled", true);
    $('#restaurant').prop("disabled", true);
    $('#roomService').prop("disabled", true);
    $('#wellness').prop("disabled", true);
    $('#spa').prop("disabled", true);
    $('#wiFi').prop("disabled", true);


    $.ajax({
        type: 'GET',
        url: '/HotelServices/all/'+$profileID,
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(data){
            data.forEach(function(d) {
                if(d.naziv==="Airport Transfer"){
                    $('#isAirportTransfer').prop("checked", true);
                    $('#AirportTransfer').val(d.cena);
                    $('#AirportTransfer').prop("disabled", false);
                }
                if(d.naziv==="Parking Lot"){
                    $('#isParkingLot').prop("checked", true);
                    $('#parkingLot').val(d.cena);
                    $('#parkingLot').prop("disabled", false);
                }
                if(d.naziv==="Pool Access"){
                    $('#isPool').prop("checked", true);
                    $('#pool').val(d.cena);
                    $('#pool').prop("disabled", false);
                }
                if(d.naziv==="Restaurant"){
                    $('#isRestaurant').prop("checked", true);
                    $('#restaurant').val(d.cena);
                    $('#restaurant').prop("disabled", false);
                }
                if(d.naziv==="Room Service"){
                    $('#isRoomService').prop("checked", true);
                    $('#roomService').val(d.cena);
                    $('#roomService').prop("disabled", false);
                }
                if(d.naziv==="Wellness"){
                    $('#isWellness').prop("checked", true);
                    $('#wellness').val(d.cena);
                    $('#wellness').prop("disabled", false);
                }
                if(d.naziv==="Spa"){
                    $('#isSpa').prop("checked", true);
                    $('#spa').val(d.cena);
                    $('#spa').prop("disabled", false);
                }
                if(d.naziv==="WiFi"){
                    $('#isWiFi').prop("checked", true);
                    $('#wiFi').val(d.cena);
                    $('#wiFi').prop("disabled", false);
                }

            });
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })
}

function doFilter(){

    var filterParams = {
        roomTypeList: roomTypesNeeded,
        priceFrom: $('#priceFrom').val(),
        priceTo: $('#priceTo').val()
    };
    $.ajax({
        type:'POST',
        url:'/Rooms/'+$profileID+'/filterRooms',
        contentType : 'application/json',
        dataType : "json",
        data: JSON.stringify(filterParams),
        success: function(data){
            if(data.length>0){
                $('#table-rooms').dataTable().fnClearTable();
                $('#table-rooms').dataTable().fnAddData(data);
            }
            else{
                //TODO alert lepo
                alert("Not enough rooms!");
            }
        },
        error: function(xhr, status, error){
            alert(xhr.responseText);

        }


    })
}

function test() {
    var isFormValid = true;
    var forma = $('#AddRoomForm');
    var list=forma.find('.input-group');
    list.each(function () {
        var that = $(this);
        if (that.find('.mod').val()==='') {
            isFormValid=false;
            that.find('.mod').focus();
        } else {
            //that.find('.mod').removeClass("highlight");
        }
    });

    if (!isFormValid) {
        //alert("Please fill all fields");
    }
    return isFormValid;

}

function testCbx() {
    var isFormValid = true;
    var forma = $('#AddServiceForm');
    var list=forma.find('.input-group');
    list.each(function () {
        var that = $(this);
        var cbx = that.find('.igp').find('.igt').find('.cbx');
        if (that.find('.mod').val()==='' && cbx.prop('checked')) {
            isFormValid=false;
            that.find('.mod').focus();
        } else {
            //that.find('.mod').removeClass("highlight");
        }
    });

    if (!isFormValid) {
        //alert("Please fill all fields");
    }
    return isFormValid;

}

function setPage(){
    $.ajax({
        type:'GET',
        url: '/Hotels/specific/'+$profileID,
        data: {},
        success: function(data) {
            document.getElementById("HotelName").innerHTML = data.naziv;
            document.getElementById("descTextArea").value = data.opis;
            document.getElementById("hotelAddress").value = data.adresa;
            document.getElementById("HotelPrice").value = data.cenaOd;
            document.getElementById("Rating").value = data.ocena;

        },
        error: function(xhr, status, error) {
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });


}

function selChange(){
    var x = document.getElementById("selRoomBtn").value;
    if(x=="1"){
        $(".controls").show();
        isGroupSel=true;
    }else{
        $(".controls").hide();
        isGroupSel=false;
    }
}

function setTable(){

    $.ajax({
        url: '/Rooms/all/'+$profileID,
        data: {},
        success: function(data) {
            if (data !== undefined && data.length > 0) {
                $('#table-rooms').dataTable().fnClearTable();
                $('#table-rooms').dataTable().fnAddData(data);
                setButtons();
            }
        },
        error: function(xhr, status, error) {
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });

}

function Reserve(btn){
    var data = roomsTable.row($(btn).parents('tr')).data();
    if(btn.innerHTML == '<i class="fa fa-cart-plus" aria-hidden="true"></i>'){
        btn.innerHTML = '<i class="fa fa-trash-alt" aria-hidden="true"></i>';
        btn.className = "btn btn-danger reservebtn";
        addRoomToReservation(data);
    }else{
        btn.innerHTML = '<i class="fa fa-cart-plus" aria-hidden="true"></i>';
        btn.className = "btn btn-success reservebtn";
        removeRoomFromReservation(data);
    }

}

function setButtons(){
    var listOfResRooms = $.parseJSON(sessionStorage.getItem("HotelReservation")).room;
    if(listOfResRooms!=null){
        var listOfIds=[];
        listOfResRooms.forEach(function(room){
            listOfIds.push(room.id);
        });
        roomsTable.rows().every(function() {
            var rowNode = this.node();
            var cells = $(rowNode).find("td:visible");
            if(listOfIds.includes(this.data().id)){
                cells[5].innerHTML = '<button class="btn btn-danger reservebtn" onClick = "Reserve(this)" type="button"><i class="fa fa-trash-alt" aria-hidden="true"></i></button>';
            }else{
                cells[5].innerHTML = '<button class="btn btn-success reservebtn" onClick = "Reserve(this)" type="button"><i class="fa fa-cart-plus" aria-hidden="true"></i></button>';
            }
        });
    }


}

function addRoomToReservation(data){
    var $Room= data;
    var $reservationId = ($.parseJSON(sessionStorage.getItem("HotelReservation"))).id;
    $.ajax({
        type: 'POST',
        url: '/HotelReservation/addRoom/'+$reservationId,
        contentType : 'application/json',
        dataType : "json",
        data : JSON.stringify($Room),
        success: function(data){
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
            updateCart(data);
            setCart();
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })
}

function removeRoomFromReservation(data){
    var $Room= data;
    var $reservationId = ($.parseJSON(sessionStorage.getItem("HotelReservation"))).id;
    $.ajax({
        type: 'POST',
        url: '/HotelReservation/deleteRoom/'+$reservationId,
        contentType : 'application/json',
        dataType : "json",
        data : JSON.stringify($Room),
        success: function(data){
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
            updateCart(data);
            setCart();
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })
}

function removeRoomFromCart(data){
    var room = data.cells[0].children[0].children[1].children[0].innerHTML;
    var roomSplit = room.split("Room");
    var roomId = roomSplit[1];
    var $reservationId = ($.parseJSON(sessionStorage.getItem("HotelReservation"))).id;
    $.ajax({
        type: 'POST',
        url: '/HotelReservation/deleteRoomCart/'+roomId+'/'+$reservationId,
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(data){
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
            updateCart(data);
            setCart();
            setButtons();
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })
}

function createInitialReservation(){
    $.ajax({
        type: 'POST',
        url: '/HotelReservation/add',
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(data){
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
            setCart();
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })

}

function setCart(){
    var $airlineReservation = $.parseJSON(sessionStorage.getItem("AirlineReservation"));
    var $hotelReservation = $.parseJSON(sessionStorage.getItem("HotelReservation"));
    var $rcsReservation = $.parseJSON(sessionStorage.getItem("RCSReservation"));
    var sumTotal=0.00;
    var cartEl = 0;

    if($airlineReservation!=null){
        document.getElementById('flightResNo').innerHTML = $airlineReservation.id;
        document.getElementById('FlightsTotal').innerHTML = $airlineReservation.price;
        sumTotal = sumTotal + parseFloat($airlineReservation.price);
        //TODO dodajte ovde za vas deo
        //cartEl = cartEl + $airlineReservation.flights.length;
    }
    if($hotelReservation!=null){
        document.getElementById('hotelResNo').innerHTML = $hotelReservation.id;
        document.getElementById('RoomsTotal').innerHTML = $hotelReservation.price;
        sumTotal = sumTotal + parseFloat($hotelReservation.price);
        if($hotelReservation.room!=null){
            cartEl = cartEl + $hotelReservation.room.length;
        }

    }
    if($rcsReservation!=null){
        document.getElementById('rcsResNo').innerHTML = $rcsReservation.id;
        document.getElementById('VehiclesTotal').innerHTML = $rcsReservation.price;
        sumTotal = sumTotal + parseFloat($rcsReservation.price);
        //TODO dodajte ovde za vas deo
        //cartEl = cartEl + $rcsReservation.vehicles.length;
    }
    document.getElementById("SumTotal").innerHTML = sumTotal+"€";
    document.getElementById('noOfRes').innerHTML = cartEl;


}

function updateReservationDate(){

    $.ajax({
        type: 'POST',
        url: '/HotelReservation/updateDate',
        contentType : 'application/json',
        dataType : "json",
        data : { dateFrom: sessionStorage.getItem("DateFromHotelRes"), dateTo: sessionStorage.getItem("DateToHotelRes")},
        success: function(data){
            sessionStorage.setItem("HotelReservation", JSON.stringify(data));
        },
        error: function(xhr, status, error) {
            if (xhr.responseText!=='true'){
                alert(xhr.responseText);
            }
        }
    })
}

function updateCart(reservation){
    if(reservation.room!=null){
        var rooms = reservation.room;
        var rowCount = $('#RoomTable tbody tr').length;
        var toDelete = rowCount-3;
        $("#RoomTable").find("tbody tr:lt("+toDelete+")").remove();
        rooms.forEach(function(room) {
            var cartRows = $('#rowSubtotal');
            var newEntry = '<tr>\n' +
                '<td>\n' +
                '<div class="media">\n' +
                '<a class="thumbnail pull-left roomImg" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" > </a>\n' +
                '<div class="media-body">\n' +
                '<h6 class="media-heading roomTypeTable">Room '+room.id +'</h6>\n' +
                '<p class="roomFloorInfo">Floor: '+room.roomFloor +' </p>\n' +
                '<p class="roomNoInfo">Number: '+room.roomNo +'</p>\n' +
                '</div>\n' +
                '</div>\n' +
                '</td>\n' +
                '<td class="text-center"><strong>'+room.cena_nocenja+'€</strong></td>\n' +
                '<td class="text-center">\n' +
                '<button type="button" onclick="removeRoomFromCart(this.parentNode.parentNode)" class="btn-md btn-danger">\n' +
                '<span class="fas fa-trash"></span>\n' +
                '</button>\n' +
                '</td>\n' +
                '</tr>';
            cartRows.before(newEntry);
        })

        var discount=0.00;
        if(reservation.discount==null){
            discount = 0.00;
        }else{
            discount = reservation.discount;
        }
        var priceNoDisc = reservation.price + discount;
        document.getElementById('subtotal').innerHTML = priceNoDisc+"€";
        document.getElementById('discount').innerHTML = discount+"€";
        document.getElementById('totalAll').innerHTML = reservation.price + "€";
        document.getElementById('RoomsTotal').innerHTML = reservation.price;
    }

}