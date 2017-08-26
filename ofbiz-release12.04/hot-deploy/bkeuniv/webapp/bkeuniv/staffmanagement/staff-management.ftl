<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"data": "genderName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"data": "staffDateOfBirth"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"data": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName"
		}
	] />
	
	<#assign fields=[
		"staffName",
		"staffEmail",
		"genderName",
		"staffDateOfBirth",
		"staffPhone",
		"departmentName"
	] />

	<#assign columnsChange=[
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"data": "genderName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"data": "staffDateOfBirth"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"data": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName"
		}
	] />

	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-staffs" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-paper" 
		urlAdd="/bkeuniv/control/create-education-progress" 
		urlDelete="/bkeuniv/control/delete-education-progress" 
		keysId=["staffId"] 
		fieldDataResult = "staffs" 
		titleChange="test"
		titleNew="test"
		titleDelete="test"
		jqTitle="test"
	/>
</div>
