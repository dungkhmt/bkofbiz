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
  

<div style="width:500px;">
<input id="staffId" type="hidden" value="${login.userLoginId}"/>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
${uiLabelMap.SelectFaculty}
<#assign LF = listFaculties.faculties?size>
<select id="faculty" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	
		 	<#if 1 < LF>
		 		<option value="all" selected>Tat ca</option>
		 	</#if>
		 	
		 	<#list listFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
${uiLabelMap.SelectYear}
<select id="academicYear" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listAcademicYears.academicYears as y>
		 		<option value="${y.academicYearId}">${y.academicYearName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">

${uiLabelMap.SelectPaperCategory}
<select id="paperCategory" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listPaperCategory.result as pc>
		 		<option value="${pc.paperCategoryId}">${pc.paperCategoryName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">

${uiLabelMap.SelectStatus}
<select id="paperDeclarationStatus" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listStatus.statuses as s>
		 		<option value="${s.paperDeclarationStatusId}">${s.paperDeclarationStatusName}</option>
		 	</#list>
</select>
</div>
		
<@buttonStore text="${uiLabelMap.View}" action="listPapers"/>
</div>

<script>
function listPapers(){
	var facultyId = document.getElementById("faculty").value;
	var academicYearId = document.getElementById("academicYear").value;
	var paperCategoryId = document.getElementById("paperCategory").value;
	var paperDeclarationStatusId = document.getElementById("paperDeclarationStatus").value;
	
	window.location.href="/bkeuniv/control/research-summary-list-papers?facultyId=" + facultyId
											+ "&academicYearId=" + academicYearId
											+ "&paperCategoryId=" + paperCategoryId
											+ "&paperDeclarationStatusId=" + paperDeclarationStatusId
}
</script>
