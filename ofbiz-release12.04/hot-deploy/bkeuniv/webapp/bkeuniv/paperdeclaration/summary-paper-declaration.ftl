<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

  <head>

    <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/south-street/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="https://cdn.datatables.net/plug-ins/725b2a2115b/integration/jqueryui/dataTables.jqueryui.css" rel="stylesheet" type="text/css" />
    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    <script src="https://cdn.datatables.net/1.10.2/js/jquery.dataTables.js"></script>
    <script src="https://cdn.datatables.net/colreorder/1.1.2/js/dataTables.colReorder.min.js"></script>
    <script src="https://cdn.datatables.net/plug-ins/725b2a2115b/api/fnFilterClear.js"></script>
    
    <script src="https://cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
    
    <meta charset="utf-8" />
    
    <title>DataTables - Context menu integration</title>
  
  </head>

<style>
#table-list-paper {
    margin-top: 60px;
}
</style>
<div id="table-list-paper">
	<table id="list-papers" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th>${paperDeclarationUiLabelMap.BkEunivStaffId}</th>
				<th>Tac gia</th>
				<th>${paperDeclarationUiLabelMap.BkEunivPaperName}</th>
				<th>${paperDeclarationUiLabelMap.BkEunivPaperCategory}</th>
				<th>Ten hoi nghi, tap chi</th>
				<th>Nam</th>
				<th>Vol. number</th>
				<th>ISSN</th>
				<th>Nam ke khai</th>
			</tr>
		</thead>
	<tbody>
	<#list resultPapers.papers as p>
		<tr>
			<td>${p.staffName}</td>
			<#if p.authors?exists>
				<td>${p.authors}</td>
			<#else>
				<td></td>
			</#if>
			<#if p.paperName?exists>
				<td>${p.paperName}</td>
			<#else>
				<td></td>
			</#if>
			<#if p.paperCategoryName?exists>
				<td>${p.paperCategoryName}</td>
			<#else>
				<td></td>
			</#if>
			
			<#if p.journalConferenceName?exists>
				<td>${p.journalConferenceName}</td>
			<#else>
				<td></td>
			</#if>
			<td>${p.year}</td>
			<#if p.volumn?exists>
				<td>${p.volumn}</td>
			<#else>
				<td></td>
			</#if>
			
			<#if p.ISSN?exists>
				<td>${p.ISSN}</td>
			<#else>
				<td></td>
			</#if>
			<td>${p.academicYearId}</td>
		</tr>
	</#list>
	</tbody>
	</table>
</div>
<script>
var obj;
$(document).ready(function() {
    //$('#list-papers').DataTable();
    
  var oTable = $('#list-papers').dataTable({
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
				obj = ui.target;
				var el = ui.target.parent();
				console.log(el);
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
</script>