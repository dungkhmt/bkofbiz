List of vehicles

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
			<td>${v
			
			
			
			
			.vehicleCode}</td>
			<td>${v.description}</td>
			<td>${v.weight}</td>
		</tr>
	</#list>
</table>