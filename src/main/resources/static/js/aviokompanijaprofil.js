$profileID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);

$(document).ready(function() {


    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/Flight/fromAviocompany/' + $profileID,
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




    var flightsTable = $('#table-flights').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: false,
        compact: true,
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'vremePoletanja', title: 'Polazak' },
            { data: 'vremeSletanja', title: 'Dolazak' },
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


//preuzmi podatke

});