<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"data": "foreignLanguageCatalogId"
		},
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogName?j_string,
			"data": "foreignLanguageCatalogName"
		}
	] />
	
	
	<#assign fields=[
		"foreignLanguageCatalogId",
		"foreignLanguageCatalogName"
	] />
	
	<#assign columnsChange=[
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"value": "foreignLanguageCatalogId"
		},
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogName?j_string,
			"value": "foreignLanguageCatalogName"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogId?j_string,
			"value": "foreignLanguageCatalogId"
		},
		{
			"name": foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogName?j_string,
			"value": "foreignLanguageCatalogName"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-foreign-language-catalog" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-foreign-language-catalog" 
		urlAdd="/bkeuniv/control/create-foreign-language-catalog" 
		urlDelete="/bkeuniv/control/delete-foreign-language-catalog" 
		keysId=["foreignLanguageCatalogId"] 
		fieldDataResult = "foreignLanguageCatalog" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Foreign Language Catalog"
	/>
</div>
