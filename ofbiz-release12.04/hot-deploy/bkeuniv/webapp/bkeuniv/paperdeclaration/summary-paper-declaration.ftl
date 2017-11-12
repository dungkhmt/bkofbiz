<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

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
				<th>${paperDeclarationUiLabelMap.BkEunivPaperName}</th>
				<th>${paperDeclarationUiLabelMap.BkEunivPaperCategory}</th>
			</tr>
		</thead>
	<tbody>
	<#list resultPapers.papers as p>
		<tr>
			<td>${p.staffName}</td>
			<td>${p.paperName}</td>
			<td>${p.categoryName}</td>
		</tr>
	</#list>
	</tbody>
	</table>
</div>
<script>
$(document).ready(function() {
    $('#list-papers').DataTable();
} );
</script>