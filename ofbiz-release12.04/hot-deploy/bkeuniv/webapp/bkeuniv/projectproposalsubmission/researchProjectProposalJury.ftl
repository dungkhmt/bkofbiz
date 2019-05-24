<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>

	function createContextMenu(id) {
		$(document).contextmenu({
			delegate: '#'+id+ ' td',
		menu: [
		<#--  {title: "${uiLabelMap.BkEunivEdit}", cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},  -->
		{title: "${uiLabelMap.BkEunivResearchProjectJuryMembers}", cmd: "members", uiIcon: "glyphicon glyphicon-user"},
		{title: "${uiLabelMap.BkEunivResearchProjectJuryAssginReviewer}", cmd: "asignreviewer", uiIcon: "glyphicon glyphicon-list-alt"},
		<#--  {title: "${uiLabelMap.BkEunivRemove}", cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}  -->
		],
		select: function(event, ui) {
			var el = ui.target.parent();
            var data = jqDataTable.table.row( el ).data();
			switch(ui.cmd){
				case "delete":
					jqDelete(data);
					break;
				case "edit":
					jqChange(data);
					break;
				case "members":
					window.location.href="/bkeuniv/control/research-project-jury-members?juryId=" + data.juryId;
					break;
				case "asignreviewer":
					window.location.href="/bkeuniv/control/research-project-jury-assgin-reviewer?juryId=" + data.juryId;
					break;
			}
		},
			beforeOpen: function(event, ui) {
				var $menu = ui.menu,
					$target = ui.target,
					extraData = ui.extraData;
				ui.menu.zIndex(9999);
			}
		});
	}
	
</script> 

<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.JuryName?j_string,
			"data": "juryName"
		},
		{
			"name": uiLabelMap.FacultyName?j_string,
			"data": "facultyName"
		},
		{
			"name": uiLabelMap.ProjectCallName?j_string,
			"data": "projectCallName"
		}
	] />	

	<#assign fields=[
		"juryId",
		"juryName",
		"facultyName",
		"projectCallName"
		
	] />
<#--  
	<#assign columnsChange=[
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"value": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"value": "staffEmail"
		}
	] />

	<#assign columnsNew=[
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivStaffPassword?j_string,
			"value": "password"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivStaffPermissionGroup?j_string,
			"value": "groupId"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"value": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"value": "staffEmail"
		}
	] />
	  -->
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListProjectProposalJuries" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["juryId"]
		<#--  titleChange=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleNew=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleDelete=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		jqTitle=staffManagementUiLabelMap.BkEunivStaffHeaderScreen  -->
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>


<#--  <#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

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

<a href="/bkeuniv/control/form-add-project-proposal-jury">Them moi hoi dong duyet thuyet minh de tai</a>
<div id="table-list" style="overflow-y: auto; padding: 2em;">
	<table id="list" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>Ten hoi dong duyet thuyet minh de tai</th>
				<th>Khoa/vien</th>
				<th>Dot goi de tai</th>
			</tr>
		</thead>
	<tbody>
	<#list resultJuries.projectproposaljuries as J>
		<tr>
			<td style="display: none">${J.juryId}</td>
			<#if J.juryName?exists>
				<td>${J.juryName}</td>
			<#else>
				<td></td>
			</#if>
			<#if J.facultyName?exists>
				<td>${J.facultyName}</td>
			<#else>
				<td></td>
			</#if>
			<#if J.projectCallName?exists>
				<td>${J.projectCallName}</td>
			<#else>
				<td></td>
			</#if>
			
			
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>
<script>
var obj;

$(document).ready(function() {
  loader.close();
  var oTable = $('#list').dataTable({
    "bJQueryUI": true,
    "sDom": 'l<"H"Rf>t<"F"ip>'
  });
  $(document).contextmenu({
    delegate: ".dataTable td",
    menu: [
      {title: "Delete", cmd: "delete"},
      {title: "Edit", cmd: "edit"},
      {title: "Thanh vien hoi dong", cmd: "members"},
      {title: "Phan cong phan bien thuyet minh", cmd: "asignreviewer"},
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
			case "members":
				obj = ui;
				var el = ui.target.parent();
				var juryId = el.children()[0].innerHTML;
				window.location.href="/bkeuniv/control/research-project-jury-members?juryId=" + juryId;
				//alert('member of jury ' + juryId);
			    break;
			case "asignreviewer":
				obj = ui;
				var el = ui.target.parent();
				var juryId = el.children()[0].innerHTML;
				window.location.href="/bkeuniv/control/research-project-jury-assgin-reviewer?juryId=" + juryId;
				//alert('member of jury ' + juryId);
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