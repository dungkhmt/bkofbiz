<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<body>
<div class="body">
<style>
#form-security-group-staff {
    margin-top: 60px;
    margin-left: 100px;
}
</style>

<div id="form-security-group-staff">
	${bkEunivUiLabelMap.SecurityGroup}
				<select id="groupId" name="groupId" onchange='changeGroup(this)'>
					<#list securityGroup.securityGroups as sg>
						<option value="${sg.groupId}">${sg.description}</option>
					</#list>
				</select>		

</div>

<div id="form-security-group-staff">	
	<div id="list-staffs">
	</div>

	

<@buttonStore text="${bkEunivUiLabelMap.BkEunivAdd}" action="updatePermission"/>
</div>
<script>
function changeGroup(group){
	var groupId = group.value;
	$.ajax({
		url: "/bkeuniv/control/get-staffs-of-security-group",
			type: 'POST',
			data: {
				"groupId": groupId				
			},
			success: function(rs){
				var html = '';
				for(i = 0; i < rs.staffs.length; i++){
					html += '<input type=' + '"' + 'checkbox' + '"' + 
					'class=' + '"' + 'staffs_bkeuniv' + '"' + 'name=' + '"' + 'staffs' + '"'
					+ 'value=' + '"' + rs.staffs[i].staffId + '"';// 
					if(rs.staffs[i].checked === 1) html += ' checked';
					html += '/>' + rs.staffs[i].staffName + '<br>';
				}
				document.getElementById("list-staffs").innerHTML = html;
			}
	})
}

function updatePermission(){
	var checked_staffs = Array.from($('.staffs_bkeuniv').map(function(index) {
		return {
			name: this.value,
			checked: this.checked
		}
	})).filter(function(el) {
		return el.checked
	})
	console.log(checked_staffs)

	if(checked_staffs.length === 0) return;
	
	var groupId = document.getElementById("groupId").value;
	var staff_ids = checked_staffs[0].name;
	for(i = 1; i < checked_staffs.length; i++){
		staff_ids = staff_ids + ',' + checked_staffs[i].name;
	}
	console.log(staff_ids);
	
	$.ajax({
			url: "/bkeuniv/control/store-security-group-users",
			type: 'POST',
			data: {
				"groupId": groupId,
				"staffs": staff_ids
			},
			success: function(rs){
				window.location.href="/bkeuniv/control/enable-security-group-user";
			}
		})
		
}
</script>
</div>