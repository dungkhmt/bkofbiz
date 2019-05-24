<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<#assign sourceSelect = [
	{ "name": "${commonUiLabels.CommonYes}" ,"value": "YES" },
	{ "name": "${commonUiLabels.CommonNo}" ,"value": "NO" }] 
/>

<#assign sourceEducationLevels = []>
<#list resultEducationLevels.educationLevels as el>
	<#assign op = { "name": el.educationLevelName?j_string ,"value": el.educationLevelId?j_string } />
						<#assign sourceEducationLevels = sourceEducationLevels + [op] />
</#list>
<body>

<script>
	function createContextMenu(id) {
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
				menu: [
			  		{title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  		{title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
				],
				select: function(event, ui) {
					var el = ui.target.parent();
					var data = jqDataTable.table.row( el ).data();
					switch(ui.cmd){
						case "edit":
							jqChange(data)
							break;
						case "delete":
							jqDelete(data);
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
			"name": bkEunivUiLabelMap.PhDStudentName?j_string,
			"data": "studentName"
		},
		{
			"name": bkEunivUiLabelMap.PhDTheSisName?j_string,
			"data": "thesisName"
		},
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"data": "educationLevelName"
		},
		{
			"name": bkEunivUiLabelMap.PhDCoSupervision?j_string,
			"data": "coSupervionName"
		},
		
		{
			"name": bkEunivUiLabelMap.EducationInstitution?j_string,
			"data": "educationInstitution"
		},
		
		{
			"name": bkEunivUiLabelMap.PhDGraduateYear?j_string,
			"data": "graduateYear",
			"pattern": "[1-9]([0-9]{0,4})",
			"title": "So nguyen duong",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />
	
	<#assign fields=[
		"phDSupervisionId",
		"staffId",
		"studentName",
		"thesisName",
		"coSupervion",
		"graduateYear",
		"staffName",
		"educationLevelName",
		"educationLevelId",
		"educationInstitution",
		"coSupervionName"
	] />
	
	<#assign columnsChange=[
		{
			"name": bkEunivUiLabelMap.PhDStudentName?j_string,
			"value": "studentName"
		},
		{
			"name": bkEunivUiLabelMap.PhDTheSisName?j_string,
			"value": "thesisName"
		},
		
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"value": "educationLevelId",
			"type": "select",
			"option": {
				"source": sourceEducationLevels,
				"maxItem": 1
			}
		},
		
		{
			"name": bkEunivUiLabelMap.PhDCoSupervision?j_string,
			"value": "coSupervion",
			"type": "select",
			"option": {
				"source": sourceSelect,
				"maxItem": 1
			}
		},
		
		{
			"name": bkEunivUiLabelMap.EducationInstitution?j_string,
			"value": "educationInstitution"
		},
		{
			"name": bkEunivUiLabelMap.PhDGraduateYear?j_string,
			"value": "graduateYear",
			"pattern": "[1-9]([0-9]{0,4})",
			"title": "So nguyen duong",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": bkEunivUiLabelMap.PhDStudentName?j_string,
			"value": "studentName"
		},
		{
			"name": bkEunivUiLabelMap.PhDTheSisName?j_string,
			"value": "thesisName"
		},
		
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"value": "educationLevelId",
			"type": "select",
			"option": {
				"source": sourceEducationLevels,
				"maxItem": 1
			}
		},
		{
			"name": bkEunivUiLabelMap.PhDCoSupervision?j_string,
			"value": "coSupervion",
			"type": "select",
			"option": {
				"source": sourceSelect,
				"maxItem": 1
			}
		},
		{
			"name": bkEunivUiLabelMap.EducationInstitution?j_string,
			"value": "educationInstitution"
		},
		{
			"name": bkEunivUiLabelMap.PhDGraduateYear?j_string,
			"value": "graduateYear",
			"pattern": "[1-9]([0-9]{0,4})",
			"title": "So nguyen duong",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListPhdStudentSupervison" 
		optionData={
			"data": {
				"pagesize": "-1"?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-phd-student-supervision" 
		urlAdd="/bkeuniv/control/create-phd-student-supervision" 
		urlDelete="/bkeuniv/control/delete-phd-student-supervision" 
		keysId=["phDSupervisionId"] 
		fieldDataResult = "results" 
		titleChange=bkEunivUiLabelMap.UpdatePhDSupervision?j_string
		titleNew=bkEunivUiLabelMap.AddNewPhDSupervision?j_string
		titleDelete=bkEunivUiLabelMap.DeletePhDSupervision?j_string
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
