
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign source = [] />

	<#assign columns=[
		{
			"name": uiAwardLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		},
		{
			"name": uiAwardLabelMap.BkEunivDescription?j_string,
			"data": "description"
		},
		{
			"name": uiAwardLabelMap.BkEunivYear?j_string,
			"data": "year"
		}
	] />
	
	<#assign fields=[
		"awardId",
		"staffId",
		"description",
		"year"
	] />
	
	<#assign columnsChange=[
		<!--
		{
			"name": uiAwardLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiAwardLabelMap.BkEunivDescription?j_string,
			"value": "description"
		},
		{
			"name": uiAwardLabelMap.BkEunivYear?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		}
	]/>
	
	<#assign columnsNew=[
		<!--
		{
			"name": uiAwardLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiAwardLabelMap.BkEunivDescription?j_string,
			"value": "description"
		},
		{
			"name": uiAwardLabelMap.BkEunivYear?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-Award"
		urlData="/bkeuniv/control/get-award" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-award" 
		urlAdd="/bkeuniv/control/create-award" 
		urlDelete="/bkeuniv/control/delete-award" 
		keysId=["awardId"] 
		fieldDataResult = "award" 
		titleChange=uiAwardLabelMap.BkEunivTitleEditAward
		titleNew=uiAwardLabelMap.BkEunivAddRow
		titleDelete=uiAwardLabelMap.BkEunivTitleDeleteAward
		jqTitle=uiAwardLabelMap.BkEunivAward
		contextmenu=true
	/>
</div>
