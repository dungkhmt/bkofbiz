<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-security-group-function {
    margin-top: 60px;
    margin-left: 100px;
}
</style>

<div id="form-security-group-function">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		${staffManagementUiLabelMap.BkEunivFullName}
	</div>
	<div style="display: inline-block;width: 69%;">
		<input style="width: 100%" type="text" width="1000" value="${staff.staff.staffName}"/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivStaffId} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<input style="width: 100%" type="text" width="1000" value="${staff.staff.staffId}"/>
	</div>
	
</div>

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