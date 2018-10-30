
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign columns=[
		{
			"name":uiLabelMap.ForeignLanguageName?j_string,
			"data": "foreignLanguageCatalogName"
		},
		{
			"name": uiLabelMap.Listen?j_string,
			"data": "listen"
		},
		{
			"name": uiLabelMap.Speaking?j_string,
			"data": "speaking"
		},
		{
			"name": uiLabelMap.Reading?j_string,
			"data": "reading"
		},
		{
			"name": uiLabelMap.Writting?j_string,
			"data": "writing"
		}
	] />
	
	<#assign sourceForeignLanguageCatalog=[]/>
	<#list resultForeignLanguageCatalog.foreignlanguageCatalog as flc>
		<#if flc?has_content>
             <#assign op = { "name": flc.foreignLanguageCatalogName?j_string ,"value": flc.foreignLanguageCatalogId?j_string } />
						<#assign sourceForeignLanguageCatalog = sourceForeignLanguageCatalog + [op] />
		</#if>
	</#list>
	
	<#assign fields=[
		"foreignLanguageId",
		"foreignLanguageCatalogId",
		"foreignLanguageCatalogName",
		"listen",
		"speaking",
		"reading",
		"writing"
	] />
	
	<#assign columnsChange=[
		{
			"name": uiLabelMap.ForeignLanguageName?j_string,
			"value": "foreignLanguageCatalogId",
			"type": "select",
			"option": {
				"source": sourceForeignLanguageCatalog,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.Listen?j_string,
			"value": "listen"
		},
		{
			"name": uiLabelMap.Speaking?j_string,
			"value": "speaking"
		},
		{
			"name": uiLabelMap.Reading?j_string,
			"value": "reading"
			
		},
		{
			"name": uiLabelMap.Writting?j_string,
			"value": "writing"
		} 
		
	] />
	
	<#assign columnsNew=[
		{
			"name": uiLabelMap.ForeignLanguageName?j_string,
			"value": "foreignLanguageCatalogId",
			"type": "select",
			"option": {
				"source": sourceForeignLanguageCatalog,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.Listen?j_string,
			"value": "listen"
		},
		{
			"name": uiLabelMap.Speaking?j_string,
			"value": "speaking"
		},
		{
			"name": uiLabelMap.Reading?j_string,
			"value": "reading"
			
		},
		{
			"name": uiLabelMap.Writting?j_string,
			"value": "writing"
		} 
		
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-EducationProgress"
		urlData="/bkeuniv/control/get-foreign-language-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-foreign-language" 
		urlAdd="/bkeuniv/control/create-foreign-language" 
		urlDelete="/bkeuniv/control/delete-foreign-language" 
		keysId=["foreignLanguageId"] 
		fieldDataResult = "foreignlanguages" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		contextmenu=true
	/>
</div>
