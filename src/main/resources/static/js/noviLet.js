$profileID0 = window.location.href.lastIndexOf('/');
$profileID1 = window.location.href.substring(0,$profileID0);
$profileID2 = $profileID1.substring($profileID1.lastIndexOf('/') + 1);

$(document).ready(function(){

    $('.timepicker').timepicker({
        timeFormat: 'HH:mm',
        //interval: 60,
        //minTime: '10',
        //maxTime: '6:00pm',
        //defaultTime: '11',
        startTime: '01:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });


    $( "#datum_poletanja").datepicker({
        dateFormat:"yy-mm-dd",
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
        dateFormat:"yy-mm-dd",
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

    $(document).on('submit', "#addForm", function(e){
        var $cena = $('#cena');
        var $ocena = $('#ocena');
        var $vreme_poletanja = $('#vreme_poletanja');
        var $vreme_sletanja = $('#vreme_sletanja');
        var $datum_poletanja = $('#datum_poletanja');
        var $datum_sletanja = $('#datum_sletanja');
        var $odredisni_aerodrom_id = $('#odredisni_aerodrom_id');
        var $polazni_aerodrom_id = $('#polazni_aerodrom_id');


        if($cena.val().trim().length === 0 || $ocena.val().trim().length === 0 || $vreme_poletanja.val().trim().length === 0  || $vreme_sletanja.val().trim().length === 0  || $datum_poletanja.val().trim().length === 0  || $datum_sletanja.val().trim().length === 0  || $odredisni_aerodrom_id.val().trim().length === 0  || $polazni_aerodrom_id.val().trim().length === 0 ){
            alert("You must fill all fields!");
            return false;
        }

        e.preventDefault();
        var flight = JSON.stringify({
            "cena": $cena.val(),
            "ocena": $ocena.val(),
            "vreme_poletanja": $vreme_poletanja.val(),
            "vreme_sletanja": $vreme_sletanja.val(),
            "aviokompanija_id": $profileID2,
            "odredisni_aerodrom_id": $odredisni_aerodrom_id.val(),
            "polazni_aerodrom_id": $polazni_aerodrom_id.val(),
            "datum_poletanja": $datum_poletanja.val(),
            "datum_sletanja": $datum_sletanja.val()
        });

        $.ajax({
            type: 'POST',
            url: '/Aviocompany/' + $profileID2 + '/addFlight',
            contentType : 'application/json',
            dataType : "text",
            data : flight,
            success: [function(newFlight){
                alert('uspesno dodavanje');
                window.location = "/Aviocompany/" + $profileID2;
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })


    })


});