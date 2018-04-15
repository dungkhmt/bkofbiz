

<table>
<tr>
	<td>
	Ten bai bao
	</td>
	<td>
		${resultPaper.paper.paperName}
	</td>
</tr>
<tr>
	<td>
		Tac gia
	</td>
	<td>
		<#if resultPaper.paper.authors?exists>
			${resultPaper.paper.authors}
		</#if>
	</td>
</tr>
<tr>
	<td>
		Hoi nghi, tap chi
	</td>
	<td>
		<#if resultPaper.paper.journalConferenceName?exists>
			${resultPaper.paper.journalConferenceName}
		</#if>
	</td>
</tr>
<tr>
	<td>
		Nam xuat ban
	</td>
	<td>
		<#if resultPaper.paper.year?exists>
			${resultPaper.paper.year}
		</#if>
	</td>
</tr>
<tr>
	<td>
		<a href="/bkeuniv/control/download-file-paper?id-paper=${resultPaper.paper.paperId}">Download file minh chung</a>

	</td>
</tr>

</table>