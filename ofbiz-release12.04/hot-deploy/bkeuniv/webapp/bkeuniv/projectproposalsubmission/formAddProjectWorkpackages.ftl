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
#form-add-workpackage-project {
    margin-top: 60px;
    margin-left: 100px;
}
</style>


<div id="form-add-workpackage-project">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			projectId 
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="researchProjectProposalId" style="width: 100%" type="text" width="1000" value="${projectProposalId}" disabled/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		staffId
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="staffId" style="width: 100%" type="text" width="1000">
		 	<#list resultMembers.members as m>
		 		<option value="${m.staffId}">${m.staffName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		content
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="content" style="width: 100%" type="text" width="1000" value="content"/>
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		nb working days
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="workingdays" style="width: 100%" type="text" width="1000" value="workingdays"/>
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		budget
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="budget" style="width: 100%" type="text" width="1000" value="budget"/>
	</div>
</div>



<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addWorkpackage"/>
</div>


<script>


function addWorkpackage(){
	var researchProjectProposalId = document.getElementById("researchProjectProposalId").value;
	var staffId = document.getElementById("staffId").value;
	var content = document.getElementById("content").value;
	var workingdays = document.getElementById("workingdays").value;
	var budget = document.getElementById("budget").value;
	
	$.ajax({
			url: "/bkeuniv/control/add-a-workpackage-project-proposal",
			type: 'POST',
			data: {
				"researchProjectProposalId": researchProjectProposalId,
				"staffId": staffId,
				"content": content,
				"workingdays": workingdays,
				"budget": budget
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=" + researchProjectProposalId;
				console.log(rs.result);
			}
		})
		

}
</script>