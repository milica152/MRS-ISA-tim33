$profileID = document.location.href.substring(document.location.href.lastIndexOf('/') + 1);

$(document).ready(function() {

    setPage();

    //#region Inicijalizacija tabela
    $('#alert').hide();
    var roomsTable = $('#table-rooms').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: false,
        compact: true,
        columns: [
            { data: 'tip_sobe', title: 'Room type' },
            { data: 'cena_nocenja', title: 'Prize per night' },
            { data: 'ocena', title: 'Rating'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="reserveRoom">Reserve</button>'}
            ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    //#endregion

    //#region Preuzimanje podataka za tabele

    $.ajax({
        url: '/Rooms/all',
        data: {},
        success: function(data) {
            if (data != undefined && data.length > 0) {
                $('#table-rooms').dataTable().fnClearTable();
                $('#table-rooms').dataTable().fnAddData(data);
            }
        },
        error: function(xhr, status, error) {
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });

        //#endregion

    $('#AddRoomBtn').on("click",function(e) {
        e.preventDefault();

        var $RoomType = $('#RoomType');
        var $PrizePerNight = $('#prizePerNight');

        var Room = {
            cena_nocenja: $PrizePerNight.val(),
            ocena: 0,
            tip_sobe: $RoomType.val()
        }
        $.ajax({
            type: 'POST',
            url: '/Rooms/addRoom/'+$profileID,
            contentType : 'application/json',
            dataType : "json",
            data : JSON.stringify(Room),
            success: function(data){
                alert("Room added!");
            },
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })
    });


});

function setPage(){
    $.ajax({
        type:'GET',
        url: '/Hotels/specific/'+$profileID,
        data: {},
        success: function(data) {
            document.getElementById("HotelName").innerHTML = data.naziv;
            document.getElementById("descTextArea").value = data.opis;
            document.getElementById("hotelAddress").value = data.adresa;
            document.getElementById("HotelPrize").value = data.cenaOd;
            document.getElementById("Rating").value = data.ocena;

        },
        error: function(xhr, status, error) {
            var errorMessage = xhr.status + ': ' + xhr.statusText
            alert('Error - ' + errorMessage);
        }
    });


}