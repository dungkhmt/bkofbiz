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
			"name": staffManagementUiLabelMap.BkEunivGender?j_string,
			"data": "genderName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"data": "staffDateOfBirth",
			"type": "date"
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
	
	<#assign sourceDepartment = [] />
	<#list resultDepartments.departments as dept>
		<#if dept?has_content>
             <#assign op = { "name": "${dept.departmentName}" ,"value": "${dept.departmentId}" } />
						<#assign sourceDepartment = sourceDepartment + [op] />
		</#if>
	</#list>
	
	<#assign sourceGender = [] />
	<#list resultGenders.genders as g>
		<#if g?has_content>
             <#assign op = { "name": "${g.genderName}" ,"value": "${g.genderId}" } />
						<#assign sourceGender = sourceGender + [op] />
		</#if>
	</#list>
	
	
	<#assign fields=[
		"staffId",
		"staffName",
		"staffEmail",
		"genderName",
		"staffGenderId",
		"staffDateOfBirth",
		"staffPhone",
		"departmentName",
		"departmentId"
	] />

	<#assign columnsChange=[
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"value": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivGender?j_string,
			"value": "staffGenderId",
			"type": "select",
			"option": {
				"source": sourceGender,
				"maxItem": 1
			}
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"value": "staffDateOfBirth",
			"type": "date"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"value": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"value": "departmentId",
			"type": "select",
			"option": {
				"source": sourceDepartment,
				"maxItem": 1
			}
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
		urlUpdate="/bkeuniv/control/update-a-staff" 
		urlAdd="not-yet" 
		urlDelete="not-yet" 
		keysId=["staffId"] 
		fieldDataResult = "staffs" 
		titleChange=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleNew=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleDelete=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		jqTitle=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
	/>
</div>
