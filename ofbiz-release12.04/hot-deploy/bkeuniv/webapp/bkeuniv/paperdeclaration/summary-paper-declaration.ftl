

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
<div id="table-list-paper" style="overflow-y: auto; padding: 2em;">
	<table id="list-papers" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
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
			<td style="display: none">${p.paperId}</td>
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
  loader.close();
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
</script>