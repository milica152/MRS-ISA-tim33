$profileID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);

$(document).ready(function() {

    //#region Inicijalizacija tabela

    var branchesTable = $('#table-branches').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: false,
        compact: true,
        columns: [
            { data: 'grad', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'brojZaposlenih', title: 'No. empl.' }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    var carsTable = $('#table-cars').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'marka', title: 'Manifacturer' },
            { data: 'cena', title: 'Price' },
            { data: 'tipVozila', title: 'Type' },
            { data: null, defaultContent: '<button type="button" class="btn btn-primary">Book it</button>' }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    //#region Preuzimanje podataka za tabele

    $.ajax({
        url: '/Filijale/all',
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#table-branches').dataTable().fnClearTable();
                $('#table-branches').dataTable().fnAddData(data);
            }
        }
    });

    $.ajax({
        url: '/Vozila/fromRC/' + $profileID,
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#table-cars').dataTable().fnClearTable();
                $('#table-cars').dataTable().fnAddData(data);

                $('#table-cars tbody').on('click', 'button', function () {
                    var data = carsTable.row($(this).parents('tr')).data();

                    $('#success-alert').toggleClass("collapse");
                    setTimeout(function() {
                        $('#success-alert').toggleClass("collapse");
                    }, 3000);
                });
            }
        }
    });

    //#endregion
});