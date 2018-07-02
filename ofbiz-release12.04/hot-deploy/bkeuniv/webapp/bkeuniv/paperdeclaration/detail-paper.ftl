
<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:15px
	}
	.modal-dialog {
		width: 60%!important;
		margin-left: 20%!important;
	}
</style>

<div class="info-table">

<table>
<tr>
	<td>
		${paperDeclarationUiLabelMap.BkEunivPaperName}
	</td>
	<td>
		${resultPaper.paper.paperName}
	</td>
</tr>
<tr>
	<td>
		${paperDeclarationUiLabelMap.BkEunivAuthors}
	</td>
	<td>
		<#if resultPaper.paper.authors?exists>
			${resultPaper.paper.authors}
		</#if>
	</td>
</tr>

<tr>
	<td>
		${paperDeclarationUiLabelMap.BkEunivPaperCategory}
	</td>
	<td>
		<#if resultPaper.paper.paperCategoryName?exists>
			${resultPaper.paper.paperCategoryName}
		</#if>
	</td>
</tr>

<tr>
	<td>
		${paperDeclarationUiLabelMap.BkEunivPaperJournalConference}
	</td>
	<td>
		<#if resultPaper.paper.journalConferenceName?exists>
			${resultPaper.paper.journalConferenceName}
		</#if>
	</td>
</tr>
<tr>
	<td>
		${paperDeclarationUiLabelMap.BkEunivPaperYear}
	</td>
	<td>
		<#if resultPaper.paper.year?exists>
			${resultPaper.paper.year}
		</#if>
	</td>
</tr>
<tr>
	<td>
		Thuoc de tai
	</td>
	<td>
		<#if resultPaper.paper.researchProjectProposalName?exists>
			${resultPaper.paper.researchProjectProposalName}
		</#if>
	</td>
</tr>

<tr>
	<td>
		<a href="/bkeuniv/control/download-file-paper?id-paper=${resultPaper.paper.paperId}">${paperDeclarationUiLabelMap.BkEunivDownloadPaper}</a>

	</td>
</tr>

</table>

