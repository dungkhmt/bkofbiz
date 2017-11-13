
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-add {
    margin-top: 60px;
}
</style>

<div id="form-add">
<table>
	<tr>
		<td>${bkEunivPermissionUiLabelMap.GroupId}</td>
		<td><input type="text" id="groupid" name="groupid" width=200px></td>
	</tr>
	<tr>
		<td>${bkEunivPermissionUiLabelMap.Description}</td>
		<td><input type="text" id="description" name="description"></td>
	</tr>
	<tr>
		<td><@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="store"/></td>
		<td><@buttonCancel text="${bkEunivUiLabelMap.BkEunivCancel}" action="cancel()"/></td>
		
	</tr>
</table>
</div>

<script>
	function store(){
		var groupId = document.getElementById("groupid").value;
		var des = document.getElementById("description").value;
		alert('store with ' + groupId + ',' + des);
		$.ajax({
			url: "/bkeuniv/control/add-new-security-group",
			type: 'POST',
			data: {
				"groupId": groupId,
				"description": des
			},
			success: function(rs){
				window.location.href="/bkeuniv/control/create-security-group";
			}
		})

	}

$(document).ready(function() {
} );
		
</script>