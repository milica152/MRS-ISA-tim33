$profileID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
$profileID0 = window.location.href.lastIndexOf('/');
$profileID1 = window.location.href.substring(0,$profileID0);
$profileID2 = $profileID1.substring($profileID1.lastIndexOf('/') + 1);

$(document).ready(function() {

    $(document).on('click', '#backBtn', function(e){
        e.preventDefault();
        window.location = "/Aviocompany";
    });

    $( "#datum_poletanja").datepicker({
        dateFormat:"dd-mm-yy",
        minDate: '0d',
        onSelect: function(e) {
            $('#datum_sletanja').datepicker('option', 'minDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#datum_poletanja').datepicker('setDate', null);
            $('#datum_sletanja').datepicker('option', 'minDate', '0d');
        }
    });
    $( "#datum_sletanja" ).datepicker({
        dateFormat:"dd-mm-yy",
        useCurrent: false,
        minDate: '0d',
        onSelect: function(e) {
            $('#datum_poletanja').datepicker('option', 'maxDate', e);    //postavlja se minimalni datum za polazak
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#datum_sletanja').datepicker('setDate', null);
            $('#datum_poletanja').datepicker('option', 'maxDate', null);
        }
    });


    var flightsTable = $('#table-flights').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: true,
        compact: true,
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'vremePolaska', title: 'Polazak' },
            { data: 'vremeDolaska', title: 'Dolazak' },
            { data: 'cena', title: 'Cena'},
            { data: 'ocena', title: 'Ocena'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="ChooseBtn">Choose</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="EditBtn">Edit</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="RemoveBtn">Remove</button>'}
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });


    $.ajax({
        type: 'GET',
        url: '/Flight/fromAviocompany/' + $profileID,
        data: {},
        success: function (data) {
            if (data !== undefined && data.length > 0) {
                $('#table-flights').dataTable().fnClearTable();
                $('#table-flights').dataTable().fnAddData(data);
            }
        }
    });


    $.ajax({
        type:'GET',
        url : '/Aviocompany/specific/' + $profileID,
        data : {},
        success: function (data) {
            $('#span-name').html(data.naziv);
            $('#span-address').html(data.adresa);
            $('#span-desc').html(data.opis);
        }
    });


    $(document).on('submit', '#addForm', function(e){
        var $cena = $('#price').val();
        var $klasa = $('#class').val();
        var $vreme_poletanja = $('#vreme_poletanja').val();
        var $vreme_sletanja = $('#vreme_sletanja').val();
        var $datum_poletanja = $('#datum_poletanja').val();
        var $datum_sletanja = $('#datum_sletanja').val();
        var $odredisni_aerodrom = $('#odredisni_aerodrom_id').val();
        var $polazni_aerodrom = $('#polazni_aerodrom_id').val();

        if($vreme_sletanja === null ||$vreme_sletanja === "" || $datum_sletanja=== null || $datum_sletanja===""){
            alert("You must fill all fields!");
            return  false;
        }

        if($cena === null || $cena === "" || $klasa===null  || $klasa === ""  || $vreme_poletanja===null || $vreme_poletanja === "" ||  $datum_poletanja===null  ||  $datum_poletanja==="" || $odredisni_aerodrom===null  || $odredisni_aerodrom==="" || $polazni_aerodrom===null || $polazni_aerodrom===""){
            alert("You must fill all fields!");
            return false;
        }
        var listaDep = $polazni_aerodrom.split(', ');
        $polazni_aerodrom = listaDep[0];


        var listaArr = $odredisni_aerodrom.split(', ');
        $odredisni_aerodrom = listaArr[0];
        e.preventDefault();

        var flight = JSON.stringify({
            "cena": $cena,
            "vreme_poletanja": $vreme_poletanja,
            "vreme_sletanja": $vreme_sletanja,
            "aviokompanija_id": $profileID,
            "odredisni_aerodrom_id": $odredisni_aerodrom,
            "polazni_aerodrom_id": $polazni_aerodrom,
            "datum_poletanja": $datum_poletanja,
            "datum_sletanja": $datum_sletanja,
            "klasa" : $klasa
        });


        $.ajax({
            type: 'POST',
            url: '/Aviocompany/' + $profileID2 + '/addFlight',
            contentType : 'application/json',
            dataType : "text",
            data : flight,
            success: [function(){
                $('#addFlightModal').modal('hide');
                popuniTabelu();
                //window.location = "/Aviocompany/" + $profileID2;
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })


    });


});

function popuniTabelu() {
    $.ajax({
        type: 'GET',
        url: '/Flight/fromAviocompany/' + $profileID,
        data: {},
        success: function (data) {
            if (data !== undefined && data.length > 0) {
                $('#table-flights').dataTable().fnClearTable();
                $('#table-flights').dataTable().fnAddData(data);
            }
        }
    });

}