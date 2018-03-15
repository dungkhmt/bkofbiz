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
  

<div style="width:500px;">
<input id="staffId" type="hidden" value="${login.userLoginId}"/>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
Chon khoa/vien
<select id="faculty" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
Chon nam 
<select id="academicYear" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listAcademicYears.academicYears as y>
		 		<option value="${y.academicYearId}">${y.academicYearName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">

Chon loai hinh bai bao
<select id="paperCategory" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listPaperCategory.result as pc>
		 		<option value="${pc.paperCategoryId}">${pc.paperCategoryName}</option>
		 		 
		 	</#list>
</select>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">

Chon trang thai
<select id="paperDeclarationStatus" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
		 	<option value="all" selected>Tat ca</option>
		 	<#list listStatus.statuses as s>
		 		<option value="${s.paperDeclarationStatusId}">${s.paperDeclarationStatusName}</option>
		 	</#list>
</select>
</div>
		
<@buttonStore text="Hien thi" action="listPapers"/>
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