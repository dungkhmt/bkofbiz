<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign source = [] />

	<#assign columns=[
		{
			"name": uiResearchProgramLabelMap.BkEunivResearchProgramName?j_string,
			"data": "researchProgramName"
		},
		{
			"name": uiResearchProgramLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		},
		{
			"name": uiResearchProgramLabelMap.BkEunivFromDate?j_string,
			"data": "fromDate"
		}
		{
			"name": uiResearchProgramLabelMap.BkEunivThruDate?j_string,
			"data": "thruDate"
		}
	] />
	
	<#assign fields=[
		"researchProgramId",
		"researchProgramName",
		"staffId",
		"thruDate",
		"fromDate"
	] />
	
	<#assign columnsChange=[
		{
			"name": uiResearchProgramLabelMap.BkEunivResearchProgramName?j_string,
			"value": "researchProgramName"
		},
		<!--
		{
			"name": uiResearchProgramLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiResearchProgramLabelMap.BkEunivFromDate?j_string,
			"value": "fromDate",
			"type": "date"
		},
		{
			"name": uiResearchProgramLabelMap.BkEunivThruDate?j_string,
			"value": "thruDate",
			"type": "date"
		}
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiResearchProgramLabelMap.BkEunivResearchProgramName?j_string,
			"value": "researchProgramName"
		},
		<!--
		{
			"name": uiResearchProgramLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiResearchProgramLabelMap.BkEunivFromDate?j_string,
			"value": "fromDate",
			"type": "date"
		},
		{
			"name": uiResearchProgramLabelMap.BkEunivThruDate?j_string,
			"value": "thruDate",
			"type": "date"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		
		urlData="/bkeuniv/control/get-research-program" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-research-program"
		urlAdd="/bkeuniv/control/create-research-program" 
		urlDelete="/bkeuniv/control/delete-research-program"
		keysId=["researchProgramId"] 
		fieldDataResult = "researchProgram" 
		titleChange= uiResearchProgramLabelMap.BkEunivEditResearchProgram
		titleNew= uiResearchProgramLabelMap.BkEunivAddResearchProgram
		titleDelete= uiResearchProgramLabelMap.BkEunivDeleteResearchProgram
		jqTitle= uiResearchProgramLabelMap.BkEunivResearchProgram
		contextmenu=true
	/>
</div>