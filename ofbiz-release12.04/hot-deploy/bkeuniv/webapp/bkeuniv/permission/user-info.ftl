<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-security-group-function {
    margin-top: 60px;
    margin-left: 100px;
}
</style>

<div id="form-security-group-function">
<table>
	<tr>
		<td>
			${staffManagementUiLabelMap.BkEunivFullName} 
		</td>
		<td>
			${staff.staff.staffName}
		</td>
	</tr>
	<tr>
		<td>
			${staffManagementUiLabelMap.BkEunivStaffId} 
		</td>
		<td>
			${staff.staff.staffId}
		</td>
	</tr>
	
</table>
<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateInfo"/>
</div>
<script>

function updateInfo(){
	$.ajax({
			url: "/bkeuniv/control/store-security-group-functions",
			type: 'POST',
			data: {
				"groupId": groupId,
				"functions": funcs
			},
			success: function(rs){
				window.location.href="/bkeuniv/control/enable-security-group-function";
			}
		})
		
}
</script>