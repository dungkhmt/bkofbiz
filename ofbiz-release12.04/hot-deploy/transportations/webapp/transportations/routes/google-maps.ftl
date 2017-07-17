<#-- <html> -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle routing</title>
<script type="text/javascript" src="https://maps.google.com/maps/api/js?key=AIzaSyBdyNiBOdg6ikZli6MhG3ivZRw2fKdW-5I&sensor=true&libraries=geometry"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


</head>

<#-- <body> -->

<div id="map" style="width: 1000px; height: 500px"></div>

<script type="text/javascript">

var Options = {
center:  new google.maps.LatLng(21.02422526767355, 105.84773308334343),//(21.008581634411787, 105.84569460449217),
zoom: 16,
mapTypeId: google.maps.MapTypeId.ROADMAP
}

var map = new google.maps.Map(document.getElementById('map'),Options);

google.maps.event.addListener(map,'click',function(event){
	var img = new google.maps.MarkerImage('ccmarker.png');
	var marker = new google.maps.Marker({
		'map':map,
		'position': event.latLng,
		'visible': true,
	});

});
	

function checkAccount(){
	//alert("checkAccount " +$("#accountId").val());
	
	$.ajax({
		type: "post",
		url: "<@ofbizUrl>checkAccount</@ofbizUrl>", 
		data: "accountId="+$("#accountId").val(),
		//data: {"k1":"v1","k2":"v2"},
		success: function (rs) {
			//alert(rs); 
			//if(data.status && data.status == "success"){
				//alert(data.returnMessage);
				console.log(rs);
				//$("#rValue").css("color","red");
				//$("#rValue").text(data.returnMessage);
				var obj = jQuery.parseJSON(rs);
				alert(obj.result);
			//}
		}
	}); 
}
	
</script>

<td>Bill number<input id="accountId" name="accountId" type="text" onblur="checkAccount();"/>
<font id="rValue"></font></td>

<#-- </body> -->
<#-- </html> -->
