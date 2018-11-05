<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

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
			"name": bkEunivUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyName?j_string,
			"data": "intellectualPropertyName"
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyDate?j_string,
			"data": "date",
			"type": "date"
		},
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"data": "description"
		}
	] />
	
	<#assign fields=[
		"staffName",
		"staffId",
		"intellectualPropertyId",
		"intellectualPropertyName",
		"date",
		"description"
	] />
	
	<#assign columnsChange=[
		{
			"name": bkEunivUiLabelMap.BkEunivFullName?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "staffId",
				"name": "staffName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyName?j_string,
			"value": "intellectualPropertyName"
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyDate?j_string,
			"value": "date",
			"type": "date"
		},
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": bkEunivUiLabelMap.BkEunivFullName?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "staffId",
				"name": "staffName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			},
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyName?j_string,
			"value": "intellectualPropertyName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.IntellectualPropertyDate?j_string,
			"value": "date",
			"type": "date",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListIntellectualProperty" 
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
		urlUpdate="/bkeuniv/control/update-intellectual-property" 
		urlAdd="/bkeuniv/control/create-intellectual-property" 
		urlDelete="/bkeuniv/control/delete-intellectual-property" 
		keysId=["intellectualPropertyId"] 
		fieldDataResult = "results" 
		titleChange=bkEunivUiLabelMap.UpdateIntellectualProperty?j_string
		titleNew=bkEunivUiLabelMap.AddNewIntellectualProperty?j_string
		titleDelete=bkEunivUiLabelMap.DeleteIntellectualProperty?j_string
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
