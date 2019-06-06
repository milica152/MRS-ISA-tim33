$(document).ready(function() {

    var tableAirline = $('#tableAirline').DataTable( {
        data: undefined,
        searching: true,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'opis', title: 'Promo description' },
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtnAirline">Choose</button>'}
        ],
        columnDefs: [
            {className: "align-middle", targets: "_all"}
        ]
    });

    var tableHotel = $('#tableHotel').DataTable( {
        data: undefined,
        searching: true,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'opis', title: 'Promo description' },
            { data: 'ocena', title: 'Rating'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtnHotel">Choose</button>'}
        ],
        columnDefs: [
            {className: "align-middle", targets: "_all"}
        ]
    });

    var tableRCS = $('#tableRCS').DataTable( {
        data: undefined,
        searching: true,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'opis', title: 'Promo description' },
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="chooseBtnRCS">Choose</button>'}
        ],
        columnDefs: [
            {className: "align-middle", targets: "_all"}
        ]
    });

    $.ajax({
        url: '/AviokompanijaProfil/all',
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#tableAirline').dataTable().fnClearTable();
                $('#tableAirline').dataTable().fnAddData(data);
            }
        }
    });
    $.ajax({
        url: '/Hotels/all',
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#tableHotel').dataTable().fnClearTable();
                $('#tableHotel').dataTable().fnAddData(data);
            }
        }
    });
    $.ajax({
        url: '/RentACar/all',
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#tableRCS').dataTable().fnClearTable();
                $('#tableRCS').dataTable().fnAddData(data);
            }
        }
    });

    $('#tableAirline tbody').on('click', '#chooseBtnAirline', function(e) {
        var data = tableAirline.row($(this).parents('tr')).data();
        sessionStorage.setItem("user","1");
        sessionStorage.setItem("service", data.naziv);
        document.location.href = "register.html";

    });
    $('#tableHotel tbody').on('click', '#chooseBtnHotel', function(e) {
        var data = tableHotel.row($(this).parents('tr')).data();
        sessionStorage.setItem("user","2");
        sessionStorage.setItem("service", data.naziv);
        document.location.href = "register.html";

    });
    $('#tableRCS tbody').on('click', '#chooseBtnRCS', function(e) {
        var data = tableRCS.row($(this).parents('tr')).data();
        sessionStorage.setItem("user","3");
        sessionStorage.setItem("service", data.naziv);
        document.location.href = "register.html";

    });



});