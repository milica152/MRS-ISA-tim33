$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: '/Filijale/all',
        success: function(data) {
            if (data != undefined) {
                for (var i = 0; i < data.length; i++) {
                    $("#rc-location").append(new Option(data[i].grad, data[i].grad));
                }
            }
        },
        error: function (error) {
            alert(error);
        },
        async: false
    });

    var table = $('#table').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        columns: [
            { data: 'naziv', title: 'Name' },
            { data: 'adresa', title: 'Address' },
            { data: 'promotivniOpis', title: 'Promo description' },
            { data: null, defaultContent: '<button type="button" class="btn btn-primary">Choose</button>' }
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    $('#search').click(function() {
        var filter = {
            nazivServisa: $('#rc-name').val(),
            adresaServisa: $('#rc-location').find(":selected").val(),
            datumOd: $('#date-from').val(),
            datumDo: $('#date-to').val()
        };

        $.ajax({
            type: 'POST',
            url: '/RentACar/all',
            contentType : 'application/json',
            dataType : "json",
            data: JSON.stringify(filter),
            success: function(data) {
                $('#table').dataTable().fnClearTable();

                if (data != undefined && data.length > 0) {
                    $('#table').dataTable().fnAddData(data);

                    $('#table tbody').on('click', 'button', function () {
                        var data = table.row($(this).parents('tr')).data();
                        window.location = "/RentACar/" + data.id;
                    });
                }
            },
            error: function (error) {
                alert(error);
            }
        });
    });

    // $('#search').click();
});