<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<#--  Project Proposal ID = ${projectProposalId}  -->

<script>
	var modal;
	var span;
	var selectedEntry;
	/*
	function createContextMenu(id) {
		
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
			menu: [
			  {title: 'View Detail', cmd: "viewdetail", uiIcon: "glyphicon glyphicon-list-alt"},
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var data = jqDataTable.table.row( el ).data();
				switch(ui.cmd){
					case "viewdetail":
						jqViewDetail(data);
						break;
						
					}		
				},
				beforeOpen: function(event, ui) {
					var $menu = ui.menu,
						$target = ui.target,
						extraData = ui.extraData;
					ui.menu.zIndex(9999);
			    }
			  });
	}
				
	function 	jqViewDetail(data){
		alert(data.researchProjectProposalId);
		//window.location.href = "/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=" + data.researchProjectProposalId;
	}
	*/
	
</script>

<div class="body">
<a href="/bkeuniv/control/detail-paper?paperId=${paperId}">Quay ve bai bao</a>

	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"data": "staffName"
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


	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivPaperMembers?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {id: r.staffId, text: "[" + r.staffId + "] "+ r.staffName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
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
				"render": 'function(r){return {id: r.staffId, text: "[" + r.staffId + "] "+ r.staffName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
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
		<!--
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
		-->
	/>
</div>
