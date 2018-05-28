<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

  <head>
    <!-- Bootstrap Core CSS -->
    <link href="/resource/bkeuniv/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resource/bkeuniv/bootstrap/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resource/bkeuniv/bootstrap/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resource/bkeuniv/bootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


    <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/south-street/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    
    <script src="https://cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
    
    <meta charset="utf-8" />
    
    <title>DataTables - Context menu integration</title>
  
  </head>


<style>
#form-add {
    margin-top: 20px;
    margin-left: 20px;
    width: 100%;
    overflow: scroll
}
</style>

<#assign prj = resultProjectProposal.projectproposal/>
<#assign note=""/>
<#if prj.note?exists>
	<#assign note=prj.note/>
</#if>
<div id="form-add">
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
			${uiLabel.ProjectProposalName}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="projectProposalName" class="form-control" style="width: 100%" type="text" value="${resultProjectProposal.projectproposal.researchProjectProposalName}"/>
	</div>	
</div>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.MaterialBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="material-budget" class="form-control" style="width: 100%" type="text" value="${resultProjectProposal.projectproposal.materialBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		Ghi chu
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="note" class="form-control" style="width: 100%" type="text" value="${note}"/>
		
	</div>
</div>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateProjectProposal"/>

</div>

<script>
function updateProjectProposal(){
	//var facultyId = document.getElementById("facultyId").value;
	//var projectCallId =  document.getElementById("projectCallId").value;
	var projectProposalName = document.getElementById("projectProposalName").value;
	var note = document.getElementById("note").value;
	
	/*
	var budget = 0;
	if(document.getElementById("budget").checkValidity()){
		budget = document.getElementById("budget").value;
	}else{
		alert("Kinh phi nhap chua dung dinh dang");
		return;
	}
	*/
	var materialbudget = 0;
	if(document.getElementById("material-budget").checkValidity()){
		materialbudget = document.getElementById("material-budget").value;
	}else{
		alert("Kinh phi nhap chua dung dinh dang");
		return;
	}
	
	//alert('addProposal facultyId = ' + facultyId + ', and projecCallId = ' + projectCallId);
	
		$.ajax({
			url: "/bkeuniv/control/update-general-infos-a-project-proposal",
			type: 'POST',
			data: {
				"researchProjectProposalId": ${resultProjectProposal.projectproposal.researchProjectProposalId},
				//"facultyId": facultyId,
				"projectProposalName": projectProposalName,
				//"projectCallId": projectCallId,
				"materialbudget": materialbudget,
				"note": note
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/my-project-proposal";
				console.log(rs.result);
			}
		})

}

</script>