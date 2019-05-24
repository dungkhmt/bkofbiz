
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	

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
		"cvPaperId",
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
			"value": "sequenceInCVPaper",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So thu tu phai la so nguyen duong"
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
			"value": "sequenceInCVPaper",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So thu tu phai la so nguyen duong"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-CVPaper"
		urlData="/bkeuniv/control/get-cv-papers" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-a-cv-paper" 
		urlAdd="/bkeuniv/control/add-a-cv-paper" 
		urlDelete="/bkeuniv/control/delete-a-cv-paper" 
		keysId=["cvPaperId"] 
		fieldDataResult = "papers" 
		titleChange="Cap nhat"
		titleNew="Them moi"
		titleDelete="Xoa"
		jqTitle="Cong trinh nghien cuu 5 nam gan day"
		contextmenu=true
	/>
</div>
