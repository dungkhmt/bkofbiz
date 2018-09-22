
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign source = [] />

	<#assign columns=[
		{
			"name": uiLabelMap.CVPaperName?j_string,
			"data": "paperName"
		},
		{
			"name": uiLabelMap.CVPaperSequence?j_string,
			"data": "sequenceInCVPaper"
		}
	] />
	
	<#assign fields=[
		"staffPaperDeclarationId",
		"sequenceInCVPaper",
		"paperName"
	] />
	
	<#assign sourcePapers = [] />
	<#list resultPapers.papers as p>
		<#if p?has_content>
             <#assign opr = { "name": p.paperName?j_string ,"value": p.staffPaperDeclarationId?j_string } />
						<#assign sourcePapers = sourcePapers + [opr] />
		</#if>
	</#list>
	
	<#assign columnsChange=[
		
		{
			"name": uiLabelMap.CVPaperName?j_string,
			"value": "staffPaperDeclarationId",
			"type": "select",
			"option": {
				"source": sourcePapers,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.CVPaperSequence?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		}
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiLabelMap.CVPaperName?j_string,
			"value": "staffPaperDeclarationId",
			"type": "select",
			"option": {
				"source": sourcePapers,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.CVPaperSequence?j_string,
			"value": "year",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "Nam phai la so nguyen duong"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-Award"
		urlData="/bkeuniv/control/???" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/??" 
		urlAdd="/bkeuniv/control/??" 
		urlDelete="/bkeuniv/control/??" 
		keysId=["awardId"] 
		fieldDataResult = "award" 
		titleChange=uiAwardLabelMap.BkEunivTitleEditAward
		titleNew=uiAwardLabelMap.BkEunivAddRow
		titleDelete=uiAwardLabelMap.BkEunivTitleDeleteAward
		jqTitle=uiAwardLabelMap.BkEunivAward
		contextmenu=true
	/>
</div>
