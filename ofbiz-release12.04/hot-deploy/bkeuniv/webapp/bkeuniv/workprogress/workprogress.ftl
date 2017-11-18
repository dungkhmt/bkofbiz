<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<script>
	function createContextMenu(id) {
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
				menu: [
			  		{title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  		{title: '${workProgressUiLabelMap.DowloadPDF}', cmd: "pdf", uiIcon: "glyphicon glyphicon-trash"},
			  		{title: '${workProgressUiLabelMap.ExportExcel}', cmd: "csv", uiIcon: "glyphicon glyphicon-trash"},
			  		{title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
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
						case "pdf":
							jqPDF(data);
							break;
						case "csv":
							jqCSV(data);
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
	
	function jqPDF(data){
  		alertify.confirm('Dowload file "Work_Progress.pdf"',
 			function(){
 			window.open("/bkeuniv/control/dowload-pdf");
   			 	alertify.success('Success');
  			},
  			function(){
    			alertify.error('Cancel');
  		});
	}	
	
	function jqCSV(data){
  		alertify.confirm('Dowload file "Work_Progress.xls"',
 			function(event){
   			 	window.open("/bkeuniv/control/export-excel-work-progress");
   			 	alertify.success('Success');
  			},
  			function(){
    			alertify.error('Cancel');
  		});
	}			
	
</script>

<div class="body">
	<#assign columns=[
		{
			"name": workProgressUiLabelMap.WorkProgressId?j_string,
			"data": "workProgressId"
		},
		{
			"name": workProgressUiLabelMap.Period?j_string,
			"data": "period"
		},
		{
			"name": workProgressUiLabelMap.Position?j_string,
			"data": "position"
		},
		{
			"name": workProgressUiLabelMap.Specialization?j_string,
			"data": "specialization"
		},
		{
			"name": workProgressUiLabelMap.Institution?j_string,
			"data": "institution"
		}
	] />
	
	<#assign fields=[
		"workProgressId",
		"period",
		"position",
		"specialization",
		"institution"
	] />
	
	<#assign columnsChange=[
		{
			"name": workProgressUiLabelMap.Period?j_string,
			"value": "period"
		},
		{
			"name": workProgressUiLabelMap.Position?j_string,
			"value": "position"
		},
		{
			"name": workProgressUiLabelMap.Specialization?j_string,
			"value": "specialization"
		},
		{
			"name": workProgressUiLabelMap.Institution?j_string,
			"value": "institution"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": workProgressUiLabelMap.Period?j_string,
			"value": "period"
		},
		{
			"name": workProgressUiLabelMap.Position?j_string,
			"value": "position"
		},
		{
			"name": workProgressUiLabelMap.Specialization?j_string,
			"value": "specialization"
		},
		{
			"name": workProgressUiLabelMap.Institution?j_string,
			"value": "institution"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-work-progress" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-work-progress" 
		urlAdd="/bkeuniv/control/create-work-progress" 
		urlDelete="/bkeuniv/control/delete-work-progress" 
		keysId=["workProgressId"] 
		fieldDataResult = "workProgress" 
		titleChange=workProgressUiLabelMap.TitleEditWorkProgress?j_string
		titleNew=workProgressUiLabelMap.TitleNewWorkProgress?j_string
		titleDelete=workProgressUiLabelMap.TitleDeleteWorkProgress?j_string
		jqTitle=workProgressUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
