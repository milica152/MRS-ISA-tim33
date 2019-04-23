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
            { data: 'brojZaposlenih', title: 'No. empl.' },
            { data: null, defaultContent: '<button type="button" class="edit-branch btn btn-secondary">Edit</button>' }
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
            { data: null, title: "Booking", defaultContent: '<button type="button" class="book-car btn btn-success">Book car</button>' },
            { data: null, defaultContent: '<button type="button" class="edit-car btn btn-secondary">Edit</button>' }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    //#region Preuzimanje podataka za tabele

    // $.ajax({
    //     url: '/RentACar/specific/' + $profileID,
    //     data: {},
    //     success: function(data) {
    //         $('#span-name').text(data.naziv);
    //         $('#span-address').text(data.adresa);
    //         $('#span-desc').text(data.promotivniOpis);
    //
    //         if (data.filijale != undefined && data.filijale.length > 0) {
    //             $('#table-branches').dataTable().fnClearTable();
    //             $('#table-branches').dataTable().fnAddData(data.filijale);
    //         }
    //
    //         if (data.vozila != undefined && data.vozila.length > 0) {
    //             $('#table-cars').dataTable().fnClearTable();
    //             $('#table-cars').dataTable().fnAddData(data.vozila);
    //
    //             $('#table-cars tbody').on('click', '.book-car', function () {
    //                 var data = carsTable.row($(this).parents('tr')).data();
    //
    //                 $('#success-alert').toggleClass("collapse");
    //                 setTimeout(function() {
    //                     $('#success-alert').toggleClass("collapse");
    //                 }, 3000);
    //             });
    //         }
    //     },
    //     error: function (error) {
    //         console.log(error);
    //         alert(error.responseText);
    //     }
    // });

    $.ajax({
        url: '/Filijale/fromRC/' + $profileID,
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#table-branches').dataTable().fnClearTable();
                $('#table-branches').dataTable().fnAddData(data);
            }
        },
        error: function (error) {
            alert(error.responseText);
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
        },
        error: function (error) {
            alert(error.responseText);
        }
    });

    //#endregion
});