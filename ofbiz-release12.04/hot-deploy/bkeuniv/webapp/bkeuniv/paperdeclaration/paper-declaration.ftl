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
		},
		{
			"name": "volumn",
			"data": "volumn"
		},
		{
			"name": "paperCategoryId",
			"data": "paperCategoryId"
		},
		{
			"name": "authors",
			"data": "authors"
		},
		{
			"name": "journalConferenceName",
			"data": "journalConferenceName"
		},
		{
			"name": "File",
			"data": "sourcePath",
			"render": 'function(data, type, row) {
				if(!!data) {
					return "<div><span class=\\"glyphicon glyphicon-download-alt\\"></span>Tải xuống</div>";
				} else {
					return "";
				}
			}'
		}
		
	] />
	
	<#assign source = [] />
	<#list paperCategory.result as paper>
		<#if paper?has_content>
             <#assign op = { "name": "${paper.paperCategoryName}" ,"value": "${paper.paperCategoryCode}" } />
						<#assign source = source + [op] />
		</#if>
	</#list>
	
	<#assign fields=[
		"paperId",
		"staffName",
		"volumn",
		"authors",
		"journalConferenceName",
		"paperCategoryId",
		"paperName",
		"sourcePath"		
	] />
	
	<#assign columnsChange=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
			
		},
		{
			"name": "paperCategoryId",
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
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
