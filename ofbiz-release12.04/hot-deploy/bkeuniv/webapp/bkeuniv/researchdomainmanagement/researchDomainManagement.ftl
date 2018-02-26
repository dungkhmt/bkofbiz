<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": "Lĩnh vực nghiên cứu",
			"data": "researchDomainName"
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
			"name": "Từ ngày",
			"value": "fromDate",
			"type": "date"
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
			"name": "Từ ngày",
			"value": "fromDate",
			"type": "date"
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
		titleChange="Chỉnh sửa"
		titleNew="Tạo mới"
		titleDelete="Xoá"
		jqTitle="Quản lý"
		contextmenu=true
	/>
</div>