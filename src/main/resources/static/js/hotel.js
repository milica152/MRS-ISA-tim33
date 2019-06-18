var noOfReser=0;

$(document).ready(function() {
    initTable();
    setCart();
    var $hotelReservation = $.parseJSON(sessionStorage.getItem("HotelReservation"));
    if($hotelReservation!=null){
        updateCart($hotelReservation);
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

    var table = $('#table').DataTable( {
        data: undefined,
        searching: false,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'opis', title: 'Promo description' },
            { data: 'ocena', title: 'Rating'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtn">Choose</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="editBtn">Edit</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="removeBtn">Remove</button>'}
        ],
        columnDefs: [
            {className: "align-middle", targets: "_all"}
        ]
    });


    $('#table tbody').on('click', '#chooseBtn', function(e) {
        var data = table.row($(this).parents('tr')).data();
        document.location.href = '/Hotels/' + data.id;

    });


    $('#table tbody').on('click', '#removeBtn', function(e) {
        var conBox = confirm("Are you sure?");
        var data = table.row($(this).parents('tr')).data();
        if(conBox){
            $.ajax({
                type: 'POST',
                url: '/Hotels/deleteHotel/'+data.id,
                data : data.id,
                success: [function(){
                    initTable();
                }],
                error: function(xhr, status, error){
                    var errorMessage = xhr.status + ': ' + xhr.statusText;
                    alert('Error - ' + errorMessage);
                }
            });

        }
        else{
            e.preventDefault();
        }
        e.preventDefault();

    } );

    $("#SearchHotel").submit(function(e) {
        e.preventDefault();

        var NameDest = $("#HotelNamePlace").val();
        var DateFrom = $("#datepickerStart").val();
        var DateTo = $("#datepickerEnd").val();

        sessionStorage.setItem("DateFromHotelRes", DateFrom);
        sessionStorage.setItem("DateToHotelRes", DateFrom);

        var search = {
            nameOrDestination: NameDest,
            dateFrom: DateFrom,
            dateTo: DateTo
        };

        $.ajax({
            type: 'POST',
            url: "/Hotels/search",
            contentType : 'application/json',
            dataType : "json",
            data: JSON.stringify(search),
            success: function(data) {
                if(data.length>0){
                    $('#table').dataTable().fnClearTable();
                    $('#table').dataTable().fnAddData(data);
                }
                else{
                    alert("Ne postoji ni jedan hotel koji zadovoljava kriterijume pretrage");
                }
                $('#HotelNamePlace').val("");
                $('#datepickerStart').val("");
                $('#datepickerEnd').val("");

            },
            error: function(xhr, status, error){
                var errorMessage = xhr.status + ': ' + xhr.statusText;
                alert('Error - ' + errorMessage);
            }
        });

    });
    function initTable(){
        $.ajax({
            url: '/Hotels/all',
            data: {},
            success: function(data) {
                if (data != undefined && data.length > 0) {
                    $('#table').dataTable().fnClearTable();
                    $('#table').dataTable().fnAddData(data);
                }
            }
        });
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
            cartEl = cartEl + $hotelReservation.room.length;
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

});