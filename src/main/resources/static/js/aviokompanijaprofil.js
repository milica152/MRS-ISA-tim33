$profileID = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
$profileID0 = window.location.href.lastIndexOf('/');
$profileID1 = window.location.href.substring(0,$profileID0);
$profileID2 = $profileID1.substring($profileID1.lastIndexOf('/') + 1);

var chartLabels = [];
var chartData = [];
var chartLabel = "";
var rate = "";
var clicked_button_id = "";
var reserved_seats = [];
var ids= [];

$(document).ready(function() {

        var ctxL = document.getElementById("lineChart").getContext('2d');  //2d
        var gradientFill = ctxL.createLinearGradient(0, 0, 0, 290);  //
        gradientFill.addColorStop(0, "rgba(173, 53, 186, 1)");    //
        gradientFill.addColorStop(1, "rgba(173, 53, 186, 0.1)");   //
        var myLineChart = new Chart(ctxL, {
            type: 'line',
            data: {
                labels: chartLabels,
                datasets: [
                    {
                        label: chartLabel,
                        data: chartData,
                        backgroundColor: gradientFill,
                        borderColor: [
                            '#AD35BA'
                        ],
                        borderWidth: 2,
                        pointBorderColor: "#fff",
                        pointBackgroundColor: "rgba(173, 53, 186, 0.1)"
                    }
                ]
            },
            options: {
                responsive: true
            }
        });

    $(document).on('click', '#backBtn', function(e){
        e.preventDefault();
        window.location = "/Aviocompany";
    });

    $(document).on('click', '#rateBtn', function (e) {
        e.preventDefault();
        document.getElementById('chart').style.display = 'none';
        document.getElementById('date').style.display = 'none';
        document.getElementById('income').style.display = 'none';
        document.getElementById('rate').style.display = 'contents';
        document.getElementById('income_dates').style.display = 'none';


        $('#rate_value').html("Rate: " + rate);
    });

    $(document).on('click', '#flightratesBtn', function (e) {
        e.preventDefault();

        $.ajax({
            type: 'GET',
            url: '/Flight/fromAviocompany/' + $profileID,
            data: {},
            success: function (data) {
                if (data !== undefined && data.length > 0) {
                    chartLabels=[];
                    chartData=[];
                    chartLabel = "Flight rates";
                    for (var flight in data){
                        chartLabels.push(data[flight].sifra);
                        chartData.push(data[flight].ocena);
                    }
                    myLineChart.data.datasets[0].label = chartLabel;
                    myLineChart.data.datasets[0].data = chartData;
                    myLineChart.data.labels = chartLabels;
                    myLineChart.update();
                    document.getElementById('rate').style.display = 'none';
                    document.getElementById('income').style.display = 'none';
                    document.getElementById('date').style.display = 'none';
                    document.getElementById('income_dates').style.display = 'none';

                    $('#chart').show();
                }


            }
        });



    });

    $(document).on('click', '#dailyBtn', function (e) {
        e.preventDefault();
        document.getElementById('date').style.display = 'contents';
        document.getElementById('rate').style.display = 'none';
        document.getElementById('income').style.display = 'none';
        document.getElementById('chart').style.display = 'none';
        document.getElementById('income_dates').style.display = 'none';


    });

    function getWeekDay(date){
        var weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        //Use the getDay() method to get the day.
        var day = date.getDay();
        return weekdays[day];
    }

    $(document).on('click', '#weeklyBtn', function (e) {
        e.preventDefault();
        document.getElementById('date').style.display = 'none';
        document.getElementById('rate').style.display = 'none';
        document.getElementById('income').style.display = 'none';
        document.getElementById('chart').style.display = 'none';
        document.getElementById('income_dates').style.display = 'none';


        chartLabel = "Number of sold tickets";
        chartLabels=[];
        chartData = [];

        //get all the days to show
        var step;
        for (step = 6; step >= 0; step--) {
            var current = new Date();  //todays date
            current.setDate(current.getDate() - step);
            var weekDay = getWeekDay(current);
            chartLabels.push(weekDay.substr(0,3));
        }

        $.ajax({
            type: 'GET',
            url: '/Flight/weeklyReport/' + $profileID,
            data: {},
            success:[function (data) {
                for (var oneData in data){
                    chartData.push(data[oneData]);
                }
                myLineChart.data.datasets[0].label = chartLabel;
                myLineChart.data.datasets[0].data = chartData;
                myLineChart.data.labels = chartLabels;
                myLineChart.update();
                $('#chart').show();
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
    });

    });


    function daysInMonth (month, year) {
        return new Date(year, month, 0).getDate();
    }

    $(document).on('click', '#monthlyBtn', function (e) {
        e.preventDefault();
        document.getElementById('date').style.display = 'none';
        document.getElementById('rate').style.display = 'none';
        document.getElementById('income').style.display = 'none';
        document.getElementById('chart').style.display = 'none';
        document.getElementById('income_dates').style.display = 'none';

        chartLabel = "Number of sold tickets";
        chartLabels=[];
        chartData = [];
        var today = new Date();
        var variableDay = new Date();
        var step;


        for(step = daysInMonth(today.getMonth(), today.getFullYear()) - 1; step >= 0; step--){
            var dateOffset = (24*60*60*1000) * step; //5 days
            variableDay.setTime(today.getTime() - dateOffset);
            var ispis = variableDay.getDate().toString() + "." +( variableDay.getMonth()+1).toString() + ".";
            chartLabels.push(ispis);
        }

        $.ajax({
            type: 'GET',
            url: '/Flight/monthlyReport/' + $profileID,
            data: {},
            success:[function (data) {
                for (var oneData in data){
                    chartData.push(data[oneData]);
                }
                myLineChart.data.datasets[0].label = chartLabel;
                myLineChart.data.datasets[0].data = chartData;
                myLineChart.data.labels = chartLabels;
                myLineChart.update();
                $('#chart').show();
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        });

    });

    $(document).on('click', '#incomeBtn', function (e) {
        e.preventDefault();
        document.getElementById('date').style.display = 'none';
        document.getElementById('rate').style.display = 'none';
        document.getElementById('chart').style.display = 'none';
        document.getElementById('income_dates').style.display = 'contents';

    });

    $(document).on('click', '#showBtn', function (e) {
        e.preventDefault();
        var date_from = $('#date_from_value').val();
        var date_to = $('#date_to_value').val();
        if(date_from === null || date_from === "" ||date_to === null || date_to === "" ){
            alert('You must fill all fields!');
            return false;
        }
        var forSending = date_from + " " + date_to;
        $.ajax({
            type: 'POST',
            url: '/Flight/incomeReport/' + $profileID,
            dataType : "text",
            data: forSending,
            success: [function (data) {
                if (data !== undefined && data.length > 0) {
                    $('#income_value').html("Income: " + data.toString());
                    document.getElementById('income').style.display = 'contents';
                }


            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        });
    });

    $( "#datum_poletanja").datepicker({
        dateFormat:"dd-mm-yy",
        minDate: '0d',
        onSelect: function(e) {
            $('.datum_sletanja').datepicker('option', 'minDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('.datum_poletanja').datepicker('setDate', null);
            $('.datum_sletanja').datepicker('option', 'minDate', '0d');
        }
    });

    $( "#date_from_value").datepicker({
        dateFormat:"dd.mm.yy",
        onSelect: function(e) {
            $('#date_to_value').datepicker('option', 'minDate', e);
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#date_from_value').datepicker('setDate', null);
        }
    });

    $( "#date_to_value" ).datepicker({
        dateFormat:"dd.mm.yy",
        useCurrent: false,
        onSelect: function(e) {
            $('#date_from_value').datepicker('option', 'maxDate', e);    //postavlja se minimalni datum za polazak
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#date_to_value').datepicker('setDate', null);
            $('#date_from_value').datepicker('option', 'maxDate', null);
        }
    });

    $( "#datum_sletanja" ).datepicker({
        dateFormat:"dd-mm-yy",
        useCurrent: false,
        minDate: '0d',
        onSelect: function(e) {
            $('.datum_poletanja').datepicker('option', 'maxDate', e);    //postavlja se minimalni datum za polazak
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('.datum_sletanja').datepicker('setDate', null);
            $('.datum_poletanja').datepicker('option', 'maxDate', null);
        }
    });

    $( "#date_value" ).datepicker({
        dateFormat:"dd-mm-yy",
        useCurrent: false,
        onSelect: function(e) {
            //e.preventDefault();
            chartLabel = "Number of sold tickets";
            chartLabels=[];
            chartLabels = ["00:00-02:00", "02:00-04:00", "04:00-06:00", "06:00-08:00", "08:00-10:00", "10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00", "20:00-22:00", "22:00-00:00"];
            chartData = [];
            var date = $('#date_value').val();

            $.ajax({
                method: 'POST',
                url:'/Flight/dailyReport/' + $profileID,
                data: date,
                success: [function (data) {

                    for (var oneData in data){
                        chartData.push(data[oneData]);
                    }
                    myLineChart.data.datasets[0].label = chartLabel;
                    myLineChart.data.datasets[0].data = chartData;
                    myLineChart.data.labels = chartLabels;
                    myLineChart.update();
                    $('#chart').show();
                }],
                error: function(xhr, status, error) {
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            });
        }
    }).keyup(function(e) {
        if(e.keyCode === 8 || e.keyCode === 46) {
            $('#date_value').datepicker('setDate', null);
        }
    });

    $(document).on('click', '#reportsBtn', function (e) {
        e.preventDefault();
        document.getElementById('rate').style.display = 'none';
        document.getElementById('income').style.display = 'none';
        document.getElementById('date').style.display = 'none';
        document.getElementById("date_value").value = "";
        document.getElementById('income_dates').style.display = 'none';
        document.getElementById("date_value").value = "";
        document.getElementById("date_from_value").value = "";
        document.getElementById("date_to_value").value = "";

        $('#chart').show();
    });

    $.ajax({
        url: '/LP/all',
        data: {},
        success: function(data) {
            for(var lp in data){
                var opcija = new Option(data[lp].nazivAerodroma + ", " + data[lp].grad + ", " + data[lp].drzava);
                document.getElementById('polazni_aerodrom_id').add(opcija);
            }
            for(var lp in data){
                var opcija = new Option(data[lp].nazivAerodroma + ", " + data[lp].grad + ", " + data[lp].drzava);
                document.getElementById('odredisni_aerodrom_id').add(opcija);
            }

        }
    });

    var flightsTable = $('#table-flights').DataTable({
        data: undefined,
        searching: false,
        lengthChange: false,
        paging: false,
        info: false,
        ordering: true,
        compact: true,
        columns: [
            { data: 'id', title: 'Id' },

            { data: 'vremePolaska', title: 'Polazak' },
            { data: 'vremeDolaska', title: 'Dolazak' },
            { data: 'cena', title: 'Cena'},
            { data: 'ocena', title: 'Ocena'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="reportsModal" id="ReserveBtn">Reserve</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="EditBtn">Edit</button>'},
            { data: null, defaultContent: '<button type="button" class="btn btn-primary" id="RemoveBtn">Remove</button>'}
        ],
        columnDefs: [
            { className: "align-middle", targets: "_all" }
        ]
    });

    function seatFunction(button){
        clicked_button_id = button.id;
        document.getElementById('nameInput').value = "";
        document.getElementById('surnameInput').value = "";
        document.getElementById('passport').value = "";
        document.getElementById('reservationInfo').style.display = 'contents';

    }

    $(document).on('click', '#ReserveBtn', function (e) {
        e.preventDefault();
        $("#reservationModal").modal();

        var data = flightsTable.row($(this).parents('tr')).data();
        var fID = data.id;

            $.ajax({
                type:'GET',
                url:'/Seats/fromPlane/' + fID ,
                data:{},
                success:[function(data){
                    var row;
                    var column;
                    var hidden_counter = 0;
                    var rows = data.length;
                    var columns = data[0].length;

                    if(!(document.getElementById("seats").childNodes.length === rows+1)){

                        for(row = 1; row<=rows;row++){

                        var column_counter= 0;
                        var row_elem = document.createElement("div");
                        row_elem.setAttribute("id", row.toString());
                        row_elem.setAttribute("class", "row row1");

                        for(column = 1; column <= columns; column ++){


                            if(data[row-1][column-1]===null){
                                hidden_counter++;
                                var hidden_btn = document.createElement("button");
                                hidden_btn.setAttribute("id", hidden_counter.toString());
                                hidden_btn.style.visibility = 'hidden';
                                row_elem.appendChild(hidden_btn);

                            }else{
                                column_counter++;

                                if(data[row-1][column-1].reserved){                       // POGLEDAJ JE L SA OVOM CRTICOM ILI BEZ
                                    var btn_r = document.createElement("button");
                                    var id_r = row.toString() + "-" + column_counter.toString();
                                    btn_r.setAttribute("id", id_r);
                                    btn_r.setAttribute("class", "reservedBtn");
                                    btn_r.disabled = true;
                                    row_elem.appendChild(btn_r);

                                }else{
                                    var btn = document.createElement("button");
                                    var id = row.toString() + "-" + column_counter.toString();
                                    btn.setAttribute("id", id);
                                    btn.setAttribute("class", "button5");
                                    btn.onclick= function() {seatFunction(this);};
                                    row_elem.appendChild(btn);

                                }
                            }
                        }
                        document.getElementById("seats").appendChild(row_elem);

                    }
                    }

                }],
                error:function(xhr, status, error) {
                    if (xhr.responseText!=='true'){
                        alert(xhr.responseText);
                    }
                }
            });

    });


    //$(document).on("click", ".button5", function (e) {

    //});

    $(document).on("click" , "#enterBtn", function(e){
        e.preventDefault();

        var $name = $('#nameInput').val();
        var $surname = $('#surnameInput').val();
        var $passport = $('#passport').val();

        if($name === null || $name===""){
            alert('Enter name!');
            return;
        }
        else if($surname === null || $surname===""){
            alert('Enter surname!');
            return;
        }
        else if($passport === null || $passport===""){
            alert('Enter passport number!');
            return;
        }

        var writing = "Name: " + $name + "\nSurname: " + $surname + "\nPassport: " + $passport;
        document.getElementById(clicked_button_id).title = writing;
        document.getElementById(clicked_button_id).style.backgroundColor = "yellow";
        document.getElementById('reservationInfo').style.display = 'none';

    });

    $.ajax({
        type: 'GET',
        url: '/Flight/fromAviocompany/' + $profileID,
        data: {},
        success: function (data) {
            if (data !== undefined && data.length > 0) {
                $('#table-flights').dataTable().fnClearTable();
                $('#table-flights').dataTable().fnAddData(data);
                chartLabels=[];
                chartData=[];
                chartLabel = "Flight rates";
                for (var flight in data){
                    chartLabels.push(data[flight].sifra);
                    chartData.push(data[flight].ocena);
                }
                myLineChart.data.datasets[0].label = chartLabel;
                myLineChart.data.datasets[0].data = chartData;
                myLineChart.data.labels = chartLabels;
                myLineChart.update();
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
            rate = data.ocena;
        }
    });


    $(document).on('submit', '#addForm', function(e){
        var $cena = $('#price').val();
        var $klasa = $('#class').val();
        var $vreme_poletanja = $('#vreme_poletanja').val();
        var $vreme_sletanja = $('#vreme_sletanja').val();
        var $datum_poletanja = $('#datum_poletanja').val();
        var $datum_sletanja = $('#datum_sletanja').val();
        var $odredisni_aerodrom = $('#odredisni_aerodrom_id').val();
        var $polazni_aerodrom = $('#polazni_aerodrom_id').val();
        var $sifra = $('#code').val();

        if($vreme_sletanja === null ||$vreme_sletanja === "" || $datum_sletanja=== null || $datum_sletanja==="" || $sifra === ""){
            alert("You must fill all fields!");
            return  false;
        }

        if($cena === null || $cena === "" || $klasa===null  || $klasa === ""  || $vreme_poletanja===null || $vreme_poletanja === "" ||  $datum_poletanja===null  ||  $datum_poletanja==="" || $odredisni_aerodrom===null  || $odredisni_aerodrom==="" || $polazni_aerodrom===null || $polazni_aerodrom===""){
            alert("You must fill all fields!");
            return false;
        }

        var listaDep = $polazni_aerodrom.split(', ');
        $polazni_aerodrom = listaDep[0];


        var listaArr = $odredisni_aerodrom.split(', ');
        $odredisni_aerodrom = listaArr[0];
        e.preventDefault();

        var flight = JSON.stringify({
            "sifra" : $sifra,
            "cena": $cena,
            "vreme_poletanja": $vreme_poletanja,
            "vreme_sletanja": $vreme_sletanja,
            "aviokompanija_id": $profileID,
            "odredisni_aerodrom_id": $odredisni_aerodrom,
            "polazni_aerodrom_id": $polazni_aerodrom,
            "datum_poletanja": $datum_poletanja,
            "datum_sletanja": $datum_sletanja,
            "klasa" : $klasa
        });


        $.ajax({
            type: 'POST',
            url: '/Aviocompany/' + $profileID2 + '/addFlight',
            contentType : 'application/json',
            dataType : "text",
            data : flight,
            success: [function(){
                $('#addFlightModal').modal('hide');
                popuniTabelu();
                //window.location = "/Aviocompany/" + $profileID2;
            }],
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })


    });


});

function popuniTabelu() {
    $.ajax({
        type: 'GET',
        url: '/Flight/fromAviocompany/' + $profileID,
        data: {},
        success: function (data) {
            if (data !== undefined && data.length > 0) {
                $('#table-flights').dataTable().fnClearTable();
                $('#table-flights').dataTable().fnAddData(data);
            }
        }
    });

}

