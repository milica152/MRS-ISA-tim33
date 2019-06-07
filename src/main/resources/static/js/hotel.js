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
                    $('#table').dataTable().fnDraw();
                    //table.fnDraw();
                    //location.reload();
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
                $('#SearchHotel')[0].reset();
            },
            error: function(xhr, status, error){
                var errorMessage = xhr.status + ': ' + xhr.statusText;
                alert('Error - ' + errorMessage);
            }
        });

    });

});