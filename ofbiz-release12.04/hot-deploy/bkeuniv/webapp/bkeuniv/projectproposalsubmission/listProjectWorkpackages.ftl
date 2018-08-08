<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<#--  Project Proposal ID = ${projectProposalId}  -->

<script>
	var modal;
	var span;
	var selectedEntry;
	function createContextMenu(id) {
		
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
			menu: [
			  {title: '${uiLabel.ViewDetail}', cmd: "viewdetail", uiIcon: "glyphicon glyphicon-list-alt"},
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

</script>

<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivContent?j_string,
			"data": "content"
		},
		{
			"name": uiLabelMap.BkEunivStaff?j_string,
			"data": "staffId"
		},
		{
			"name": uiLabelMap.BkEunivWorkingDays?j_string,
			"data": "workingDays"
		},
		{
			"name": uiLabelMap.BkEunivBudget?j_string,
			"data": "budget"
		}
	] />

	<#assign fields=[
		"researchProjectProposalId",
		"contentItemSeq",
		"content",
		"staffId",
		"workingDays",
		"budget"
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

	<#assign members=[]/> 
	<#list resultMembers.members as m> 
		<#if m?has_content> 
				<#assign op = { "name": m.staffName?j_string ,"value": m.staffId?j_string } /> 
				<#assign members = members + [op] /> 
		</#if> 
	</#list>
	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivContent?j_string,
			"value": "content",
			"type": "textarea"
		},
		{
			"name": uiLabelMap.BkEunivStaff?j_string,
			"value": "staffId",
			"type": "select",
			"option":{
				"source": members,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.BkEunivWorkingDays?j_string,
			"value": "workingDays",
			"pattern": "[0-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon 9999"
		},
		{
			"name": uiLabelMap.BkEunivBudget?j_string,
			"value": "budget",
			"pattern": "[0-9]([0-9]{0,20})",
			"title": "So nguyen duong nho hon 20 chu so"
		}
	] />

	

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivStaff?j_string,
			"value": "staffId",
			"type": "select",
			"option":{
				"source": members,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.BkEunivContent?j_string,
			"value": "content",
			"type": "textarea"
		},
		{
			"name": uiLabelMap.BkEunivWorkingDays?j_string,
			"value": "workingDays",
			"pattern": "[0-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon 9999"
		},
		{
			"name": uiLabelMap.BkEunivBudget?j_string,
			"value": "budget",
			"pattern": "[0-9]([0-9]{0,20})",
			"title": "So nguyen duong nho hon 20 chu so"
		}
		]
	 />



	<#--  $.ajax({
			url: "/bkeuniv/control/add-a-workpackage-project-proposal",
			type: 'POST',
			data: {
				"researchProjectProposalId": researchProjectProposalId,
				"staffId": staffId,
				"content": content,
				"workingdays": workingdays,
				"budget": budget
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=" + researchProjectProposalId;
				console.log(rs.result);
			}
		})  -->
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-proposal-content-item"
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
		urlUpdate="/bkeuniv/control/update-a-workpackage-project-proposal" 
		urlAdd="/bkeuniv/control/add-a-workpackage-project-proposal" 
		optionDataAdd={
			"data": {
				"researchProjectProposalId": projectProposalId?j_string
			}
		}
		urlDelete="/bkeuniv/control/delete-a-workpackage-project-proposal" 
		keysId=["researchProjectProposalId","contentItemSeq"] 
		fieldDataResult = "projectProposalContentItems" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabel.TitleProjectSubmissionManagement?j_string
		contextmenu=true
		backToUrl={
			"href": '/bkeuniv/control/detail-research-project-proposal-update?researchProjectProposalId='+ projectProposalId,
			"text": "Quay ve de tai"
		}
	/>
</div>