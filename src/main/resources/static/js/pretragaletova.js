$(document).ready(function() {


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
        if(selected === 'Round-trip'){
            $('#datepickerEnd').show();
        }
        else{
            $('#datepickerEnd').hide();
            $('#datepickerEnd').val("");
        }
    });

    $(document).on('submit', '#search-form', function (e) {
        //skupi podatke
        var $depAir = $('#departureAirport').val();
        var $arrAir = $('#arrivalAirport').val();
        var $depTime = $('#datepickerStart').val();
        var $arrTime = $('#datepickerEnd').val();
        var $noPass = $('#Passengers').val();
        var $type = $('#FlightType').val();
        var $class = $('#Class').val();

        if($depAir == null){
            $depAir = "";
        }
        if($arrAir == null){
            $arrAir = "";
        }
        if($noPass == null){
            $noPass = "";
        }
        if($type == null){
            $type = "";
        }
        if($class == null){
            $class = "";
        }
        if($noPass.length > 3 || $noPass===""){
            $noPass = "0";
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
            "dateTo" : $arrTime,
            "departureAirport" : $depAir,
            "arrivalAirport" : $arrAir,
            "NoPassengers" : $noPass,
            "type" : $type,
            "klasa" : $class
        });


        alert('nije normalan');

        $.ajax({
            method:'POST',
            url: '/Flight/search',
            contentType : 'application/json',
            dataType : "json",
            data: searchParameters,     //posalji prikupljene podatke
            success: [function(data) {
                alert('success!');
                if (data !== undefined && data.length > 0) {
                    $('#table').dataTable().fnClearTable();
                    $('#table').dataTable().fnAddData(data);

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

    var table = $('#table').DataTable( {
        data: undefined,
        searching: false,
        ordering: true,
        lengthChange: false,
        columns: [
            { data: '', title: 'Departure' },
            { data: '', title: 'Arrival' },
            { data: 'vremePolaska', title: 'Dep. time' },
            { data: 'vremePovratka', title: 'Arr. time'},
            { data: 'cena', title: 'Price'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtn">Choose</button>'}

        ],
        columnDefs: [
            {className: "align-middle", targets: "_all"}
        ]
    });


    $.ajax({
        url: '/Flight/all',
        data: {},
        success: function(data) {
            if (data !== undefined && data.length > 0) {

                $('#table').dataTable().fnClearTable();
                $('#table').dataTable().fnAddData(data);
            }
        }
    });

    $('#table tbody').on('click', '#chooseBtn', function(e) {
        var data = table.row($(this).parents('tr')).data();    //podaci o letu koji je kliknut
        window.location = "/Flight/" + data.id;
    });

});