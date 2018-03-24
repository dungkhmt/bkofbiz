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
<a href="/bkeuniv/control/detail-research-project-proposal-update?researchProjectProposalId=${projectProposalId}">Quay ve de tai</a>

		<#assign columns=[
		{
			"name": uiLabelMap.BkEunivProductType?j_string,
			"data": "researchProductTypeName"
		},
		{
			"name": uiLabelMap.BkEunivQuantity?j_string,
			"data": "quantity"
		}
	] />

	<#assign fields=[
		"researchProjectProposalId",
		"researchProductId",
		"researchProductTypeId",
		"researchProductTypeName",
		"quantity"
	] />
	<#assign columnsChange=[
		{
			"name": uiLabelMap.BkEunivProductType?j_string,
			"value": "researchProductTypeId"
		},
		{
			"name": uiLabelMap.BkEunivQuantity?j_string,
			"value": "quantity"
		}
	] />

	<#assign productTypes=[]/> 
	<#list resultProjectProductTypes.projectProposalProductTypes as pt> 
		<#if pt?has_content> 
				<#assign op = { "name": pt.researchProductTypeName?j_string ,"value": pt.researchProductTypeId?j_string } /> 
				<#assign productTypes = productTypes + [op] /> 
		</#if> 
	</#list>

	<#assign columnsNew=[
		{
			"name": uiLabelMap.BkEunivProductType?j_string,
			"value": "researchProductTypeId",
			"type": "select",
			"option":{
				"source": productTypes,
				"maxItem": 1
			}
		},
		{
			"name": uiLabelMap.BkEunivQuantity?j_string,
			"value": "quantity"
		}		
		]
	 />



	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-proposal-products"
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
		urlUpdate="" 
		urlAdd="/bkeuniv/control/add-a-project-proposal-product-type" 
		optionDataAdd={
			"data": {
				"researchProjectProposalId": projectProposalId?j_string
			}
		}
		urlDelete="" 
		keysId=["researchProjectProposalId"] 
		fieldDataResult = "projectProposalProducts" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabel.TitleProjectSubmissionManagement?j_string
		contextmenu=false
	/>
</div>