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
			"name": "Staff Name"?j_string,
			"data": "staffName"
		},
		{
			"name": "Role Type"?j_string,
			"data": "roleTypeName"
		}
	] />
	
	<#assign fields=[
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

	<#assign columnsChange=[
		{
			"name": "Staff Name"?j_string,
			"value": "staffId"
		
		},
		{
			"name": "Role Type Name"?j_string,
			"value": "roleTypeName"
		}
	
	] />
	
	<#assign columnsNew=[
		{
			"name": "Staff Name"?j_string,
			"value": "staffId"
			<#--  "type":"select",
			"option":{
				"source": staffs,
				"maxItem": 1
			}
		  -->
		},
		{
			"name": "Role Type"?j_string,
			"value": "roleTypeId"
			<#--  "type": "select",
			"option":{
				"source": roleTypeList,
				"maxItem": 1
			}  -->
		
		}
		]
	 />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-members-of-project-proposals?researchProjectProposalId=${projectProposalId}"
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="" 
		urlAdd="/bkeuniv/control/create-a-member-of-project-proposal?researchProjectProposalId=${projectProposalId}" 
		urlDelete="" 
		keysId=["researchProjectProposalId","staffId"] 
		fieldDataResult = "members" 
		titleChange=""
		titleNew=""
		titleDelete=""
		jqTitle=uiLabel.TitleProjectSubmissionManagement?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
