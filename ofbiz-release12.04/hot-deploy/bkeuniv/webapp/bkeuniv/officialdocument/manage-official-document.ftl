<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	function createContextMenu(id) {
		$(document).contextmenu({
			delegate: '#'+id+ ' td',
		menu: [
			<#-- {title: '${uiLabelMap.BkEunivReview}', cmd: "review", uiIcon: "fa fa-eye"}, -->
			{title: '${uiLabelMap.BkEunivDownload}', cmd: "download", uiIcon: "fa fa-download"},
			{title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
            {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
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
                case "edit":
                    jqChange(data)
                    break;
                case "delete":
                    jqDelete(data);
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

    function getLinkDownLoad(data) {
        return "/bkeuniv/control/download-file-official-document?officialDocumentId="+data.officialDocumentId;
    }
</script>

<div class="body">

	<#assign columns=[
		{
			"name": uiLabelMap.officialDocumentName?j_string,
			"data": "officialDocumentName",
			"render": 'function(value, name, dataColumns, id) {
                return "<a href=\\"/bkeuniv/control/download-file-official-document?officialDocumentId="+dataColumns.officialDocumentId+"\\">" + value + "</a>";
			}'
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

    <#assign columnsChange=[
		{
			"name": uiLabelMap.officialDocumentName?j_string,
			"value": "officialDocumentName"
		},
		{
			"name": uiLabelMap.officialDocumentTypeName?j_string,
			"value": "officialDocumentTypeId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {id: r.officialDocumentTypeId, text:r.officialDocumentTypeName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListOfficialDocumentType"
			}
		},
    	{
			"name": "Upload"?j_string,
			"value": "file",
			"type": "dropify",
            "accept": ".doc, .docx, .pdf, .csv, .xls, .xlsx"
		}
	] />
	
	<#assign fields=[
		"staffId",
        "staffName",
		"officialDocumentId",
		"officialDocumentName",
		"officialDocumentTypeName",
		"officialDocumentTypeId",
		"uploadDate",
        {
            "name": "file",
            "generate": "getLinkDownLoad"
        }
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListOfficialDocument" 
        urlUpdate="/bkeuniv/control/update-official-document"
		urlDelete="/bkeuniv/control/delete-official-document"
		columns=columns 
        columnsChange=columnsChange
		dataFields=fields
		sizeTable=sizeTable
		keysId=["officialDocumentId"]
		fieldDataResult = "results"
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
		titleDelete=uiLabelMap.BkEunivConfirmToDelete
	/>

</div>