<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign source = [] />

	<#assign columns=[
		{
			"name": uiPatentLabelMap.BkEunivPatentName?j_string,
			"data": "patentName"
		},
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		},
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"data": "year"
		}
	] />
	
	<#assign fields=[
		"patentId",
		"patentName",
		"staffId",
		"year"
	] />
	
	<#assign columnsChange=[
		{
			"name": uiPatentLabelMap.BkEunivPatentName?j_string,
			"value": "patentName"
		},
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"value": "year"
		}
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiPatentLabelMap.BkEunivPatentName?j_string,
			"value": "patentName"
		},
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"value": "year"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		
		urlData="/bkeuniv/control/get-patent" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-patent"
		urlAdd="/bkeuniv/control/create-patent" 
		urlDelete="/bkeuniv/control/delete-patent"
		keysId=["patentId"] 
		fieldDataResult = "patent" 
		titleChange= uiPatentLabelMap.BkEunivEditPatent
		titleNew= uiPatentLabelMap.BkEunivAddPatent
		titleDelete= uiPatentLabelMap.BkEunivDeletePatent
		jqTitle= uiPatentLabelMap.BkEunivPatent
		contextmenu=true
	/>
</div>