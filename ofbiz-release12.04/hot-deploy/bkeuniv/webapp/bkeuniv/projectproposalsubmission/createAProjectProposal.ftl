<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-add {
    margin-top: 20px;
    margin-left: 20px;
    width: 100%
    overflow: scroll
}
</style>

<div id="form-add">

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="projectCallId" type="hidden" value="${parameters.projectCallId}"/>
	</div>
	
</div>

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			${uiLabel.ProjectProposalName}
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="projectProposalName" style="width: 100%" type="text" width="1000" value=""/>
	</div>
	
</div>

<div class="inline-box" style="width: 100%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		${uiLabel.Faculty}
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="facultyId" style="width: 100%" type="text" width="1000">
		 	<#list resultFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>


<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addProjectProposal"/>

</div>

<script>
function addProjectProposal(){
	var facultyId = document.getElementById("facultyId").value;
	var projectCallId =  document.getElementById("projectCallId").value;
	var projectProposalName = document.getElementById("projectProposalName").value;
	//alert('addProposal facultyId = ' + facultyId + ', and projecCallId = ' + projectCallId);
	
		$.ajax({
			url: "/bkeuniv/control/add-a-project-proposal",
			type: 'POST',
			data: {
				"facultyId": facultyId,
				"projectProposalName": projectProposalName,
				"projectCallId": projectCallId
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/my-project-proposal";
				console.log(rs.result);
			}
		})

}

</script>