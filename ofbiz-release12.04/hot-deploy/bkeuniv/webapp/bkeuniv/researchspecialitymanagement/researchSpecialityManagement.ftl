<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivResearchSpeciality?j_string,
			"data": "researchSpecialityName"
		},
		{
			"name": uiLabelMap.BkEunivFromDate?j_string,
			"data": "fromDate",
			"type": "date"
		},
		{
			"name": uiLabelMap.BkEunivThruDate?j_string,
			"data": "thruDate",
			"type": "date"
		}
	] />

    <#assign fields=[
		"fromDate",
		"researchDomainId",
		"researchDomainName",
		"researchSpecialityName",
		"researchSpecialitySeqId",
		"researchSubDomainName",
		"researchSubDomainSeqId",
		"staffId",
		"staffName",
		"staffResearchSpecialityId",
		"thruDate"
	] />

	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivResearchDomain?j_string,
			"value": "researchDomainId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"eventChange": '$("#jqModalChange #researchsubdomainseqid").empty',
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		},
		{
			"name": uiLabelMap.BkEunivResearchSubDomain?j_string,
			"value": "researchSubDomainSeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"eventChange": '$("#jqModalChange #researchspecialityseqid").empty',
				"query": {
					"filter": { "field": "researchDomainId", "value": '$("#jqModalChange #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchSubDomainSeqId, text: "[" + r.researchSubDomainCode + "] "+ r.researchSubDomainName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSubDomainManagement"
			}
		},
		{
			"name": uiLabelMap.BkEunivResearchSpeciality?j_string,
			"value": "researchSpecialitySeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {
						"expressions": 
							[
								{ "field": "researchDomainId", "value": '$("#jqModalChange #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]},
								{"field": "researchSubDomainSeqId", "value": '$("#jqModalChange #researchsubdomainseqid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
							],
						"operation": "AND"
					}
				},

				"render": 'function(r){return {id: r.researchSpecialitySeqId, text: "[" + r.researchSpecialityCode + "] "+ r.researchSpecialityName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement"
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
				"eventChange": '$("#jqModalAdd #researchsubdomainseqid").empty',
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		},
		{
			"name": uiLabelMap.BkEunivResearchSubDomain?j_string,
			"value": "researchSubDomainSeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"eventChange": '$("#jqModalAdd #researchspecialityseqid").empty',
				"query": {
					"filter": {"field": "researchDomainId", "value": '$("#jqModalAdd #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchSubDomainSeqId, text: "[" + r.researchSubDomainCode + "] "+ r.researchSubDomainName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSubDomainManagement"
			}
		},
		{
			"name": uiLabelMap.BkEunivResearchSpeciality?j_string,
			"value": "researchSpecialitySeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {
						"expressions": 
							[
								{ "field": "researchDomainId", "value": '$("#jqModalAdd #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]},
								{"field": "researchSubDomainSeqId", "value": '$("#jqModalAdd #researchsubdomainseqid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
							],
						"operation": "AND"
					}
				},
				"render": 'function(r){return {id: r.researchSpecialitySeqId, text: "[" + r.researchSpecialityCode + "] "+ r.researchSpecialityName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement"
			}
		}
	] />

	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffResearchSpecialityManagement" 
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
		urlUpdate="/bkeuniv/control/update-staff-research-speciality" 
		urlAdd="/bkeuniv/control/create-staff-research-speciality" 
		urlDelete="/bkeuniv/control/delete-staff-research-speciality" 
		keysId=["staffResearchSpecialityId"]
		fieldDataResult = "results" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		contextmenu=true
	/>
</div>