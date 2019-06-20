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
            { data: 'id', visible: false },
            { data: 'grad', title: 'City' },
            { data: 'adresa', title: 'Address' },
            { data: 'brojZaposlenih', title: 'No. Empl.' },
            {
                data: null,
                render: function(data, type, row)
                {
                    return '<button type="button" class="edit-branch btn btn-secondary" data-id="' + data.id + '">Edit</button>';
                }
            }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" },
            { width: "30px", targets: 3 }
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
            { data: 'brojMesta', title: 'Seats' },
            {
                data: null,
                render: function(data, type, row)
                {
                    return '<button type="button" class="book-car btn btn-success" data-id="' + data.id + '">Book</button>';
                }
            },
            {
                data: null,
                render: function(data, type, row)
                {
                    return '<button type="button" class="edit-car btn btn-secondary" data-id="' + data.id + '">Edit</button>';
                }
            }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" },
            { width: "40px", targets: 5 },
            { width: "40px", targets: 6 }
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

    function getRCBranches() {
        $.ajax({
            url: '/Filijale/fromRC/' + $profileID,
            data: {},
            success: function(data) {
                if (data != undefined && data.length > 0) {
                    $table = $('#table-branches').dataTable();
                    $table.fnClearTable();
                    $table.fnAddData(data);

                    $('#table-branches .edit-branch').click(function () {
                        var id = $(this).data('id');
                        var data = $table.fnGetData();
                        $dialog = $('#branch-dialog');

                        for (var i = 0; i < data.length; i++) {
                            if (data[i].id == id) {
                                $dialog.find('#city').val(data[i].grad);
                                $dialog.find('#address').val(data[i].adresa);
                                $dialog.find('#employees').val(data[i].brojZaposlenih);
                                break;
                            }
                        }

                        $dialog.find('.message-error').hide();
                        $dialog.find('.message-success').hide();
                        $dialog.find('#dialog-title').text('Edit branch with ID: ' + id);
                        $dialog.find('#btn-save').data('id', id);
                        $dialog.modal('show');
                    });
                }
            },
            error: function (error) {
                alert(error.responseText);
            }
        });
    }
    getRCBranches();

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
            $table = $('#table-cars').dataTable();
            $table.fnClearTable();
            if (data != undefined && data.length > 0) {
                $table.fnAddData(data);
                setEventForCarInTable($table);
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });

    function setEventForCarInTable($table) {
        // Event za editovanje vozila
        $('#table-cars .edit-car').click(function () {
            var id = $(this).data('id');
            var data = $table.fnGetData();
            $dialog = $('#car-dialog');

            for (var i = 0; i < data.length; i++) {
                if (data[i].id == id) {
                    $dialog.find('#name').val(data[i].naziv);
                    $dialog.find('#brand').val(data[i].marka);
                    $dialog.find('#price').val(data[i].cena);
                    $dialog.find('#type').val(data[i].tipVozila);
                    $dialog.find('#seats').val(data[i].brojMesta);
                    break;
                }
            }

            $dialog.find('.message-error').hide();
            $dialog.find('.message-success').hide();
            $dialog.find('#dialog-title').text('Edit car with ID: ' + id);
            $dialog.find('#btn-save').data('id', id);
            $dialog.modal('show');
        });

        // Event za rezervaciju vozila
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
                $table = $('#table-cars').dataTable();
                $table.fnClearTable();

                if (data != undefined && data.length > 0) {
                    $table.fnAddData(data);
                    setEventForCarInTable($table);
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
        $dialog.find('.message-error').hide();
        $dialog.find('.message-success').hide();
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
            $dialog.find('.message-error').show();
            $dialog.find('.message-success').hide();
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
                    $dialog.find('.message-error').hide();
                    $dialog.find('.message-success').show();
                },
                error: function (error) {
                    console.log(error);
                    alert(error.responseText);
                }
            });
        }
    });

    $('#btn-add-branch').click(function () {
        $dialog = $('#branch-dialog');
        $dialog.find('#city').val('');
        $dialog.find('#address').val('');
        $dialog.find('#employees').val('');
        $dialog.find('.message-error').hide();
        $dialog.find('.message-success').hide();
        $dialog.find('#dialog-title').text('Add new branch');
        $dialog.find('#btn-save').removeData('id');
        $dialog.modal('show');
    });

    $('#branch-dialog #btn-save').click(function () {
        $dialog = $('#branch-dialog');
        $id = $(this).data('id');
        var podaci = {
            id: $id,
            grad: $dialog.find('#city').val(),
            adresa: $dialog.find('#address').val(),
            brojZaposlenih: $dialog.find('#employees').val(),
            rentACar: {
                id : $profileID
            }
        };

        if (podaci.grad == '' || podaci.adresa == '' || podaci.brojZaposlenih == 0) {
            $dialog.find('.message-error').show();
            $dialog.find('.message-success').hide();
        } else {
            $.ajax({
                type: ($id == undefined ? 'POST' : 'PUT'),
                url: '/Filijale/' + ($id == undefined ? '' : $id),
                contentType : 'application/json',
                dataType : "json",
                data : JSON.stringify(podaci),
                success: function(data) {
                    $dialog.find('.message-error').hide();
                    $dialog.find('.message-success').show();
                    getRCBranches();
                },
                error: function (error) {
                    console.log(error);
                    alert(error.responseText);
                }
            });
        }
    });

    $('#btn-add-car').click(function () {
        $dialog = $('#car-dialog');
        $dialog.find('#name').val('');
        $dialog.find('#brand').val('');
        $dialog.find('#price').val('');
        $dialog.find('#type').val('');
        $dialog.find('#seats').val('');
        $dialog.find('.message-error').hide();
        $dialog.find('.message-success').hide();
        $dialog.find('#dialog-title').text('Add new car');
        $dialog.find('#btn-save').removeData('id');
        $dialog.modal('show');
    });

    $('#car-dialog #btn-save').click(function () {
        $dialog = $('#car-dialog');
        $id = $(this).data('id');

        var podaci = {
            id: $id,
            naziv: $dialog.find('#name').val(),
            marka: $dialog.find('#brand').val(),
            cena: $dialog.find('#price').val(),
            tipVozila: $dialog.find('#type').val(),
            brojMesta: $dialog.find('#seats').val(),
            rentACar: {
                id : $profileID
            }
        };

        if (podaci.naziv == '' || podaci.marka == '' || podaci.cena == 0 ||
            podaci.tipVozila <= 0 || podaci.brojMesta <= 0) {
            $dialog.find('.message-error').show();
            $dialog.find('.message-success').hide();
        } else {
            $.ajax({
                type: ($id == undefined ? 'POST' : 'PUT'),
                url: '/Vozila/' + ($id == undefined ? '' : $id),
                contentType : 'application/json',
                dataType : "json",
                data : JSON.stringify(podaci),
                success: function(data) {
                    $dialog.find('.message-error').hide();
                    $dialog.find('.message-success').show();
                    $('#search-cars').click();
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