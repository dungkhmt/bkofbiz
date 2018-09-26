<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<div class="body">


	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"data": "staffName"
		},
		{
			"name": uiLabelMap.BkEunivSequenceMembers?j_string,
			"data": "sequence"
		},
		{
			"name": uiLabelMap.BkEunivCorrespondingAuthor?j_string,
			"data": "correspondingAuthor"
		},
		{
			"name": uiLabelMap.AffiliationOutsideUniversity?j_string,
			"data": "affiliationOutsideUniversity"
		},
		
		{
			"name": uiLabelMap.BkEunivRoleName?j_string,
			"data": "roleName"
		}
	] />
	
	<#assign fields=[
		"staffPaperDeclarationId",
		"paperId",
		"staffName",
		"staffId",
		"sequence",
		"correspondingAuthor",
		"affiliationOutsideUniversity",
		"roleId",
		"roleName"
	] />
  <#assign roleTypeList=[]/>
  <#list resultRole.roles as rt>
    <#if rt?has_content>
             <#assign op = { "name": rt.roleName?j_string ,"value": rt.roleId?j_string } />
            <#assign roleTypeList = roleTypeList + [op] />
    </#if>
  </#list>

  <#assign yesnoList=[]/>
  <#list resultYesNo.yn as yn>
    <#if yn?has_content>
             <#assign op = { "name": yn.name?j_string ,"value": yn.value?j_string } />
            <#assign yesnoList = yesnoList + [op] />
    </#if>
  </#list>
  
  <#assign noyesList=[]/>
  <#list resultNoYes.ny as ny>
    <#if ny?has_content>
             <#assign op = { "name": ny.name?j_string ,"value": ny.value?j_string } />
            <#assign noyesList = noyesList + [op] />
    </#if>
  </#list>

		<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
		},
				{
			"name": uiLabelMap.BkEunivSequenceMembers?j_string,
			"value": "sequence",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong"
		},
		{
			"name": uiLabelMap.BkEunivCorrespondingAuthor?j_string,
			"value": "correspondingAuthor",
			"type": "select",
			"option":{
				"source": yesnoList,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.AffiliationOutsideUniversity?j_string,
			"value": "affiliationOutsideUniversity",
			"type": "select",
			"option":{
				"source": noyesList,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.BkEunivRoleName?j_string,
			"value": "roleId",
			"type": "select",
			"option":{
				"source": roleTypeList,
				"maxItem": 1
			}
		}
	
	] />
	

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
		},
				{
			"name": uiLabelMap.BkEunivSequenceMembers?j_string,
			"value": "sequence",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong"
		},
		{
			"name": uiLabelMap.BkEunivCorrespondingAuthor?j_string,
			"value": "correspondingAuthor",
			"type": "select",
			"option":{
				"source": yesnoList,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.AffiliationOutsideUniversity?j_string,
			"value": "affiliationOutsideUniversity",
			"type": "select",
			"option":{
				"source": noyesList,
				"maxItem": 1
			}
		},

		{
			"name": uiLabelMap.BkEunivRoleName?j_string,
			"value": "roleId",
			"type": "select",
			"option":{
				"source": roleTypeList,
				"maxItem": 1
			}
		}
		]
	 />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-members-of-a-paper"
		optionData={
			"data": {
				"paperId": paperId?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-member-of-a-paper" 
		urlAdd="/bkeuniv/control/create-member-of-a-paper" 
		optionDataAdd={
			"data": {
				"paperId": paperId?j_string
			}
		}
		urlDelete="/bkeuniv/control/remove-member-of-a-paper" 
		keysId=["staffPaperDeclarationId"] 
		fieldDataResult = "staffs" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		jqTitle=uiLabelMap.TitleProjectSubmissionManagement?j_string
		contextmenu=true
		backToUrl={
			"href": '/bkeuniv/control/detail-paper?paperId='+paperId,
			"text": "Quay ve bai bao"
		}
	/>
</div>
