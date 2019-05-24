<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
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
#form-view {
    margin-top: 20px;
    margin-left: 20px;
}
</style>

<div id="form-view">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
${uiLabel.Faculty}
<#assign LF = listFaculties.faculties?size>
<select id="faculty" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	
		 	<#if 1 < LF>
		 		<option value="all" selected>${uiLabel.All}</option>
		 	</#if>
		 	
		 	<#list listFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 		 
		 	</#list>
</select>
</div>



<div class="inline-box" style="width: 50%; padding: 10px 0px;">

${uiLabel.ProjectCallName}
<!--
<select id="projectCallId" style="width: 100%" type="text" width="1000" onChange='changeProjectCall()'>
-->
<select id="projectCallId" class="form-control" onChange='changeProjectCall()'>
		 	<option value="all" selected>${uiLabel.All}</option>
		 	<#list resultProjectCalls.projectCalls as pc>
		 		<option value="${pc.projectCallId}">${pc.projectCallName}</option>
		 	</#list>
</select>
</div>
		
<div class="inline-box" style="width: 50%; padding: 10px 0px;">

${uiLabel.Status}
<select id="projectProposalStatusId" class="form-control">
		 	<option value="all" selected>${uiLabel.All}</option>
		 	<#list resultProjectProposalStatus.projectProposalStatus as pps>
		 		<option value="${pps.statusId}">${pps.statusName}</option>
		 	</#list>
</select>
</div>


<@buttonView text="${uiLabel.ViewList}" action="listProposals"/>
<@buttonExportExcel text="${uiLabel.Excel}" action="exportProposalsExcel"/>

</div>


<script>
function exportProposalsExcel(){
	var facultyId = document.getElementById("faculty").value;
	var projectCallId = document.getElementById("projectCallId").value;
	var projectProposalStatusId = document.getElementById("projectProposalStatusId").value;
	
	//alert('Xuat excel projectCallId = ' + projectCallId);
	window.location.href = "/bkeuniv/control/export-excel-project-proposals?facultyId=" + facultyId +
				"&projectCallId=" + projectCallId +
				"&projectProposalStatusId=" + projectProposalStatusId;
}

function listProposals(){
	var facultyId = document.getElementById("faculty").value;
	var projectCallId = document.getElementById("projectCallId").value;
	var projectProposalStatusId = document.getElementById("projectProposalStatusId").value;
	//alert("facultyId = " + facultyId + ", projectCallId = " + projectCallId);
	
	window.location.href="/bkeuniv/control/list-research-project-proposal?facultyId=" + facultyId
											+ "&projectCallId=" + projectCallId +
											"&projectProposalStatusId=" + projectProposalStatusId;
}
</script>
