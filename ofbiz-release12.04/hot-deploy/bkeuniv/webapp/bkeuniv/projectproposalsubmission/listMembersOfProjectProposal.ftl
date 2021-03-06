<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<#--  Project Proposal ID = ${projectProposalId}  -->

<script>
</script>

<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivStaffName?j_string,
			"data": "staffName"
		},
		{
			"name": uiLabelMap.BkEunivRoleType?j_string,
			"data": "roleTypeName"
		}
	] />
	
	<#assign fields=[
		"projectProposalMemberId",
		"researchProjectProposalId",
		"staffName",
		"staffId",
		"roleTypeId",
		"roleTypeName"
	] />
<#--  	
	<#assign roleTypeList=[]/>
	<#list resultRoleTypes.projectProposalRoleTypes as rt>
		<#if rt?has_content>
             <#assign op = { "name": rt.roleTypeName?j_string ,"value": rt.roleTypeId?j_string } />
						<#assign roleTypeList = roleTypeList + [op] />
		</#if>
	</#list>
	
	<#assign staffs=[]/>
	<#list resultStaffs.staffs as st>
		<#if st? has_content>
			<#assign op={"name": st.staffName? j_string, "value": st.staffId?j_string}/>
			<#assign staffs = staffs + [op]/>
		</#if>
	</#list>  -->

	<#assign roleTypeList=[]/>
  <#list resultRoleTypes.projectProposalRoleTypes as rt>
    <#if rt?has_content>
             <#assign op = { "name": rt.roleTypeName?j_string ,"value": rt.roleTypeId?j_string } />
            <#assign roleTypeList = roleTypeList + [op] />
    </#if>
  </#list>


	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivStaffName?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
		},
		{
			"name": uiLabelMap.BkEunivRoleType?j_string,
			"value": "roleTypeId",
			"type": "select",
			"option":{
				"source": roleTypeList,
				"maxItem": 1
			}
		}
	
	] />
	

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivStaffName?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
		},
		{
			"name": uiLabelMap.BkEunivRoleType?j_string,
			"value": "roleTypeId",
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
		<!--
		urlData="/bkeuniv/control/get-members-of-project-proposals?researchProjectProposalId=${projectProposalId}"
		-->
		urlData="/bkeuniv/control/get-members-of-project-proposals"
		optionData={
			"data": {
				"researchProjectProposalId": projectProposalId?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-member-of-project-proposal" 
		urlAdd="/bkeuniv/control/create-a-member-of-project-proposal?researchProjectProposalId=${projectProposalId}" 
		urlDelete="/bkeuniv/control/delete-member-of-project-proposal" 
		keysId=["projectProposalMemberId"] 
		fieldDataResult = "members" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		jqTitle=uiLabel.TitleProjectSubmissionManagement?j_string
		contextmenu=true
		backToUrl={
			"href": '/bkeuniv/control/detail-research-project-proposal-update?researchProjectProposalId='+ projectProposalId,
			"text": "Quay ve de tai"
		}
	/>
</div>
