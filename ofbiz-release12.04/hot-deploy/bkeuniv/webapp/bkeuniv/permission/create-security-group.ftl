<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

<style>
#table-list-security-group {
    margin-top: 60px;
}


</style>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">




<div id="table-list-security-group">
	<@buttonAdd text="${bkEunivUiLabelMap.BkEunivAdd}" action="addNew"/>
	
	<table id="list-security-groups" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th>Ma nhom chuc nang</th>
				<th>Mo ta</th>
			<tr>
		</thead>
		<tbody>
		<#list result.securityGroups as sg>
			<tr>
				<td>${sg.groupId}</td>
				<td>${sg.description}</td>
			<tr>
		</#list>
		</tbody>
	</table>
</div>

<script>
function addNew() {
	//window.location.href="https://google.com"
	window.location.href="form-create-security-group"
}
$(document).ready(function() {
    $('#list-security-groups').DataTable();
} );
</script>