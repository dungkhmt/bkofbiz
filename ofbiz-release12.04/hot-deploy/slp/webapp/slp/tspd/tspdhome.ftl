
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">TSPD</h1>
		</div>
	</div>
	
	<div class="row">
		<form action="tspd/uploadSolution" method="POST" id="tspdsolution" commandName="tspdsolution" enctype="multipart/form-data" role="form" class="form-horizontal">
			<input id="input-solution" path="tspdSolutionFile" name="tspdSolutionFile" type="file" class="file file-loading " style="display:none" />
			<a class="btn btn-primary " id="submit-button-solution" onclick="uploadSolution()" >Upload Solution</a>
		</form>
		<div id="GoogleMap" style="width:100%;height:500px"></div>
		<form action="tspd-solve" method="POST" commandName="tspd" role="form" class="form-horizontal">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="form-group">
						<label class="control-label col-lg-3">Truck speed (km/h)</label>
						<div class="col-lg-3">
							<input path="truckSpeed" name="truckSpeed" id="truckSpeed" class="form-control" placeholder="Truck speed"/>
						</div>
						<label class="control-label col-lg-3">Drone speed (km/h)</label>
						<div class="col-lg-3">
							<input path="droneSpeed" name="droneSpeed" id="droneSpeed" class="form-control" placeholder="Drone speed"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<div class="form-group">
						<label class="control-label col-lg-3">Cost per unit (km) of truck</label>
						<div class="col-lg-3">
							<input path="truckCost" name="truckCost" id="truckCost" class="form-control" placeholder="Cost per unit of truck"/>
						</div>
						<label class="control-label col-lg-3">Cost per unit (km) of drone</label>
						<div class="col-lg-3">
							<input path="droneCost" name="droneCost" id="droneCost" class="form-control" placeholder="Cost per unit of drone"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<div class="form-group">
						<label class="control-label col-lg-3">Wait time (Delta) (minute)</label>
						<div class="col-lg-3">
							<input path="delta" name="delta" id="delta" class="form-control" placeholder="Wait time" />
						</div>
						<label class="control-label col-lg-3">Drone endurance (e) (km)</label>
						<div class="col-lg-3">
							<input path="endurance" name="endurance" id="endurance" class="form-control" placeholder="endurance"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<input path="listPoints" name="listPoints" id="listPoints" type="hidden"/>
					<input path="inputjson" name="inputjson" id="inputjson" type="hidden"/>
					<button class="btn btn-primary col-lg-offset-5" onclick="run_algorithm();" type="submit">Run</button>
					<a class="btn btn-primary" onclick="save_file(this);">Save</a>
					<a class="btn btn-primary" onclick="upload_file();">Upload</a>
					<input id="file-tsp-data" class="file file-loading" type="file" style="display:none"/>	
				</div>
			</div>		
		</div>
		</form>
		
	</div>
</div>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBdyNiBOdg6ikZli6MhG3ivZRw2fKdW-5I&libraries=places&callback=initialize" async defer></script>
<script type="text/javascript">
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
function initialize(){
	var mapProp = {
		center: {lat: 21.033333, lng: 105.849998},
		zoom: 12,
		scrollwheel: false,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	console.log("log 1");
	map=new google.maps.Map(document.getElementById("GoogleMap"),mapProp);
	
	map.addListener('click',function(event){
		var pos = event.latLng;
	
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
	});
	console.log("log 2");
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
