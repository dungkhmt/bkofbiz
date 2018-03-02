<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": "Chuyên ngành nghiên cứu",
			"data": "researchSpecialityName"
		},
		{
			"name": "Từ ngày",
			"data": "fromDate",
			"type": "date"
		},
		{
			"name": "Đến ngày",
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
			"name": "Lĩnh vực nghiên cứu",
			"value": "researchDomainId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		},
		{
			"name": "Lĩnh vực nghiên cứu con",
			"value": "researchSubDomainSeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchDomainId", "value": '$("#jqModalAdd #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchSubDomainSeqId, text: "[" + r.researchSubDomainCode + "] "+ r.researchSubDomainName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSubDomainManagement"
			}
		},
		{
			"name": "Chuyên ngành nghiên cứu",
			"value": "researchSpecialitySeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchSubDomainSeqId", "value": '$("#jqModalAdd #researchsubdomainseqid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},

				"render": 'function(r){return {id: r.researchSpecialitySeqId, text: "[" + r.researchSpecialityCode + "] "+ r.researchSpecialityName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement"
			}
		},
		{
			"name": "Từ ngày",
			"value": "fromDate",
			"type": "date",
			"defaultValue": .now
		},
		{
			"name": "Đến ngày",
			"value": "thruDate",
			"type": "date"
		}
	] />

	<#assign columnsNew=[
		{
			"name": "Lĩnh vực nghiên cứu",
			"value": "researchDomainId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"value": "researchDomainId",
				"name": "researchDomainName",
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement"
			}
		},
		{
			"name": "Lĩnh vực nghiên cứu con",
			"value": "researchSubDomainSeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchDomainId", "value": '$("#jqModalAdd #researchdomainid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},
				"render": 'function(r){return {id: r.researchSubDomainSeqId, text: "[" + r.researchSubDomainCode + "] "+ r.researchSubDomainName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSubDomainManagement"
			}
		},
		{
			"name": "Chuyên ngành nghiên cứu",
			"value": "researchSpecialitySeqId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"query": {
					"filter": {"field": "researchSubDomainSeqId", "value": '$("#jqModalAdd #researchsubdomainseqid").val()||""', "operation": "EQUAL",  "build_script": ["value"]}
				},

				"render": 'function(r){return {id: r.researchSpecialitySeqId, text: "[" + r.researchSpecialityCode + "] "+ r.researchSpecialityName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement"
			}
		},
		{
			"name": "Từ ngày",
			"value": "fromDate",
			"type": "date",
			"defaultValue": .now
		},
		{
			"name": "Đến ngày",
			"value": "thruDate",
			"type": "date"
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
		titleChange="Chỉnh sửa"
		titleNew="Tạo mới"
		titleDelete="Xoá"
		jqTitle="Quản lý"
		contextmenu=true
	/>
</div>