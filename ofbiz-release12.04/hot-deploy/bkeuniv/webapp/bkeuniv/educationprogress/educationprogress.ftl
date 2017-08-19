<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<script>
var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
</script>
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
		"graduateDate"
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
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-education-progress" 
		columns=columns 
		dataFields=fields 
		sizeTable="sizeTable" 
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

<div class="loader hidden-loading"></div>
<div id="add-education-progress"></div>
<div id="change-education-progress"></div>