<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resource/bkeuniv/css/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-style {
    margin-top: 20px;
    margin-left: 20px;
    overflow: scroll;
}
</style>

<div id="form-style">
<a href="/bkeuniv/control/detail-jury-proposal?juryId=${juryId}">Quay ve hoi dong</a>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		khoa/vien
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="facultyId" style="width: 100%" type="text" width="1000" onchange="changeFaculty()">
		 	<#list resultFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Bo mon
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="departmentId" style="width: 100%" type="text" width="1000" onchange="changeDepartment()">
		</select> 
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Thanh vien
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="staffId" style="width: 100%" type="text" width="1000">
		</select> 
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Vai tro
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="juryRoleTypeId" style="width: 100%" type="text" width="1000">
		 	<#list resultJuryRoleTypes.juryRoleTypes as jrt>
		 		<option value="${jrt.juryRoleTypeId}">${jrt.juryRoleTypeName}</option>
		 	</#list>
		</select> 
	</div>
</div>


<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addMemberProjectProposalJury"/>

<div>
	<table id="tbl-jury-members">
		<#list resultMembers.members as m>
			<tr>
				<td>${m.staffName}</td>
				<td>${m.juryRoleTypeName}</td>
				<!--
				<#if m.juryName?exists>
					<td>${m.juryName}</td>
				<#else>
					<td></td>
				</#if>
				-->
			</tr>
		</#list>
	</table>
</div>

</div>


<script>
$( document ).ready(function() {
    changeFaculty();
});
function clearSelectBox(tbl){
	var i;
	for(i = tbl.length-1; i >= 0; i--){
		tbl.remove(i);
	}
}


function changeFaculty(){
	var facultyId = document.getElementById("facultyId").value;
	$.ajax({
			url: "/bkeuniv/control/get-departments-of-faculty",
			type: 'POST',
			data: {
				"facultyId": facultyId
			},
			success: function(rs){
				console.log(rs.departments);
				var tbldepartment = document.getElementById("departmentId");
				
				clearSelectBox(tbldepartment);
				for(j = 0; j <= rs.departments.length-1; j++){
					var id = rs.departments[j].id;
					var name = rs.departments[j].name;
					var opt = document.createElement('option');
					opt.value = id;
					opt.innerHTML = name;
					tbldepartment.appendChild(opt);
				}
				changeDepartment();
			}
		})
	
}

function changeDepartment(){
	var departmentId = document.getElementById("departmentId").value;
	//alert(departmentId);
	
	$.ajax({
			url: "/bkeuniv/control/get-staffs-of-department",
			type: 'POST',
			data: {
				"departmentId": departmentId
			},
			success: function(rs){
				console.log(rs.staffs);
				var tblstaff = document.getElementById("staffId");
				
				clearSelectBox(tblstaff);
				for(j = 0; j <= rs.staffs.length-1; j++){
					var id = rs.staffs[j].id;
					var name = rs.staffs[j].name;
					var opt = document.createElement('option');
					opt.value = id;
					opt.innerHTML = name;
					tblstaff.appendChild(opt);
				}
			}
		})
	
}

function addMemberProjectProposalJury(){
	var staffId = document.getElementById("staffId").value;
	var juryRoleTypeId = document.getElementById("juryRoleTypeId").value;
	var juryId = ${juryId};
	
	$.ajax({
			url: "/bkeuniv/control/add-a-member-project-proposal-jury",
			type: 'POST',
			data: {
				"staffId": staffId,
				"juryRoleTypeId": juryRoleTypeId,
				"juryId": juryId
			},
			success: function(rs){
				var tbl = document.getElementById("tbl-jury-members");
				$('#tbl-jury-members tr:last').after('<tr><td>' + rs.staffName + '</td><td>' + rs.juryRoleTypeName + 
				'</td>'
				 //+ '<td>' + juryId + '</td></tr>'
				 );
			}
		})
		

}
</script>