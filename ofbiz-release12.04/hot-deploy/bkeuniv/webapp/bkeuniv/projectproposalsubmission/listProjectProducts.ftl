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
			"name": uiLabelMap.BkEunivProductType?j_string,
			"data": "researchProductTypeName"
		},
		{
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"data": "researchProductName"
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
		"researchProductName",
		"quantity"
	] />
	
	<#assign productTypes=[]/> 
	<#list resultProjectProductTypes.projectProposalProductTypes as pt> 
		<#if pt?has_content> 
				<#assign op = { "name": pt.researchProductTypeName?j_string ,"value": pt.researchProductTypeId?j_string } /> 
				<#assign productTypes = productTypes + [op] /> 
		</#if> 
	</#list>
	
	<#assign columnsChange=[
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
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"value": "researchProductName"
		},
		
		{
			"name": uiLabelMap.BkEunivQuantity?j_string,
			"value": "quantity",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong"
		}
	] />

	

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
			"name": uiLabelMap.BkEunivResearchProductName?j_string,
			"value": "researchProductName"
		},
		{
			"name": uiLabelMap.BkEunivQuantity?j_string,
			"value": "quantity",
			"pattern": "[1-9]([0-9]{0,2})",
			"title": "So nguyen duong"
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
		urlUpdate="/bkeuniv/control/update-a-project-proposal-product-type" 
		optionDataUpdate={
			"data": {
				"researchProjectProposalId": projectProposalId?j_string
			}
		}
		
		urlAdd="/bkeuniv/control/add-a-project-proposal-product-type" 
		optionDataAdd={
			"data": {
				"researchProjectProposalId": projectProposalId?j_string
			}
		}
		urlDelete="/bkeuniv/control/remove-a-project-proposal-product-type" 
		keysId=["researchProductId"] 
		fieldDataResult = "projectProposalProducts" 
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