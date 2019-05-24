<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<#assign educationSelect = [] >

<#if listEducationLevel?has_content>
	<#list listEducationLevel as el>
		<#if el?has_content>
            <#assign op = { "name": "${el.educationLevelName}" ,"value": "${el.educationLevelId}" } />
			<#assign educationSelect = educationSelect + [op] />
		</#if>
	</#list>
</#if>

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
							console.log(data)
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
			"name": bkEunivUiLabelMap.ThesisSubjectId?j_string,
			"data": "thesisSubjectPhDMasterId"
		},
		{
			"name": bkEunivUiLabelMap.ThesisSubjectName?j_string,
			"data": "thesisSubjectPhDMasterName"
		},
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"data": "educationLevelName"
		},
		{
			"name": commonUiLabels.CommonDescription?j_string,
			"data": "description"
		}
	] />
	
	<#assign fields=[
		"thesisSubjectPhDMasterId",
		"thesisSubjectPhDMasterName",
		"educationLevelName",
		"educationLevelId",
		"staffId",
		"description"
	] />
	
	<#assign columnsChange=[
		{
			"name": bkEunivUiLabelMap.ThesisSubjectName?j_string,
			"value": "thesisSubjectPhDMasterName"
		},
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"value": "educationLevelId",
			"type": "select",
			"option": {
				"source": educationSelect,
				"maxItem": 1
			}
		},
		{
			"name": commonUiLabels.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": bkEunivUiLabelMap.ThesisSubjectName?j_string,
			"value": "thesisSubjectPhDMasterName"
		},
		{
			"name": bkEunivUiLabelMap.EducationLevel?j_string,
			"value": "educationLevelId",
			"type": "select",
			"option": {
				"source": educationSelect,
				"maxItem": 1
			}
		},
		{
			"name": commonUiLabels.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListThesisSubject" 
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
		urlUpdate="/bkeuniv/control/update-cv-thesis-subject-for-student" 
		urlAdd="/bkeuniv/control/create-cv-thesis-subject-for-student" 
		urlDelete="/bkeuniv/control/delete-cv-thesis-subject-for-student" 
		keysId=["thesisSubjectPhDMasterId"] 
		fieldDataResult = "results" 
		titleChange=bkEunivUiLabelMap.UpdateThesisSubject?j_string
		titleNew=bkEunivUiLabelMap.AddThesisSubject?j_string
		titleDelete=bkEunivUiLabelMap.DeleteThesisSubject?j_string
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>

