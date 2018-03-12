<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">
	
	<#assign source = [] />
	
	<#assign columns=[
		{
			"name": uiApplied.BkEunivARPStaff?j_string,
			"data": "staffId"
		},
		{
			"name": uiApplied.BkEunivARPName?j_string,
			"data": "name"
		},
		{
			"name": uiApplied.BkEunivARPDescription?j_string,
			"data": "description"
		},
		{
			"name": uiApplied.BkEunivARPPeriod?j_string,
			"data": "period"
		}
	]/>
	
	<#assign fields=[
		"appliedResearchProjectId",
		"staffId",
		"name",
		"description",
		"period"
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiApplied.BkEunivARPStaff?j_string,
			"value": "staffId"
		},
		{
			"name": uiApplied.BkEunivARPName?j_string,
			"value": "name"
		},
		{
			"name": uiApplied.BkEunivARPDescription?j_string,
			"value": "description"
		},
		{
			"name": uiApplied.BkEunivARPPeriod?j_string,
			"value": "period"
		}
	]/>
	
	<#assign columnsChange=[
		{
			"name": uiApplied.BkEunivARPStaff?j_string,
			"value": "staffId"
		},
		{
			"name": uiApplied.BkEunivARPName?j_string,
			"value": "name"
		},
		{
			"name": uiApplied.BkEunivARPDescription?j_string,
			"value": "description"
		},
		{
			"name": uiApplied.BkEunivARPPeriod?j_string,
			"value": "period"
		}
	]/>
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<@jqDataTable
		id="DataTable-AppliedResearchProject"
		urlData="/bkeuniv/control/get-applied-project-declaration-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-applied-project-declaration-staff" 
		urlAdd="/bkeuniv/control/create-applied-project-declaration-staff" 
		urlDelete="/bkeuniv/control/delete-applied-project-declaration-staff" 
		keysId=["appliedResearchProjectId"] 
		fieldDataResult = "project" 
		titleChange=uiApplied.BkEunivARPEdit
		titleNew=uiApplied.BkEunivARPAdd
		titleDelete=uiApplied.BkEunivARPDelete
		jqTitle=uiApplied.BkEunivARP
		contextmenu=true
	/>
</div>
