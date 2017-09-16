
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">${slplabel.sampledataheader}</h1>
		</div>
	</div>
	<form action="save-sample-data" method="POST" id="sampledata" class="form-horizontal">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="form-group">
						<label class="control-label col-lg-1">${slplabel.name}</label>
						<div class="col-lg-3">
							<input id="name" name="name" class="form-control" />
						</div>
						<label class="control-label col-lg-1">${slplabel.description}</label>
						<div class="col-lg-3">
							<input id="description" name="description" class="form-control" />
						</div>
						<input name="listPoints" id="listPoints" type="hidden"/>
						<button type="submit" class="btn btn-primary" onclick="pushdata()">${slplabel.saveinput}</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div class="row">
		<div id="GoogleMap" style="width:100%;height:500px"/>
	</div>
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBwAngqkMZ4_13MlWc77yxI6n8uanVWVMM&libraries=places&callback=initialize" async defer >
	</script>
	<script type="text/javascript">
var map;
var listMarker = [];
function initialize(){
	var mapProp = {
		center: {lat: 21.033333, lng: 105.849998},
		zoom: 12,
		scrollwheel: false,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
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
}

function pushdata(){
	var listPoints = [];
	for(var i=0; i<listMarker.length; i++){
		listPoints.push({
			"ID" : i,
			"lat" : listMarker[i].getPosition().lat(),
			"lng" : listMarker[i].getPosition().lng()
		});
	}
	console.log(JSON.stringify(listPoints));
	$('#listPoints').val(JSON.stringify(listPoints));	
	/*$.ajax({
				    url: "/bkeuniv/control/delete-scientificserviceexperience",
				    type: 'post',
				    data: se,
				    datatype:"json",
					success: function(data) {}
		});*/
}
			</script>
			