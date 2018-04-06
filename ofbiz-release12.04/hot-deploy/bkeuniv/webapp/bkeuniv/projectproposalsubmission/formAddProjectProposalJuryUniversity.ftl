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
#form-add-project-call {
    margin-top: 60px;
    margin-left: 100px;
}
</style>


<div id="form-add-project-call">

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			Ten Hoi Dong
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="juryName" style="width: 100%" type="text" width="1000" value=""/>
	</div>
	
</div>

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		dot goi de tai
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="projectCallId" style="width: 100%" type="text" width="1000">
		 	<#list resultProjectCalls.projectCalls as pc>
		 		<option value="${pc.projectCallId}">${pc.projectCallName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Khoa/vien
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="facultyId" style="width: 100%" type="text" width="1000">
		 	<#list resultFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 	</#list>
		</select>
		
	</div>
</div>



<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addProjectProposalJury"/>
</div>


<script>


function addProjectProposalJury(){
	var facultyId = document.getElementById("facultyId").value;
	var juryName = document.getElementById("juryName").value;
	var projectCallId = document.getElementById("projectCallId").value;
	
	$.ajax({
			url: "/bkeuniv/control/add-a-project-proposal-jury",
			type: 'POST',
			data: {
				"facultyId": facultyId,
				"juryName": juryName,
				"projectCallId": projectCallId
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/research-project-jury-university";
				console.log(rs.result);
			}
		})
}
</script>