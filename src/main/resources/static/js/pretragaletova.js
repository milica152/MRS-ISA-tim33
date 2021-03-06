var current_data = [];
var desiredType = "";
var returnDate = "";
var departureAir = "";
var arrivalAir = "";
var noPassengers = 0;
var fClass = "";
var loggedUsername = "";


$(document).ready(function() {

    var isLogged = false;
    var isCorrectAdmin = false;


    $.ajax({
        type: 'GET',
        url: '/whoami',
        success: function (data) {
            if (data == undefined || data == "") {
                isLogged = false;
                loggedUsername = "";
                hideAll(isLogged);

            } else {
                isLogged = true;
                for (var i = 0; i < data.authorities.length; i++) {
                    if (data.authorities[i].authority == 'SISTEM_ADMIN' || data.authorities[i].authority == 'ADMIN_RCS') {
                        isCorrectAdmin = true;
                        break;
                    }
                }
                loggedUsername = data.username;
                if (!isCorrectAdmin) {
                    hideAll(isLogged);
                }
            }

            //initTables(!isCorrectAdmin, isLogged);
        },
        async: false
    });


    function hideAll(isLogged){
        if(!isLogged){
            document.getElementById('li_edit').style.display = 'none';
        }

    }

        var table = $('#flights_table').DataTable( {
            data: undefined,
            searching: false,
            ordering: true,
            lengthChange: false,
            columns: [
                { data: 'id', title: "ID"},

                { data: 'vremePolaska', title: 'Dep. time' },
                { data: 'vremeDolaska', title: 'Arr. time'},
                { data: 'cena', title: 'Price'},
                {
                    data: null,
                    render: function(data, type, row)
                    {
                        return !isLogged || isCorrectAdmin ? '' : '<button type="button" class="reserve-btn btn btn-primary" id="reserveBtn">Reserve</button>';
                    }
                }
                //{ data: null, defaultContent: '<button type="button" class="btn btn-primary" id="reserveBtn">Reserve</button>'}

                ],
            columnDefs: [
                {className: "align-middle", targets: "_all"}
            ]
        });

        var tableairlines = $('#tableairlines').DataTable( {
            data: undefined,
            searching: false,
            lengthChange: false,
            columns: [
                { data: 'naziv', title: 'Name' },
                { data: 'adresa', title: 'Address' },
                { data: 'opis', title: 'Promo description' },
                { data: 'ocena', title: 'Rating'},
                { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtn">Choose</button>'},
                {
                    data: null,
                    render: function(data, type, row)
                    {
                        return !isCorrectAdmin ? '' : '<button type="button" class="edit-btn btn btn-secondary" >Edit</button>';
                    }
                },
                {
                    data: null,
                    render: function(data, type, row)
                    {
                        return !isCorrectAdmin ? '' : '<button type="button" class="delete-btn btn btn-danger">Delete</button>';
                    }
                }





                //{ data: null, defaultContent: '<div class="edit_div"><button type="button" class="btn btn-primary" id="editBtn">Edit</button></div>'},
                //{ data: null, defaultContent: '<div class="remove_div"><button type="button" class=" delete-btn btn btn-primary" id="removeBtn">Remove</button></div>'}
            ],
            columnDefs: [
                {className: "align-middle", targets: "_all"}
            ]
        });


    refreshEditModal();


    function refreshEditModal(){
        if(loggedUsername !== ""){

            $.ajax({
                type : 'POST',
                url : '/findByUsername',
                dataType : "json",
                data : loggedUsername,
                success : function (data) {
                    $('#f_name').val(data.ime);
                    $('#l_name').val(data.prezime);
                    $('#email_address').val(data.email);
                    $('#user_name').val(data.username);
                    $('#newPassword').val(data.password);
                    $('#newPassword2').val(data.password);
                },
                error : function () {
                    alert("There is a problem with autentification.");
                }
            });
        }
    }

    $(document).on('click', '#doneEditBtn', function(e){
        e.preventDefault();

        var $firstName = $('#f_name').val();
        var $lastName = $('#l_name').val();
        var $email = $('#email_address').val();
        var $password = $('#newPassword').val();
        var $password2 = $('#newPassword2').val();


        if($password !== $password2){
            alert("Different passwords!");
            return false;
        }

        if($firstName === "" || $lastName === "" ||  $email === ""){
            alert('All fields must be filled!');
            return false;
        }
        if($password === "" || $password2 === "" ){
            alert('All fields must be filled!');
            return false;
        }

        var userInfo = JSON.stringify({
            "password" : $password,
            "name" : $firstName,
            "surname" : $lastName,
            "email" : $email
        });
        $.ajax({
            type : 'POST',
            url : '/editInfo',
            dataType : "text",
            contentType : 'application/json',
            data : userInfo,
            success : function (data) {
                if(data === "ok"){
                    alert("Profile successfully changed!");
                }else {
                    alert(data);
                }

                refreshEditModal();
            },
            error : function (xhr, error, status) {
                alert("Error : " + status +  error);
            }
        });



    });






    $( "#datepickerStart").datepicker({
        dateFormat:"dd-mm-yy",
        minDate: '0d',
        onSelect: function(e) {
            $('#datepickerEnd').datepicker('option', 'minDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
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
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#datepickerEnd').datepicker('setDate', null);
            $('#datepickerStart').datepicker('option', 'maxDate', null);
        }
    });

    $('#FlightType').change(function() {
        var selected = $(this).val();
        desiredType = $(this).val();
        if(selected === 'Round-trip'){
            $('#datepickerEnd').show();
        }
        else{
            $('#datepickerEnd').hide();
            $('#datepickerEnd').val("");
        }
    });


    $(document).on('click', '#reserveBtn', function(e){
        e.preventDefault();
        var data = table.row($(this).parents('tr')).data();
        $.ajax({
            method:'GET',
            url: '/Flight/getAirlineId/' + data.id,
            dataType : "text",
            success: [function(id) {
                window.location = "/Aviocompany/" + id + "/reservation/" + data.id;
            }],
            error: function(xhr, status, error){
                var errorMessage = xhr.status + ': ' + xhr.statusText;
                alert('Error - ' + errorMessage);
            }
        });


    });

    $.ajax({
        url: '/Flight/all',
        data: {},
        success: function(data) {
            if (data !== undefined && data.length > 0) {
                $('#flights_table').dataTable().fnClearTable();
                $('#flights_table').dataTable().fnAddData(data);
            }
        }
    });

    $('#flights_table tbody').on('click', '#chooseBtn', function(e) {
        e.preventDefault();
        var data = table.row($(this).parents('tr')).data();    //podaci o letu koji je kliknut
        //direkt na reyervaciju
        //window.location = "/Aviocompany/" + idAviokompanije + "/reservation/" + data.id;                //ide se na prozor rezervacije
    });


    $(document).on('submit', '#search-airline-form', function (e) {
        e.preventDefault();

        var $name = $('#airline_name').val();
        var $city = $('#airline_city').val();

        if($name == null){
            $name="";
        }
        if($city == null){
            $city= "";
        }

        var searchParams = JSON.stringify({
            "name" : $name,
            "city" : $city
        });


        $.ajax({
            method:'POST',
            url: '/Aviocompany/search',
            contentType : 'application/json',
            dataType : "json",
            data: searchParams,     //posalji prikupljene podatke
            success: [function(data) {

                if (data !== undefined && data.length > 0) {
                    $('#tableairlines').dataTable().fnClearTable();
                    $('#tableairlines').dataTable().fnAddData(data);
                }
                else{
                    alert("Ne postoji nijedna kompanija koja zadovoljava kriterijume pretrage");
                }
            }],
            error: function(xhr, status, error){
                var errorMessage = xhr.status + ': ' + xhr.statusText;
                alert('Error - ' + errorMessage);
            }
        });
    });


    $(document).on('submit', '#search-form', function (e) {
        e.preventDefault();

        var $depAir = $('#departureAirport').val();
        var $arrAir = $('#arrivalAirport').val();
        var $depTime = $('#datepickerStart').val();
        var $noPass = $('#Passengers').val();
        var $class = $('#Class').val();
        returnDate = $('#datepickerEnd').val();
        departureAir =  $('#departureAirport').val();
        arrivalAir = $('#arrivalAirport').val();
        noPassengers = $('#Passengers').val();
        fClass = $('#Class').val();


        //validation
        if($depAir == null){
            $depAir = "";
        }
        if($arrAir == null){
            $arrAir = "";
        }
        if($class == null){
            $class = "";
        }
        if($depAir !== ""){
            var listaDep = $depAir.split(', ');
            $depAir = listaDep[0];
        }
        if($arrAir !== ""){
            var listaArr = $arrAir.split(', ');
            $arrAir = listaArr[0];
        }
        e.preventDefault();
        var searchParameters = JSON.stringify({
            "dateFrom" : $depTime,
            "departureAirport" : $depAir,
            "arrivalAirport" : $arrAir,
            "NoPassengers" : $noPass,
            "klasa" : $class
        });


        $.ajax({
            method:'POST',
            url: '/Flight/search',
            contentType : 'application/json',
            dataType : "json",
            data: searchParameters,     //posalji prikupljene podatke
            success: [function(data) {
                current_data = [];
                for(var let in data){
                    current_data.push(data[let].id);
                }
                if (data !== undefined && data.length > 0) {
                    document.getElementById('whole_filter').style.display = 'contents';
                    $('#flights_table').dataTable().fnClearTable();
                    $('#flights_table').dataTable().fnAddData(data);
                }
                else{
                    alert("Ne postoji nijedan let koji zadovoljava kriterijume pretrage");
                }
            }],
            error: function(xhr, status, error){
                var errorMessage = xhr.status + ': ' + xhr.statusText;
                alert('Error - ' + errorMessage);
            }
        });
    });

    $.ajax({
        url: '/LP/all',
        data: {},
        success: function(data) {
            for(var lp in data){
                var opcija = new Option(data[lp].nazivAerodroma + ", " + data[lp].grad + ", " + data[lp].drzava);
                document.getElementById('departureAirport').add(opcija);
            }
            for(var lp in data){
                var opcija = new Option(data[lp].nazivAerodroma + ", " + data[lp].grad + ", " + data[lp].drzava);
                document.getElementById('arrivalAirport').add(opcija);
            }

        }
    });


    $.ajax({
        url: '/Aviocompany/all',
        data: {},
        success: function(data) {
            for(var airline in data){
                var opcija = new Option(data[airline].naziv);
                opcija.setAttribute("value", data[airline].naziv);
                document.getElementById('airlines').add(opcija);
            };
            if (data !== undefined && data.length > 0) {
                $('#tableairlines').dataTable().fnClearTable();
                $('#tableairlines').dataTable().fnAddData(data);
            }
        },
        error: function(xhr, status, error){
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });

    $('#tableairlines tbody').on('click', '#chooseBtn', function(e) {
        e.preventDefault();
        //var id = $(this).data('id');
        var data = tableairlines.row($(this).parents('tr')).data();    //podaci o aviokomp koja je kliknuta
        window.location = "/Aviocompany/" + data.id;
    });

    $('#tableairlines tbody').on('click', '.delete-btn', function(e) {
        e.preventDefault();
        var conBox = confirm("Are you sure?");
        //var id = $(this).data('id');

        var data = tableairlines.row($(this).parents('tr')).data();
        if(conBox){
            $.ajax({
                type: 'POST',
                url: '/Aviocompany/deleteAviocompany/'+data.id,
                data : data.id,
                success: [function(){
                    $('#tableairlines').dataTable().fnDraw();
                    //table.fnDraw();
                    //location.reload();
                }],
                error: function(xhr, status, error){
                    var errorMessage = xhr.status + ': ' + xhr.statusText
                    alert('Error - ' + errorMessage);
                }
            });

        }
        else{
            e.preventDefault();
        }




    } );

    $(document).on('click', '#filterFlights', function(e){

        e.preventDefault();
        doFilter();

    });

});

function doFilter(){

    var filterParams = {
        duration: $('#duration').val(),
        minPrice: $('#minPrice').val(),
        maxPrice: $('#maxPrice').val(),
        airline: $('#airlines').val(),
        flights: current_data
    };
    $.ajax({
        type:'POST',
        url:'/Flight/filter',
        contentType : 'application/json',
        dataType : "json",
        data: JSON.stringify(filterParams),
        success: function(data){
            if(data.length>0){
                $('#flights_table').dataTable().fnClearTable();
                $('#flights_table').dataTable().fnAddData(data);
            }
            else{
                alert("No flight that fulfills these parameters!");
            }
        },
        error: function(xhr, status, error){
            alert(xhr.responseText);

        }


    })
}