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

    $.ajax({
        url: '/RentACar/specific/' + $profileID,
        data: {},
        success: function(data) {
            $('#span-name').text(data.naziv);
            $('#span-address').text(data.adresa);
            $('#span-desc').text(data.promotivniOpis);
        },
        error: function (error) {
            console.log(error);
            alert(error.responseText);
        }
    });

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
        type: 'GET',
        url: '/Filijale/all',
        success: function(data) {
            if (data != undefined) {
                for (var i = 0; i < data.length; i++) {
                    $("#pickup-location").append(new Option(data[i].grad, data[i].grad));
                    $("#return-location").append(new Option(data[i].grad, data[i].grad));
                }
            }
        },
        error: function (error) {
            alert(error);
        },
        async: false
    });

    $.ajax({
        url: '/Vozila/fromRC/' + $profileID,
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#table-cars').dataTable().fnClearTable();
                $('#table-cars').dataTable().fnAddData(data);

                $('#table-cars .book-car').each(function() {
                   $(this).click(function () {
                       var data = carsTable.row($(this).parents('tr')).data();

                       $('#success-alert').toggleClass("collapse");
                       setTimeout(function() {
                           $('#success-alert').toggleClass("collapse");
                       }, 3000);
                   });
                });
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });

    //#endregion

    //#region Funkcije dugmica

    $('#search-cars').click(function() {
        var filter = {
            mestoPreuzimanja: $('#pickup-location').find(':selected').val(),
            mestoVracanja: $('#return-location').find(':selected').val(),
            datumPreuzimanja: $('#date-from').val(),
            datumVracanja: $('#date-to').val(),
            cenaOd: $('#price-min').val(),
            cenaDo: $('#price-max').val(),
            tipVozila: $('#car-type').val(),
            brojPutnika: $('#passengers').val()
        };

        $.ajax({
            type: 'POST',
            url: '/Vozila/fromRCWithFilters/' + $profileID,
            contentType : 'application/json',
            dataType : "json",
            data: JSON.stringify(filter),
            success: function(data) {
                $('#table-cars').dataTable().fnClearTable();

                if (data != undefined && data.length > 0) {
                    $('#table-cars').dataTable().fnClearTable();
                    $('#table-cars').dataTable().fnAddData(data);

                    $('#table-cars .book-car').each(function() {
                        $(this).click(function () {
                            var data = carsTable.row($(this).parents('tr')).data();

                            $('#success-alert').toggleClass("collapse");
                            setTimeout(function() {
                                $('#success-alert').toggleClass("collapse");
                            }, 3000);
                        });
                    });
                }
            },
            error: function (error) {
                alert(error);
            }
        });
    });

    $('#btn-edit-profile').click(function() {
        $dialog = $('#rc-profile-dialog');
        $dialog.find('#rc-name').val($('#span-name').text());
        $dialog.find('#rc-address').val($('#span-address').text());
        $dialog.find('#rc-description').val($('#span-desc').text());
        $dialog.find('#rc-edit-error').hide();
        $dialog.find('#rc-edit-success').hide();
        $dialog.modal('show');
    });

    $('#rc-profile-dialog').find("#btn-save").click(function() {
        $dialog = $('#rc-profile-dialog');

        var podaci = {
            id: $profileID,
            naziv: $dialog.find('#rc-name').val(),
            adresa: $dialog.find('#rc-address').val(),
            promotivniOpis: $dialog.find('#rc-description').val()
        };

        if (podaci.naziv == '' || podaci.adresa == '' || podaci.promotivniOpis == '') {
            $dialog.find('#rc-edit-error').show();
            $dialog.find('#rc-edit-success').hide();
        } else {
            $.ajax({
                type: 'PUT',
                url: '/RentACar/' + $profileID,
                contentType : 'application/json',
                dataType : "json",
                data : JSON.stringify(podaci),
                success: function(data) {
                    $('#span-name').text(data.naziv);
                    $('#span-address').text(data.adresa);
                    $('#span-desc').text(data.promotivniOpis);
                    $dialog.find('#rc-edit-error').hide();
                    $dialog.find('#rc-edit-success').show();
                },
                error: function (error) {
                    console.log(error);
                    alert(error.responseText);
                }
            });
        }
    });

    //#endregion
});