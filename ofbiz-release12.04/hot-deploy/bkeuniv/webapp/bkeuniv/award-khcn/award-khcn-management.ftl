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
			"name": bkEunivUiLabelMap.BkEunivAwardName?j_string,
			"data": "awardKHCNName"
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardAmout?j_string,
			"data": "amount",
			"render": 'function(value, name, dataColumns, id) {
				return value.toLocaleString("en-US", { style: "currency", currency: "USD" }).replace("$", "") + " đ";
			}'
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardTime?j_string,
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
		"awardKHCNId",
		"awardKHCNName",
		"date",
		"description",
		"amount"
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
			"name": bkEunivUiLabelMap.BkEunivAwardName?j_string,
			"value": "awardKHCNName"
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardAmout?j_string,
			"value": "amount",
			"pattern": "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardTime?j_string,
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
			"name": bkEunivUiLabelMap.BkEunivAwardName?j_string,
			"value": "awardKHCNName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardAmout?j_string,
			"value": "amount",
			"pattern": "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": bkEunivUiLabelMap.BkEunivAwardTime?j_string,
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
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=jqGetListAwardKHCN" 
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
		urlUpdate="/bkeuniv/control/update-award-khcn" 
		urlAdd="/bkeuniv/control/create-award-khcn" 
		urlDelete="/bkeuniv/control/delete-award-khcn" 
		keysId=["awardKHCNId"] 
		fieldDataResult = "results" 
		titleChange=bkEunivUiLabelMap.UpdateAwardKHCN?j_string
		titleNew=bkEunivUiLabelMap.AddNewAwardKHCN?j_string
		titleDelete=bkEunivUiLabelMap.DeleteAwardKHCN?j_string
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
