$profileID = document.location.href.substring(document.location.href.lastIndexOf('/') + 1);
var isGroupSel = false;
var roomTypesNeeded=[];
var noOfReser=0;
var clicks=0;
var roomsTable;

$(document).ready(function() {

    if(sessionStorage.getItem("HotelReservation")==null){
        createInitialReservation();
        setCart();
    }

    //#region Click dropdown
    $(".controls").hide();

    var panels = $('.service-infos');
    var panelsButton = $('.dropdown-service');
    panels.hide();

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

    //#region Region inicijalizovanja tabele Quick Res
    var quickTable = $('#quick-table').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: true,
        compact: true,
        columns: [
            { data: 'roomId', title: 'Room Id' },
            { data: 'price', title: 'Price' },
            { data: 'priceBefore', title: 'Regular price' },
            { data: 'discount', title: 'Discount' },
            { data: 'dateFrom', title: 'From Date'},
            { data: 'dateTo', title: 'To Date'},
            { data: 'services', title: 'Additional Services'},
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
    setAddQuickModal();

    //#region Region za polje sa datumima
    $( "#datepickerStart").datepicker({
        dateFormat:"dd-mm-yy",
        minDate: '0d',
        onSelect: function(e) {
            $('#datepickerEnd').datepicker('option', 'minDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode == 8 || e.keyCode == 46) {
            $('#datepickerStart').datepicker('setDate', null);
            $('#datepickerEnd').datepicker('option', 'minDate', '0d');
        }
    });
    $( "#datepickerEnd" ).datepicker({
        dateFormat:"dd-mm-yy",
        useCurrent: false,
        minDate: '0d',
        onSelect: function(e) {
            $('#datepickerStart').datepicker('option', 'maxDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode == 8 || e.keyCode == 46) {
            $('#datepickerEnd').datepicker('setDate', null);
            $('#datepickerStart').datepicker('option', 'maxDate', null);
        }
    });
    //#endregion

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
                    resetServices();
                    setServices();
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

    //#region Region dodavanja Quick res
    $(document).on('click', '#AddSelServiceBtn', function(e){
        e.preventDefault();
        var $Discount = $('#QuickDisc').val();
        var $RoomQuick = $('#RoomQuick').val();
        var $DateFrom = $('#datepickerStart').val();
        var $DateTo = $('#datepickerEnd').val();
        var selectedServices =  $('.cbx2:checkbox:checked');
        var listOfSelected = new Array();
        if(selectedServices.length!=0){
            selectedServices.each(function(){
                listOfSelected.push($(this).attr("value"));
            });
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

    //#region Dodavanje dodatnih usluga u rezervaciju
    resetServices();
    setServices();
    $(document).on('click', '#AddSelServiceBtn', function(e){
        addServicesToReservation();
    });
    //#endregion

    if(sessionStorage.getItem("HotelReservation")!=null){
        if($.parseJSON(sessionStorage.getItem("HotelReservation")).hotelId != $profileID ){
            createInitialReservation();
            setCart();

        }else if($.parseJSON(sessionStorage.getItem("HotelReservation"))!=null){
            //setCart();
            updateCart($.parseJSON(sessionStorage.getItem("HotelReservation")));
            setButtons();
            setCart();

        }
    }
    //setCart();


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
                //setButtons();
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

    if(($.parseJSON(sessionStorage.getItem("HotelReservation")).room)!==null){
        var listOfResRooms = $.parseJSON(sessionStorage.getItem("HotelReservation")).room;
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
        url: '/HotelReservation/add/'+$profileID,
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(data){
            updateCart(JSON.stringify(data));
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
        if($hotelReservation.price!= null){
            document.getElementById('RoomsTotal').innerHTML = $hotelReservation.price;
            sumTotal = sumTotal + parseFloat($hotelReservation.price);
        }else{
            document.getElementById('RoomsTotal').innerHTML = "0.00";
        }

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

function updateCart(reservation){
    if(reservation.room!=null){
        var rooms = reservation.room;
        var rowCount = $('#RoomTable tbody tr').length;
        var toDelete = rowCount-4
        ;
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
        var priceForRooms = 0.00;
        rooms.forEach(function(room){
            priceForRooms = priceForRooms + room.cena_nocenja;
        });
        var noDiscount = reservation.price/(1-discount);
        var addServ = noDiscount - priceForRooms;
        var disc = noDiscount * discount;
        document.getElementById('roomsTotalPrice1').innerHTML = priceForRooms+"€";
        document.getElementById('additionalServices').innerHTML = addServ+"€";
        document.getElementById('discount').innerHTML = disc+"€";
        document.getElementById('totalAll').innerHTML = reservation.price + "€";
        document.getElementById('RoomsTotal').innerHTML = reservation.price;
    }

}

function setServices(){
    $.ajax({
        url: '/HotelServices/all/'+$profileID,
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(services){
            services.forEach(function(service){
                if(service.naziv==="Airport Transfer"){
                    document.getElementById("airportTransferLabel").classList.remove("disabled");
                    document.getElementById("airportTransferPrice").removeAttribute("hidden");
                    $('#airportTransferPrice span').text(service.cena + "€");
                    document.getElementById("ATcbx").disabled = false;
                }else if(service.naziv==="WiFi"){
                    document.getElementById("wiFiLabel").classList.remove("disabled");
                    document.getElementById("wiFiPrice").removeAttribute("hidden");
                    $('#wiFiPrice span').text(service.cena + "€");
                    document.getElementById("wiFicbx").disabled = false;
                }else if(service.naziv==="Parking Lot"){
                    document.getElementById("parkingLabel").classList.remove("disabled");
                    document.getElementById("parkingPrice").removeAttribute("hidden");
                    $('#parkingPrice span').text(service.cena + "€");
                    document.getElementById("PLcbx").disabled = false;
                }else if(service.naziv==="Pool Access"){
                    document.getElementById("poolLabel").classList.remove("disabled");
                    document.getElementById("poolPrice").removeAttribute("hidden");
                    $('#poolPrice span').text(service.cena + "€");
                    document.getElementById("Poolcbx").disabled = false;
                }else if(service.naziv==="Restaurant"){
                    document.getElementById("restLabel").classList.remove("disabled");
                    document.getElementById("restaurantPrice").removeAttribute("hidden");
                    $('#restaurantPrice span').text(service.cena + "€");
                    document.getElementById("Restcbx").disabled = false;
                }else if(service.naziv==="Room Service"){
                    document.getElementById("roomServiceLabel").classList.remove("disabled");
                    document.getElementById("roomServicePrice").removeAttribute("hidden");
                    $('#roomServicePrice span').text(service.cena + "€");
                    document.getElementById("RScbx").disabled = false;
                }else if(service.naziv==="Wellness"){
                    document.getElementById("wellnessLabel").classList.remove("disabled");
                    document.getElementById("wellnessPrice").removeAttribute("hidden");
                    $('#wellnessPrice span').text(service.cena + "€");
                    document.getElementById("Wellcbx").disabled = false;
                }else if(service.naziv==="Spa"){
                    document.getElementById("spaLabel").classList.remove("disabled");
                    document.getElementById("spaPrice").removeAttribute("hidden");
                    $('#spaPrice span').text(service.cena + "€");
                    document.getElementById("Spacbx").disabled = false;
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

function resetServices(){
    document.getElementById("airportTransferLabel").classList.add("disabled");
    document.getElementById("airportTransferPrice").hidden= true;
    document.getElementById("wiFiLabel").classList.add("disabled");
    document.getElementById("wiFiPrice").hidden= true;
    document.getElementById("parkingLabel").classList.add("disabled");
    document.getElementById("parkingPrice").hidden= true;
    document.getElementById("poolLabel").classList.add("disabled");
    document.getElementById("poolPrice").hidden= true;
    document.getElementById("restLabel").classList.add("disabled");
    document.getElementById("restaurantPrice").hidden= true;
    document.getElementById("roomServiceLabel").classList.add("disabled");
    document.getElementById("roomServicePrice").hidden= true;
    document.getElementById("wellnessLabel").classList.add("disabled");
    document.getElementById("wellnessPrice").hidden= true;
    document.getElementById("spaLabel").classList.add("disabled");
    document.getElementById("spaPrice").hidden= true;
    document.getElementById("ATcbx").disabled = true;
    document.getElementById("PLcbx").disabled = true;
    document.getElementById("Poolcbx").disabled = true;
    document.getElementById("Restcbx").disabled = true;
    document.getElementById("RScbx").disabled = true;
    document.getElementById("Wellcbx").disabled = true;
    document.getElementById("Spacbx").disabled = true;
    document.getElementById("wiFicbx").disabled = true;

}

function addServicesToReservation(){
   var $reservationId = ($.parseJSON(sessionStorage.getItem("HotelReservation"))).id;
   var selectedServices =  $('.cbx1:checkbox:checked');
   var listOfSelected = new Array();
   selectedServices.each(function(){
      listOfSelected.push($(this).attr("value"));
   });

    $.ajax({
        type: 'post',
        url: '/HotelReservation/addServices/'+$reservationId,
        data: {services: listOfSelected},
        dataType: "json",
        traditional: true,
        success: function(data) {
            //alert(data);
            if(data==null){
                alert("You must choose rooms first!");
            }
            else{
                sessionStorage.setItem("HotelReservation", JSON.stringify(data));
                setCart();
                updateCart($.parseJSON(sessionStorage.getItem("HotelReservation")));
                setAddQuickModal();
                //alert("bravo bravo");
            }
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });
}

function setAddQuickModal(){
    $.ajax({
        url: '/Rooms/all/'+$profileID,
        data: {},
        success: function(rooms) {
            if(rooms.length!==0){
                rooms.forEach(function(room){
                    var opcija = new Option(room.id);
                    document.getElementById('RoomQuick').add(opcija);
                });
            }
        }
    });
    document.getElementById("rowAddQuick").innerHTML="";
    var rowAdd = $('#rowAddQuick');

    $.ajax({
        url: '/HotelServices/all/'+$profileID,
        contentType : 'application/json',
        dataType : "json",
        data : {},
        success: function(services){
            services.forEach(function(service){
                if(service.naziv==="Airport Transfer"){
                    var span = "<div class=\"col\">\n" +
                        "                                <label class=\"btn btnsquick btn-success\" id=\"airportTransferLabel1\">\n" +
                        "                                    <div class=\"hvrbox\">\n" +
                        "                                        <img id=\"airportTransferImage1\" src=\"https://cdn4.iconfinder.com/data/icons/hotel-facilities-1/512/airport-transfer3-512.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                        <div class=\"hvrbox-layer_top\">\n" +
                        "                                            <div class=\"hvrbox-text1\">Airport Transfer</div>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                      <label id=\"airportTransferPrice1\" class=\"priceForService\"><input id = \"ATcbx1\" class=\"cbx2\" type=\"checkbox\" name=\"checkbox\" value=\"Airport Transfer\"><span></span></label>\n" +
                        "                                </label>\n" +
                        "                            </div>";
                    rowAdd.before(span);
                    $('#airportTransferPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="WiFi"){
                    var span1="<div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"wiFiLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"wiFiImage\" src=\"https://cdn3.iconfinder.com/data/icons/wireless/512/21-256.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">WiFi</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"wiFiPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"wiFicbx1\" type=\"checkbox\" name=\"checkbox\" value=\"WiFi\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span1);
                    $('#wiFiPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Parking Lot"){
                    var span2="<div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success btnsquick\" id=\"parkingLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"parkingImage1\" src=\"https://cdn3.iconfinder.com/data/icons/hotel-vacation/33/parking-2-256.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Parking Lot</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"parkingPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"PLcbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Parking Lot\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span2);
                    $('#parkingPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Pool Access"){
                    var span3 = " <div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"poolLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"poolImage1\" src=\"https://cdn1.iconfinder.com/data/icons/real-estate-set-2/512/38-512.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Pool Access</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label  id=\"poolPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"Poolcbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Pool Access\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span3);
                    $('#poolPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Restaurant"){
                    var span4 = "<div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"restLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"restImage\" src=\"https://cdn3.iconfinder.com/data/icons/glyph/227/Cook-256.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Restaurant</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"restaurantPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"Restcbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Restaurant\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span4);
                    $('#restaurantPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Room Service"){
                    var span5="<div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"roomServiceLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"roomServiceImage\" src=\"https://cdn4.iconfinder.com/data/icons/hotel-13/24/hotel-room-service-512.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Room Service</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"roomServicePrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"RScbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Room Service\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span5);
                    $('#roomServicePrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Wellness"){
                    var span6 = " <div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"wellnessLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"wellnessImage\" src=\"https://cdn4.iconfinder.com/data/icons/hotel-services-and-facilities-3/32/Hotel-40-256.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Wellness</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"wellnessPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"Wellcbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Wellness\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span6);
                    $('#wellnessPrice1 span').text(service.cena + "€");
                }else if(service.naziv==="Spa"){
                    var span7 =" <div class=\"col\">\n" +
                        "                               <label class=\"btn btnsquick btn-success\" id=\"spaLabel1\">\n" +
                        "                                   <div class=\"hvrbox\">\n" +
                        "                                       <img id=\"spaImage\" src=\"https://cdn4.iconfinder.com/data/icons/beauty-spa-solid-style/24/sign-spa--256.png\" alt=\"...\" class=\"img-thumbnail1 hvrbox-layer_bottom\">\n" +
                        "                                       <div class=\"hvrbox-layer_top\">\n" +
                        "                                           <div class=\"hvrbox-text1\">Spa</div>\n" +
                        "                                       </div>\n" +
                        "                                   </div>\n" +
                        "                                   <label id=\"spaPrice1\" class=\"priceForService\"><input class=\"cbx2\" id = \"Spacbx1\" type=\"checkbox\" name=\"checkbox\" value=\"Spa\"><span></span></label>\n" +
                        "                               </label>\n" +
                        "                           </div>";
                    rowAdd.before(span7);
                    $('#spaPrice1 span').text(service.cena + "€");
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