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
$(document).ready(function() {
    $('#list-papers').DataTable();
} );
</script>