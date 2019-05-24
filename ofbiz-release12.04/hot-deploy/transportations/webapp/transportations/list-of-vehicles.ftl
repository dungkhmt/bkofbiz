<script rel="javascript" type="text/javascript" href="js/jquery-1.11.3.min.js"></script>

List of vehicles

<form action="<@ofbizUrl>createvehicle</@ofbizUrl>" method="post">

Bien so xe: <input type="text" name="vehicleCode"/>
Mo ta: <input type="text" name="description"/>

<input type="submit" value="Them moi route"/>

</form>

<table>
	<tr>
		<td>STT</td>
		<td>Bien so</td>
		<td> mo ta </td>
		<td>Tai trong </td>
	</tr>
	
	<#assign index=0/>
	<#list lstVehicles.listVehicles as v>
		<#assign index = index + 1>
		<tr>
			<td>${index}</td>
			<td>${v.vehicleCode}</td>
			<td>${v.description}</td>
			<#if v.weight?exists>
				<td>${v.weight}</td>
			</#if>>
			<#assign html='<button onclick="removeV(' + v.vehicleId + ')">Xoa xe</button>'/>
			<td>${html}</td>
		</tr>
	</#list>
</table>


<script>
function removeV(id){
	
	
	$.ajax({
					url: "/transportations/control/remove-a-vehicle",
					type: 'POST',
					data: {
						"vehicleId":  id
					},
					success:function(rs){
						alert('return');
						console.log(rs);
						window.location.href="/transportations/control/listvehicle";
					}
	});
}



</script>