<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<div class="body">

	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"data": "staffName"
		},
		{
			"name": uiLabelMap.Affiliation?j_string,
			"data": "affilliation"
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
			"name": uiLabelMap.BkEunivRoleName?j_string,
			"data": "roleName"
		}
	] />
	
	<#assign fields=[
		"externalMemberPaperDeclarationId",
		"paperId",
		"staffName",
		"affilliation",
		"roleId",
		"roleName",
		"sequence",
		"correspondingAuthor"
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
 
  

	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"value": "staffName"
		},
		{
			"name": uiLabelMap.Affiliation?j_string,
			"value": "affilliation"
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
			"value": "staffName"
		},
		{
			"name": uiLabelMap.Affiliation?j_string,
			"value": "affilliation"
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
		urlData="/bkeuniv/control/get-external-members-of-a-paper"
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
		urlUpdate="/bkeuniv/control/update-external-member-of-a-paper" 
		urlAdd="/bkeuniv/control/create-external-member-of-a-paper" 
		optionDataAdd={
			"data": {
				"paperId": paperId?j_string
			}
		}
		urlDelete="/bkeuniv/control/remove-external-member-of-a-paper" 
		keysId=["externalMemberPaperDeclarationId"] 
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
