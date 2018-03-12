<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": sseLabels.BkEunivSSESID?j_string,
			"data": "staffName"
		},
		{
			"name": sseLabels.BkEunivSSESDescription?j_string,
			"data": "description"
		},
		{
			"name": sseLabels.BkEunivSSESQuantity?j_string,
			"data": "quantity"
		}
	] />
	
	<#assign fields=[
		"scientificServiceExperienceId",
		"staffId",
		"staffName",
		"description",
		"quantity"
	] />
	
	<#assign columnsChange=[
		{
			"name": sseLabels.BkEunivSSESDescription?j_string,
			"value": "description"
		},
		{
			"name": sseLabels.BkEunivSSESQuantity?j_string,
			"value": "quantity",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong nho hon 999"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": sseLabels.BkEunivSSESDescription?j_string,
			"value": "description"
		},
		{
			"name": sseLabels.BkEunivSSESQuantity?j_string,
			"value": "quantity",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong nho hon 999"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-scientific-service-experience" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-scientific-service-experience" 
		urlAdd="/bkeuniv/control/create-scientific-service-experience" 
		urlDelete="/bkeuniv/control/delete-scientific-service-experience" 
		keysId=["scientificServiceExperienceId"] 
		fieldDataResult = "scientificServiceExperiences" 
		titleChange=sseLabels.BkEunivSSESUpdateTitle?j_string
		titleNew=sseLabels.BkEunivSSESAddTitle?j_string
		titleDelete=sseLabels.BkEunivSSESConfirmDelete?j_string
		jqTitle=sseLabels.BkEunivEMSATA?j_string
		contextmenu=true
	/>
</div>
