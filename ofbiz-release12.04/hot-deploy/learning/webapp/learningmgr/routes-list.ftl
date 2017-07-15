Route management

<form action="<@ofbizUrl>createARoute</@ofbizUrl>" method="post">
        <label>Date</label>	<input type="text" name="date" id="date" value=""/><br>
        <label>Shipper</label><select id="shipper" name="shipper">
        	<#list listShippers as s>
        		<option value="${s.id}">${s.name}</option>
        	</#list>
        </select><br>
        <label>Sequence</label><input type="text" name="seq" id="seq" value=""/><br>
        <div class="button-bar">
            <input type="submit" value="${uiLearningMap.CreateARoute}"/>
        </div>
</form>

<#if listRoutes?exists>
	<table>
		<tr>
			<td>routeID</td>
			<td>Date</td>
			<td>ShipperID</td>
		</tr>
		<#list listRoutes as r>
			<tr>
				<td>${r.id}</td>
				<td>${r.date}</td>
				<td>${r.shipperId}</td>
			<tr>
		</#list>
	</table>
<#else>
</#if>