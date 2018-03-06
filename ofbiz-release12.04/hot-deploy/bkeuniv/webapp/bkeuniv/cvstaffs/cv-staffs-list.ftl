<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	var modalChangePassword;
	function createContextMenu(id) {
	
	}
				
	function jqPDF(data){
		console.log(data);
	}
	
</script>

<div class="body">
	<div id = "modelChangePassword">
	
	</div>
	
	<#assign columns=[
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivFaculty?j_string,
			"data": "facultyName"
		},
    {
			"name": "CV"?j_string,
			"data": "staffId",
      "render": 'function(value, name, dataColumns, id) {
				return \'<a href="/bkeuniv/control/profile-science-pdf?sections=education-progress&sections=work-progress&sections=publications&sections=recent-5-year-projects&sections=award&sections=scientific-service&sections=projects-applied&sections=patent&sections=phd-defensed&sections=graduate-students&staffId=\'+value+\'" target="_blank">CV PDF</a>\';
			}'
		}
	] />
	
	<#assign fields=[
		"staffId",
		"staffName",
		"staffEmail",
		"genderName",
		"staffGenderId",
		"staffDateOfBirth",
		"staffPhone",
		"departmentName",
		"departmentId",
		"facultyName",
		"facultyId"
		
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		urlUpdate="/bkeuniv/control/update-a-staff" 
		urlAdd="/bkeuniv/control/create-a-staff" 
		urlDelete="/bkeuniv/control/remove-a-staff" 
		keysId=["staffId"] 
		fieldDataResult = "results" 
		titleChange=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleNew=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleDelete=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		jqTitle=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>



<#--  

<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

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
  
<@Loader handleToggle="loader">
	<@IconSpinner/>
</@Loader>

<script>
  	loader.open();
  </script>
<div id="table-list-paper" style="overflow-y: auto; padding: 2em;">
	<table id="list-papers" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>Ho ten</th>
				<th>Bo mon</th>
				<th>Khoa/Vien</th>
				<th>CV</th>
			</tr>
		</thead>
	<tbody>
	<#list resultStaffs.staffs as st>
		<tr>
			<td style="display: none">${st.staffId}</td>
			<td>${st.staffName}</td>
			<td>${st.departmentName}</td>
			<td>${st.facultyName}</td>
			<td><a href="/bkeuniv/control/profile-science-pdf?
			idxEducationProgress=1&idxPublications=2&idxWorkProgress=3
			&idxRecent5YearProjects=4&idxAward=5
			&idxScientificService=6&idxPatent=7&idxProjectsApplied=8
			&idxPhDDefensed=9&idxGraduateStudents=10&staffId=ABC">CV PDF</a></td>
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>
<script>
var obj;

$(document).ready(function() {
  loader.close();
  var oTable = $('#list-papers').dataTable({
    "bJQueryUI": true,
    "sDom": 'l<"H"Rf>t<"F"ip>'
  });
  $(document).contextmenu({
    delegate: ".dataTable td",
    menu: [
      {title: "Delete", cmd: "delete"},
      {title: "Edit", cmd: "edit"}
    ],
    select: function(event, ui) {
        switch(ui.cmd){
            case "delete":
                $(ui.target).parent().remove();
                break;
            case "edit":
				obj = ui;
				var el = ui.target.parent();
				var paperId = el.children()[0].innerHTML;
				alert('edit paper ' + paperId);
			    break;
        }
    },
    beforeOpen: function(event, ui) {
        var $menu = ui.menu,
            $target = ui.target
        ui.menu.zIndex(0);
    }
  });
    
} );
</script>  -->