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
			"name": uiPatentLabelMap.BkEunivPatentCateoryName?j_string,
			"data": "patentCategoryName"
		},
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		},
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"data": "year"
		},
		{
			"name": uiPatentLabelMap.BkEunivAcademicYear?j_string,
			"data": "academicYearId"
		}
	] />
	
	<#assign fields=[
		"patentId",
		"patentName",
		"patentCategoryName",
		"patentCategoryId",
		"staffId",
		"academicYearId",
		"year"
	] />
	<#assign sourceAcademicYear = [] />
	<#list academicYearS.academicYears as y>
		<#if y?has_content>
             <#assign opy = { "name": y.academicYearName?j_string ,"value": y.academicYearId?j_string } />
						<#assign sourceAcademicYear = sourceAcademicYear + [opy] />
		</#if>
	</#list>
	
	<#assign sourcePatentCategory = [] />
	<#list resultPatentCategory.patentCategory as pc>
		<#if pc?has_content>
             <#assign opy = { "name": pc.patentCategoryName?j_string ,"value": pc.patentCategoryId?j_string } />
						<#assign sourcePatentCategory = sourcePatentCategory + [opy] />
		</#if>
	</#list>
	
	<#assign columnsChange=[
		{
			"name": uiPatentLabelMap.BkEunivPatentName?j_string,
			"value": "patentName"
		},
		<!--
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		},
		{
			"name": uiPatentLabelMap.BkEunivPatentCateoryName?j_string,
			"value": "patentCategoryId",
			"type": "select",
			"option": {
				"source": sourcePatentCategory,
				"maxItem": 1
			}
		},
		{
			"name": uiPatentLabelMap.BkEunivAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		}
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiPatentLabelMap.BkEunivPatentName?j_string,
			"value": "patentName"
		},
		<!--
		{
			"name": uiPatentLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		-->
		{
			"name": uiPatentLabelMap.BkEunivPatentCateoryName?j_string,
			"value": "patentCategoryId",
			"type": "select",
			"option": {
				"source": sourcePatentCategory,
				"maxItem": 1
			}
		},
		{
			"name": uiPatentLabelMap.BkEunivYear?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		},
		{
			"name": uiPatentLabelMap.BkEunivAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
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