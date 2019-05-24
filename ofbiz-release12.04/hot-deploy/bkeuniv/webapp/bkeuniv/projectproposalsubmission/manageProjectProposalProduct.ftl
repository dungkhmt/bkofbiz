<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>


<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"data": "researchProductName"
		},
		{
			"name": uiLabelMap.BkEunivResearchProductType?j_string,
			"data": "researchProductTypeName",
			"width": '20%'
		}
	] />
	
	<#assign fields=[
		"researchProductId",
		"researchProductRegisteredId",
		"researchProductName",
		"sourcePathUpload",
		"approvedByStaffId",
		"researchProductTypeId",
		"researchProductTypeName"
	] />

	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"value": "researchProductName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": uiLabelMap.BkEunivResearchProductType?j_string,
			"value": "researchProductTypeId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchProjectProposalId", "value": parameters.researchProjectProposalId?js_string?j_string, "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchProductTypeId, text:r.researchProductTypeName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchProjectTypeProduct"
			},
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"value": "researchProductName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": uiLabelMap.BkEunivResearchProductType?j_string,
			"value": "researchProductTypeId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchProjectProposalId", "value": parameters.researchProjectProposalId?js_string?j_string, "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchProductTypeId, text:r.researchProductTypeName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchProjectTypeProduct"
			},
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchProjectProduct" 
		columns=columns
		dataFields=fields
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-research-project-product" 
		urlAdd="/bkeuniv/control/create-research-project-product" 
		urlDelete="/bkeuniv/control/remove-research-project-product"
		keysId=["researchProductId"]
		contextmenu=true
		backToUrl={
			"href": '/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId='+parameters.researchProjectProposalId?js_string,
			"text": "Quay ve de tai"
		}
	/>
</div>
