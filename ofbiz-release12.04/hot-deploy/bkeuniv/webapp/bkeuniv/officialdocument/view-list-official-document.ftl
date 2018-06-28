<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	function createContextMenu(id) {
		$(document).contextmenu({
			delegate: '#'+id+ ' td',
		menu: [
			{title: '${uiLabelMap.BkEunivReview}', cmd: "review", uiIcon: "fa fa-eye"},
			{title: '${uiLabelMap.BkEunivDownload}', cmd: "download", uiIcon: "fa fa-download"},
			<#--  {title: '${uiLabelMap.BkEunivRemove}', cmd: "remove", uiIcon: "glyphicon glyphicon-trash"}  -->
		],
		select: function(event, ui) {
			var el = ui.target.parent();
			var data = jqDataTable.table.row( el ).data();
			switch(ui.cmd){
				case "review":
					window.open("/bkeuniv/control/download-file-official-document?type=inline&officialDocumentId="+data.officialDocumentId, "_blank");
					break;
				case "download":
					window.open("/bkeuniv/control/download-file-official-document?officialDocumentId="+data.officialDocumentId, "_blank");
					break;
				case "remove":
					
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
</script>

<div class="body">

	<#assign columns=[
		{
			"name": uiLabelMap.officialDocumentId?j_string,
			"data": "officialDocumentId"
		},
		{
			"name": uiLabelMap.officialDocumentName?j_string,
			"data": "officialDocumentName"
		},
		{
			"name": uiLabelMap.officialDocumentTypeName?j_string,
			"data": "officialDocumentTypeName"
		},
		{
			"name": uiLabelMap.staffName?j_string,
			"data": "staffName"

		},
    	{
			"name": uiLabelMap.uploadDate?j_string,
			"data": "uploadDate",
			"type": "date"
		}
	] />
	
	<#assign fields=[
		"staffId",
		"officialDocumentId",
		"officialDocumentName",
		"officialDocumentTypeName",
		"officialDocumentTypeId",
		"uploadDate"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListOfficialDocument" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		urlDelete="/bkeuniv/control/remove-a-staff" 
		keysId=["officialDocumentId"] 
		fieldDataResult = "results"
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>

</div>