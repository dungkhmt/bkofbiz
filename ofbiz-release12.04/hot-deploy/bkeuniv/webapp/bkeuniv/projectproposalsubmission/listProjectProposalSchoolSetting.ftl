<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

  <head>

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
Chon khoa/vien
<#assign LF = listFaculties.faculties?size>
<select id="faculty" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	
		 	<#if 1 < LF>
		 		<option value="all" selected>Tat ca</option>
		 	</#if>
		 	
		 	<#list listFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 		 
		 	</#list>
</select>
</div>



<div class="inline-box" style="width: 50%; padding: 10px 0px;">

Chon dot goi de tai
<select id="projectCallId" style="width: 100%" type="text" width="1000" onChange='changeProjectCall()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list resultProjectCalls.projectCalls as pc>
		 		<option value="${pc.projectCallId}">${pc.projectCallName}</option>
		 	</#list>
</select>
</div>
		
<@buttonView text="Xem danh sach" action="listProposals"/>
<@buttonExportExcel text="Xuat Excel" action="exportProposalsExcel"/>

</div>

<script>
function exportProposalsExcel(){
	var facultyId = document.getElementById("faculty").value;
	var projectCallId = document.getElementById("projectCallId").value;
	alert('Xuat excel projectCallId = ' + projectCallId);
	window.location.href = "/bkeuniv/control/export-excel-project-proposals?facultyId=" + facultyId +
				"&projectCallId=" + projectCallId;
}

function listProposals(){
	var facultyId = document.getElementById("faculty").value;
	var projectCallId = document.getElementById("projectCallId").value;
	//alert("facultyId = " + facultyId + ", projectCallId = " + projectCallId);
	
	window.location.href="/bkeuniv/control/list-research-project-proposal?facultyId=" + facultyId
											+ "&projectCallId=" + projectCallId
}
</script>
