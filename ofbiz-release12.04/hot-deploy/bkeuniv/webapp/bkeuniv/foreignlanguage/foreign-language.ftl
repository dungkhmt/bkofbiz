<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageId?j_string,
			"data": "foreignLanguageId"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"data": "foreignLanguageCatalogId"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivListen?j_string,
			"data": "listen"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivSpeaking?j_string,
			"data": "speaking"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivReading?j_string,
			"data": "reading"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivWriting?j_string,
			"data": "writing"
		}
	] />
	
	<#assign sourceStaff = [] />
	<#list staff.staffs as rs>
		<#if rs?has_content>
             <#assign op = { "name": "${rs.staffName}" ,"value": "${rs.staffId}" } />
						<#assign sourceStaff = sourceStaff + [op] />
		</#if>
	</#list>
	
	<#assign sourceForeign = [] />
	<#list foreignLanguageCatalog.result as fs>
		<#if fs?has_content>
             <#assign op = { "name": "${fs.foreignLanguageCatalogName}" ,"value": "${fs.foreignLanguageCatalogId}" } />
						<#assign sourceForeign = sourceForeign + [op] />
		</#if>
	</#list>
	
	<#assign fields=[
		"foreignLanguageId",
		"staffId",
		"foreignLanguageCatalogId",
		"listen",
		"speaking",
		"reading",
		"writing"		
	] />
	
	<#assign columnsChange=[
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageId?j_string,
			"value": "foreignLanguageId"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId",
			"type": "select",
			"option": {
				"source": sourceStaff,
				"maxItem": 1
			}
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"value": "foreignLanguageCatalogId",
			"type": "select",
			"option": {
				"source": sourceForeign,
				"maxItem": 1
			}
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivListen?j_string,
			"value": "listen"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivSpeaking?j_string,
			"value": "speaking"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivReading?j_string,
			"value": "reading"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivWriting?j_string,
			"value": "writing"
		}
		
		
	] />
	
	<#assign columnsNew=[
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageId?j_string,
			"value": "foreignLanguageId"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId",
			"type": "select",
			"option": {
				"source": sourceStaff,
				"maxItem": 1
			}
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"value": "foreignLanguageCatalogId",
			"type": "select",
			"option": {
				"source": sourceForeign,
				"maxItem": 1
			}
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivListen?j_string,
			"value": "listen"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivSpeaking?j_string,
			"value": "speaking"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivReading?j_string,
			"value": "reading"
		},
		{
			"name": foreignLanguageUiLabelMap.BkEunivWriting?j_string,
			"value": "writing"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-foreign-language" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-foreign-language" 
		urlAdd="/bkeuniv/control/create-foreign-language" 
		urlDelete="/bkeuniv/control/delete-foreign-language" 
		keysId=["researchSpecialityId"] 
		fieldDataResult = "foreignLanguage" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Foreign Languages"
	/>
</div>
