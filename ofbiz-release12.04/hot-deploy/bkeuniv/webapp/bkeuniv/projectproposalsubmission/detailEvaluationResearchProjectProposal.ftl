
<style>
#form-view {
    margin-top: 20px;
    margin-left: 20px;
    overflow: scroll;
}
</style>

<div id="form-view">
<table border = "1">
<#list resultEvaluation.reviewprojectproposals as e>
<tr>
	<td>
		<table>
			<tr>
				<td> Thanh hoi dong </td>
				<td>${e.staffName}</td>
			</tr>
			<tr>
				<td> Li do de tai </td>
				<td>${e.evaluationMotivation}</td>
			</tr>
			<tr>
				<td> Tinh moi </td>
				<td>${e.evaluationInnovation}</td>
			</tr>
			<tr>
				<td> TInh ung dung </td>
				<td>${e.evaluationApplicability}</td>
			</tr>
			<tr>
				<td> Phuong phap nghien cuu </td>
				<td>${e.evaluationResearchMethod}</td>
			</tr>
			<tr>
				<td> Noi dung </td>
				<td>${e.evaluationResearchContent}</td>
			</tr>
			<tr>
				<td> Bai bao </td>
				<td>${e.evaluationPaper}</td>
			</tr>
			<tr>
				<td> San pham </td>
				<td>${e.evaluationProduct}</td>
			</tr>
			<tr>
				<td> Sang che </td>
				<td>${e.evaluationPatent}</td>
			</tr>
			<tr>
				<td> Dao tao Sau dai hoc </td>
				<td>${e.evaluationGraduateStudent}</td>
			</tr>
			<tr>
				<td> Nhom nghien cuu tre </td>
				<td>${e.evaluationYoungResearcher}</td>
			</tr>
			<tr>
				<td> Dao tao </td>
				<td>${e.evaluationEducation}</td>
			</tr>
			<tr>
				<td> Kinh phi hop li </td>
				<td>${e.evaluationReasonableBudget}</td>
			</tr>
			
			<tr>
				<td> Tong diem </td>
				<td>${e.totalEvaluation}</td>
			</tr>
			
		</table>
	</td>
</tr>
</#list>
</table>
</div>