
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign columns=[
		{
			"name": educationProgressUiLabelMap.BkEunivInstitution?j_string,
			"data": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType?j_string,
			"data": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality?j_string,
			"data": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate?j_string,
			"data": "graduateDate"
		} 
	] />
	
	<#assign fields=[
		"educationProgressId",
		"educationType",
		"institution",
		"speciality",
		"graduateDate",
		"staffId"
	] />
	
	<#assign columnsChange=[
		{
			"name": educationProgressUiLabelMap.BkEunivInstitution?j_string,
			"value": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType?j_string,
			"value": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality?j_string,
			"value": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate?j_string,
			"value": "graduateDate",
			"type": "date"
		} 
	] />
	
	<#assign columnsNew=[
		{
			"name": educationProgressUiLabelMap.BkEunivInstitution?j_string,
			"value": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType?j_string,
			"value": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality?j_string,
			"value": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate?j_string,
			"value": "graduateDate",
			"type": "date"
		} 
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-EducationProgress"
		urlData="/bkeuniv/control/get-education-progress" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-education-progress" 
		urlAdd="/bkeuniv/control/create-education-progress" 
		urlDelete="/bkeuniv/control/delete-education-progress" 
		keysId=["educationProgressId"] 
		fieldDataResult = "educationProgress" 
		titleChange="test"
		titleNew="test"
		titleDelete="test"
		jqTitle="test"
		contextmenu=true
	/>
</div>
