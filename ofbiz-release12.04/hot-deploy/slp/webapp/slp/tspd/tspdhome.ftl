
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">TSPD</h1>
		</div>
	</div>

	<div class="row">
		<form action="tspd/uploadSolution" method="POST" id="tspdsolution" commandName="tspdsolution" enctype="multipart/form-data" role="form" class="form-horizontal">
			<input id="input-solution" path="tspdSolutionFile" name="tspdSolutionFile" type="file" class="file file-loading " style="display:none" />
			<a class="btn btn-primary " id="submit-button-solution" onclick="uploadSolution()" >${slplabel.uploadsolution}</a>
		</form>
		<div class="row">
		<div class="col-lg-3">
			<select id="data-sample">
			</select>
		</div>
		<div class="col-lg-3">
		<a class="btn btn-primary " href="create-sample-data" >Create new Sample Data</a>
		</div>
		</div>
		
		<div id="GoogleMap" style="width:100%;height:500px"></div>
		<form action="tspd-select-module" method="POST" commandName="tspd" role="form" class="form-horizontal">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="form-group">
							<label class="control-label col-lg-3">${slplabel.truckspeed}</label>
							<div class="col-lg-3">
								<input name="truckSpeed" id="truckSpeed" class="form-control" placeholder="${slplabel.truckspeed}" value='50'/>
							</div>
							<label class="control-label col-lg-3">${slplabel.dronespeed}</label>
							<div class="col-lg-3">
								<input name="droneSpeed" id="droneSpeed" class="form-control" placeholder="${slplabel.dronespeed}" value='25'/>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top:10px">
						<div class="form-group">
							<label class="control-label col-lg-3">${slplabel.costtruck}</label>
							<div class="col-lg-3">
								<input name="truckCost" id="truckCost" class="form-control" placeholder="${slplabel.costtruck}" value='50'/>
							</div>
							<label class="control-label col-lg-3">${slplabel.costdrone}</label>
							<div class="col-lg-3">
								<input name="droneCost" id="droneCost" class="form-control" placeholder="${slplabel.costdrone}" value ='2'/>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top:10px">
						<div class="form-group">
							<label class="control-label col-lg-3">${slplabel.delta}</label>
							<div class="col-lg-3">
								<input name="delta" id="delta" class="form-control" placeholder="${slplabel.delta}" value='15'/>
							</div>
							<label class="control-label col-lg-3">${slplabel.droneendurance}</label>
							<div class="col-lg-3">
								<input name="endurance" id="endurance" class="form-control" placeholder="${slplabel.droneendurance}" value ='15'/>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top:10px">
						<input name="listPoints" id="listPoints" type="hidden"/>
						<input name="inputjson" id="inputjson" type="hidden"/>
						<button class="btn btn-primary col-lg-offset-5" onclick="run_algorithm();" type="submit">${slplabel.start}</button>
						<a class="btn btn-primary" onclick="save_file(this);">${slplabel.saveinput}</a>
						<a class="btn btn-primary" onclick="upload_file();">${slplabel.uploadinput}</a>
						<input id="file-tsp-data" class="file file-loading" type="file" style="display:none"/>	
					</div>
				</div>		
			</div>
		</form>

	</div>
</div>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBdyNiBOdg6ikZli6MhG3ivZRw2fKdW-5I&libraries=places&callback=initialize" async defer></script>
<script type="text/javascript">
$(function() {
    
	$.ajax({
		url: "/slp/control/get-data-set",
		type: 'post',
	    dataType: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data) {
			console.log(data);
			data=data.sol;
			console.log(data);
			var $select=$('#data-sample').selectize({
				options: data,
				labelField: 'DS_Name',
				valueField: 'DS_Id',
				onChange: function(value){
					loaddirectionandpoint(value);
				}
			});
		}
	});
});

