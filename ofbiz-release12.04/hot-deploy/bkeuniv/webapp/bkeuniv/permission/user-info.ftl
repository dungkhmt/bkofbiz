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
#form-security-group-function {
    margin-top: 60px;
    margin-left: 100px;
}
</style>


<div id="form-security-group-function">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivStaffId} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="staffId" style="width: 100%" type="text" width="1000" value="${staff.staff.staffId}" disabled/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		${staffManagementUiLabelMap.BkEunivFullName}
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="staffName" style="width: 100%" type="text" width="1000" value="${staff.staff.staffName}"/>
	</div>
	
</div>



<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivEmail} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="email" style="width: 100%" type="text" width="1000" value="${staff.staff.staffEmail}"/>
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivBirthDate} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<#if staff.staff.staffDateOfBirth?exists>
			<input id="birthDate" style="width: 100%" type="text" width="1000" value="${staff.staff.staffDateOfBirth}"/>
		<#else>
			<input id="birthDate" style="width: 100%" type="text" width="1000" value=""/>	
		</#if>
	</div>
	<script type="text/javascript">$(function () {$("#birthDate").datepicker({format: "yyyy-mm-dd"});});</script>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivPhone} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<#if staff.staff.staffDateOfBirth?exists>
			<input id="phone" style="width: 100%" type="text" width="1000" value="${staff.staff.staffPhone}"/>
		<#else>
			<input id="phone" style="width: 100%" type="text" width="1000" value=""/>	
		</#if>
	</div>
</div>


<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivFaculty} 
	</div>

	<div style="display: inline-block;width: 69%;">
		<!--
		<input style="width: 100%" type="text" width="1000" value="${staff.staff.departmentId}"/>
		-->
		<select id="facultyId" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<#list staff.faculties as f>
		 		<#if "${f.facultyId}" = "${staff.selected_faculty_id}">
		 			<option value="${f.facultyId}" selected>${f.facultyName}</option>
		 		<#else>	
		 			<option value="${f.facultyId}">${f.facultyName}</option>
		 		</#if> 
		 	</#list>
		</select> 
	</div>
</div>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivDepartment} 
	</div>


	<div style="display: inline-block;width: 69%;">
		<!--
		<input style="width: 100%" type="text" width="1000" value="${staff.staff.departmentId}"/>
		-->
		<select id="departmentId" style="width: 100%" type="text" width="1000">
		 	<#list staff.departments as d>
		 		<#if "${d.departmentId}" = "${staff.staff.departmentId}">
		 			<option value="${d.departmentId}" selected>${d.departmentName}</option>
		 		<#else>	
		 			<option value="${d.departmentId}">${d.departmentName}</option>
		 		</#if> 
		 	</#list>
		</select> 
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${staffManagementUiLabelMap.BkEunivGender} 
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="genderId" style="width: 100%" type="text" width="1000">
		 	<#list staff.genders as g>
				<#if staff.staff.staffGenderId?exists>
		 			<#if "${g.genderId}" = "${staff.staff.staffGenderId}">
		 				<option value="${g.genderId}" selected>${g.genderName}</option>
		 			<#else>
		 				<option value="${g.genderId}">${g.genderName}</option>	
		 			</#if>	
		 		<#else>	
		 			<option value="${g.genderId}">${g.genderName}</option>
		 		</#if> 
		 	</#list>
		</select> 
	</div>
</div>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateInfo"/>
</div>


<script>

function changeFaculty(){
	var facultyId = document.getElementById("facultyId").value;
	//alert(facultyId);
	$.ajax({
			url: "/bkeuniv/control/get-departments-of-faculty",
			type: 'POST',
			data: {
				"facultyId": facultyId
				
			},
			success: function(rs){
				var department = document.getElementById("departmentId");
				while(department.options.length){
					department.remove(0);
				}
				for(i = 0; i < rs.departments.length; i++){
					var o = document.createElement("option");
					o.value = rs.departments[i].id;
					o.text = rs.departments[i].name;
					department.appendChild(o);
				}
			}
		})
	
}

function updateInfo(){
	var staffId = document.getElementById("staffId").value;
	var departmentId = document.getElementById("departmentId").value;
	var genderId = document.getElementById("genderId").value;
	var email = document.getElementById("email").value;
	var staffName = document.getElementById("staffName").value;
	var phone = document.getElementById("phone").value;
	var birthDate = document.getElementById("birthDate").value;
	
	$.ajax({
			url: "/bkeuniv/control/update-staff-info",
			type: 'POST',
			data: {
				"staffId": staffId,
				"departmentId": departmentId,
				"genderId": genderId,
				"email": email,
				"birthDate": birthDate,
				"staffName": staffName,
				"phone": phone,
			},
			success: function(rs){
				window.location.href="/bkeuniv/control/main";
				console.log(rs.result);
			}
		})
		

}
</script>