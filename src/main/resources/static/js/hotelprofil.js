$profileID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);

$(document).ready(function() {

    //#region Inicijalizacija tabela

    var roomsTable = $('#table-rooms').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: false,
        compact: true,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'opis', title: 'Promo description' },
            { data: 'ocena', tile: 'Rating'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="ChooseBtn">Choose</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="EditBtn">Edit</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="RemoveBtn">Remove</button>'}
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    //#region Preuzimanje podataka za tabele

    /*$.ajax({
        url: '/Sobe/all',
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
*/
    //#endregion
});