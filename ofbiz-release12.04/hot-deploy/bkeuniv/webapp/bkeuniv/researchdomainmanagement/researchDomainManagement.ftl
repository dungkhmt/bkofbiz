<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivResearchDomain?j_string,
			"data": "researchDomainName"
		},
		{
			"name": uiLabelMap.BkEunivFromDate?j_string,
			"data": "fromDate",
			"type": "date"
		}
	] />

    <#assign fields=[
		"researchDomainId",
		"researchDomainName",
		"staffId",
		"staffName",
		"staffResearchDomainId",
		"fromDate",
		"thruDate"
	] />

	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivResearchDomain?j_string,
			"value": "researchDomainId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		}
	] />

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivResearchDomain?j_string,
			"value": "researchDomainId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		}
	] />

	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffResearchDomainManagement" 
		optionData={
			"data": {
				"pagesize": "-1"?j_string
			}
		}
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew
		urlUpdate="/bkeuniv/control/update-staff-research-domain" 
		urlAdd="/bkeuniv/control/create-staff-research-domain" 
		urlDelete="/bkeuniv/control/delete-staff-research-domain" 
		keysId=["staffResearchDomainId"]
		fieldDataResult = "results"
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		subTitleDelete=uiLabelMap.BkEunivConfirmToDelete
		contextmenu=true
	/>
</div>