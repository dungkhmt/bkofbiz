<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": educationProgressUiLabelMap.BkEunivInstitution,
			"value": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType,
			"value": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality,
			"value": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate,
			"value": "graduateDate"
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
			"name": educationProgressUiLabelMap.BkEunivInstitution,
			"value": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType,
			"value": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality,
			"value": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate,
			"value": "graduateDate"
		} 
	] />
	
	<#assign columnsNew=[
		{
			"name": educationProgressUiLabelMap.BkEunivInstitution,
			"value": "institution"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivEducationType,
			"value": "educationType"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivSpeciality,
			"value": "speciality"
		},
		{
			"name": educationProgressUiLabelMap.BkEunivGraduateDate,
			"value": "graduateDate"
		} 
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
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
	/>
</div>
