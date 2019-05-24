<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<#assign sourceSelect = [
	{ "name": "${bkEunivUiLabelMap.CommonYes}" ,"value": "YES" },
	{ "name": "${bkEunivUiLabelMap.CommonNo}" ,"value": "NO" }] 
/>

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
			"name": bkEunivUiLabelMap.StudentResearchCallId?j_string,
			"data": "studentResearchCallId"
		},
		{
			"name": bkEunivUiLabelMap.StudentResearchCallName?j_string,
			"data": "studentResearchCallName"
		},
		{
			"name": bkEunivUiLabelMap.BkEunivFromDate?j_string,
			"data": "date",
			"type": "date"
		}
	] />
	
	<#assign fields=[
		"studentResearchCallId",
		"studentResearchCallName",
		"date"
	] />
	
	<#assign columnsChange=[
		{
			"name": bkEunivUiLabelMap.StudentResearchCallName?j_string,
			"value": "studentResearchCallName"
		},
		{
			"name": bkEunivUiLabelMap.BkEunivFromDate?j_string,
			"value": "date",
			"type": "date"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": bkEunivUiLabelMap.StudentResearchCallName?j_string,
			"value": "studentResearchCallName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.BkEunivFromDate?j_string,
			"value": "date",
			"type": "date",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListStudentResearchConference" 
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
		urlUpdate="/bkeuniv/control/update-research-conference" 
		urlAdd="/bkeuniv/control/create-research-conference" 
		urlDelete="/bkeuniv/control/delete-research-conference" 
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
