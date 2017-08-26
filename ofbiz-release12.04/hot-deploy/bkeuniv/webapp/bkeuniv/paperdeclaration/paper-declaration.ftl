<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"data": "paperName"
		}
	] />
	
	<#assign fields=[
		"paperId",
		"staffName",
		"paperName"		
	] />
	
	<#assign columnsChange=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-papers-of-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-paper" 
		urlAdd="/bkeuniv/control/create-education-progress" 
		urlDelete="/bkeuniv/control/delete-education-progress" 
		keysId=["paperId"] 
		fieldDataResult = "papers" 
		titleChange="test"
		titleNew="test"
		titleDelete="test"
		jqTitle="test"
	/>
</div>
