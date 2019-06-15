$(document).ready(function() {
	var destinations = [];


    $("#AddAirlineForm").submit(function(e){
        e.preventDefault();
        var $AirlineName = $('#airlineName');
        var $AirlineAddress = $('#airlineAddress');
        var $AirlineDesc = $('#airlineDesc');

        var aviokompanija = {
            adresa: $AirlineAddress.val(),
            naziv: $AirlineName.val(),
            opis: $AirlineDesc.val()
        };

        var dataToSend = {
			"airline":aviokompanija,
			"destinations": destinations
		};

        $.ajax({
            type: 'POST',
            url: '/AviokompanijaProfil/addAirline',
            contentType : 'application/json',
            dataType : "json",
            data : JSON.stringify(dataToSend),
            success: function(data){
                sessionStorage.setItem("user", "1");
                sessionStorage.setItem("service", data.naziv);
                document.location.href = "register.html";
            },
            error: function(xhr, status, error) {
                if (xhr.responseText!=='true'){
                    alert(xhr.responseText);
                }
            }
        })

    });

	$('#chooseDest').one("click",function() {
		$.ajax({
			url: '/Destinacija/all',
			data: {},
			success: function(data) {
				if (data !== undefined && data.length > 0) {
					var i;
					for (i = 0; i < data.length; i++) {
						addCheckbox(data[i].grad);
						}
				}
			},
			error: function(xhr, status, error) {
				if (xhr.responseText!=='true'){
					alert(xhr.responseText);
				}
			}
		});
	});



	$("#saveDests").click(function(event){
		event.preventDefault();
		var searchIDs = $("#checkboxes input:checkbox:checked").map(function(){
			getDestination($(this).val());
		}).get();
	});

	function addCheckbox(name) {
		var container = $('#checkboxes');
		var inputs = container.find('input');
		var id = inputs.length+1;
		$('<input />', { type: 'checkbox', id: 'cb'+id, value: name }).appendTo(container);
		$('<label />', { 'for': 'cb'+id, text: name }).appendTo(container);
		$('</br>').appendTo(container);
	}
	function getDestination(name){
		$.ajax({
			type: 'GET',
			url: '/Destinacija/'+name,
			data:{},
			success: [function(data){
				destinations.push(data);
				return data;
			}],
			error: function(xhr, status, error){
				var errorMessage = xhr.status + ': ' + xhr.statusText
				alert('Error - ' + errorMessage);
			}

		});
	}
});