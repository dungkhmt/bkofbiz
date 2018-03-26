
<style>
#form-style {
    margin-top: 20px;
    margin-left: 20px;
    overflow: scroll;
    width: 100%
}
</style>

<div id="form-style">

<table>
	<tr>
		<td><a href = "/bkeuniv/control/research-project-jury-members?juryId=${resultJury.projectproposaljury.juryId}">${uiLabel.JuryMembers}</a></td>
	</tr>

	<tr>
		<td><a href = "/bkeuniv/control/research-project-jury-assgin-reviewer?juryId=${resultJury.projectproposaljury.juryId}">${uiLabel.AssignEvaluation}</a></td>
	</tr>
	
	
	<tr>
		<td>${resultJury.projectproposaljury.juryName}</td>
	</tr>
	<tr>
		<table>
			<tr>
				<td>${uiLabel.FullName}</td>
				<td>${uiLabel.Role}</td>
				<td>${uiLabel.ListAssignedProposalForEvaluation}</td>
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
