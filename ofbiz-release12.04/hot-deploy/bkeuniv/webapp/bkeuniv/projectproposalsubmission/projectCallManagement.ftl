<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign sourceProjectCallStatus = [] />
	<#list listProjectCallStatus.projectCallStatus as pcs>
		<#if pcs?has_content>
             <#assign op = { "name": pcs.statusName?j_string ,"value": pcs.statusId?j_string } />
						<#assign sourceProjectCallStatus = sourceProjectCallStatus + [op] />
		</#if>
	</#list>

	<#assign sourceProjectCategory = [] />
	<#list listProjectCategory.projectCategory as pc>
		<#if pc?has_content>
             <#assign op = { "name": pc.projectCategoryName?j_string ,"value": pc.projectCategoryId?j_string } />
						<#assign sourceProjectCategory = sourceProjectCategory + [op] />
		</#if>
	</#list>
	
	<#assign columns=[
		{
			"name": "Ten dot goi de tai",
			"data": "projectCallName"
		},
		
		{
			"name": "Loai hinh de tai",
			"data": "projectCategoryName"
		},
		{
			"name": "Nam",
			"data": "year"
		}
		,
		{
			"name": "Trang thai",
			"data": "statusName"
		}
	] />

    <#assign fields=[
		"projectCallId",
		"projectCallName",
		"projectCategoryName",
		"year",
		"projectCategoryId",
		"statusId",
		"statusName"
	] />

	<#assign columnsChange=[
		{
			"name": "Ten dot goi de tai",
			"value": "projectCallName"
		},
		{
			"name": "Loai hinh de tai",
			"value": "projectCategoryId",
			"type": "select",
			"option": {
				"source": sourceProjectCategory,
				"maxIterm": 1
			}
		},
		{
			"name": "Nam",
			"value": "year"
		}
	] />

	<#assign columnsNew=[
		{
			"name": "Ten dot goi de tai",
			"value": "projectCallName"
		},
		{
			"name": "Loai hinh de tai",
			"value": "projectCategoryId",
			"type": "select",
			"option": {
				"source": sourceProjectCategory,
				"maxIterm": 1
			}
		},
		{
			"name": "Nam",
			"value": "year"
		}
	] />
	

	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/get-list-project-calls" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-project-call" 
		urlAdd="/bkeuniv/control/create-a-project-call" 
		urlDelete="/bkeuniv/control/remove-project-call" 
		keysId=["projectCallId"] 
		fieldDataResult = "projectCalls" 
		titleChange="Chinh sua"
		titleNew="Tao moi"
		titleDelete="Xoa"
		jqTitle="Quan ly dot goi de tai"
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>

<script>
	var modal;
	var span;
	var selectedEntry;
	function createContextMenu(id) {
		
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
			menu: [
			  {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"},
			  {title: 'Dong dot goi de tai', cmd: "closeprojectcall", uiIcon: "glyphicon glyphicon-user"},
			  {title: 'Mo dot goi de tai cho dang ky moi', cmd: "openprojectcallforsubmission", uiIcon: "glyphicon glyphicon-user"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var data = jqDataTable.table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						jqChange(data)
						break;
					case "delete":
						jqDelete(data);
						break;
					case "closeprojectcall":
						closeProjectCall(data);
						break;
					case "openprojectcallforsubmission":
						openProjectCallForSubmission(data);
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
	
	function closeProjectCall(data){
		//alert("Dong dot goi de tai" + data.projectCallId);
		
		alertify.confirm('Xac nhan dong de tai', "Ban co muon thuc su dong khong?",
		function(){
				alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/close-project-call",
					type: 'POST',
					data: {
						"projectCallId": data.projectCallId
					},
					success:function(rs){
						console.log(rs);
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});
		
	}

	function openProjectCallForSubmission(data){
		//alert("Dong dot goi de tai" + data.projectCallId);
		
		alertify.confirm('Xac nhan mo de tai', "Ban co muon thuc su muon mo khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/open-project-call-for-submission",
					type: 'POST',
					data: {
						"projectCallId": data.projectCallId
					},
					success:function(rs){
						console.log(rs);
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});
		
	}

</script>

<#--      

<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

  <head>

    <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/south-street/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    
    <script src="https://cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
    
    <meta charset="utf-8" />
    
    <title>DataTables - Context menu integration</title>
  
  </head>
  
<@Loader handleToggle="loader">
	<@IconSpinner/>
</@Loader>

<script>
  	loader.open();
  </script>

<a href="/bkeuniv/control/form-add-project-call">Them moi dot goi de tai</a>
<div id="table-list" style="overflow-y: auto; padding: 2em;">
	<table id="list" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>Ten dot goi de tai</th>
				<th>Loai hinh de tai</th>
				<th>Nam</th>
				
			</tr>
		</thead>
	<tbody>
	<#list resultProjectCalls.projectCalls as pc>
		<tr>
			<td style="display: none">${pc.projectCallId}</td>
			<#if pc.projectCallName?exists>
				<td>${pc.projectCallName}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.projectCategoryName?exists>
				<td>${pc.projectCategoryName}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.year?exists>
				<td>${pc.year}</td>
			<#else>
				<td></td>
			</#if>
			
			
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>
<script>
var obj;

$(document).ready(function() {
  loader.close();
  var oTable = $('#list').dataTable({
    "bJQueryUI": true,
    "sDom": 'l<"H"Rf>t<"F"ip>'
  });
  $(document).contextmenu({
    delegate: ".dataTable td",
    menu: [
      {title: "Delete", cmd: "delete"},
      {title: "Edit", cmd: "edit"}
    ],
    select: function(event, ui) {
        switch(ui.cmd){
            case "delete":
                $(ui.target).parent().remove();
                break;
            case "edit":
				obj = ui;
				var el = ui.target.parent();
				var paperId = el.children()[0].innerHTML;
				alert('edit paper ' + paperId);
			    break;
        }
    },
    beforeOpen: function(event, ui) {
        var $menu = ui.menu,
            $target = ui.target
        ui.menu.zIndex(0);
    }
  });
    
} );
</script>  -->