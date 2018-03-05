Route management

<form action="<@ofbizUrl>createARoute</@ofbizUrl>" method="post">
        <table>
        <tr>
        	<td>
        	<label>Date</label>
        	</td>
        	<td>
        	<input type="text" name="date" id="date" value="" style="width:200px"/><br>
        	</td>
        </tr>
        <tr>
        
        	<td> <label>Shipper</label> </td>
        	<td> <select id="shipper" name="shipper" style="width:200px">
        	<#list listShippers as s>
        		<option value="${s.id}">${s.name}</option>
        	</#list>
        	
       		</select>
       		</td><br>
       		
        </tr>
        <tr>
        
        	<td><label>Sequence</label></td>
        	<td>
        		<input type="text" name="seq" id="seq" value="" style="width:200px"/><br>
        	</td>
        </tr>
        <tr>
        	<td>
        	<div class="button-bar">
          	  <input type="submit" value="Them moi route"/>
       		 </div>
       		 </td>
        </tr>
        </table>
</form>



<#if listRoutes?exists>
	<table>
		<tr>
			<td>routeID</td>
			<td>Date</td>
			<td>ShipperID</td>
			<td>ShipperName</td>
		</tr>
		<#list listRoutes as r>
			<tr>
				<td>${r.id}</td>
				<td>${r.date}</td>
				<td>${r.shipperId}</td>
				<td>${r.shipperName}</td>
			<tr>
		</#list>
	</table>
<#else>
</#if>

<a href="export-excel">Export Excel</a><br>
<a href="download-file">DownLoad File</a><br>
<a href="routes-pdf">PDF</a>