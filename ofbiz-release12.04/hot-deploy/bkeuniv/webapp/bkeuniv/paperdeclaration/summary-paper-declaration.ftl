<table>
<#list resultPapers.papers as p>
	<tr>
		<td>${p.staffName}</td>
		<td>${p.paperName}</td>
		<td>${p.categoryName}</td>
	</tr>
</#list>
</table>