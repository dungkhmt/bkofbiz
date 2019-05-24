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
			"name": bkEunivUiLabelMap.BkEunivRecentResearchDirection?j_string,
			"data": "keywords"
		},
		{
			"name": bkEunivUiLabelMap.BkYearOfRecentResearchDirection?j_string,
			"data": "startYear"
		}
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"data": "description"
		}
	] />
	
	<#assign fields=[
		"recentResearchDirectionId",
		"staffId",
		"keywords",
		"startYear",
		"startDate",
		"description"
	] />
	
	<#assign columnsChange=[
		{
			"name": bkEunivUiLabelMap.BkEunivRecentResearchDirection?j_string,
			"value": "keywords"
		},
		{
			"name": bkEunivUiLabelMap.BkYearOfRecentResearchDirection?j_string,
			"value": "startYear"
		},
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": bkEunivUiLabelMap.BkEunivRecentResearchDirection?j_string,
			"value": "keywords"
		},
		{
			"name": bkEunivUiLabelMap.BkYearOfRecentResearchDirection?j_string,
			"value": "startYear"
		},
		{
			"name": bkEunivUiLabelMap.CommonDescription?j_string,
			"value": "description"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListRecentResearchDirection" 
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
		urlUpdate="/bkeuniv/control/update-recent-research-direction" 
		urlAdd="/bkeuniv/control/create-recent-research-direction" 
		urlDelete="/bkeuniv/control/delete-recent-research-direction" 
		keysId=["recentResearchDirectionId"] 
		fieldDataResult = "results" 
		titleChange=bkEunivUiLabelMap.BkEunivCreateRecentResearchDirection?j_string
		titleNew=bkEunivUiLabelMap.BkEunivUpdateRecentResearchDirection?j_string
		titleDelete=bkEunivUiLabelMap.BkDeleteRecentResearchDirection?j_string
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>





