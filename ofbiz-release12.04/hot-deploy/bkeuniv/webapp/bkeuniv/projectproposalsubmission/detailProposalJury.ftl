
<style>
#form-style {
    margin-top: 20px;
    margin-left: 20px;
}
</style>

<div id="form-style">

<table>
	<tr>
		<td><a href = "/bkeuniv/control/research-project-jury-members?juryId=${resultJury.projectproposaljury.juryId}">Thanh vien HD phan bien</a></td>
	</tr>

	<tr>
		<td><a href = "/bkeuniv/control/research-project-jury-assgin-reviewer?juryId=${resultJury.projectproposaljury.juryId}">Phan cong phan bien</a></td>
	</tr>
	
	
	<tr>
		<td>${resultJury.projectproposaljury.juryName}</td>
	</tr>
	<tr>
		<table>
			<tr>
				<td>Thanh vien</td>
				<td>Vai tro</td>
				<td>Danh sach de tai duoc phan cong phan bien</td>
			</tr>
			<#list resultMembers.members as m>
				<tr>
					<td>
						${m.staffName}
					</td>
					<td>
						${m.juryRoleTypeName}
					</td>
					<td>
						<#list m.projectproposals as p>
							${p.researchProjectProposalName}<br>
						</#list>
					</td>
				</tr>
			</#list>
		</table>
	</tr>
</table>

</div>
