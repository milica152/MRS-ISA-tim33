$profileID = document.location.href.substring(document.location.href.lastIndexOf('/') + 1);
var isGroupSel = false;
var roomTypesNeeded=[];

$(document).ready(function() {

    setPage();
    $(".controls").hide();

    //#region Inicijalizacija tabela
    $('#alert').hide();
    var roomsTable = $('#table-rooms').DataTable({
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
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="reserveRoom">Reserve</button>'}
            ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    setTable();
    setModal();

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
                    alert("Dodalaaa");
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
            }
        },
        error: function(xhr, status, error) {
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });

}