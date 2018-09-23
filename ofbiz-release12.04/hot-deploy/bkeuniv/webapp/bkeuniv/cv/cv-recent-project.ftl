
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	

	<#assign columns=[
		{
			"name": uiLabelMap.CVProjectName?j_string,
			"data": "researchProjectProposalName"
		},
		{
			"name": uiLabelMap.CVProjectSequence?j_string,
			"data": "sequenceInCVProject"
		}
	] />
	
	<#assign fields=[
		"cvProjectId",
		"projectProposalMemberId",
		"sequenceInCVProject",
		"researchProjectProposalName"
	] />
	
	<#assign sourceProjects = [] />
	<#list resultProjects.projectDeclarations as p>
		<#if p?has_content>
             <#assign opr = { "name": p.researchProjectProposalName?j_string ,"value": p.projectProposalMemberId?j_string } />
						<#assign sourceProjects = sourceProjects + [opr] />
		</#if>
	</#list>
	
	<#assign columnsChange=[
		
		{
			"name": uiLabelMap.CVProjectName?j_string,
			"value": "projectProposalMemberId",
			"type": "select",
			"option": {
				"source": sourceProjects,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.CVProjectSequence?j_string,
			"value": "sequenceInCVProject",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So thu tu phai la so nguyen duong"
		}
	]/>
	
	<#assign columnsNew=[
		{
			"name": uiLabelMap.CVProjectName?j_string,
			"value": "projectProposalMemberId",
			"type": "select",
			"option": {
				"source": sourceProjects,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.CVProjectSequence?j_string,
			"value": "sequenceInCVProject",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So thu tu phai la so nguyen duong"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-CVProject"
		urlData="/bkeuniv/control/get-cv-project" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-a-cv-project" 
		urlAdd="/bkeuniv/control/add-a-cv-project" 
		urlDelete="/bkeuniv/control/delete-a-cv-project" 
		keysId=["cvProjectId"] 
		fieldDataResult = "projects" 
		titleChange="Cap nhat"
		titleNew="Them moi"
		titleDelete="Xoa"
		jqTitle="De tai, du an 5 nam gan day"
		contextmenu=true
	/>
</div>