function loaddirectionandpoint(DS_Id){
	var tmp={
		"DS_Id":DS_Id
	};
	console.log(JSON.stringify(tmp));
	$.ajax({
		url: "/slp/control/get-directions",
		type: 'post',
		data:tmp,
	    datatype: "json",
		success: function (data) {
			console.log(data);
		}
	});
	setNullListMarker();
	$.ajax({
		url: "/slp/control/get-points",
		type: 'post',
		data:tmp,
	    datatype: "json",
		success: function (data) {
			data=data.sol;
			console.log(data);
			listMarker=[];
			for(var i=0; i<data.length; i++){
				var point = data[i];
				var pos = new google.maps.LatLng(point.P_Lat, point.P_Lng);
				var markerPoint = new google.maps.Marker({
					map: map,
					position: pos,
					icon: "https://www.google.com/mapfiles/marker_green.png"
				});
				listMarker.push(markerPoint);
				markerPoint.addListener('click',function(){
					this.setMap(null);
					var indexMarker = listMarker.indexOf(this);
					listMarker.splice(indexMarker,1);
				});
			}
		}
	});
}
function makeJson(){
	var json={};
	json.truckSpeed=$("#truckSpeed").val();
	json.droneSpeed=$("#droneSpeed").val();
	json.truckCost=$("#truckCost").val();
	json.droneCost=$("#droneCost").val();
	json.delta=$("#delta").val();
	json.endurance=$("#endurance").val();
	json.listPoints=$("#listPoints").val();
	console.log(json);
	$("#inputjson").val(json);
}
var map;
var listMarker = [];
function setNullListMarker(){
	for(var i=0;i<listMarker.length;i++)
		listMarker[i].setMap(null);
}
function initialize(){
	var mapProp = {
		center: {lat: 21.033333, lng: 105.849998},
		zoom: 12,
		scrollwheel: false,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	console.log("log 1");
	map=new google.maps.Map(document.getElementById("GoogleMap"),mapProp);
}
function run_algorithm(){
	var listPoints = [];
	for(var i=0; i<listMarker.length; i++){
		listPoints.push({
			"ID" : i,
			"lat" : listMarker[i].getPosition().lat(),
			"lng" : listMarker[i].getPosition().lng()
		});
	}
	$('#listPoints').val(JSON.stringify(listPoints));		
	makeJson();
}
function save_file(el){
	var listPoints = [];
	for(var i=0; i<listMarker.length; i++){
		listPoints.push({
			"ID" : i,
			"lat" : listMarker[i].getPosition().lat(),
			"lng" : listMarker[i].getPosition().lng()
		});
	}
	var save_data = {
			"truckSpeed" : $('#truckSpeed').val(),
			"droneSpeed" : $('#droneSpeed').val(),
			"truckCost" : $('#truckCost').val(),
			"droneCost" : $('#droneCost').val(),
			"delta" : $('#delta').val(),
			"endurance" : $('#endurance').val(),
			"listPoints" : listPoints
	}
	//console.log("save_data = "+save_data);
	//download(JSON.stringify(save_data),'dataTSP.json','text/plain');
	save_data = "text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(save_data));
    // what to return in order to show download window?
    el.setAttribute("href", "data:"+save_data);
    el.setAttribute("download", "dataTSP.json");
	window.location = baseUrl + "/tsp-drone/tspd-solve-home";
}
function upload_file(){
	$('#file-tsp-data').click();
}
function uploadSolution(){
	$('#input-solution').click();
}
(function(){

    function onChange(event) {
        var reader = new FileReader();
        reader.onload = onReaderLoad;
        reader.readAsText(event.target.files[0]);
    }
    function onReaderLoad(event){
        //console.log(event.target.result);
        var obj = JSON.parse(event.target.result);
        show_data(obj);
    }

    function show_data(obj){
        //alert('truckSpeed : ' + truckSpeed + ', droneSpeed : ' + droneSpeed);
    	listMarker = [];
    	initialize();
        $('#truckSpeed').val(obj.truckSpeed);
    	$('#droneSpeed').val(obj.droneSpeed);
    	$('#truckCost').val(obj.truckCost);
    	$('#droneCost').val(obj.droneCost);
    	$('#delta').val(obj.delta);
    	$('#endurance').val(obj.endurance);

    	for(var i=0; i<obj.listPoints.length; i++){
    		var point = obj.listPoints[i];
    		var pos = new google.maps.LatLng(point.lat, point.lng);

    		var markerPoint = new google.maps.Marker({
    			map: map,
    			position: pos,
    			draggable: true,
    			icon: "https://www.google.com/mapfiles/marker_green.png"
    		});

    		listMarker.push(markerPoint);

    		markerPoint.addListener('click',function(){
    			this.setMap(null);
    			var indexMarker = listMarker.indexOf(this);
    			listMarker.splice(indexMarker,1);
    		});
    	}
    }

    document.getElementById('file-tsp-data').addEventListener('change', onChange);
}());
(function(){
    function onChange(event) {
    	console.log("input-solution");
        $("#tspdsolution").submit();
    }
    document.getElementById('input-solution').addEventListener('change', onChange);
}());
/*
function tspd_solve(){
	//console.log("-----------")
	var data = {
		"truckSpeed" : $('#truckSpeed').val(),
		"droneSpeed" : $('#droneSpeed').val(),
		"truckCost" : $('#truckCost').val(),
		"droneCost" : $('#droneCost').val(),
		"delta" : $('#delta').val(),
		"endurance" : $('#endurance').val()
	}
	data["listPoints"] = [];
	for(var i=0; i<listMarker.length; i++){
		data.listPoints.push({
			"ID" : i,
			"lat" : listMarker[i].getPosition().lat(),
			"lng" : listMarker[i].getPosition().lng()
		});
	}
	console.log("data send: "+JSON.stringify(data));
	$.ajax({
		type : 'POST',
		url : baseUrl + '/tsp-drone/tspd-solve',
		data : JSON.stringify(data),
		contentType : 'application/json',
	});
}*/

								</script>
								