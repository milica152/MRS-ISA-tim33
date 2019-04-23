$(document).ready(function() {

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
    $.ajax({
        'url': '/Hotels/all',
        'method': "GET",
        'contentType': 'application/json'
    }).done( function(data) {
        $('#table').dataTable( {
            "aaData": data,
            retrieve: true,
            columns: [
                { data: 'naziv', title: 'Name' },
                { data: 'adresa', title: 'Address' },
                { data: 'opis', title: 'Promo description' },
                { data: 'ocena', tile: 'Rating'},
                { data: null, defaultContent: '<button type="button" class="btn btn-primary">Choose</button>'}
            ]
        })
    })

    /*$('#search').click(function() {
        $.ajax({
            url: '/Hotels/all',
            data: {},
            success: function(data) {
                $('#table').dataTable().fnClearTable();
                $('#table').dataTable().fnAddData(data);

                $('#table tbody').on('click', 'button', function() {
                    var data = table.row($(this).parents('tr')).data();
                    alert("Clicked Hotel ID: " + data.id);
                } );
            }
        });
    });*/
});