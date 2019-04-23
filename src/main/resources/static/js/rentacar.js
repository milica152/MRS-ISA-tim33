$(document).ready(function() {
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
        $.ajax({
            url: '/RentACar/all',
            data: {},
            success: function(data) {
                if (data != undefined && data.length > 0) {
                    $('#table').dataTable().fnClearTable();
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