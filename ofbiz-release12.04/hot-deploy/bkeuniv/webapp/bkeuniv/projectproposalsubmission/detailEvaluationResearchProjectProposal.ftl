
<style>
#form-view {
    margin-top: 20px;
    margin-left: 20px;
    overflow: scroll;
    width: 100%
}
</style>

<div id="form-view">
<table border = "1">
<#list resultEvaluation.reviewprojectproposals as e>
<tr>
	<td>
		<table border="1">
			<tr>
				<td> Thanh vien hoi dong </td>
				<!--
				<td>${e.staffName}</td>
				-->
				<td>Anonymous</td>
			</tr>
			<tr>	
				<td> Li do de tai </td>
				<td>${e.evaluationMotivation}</td>
				<td> Tinh moi </td>
				<td>${e.evaluationInnovation}</td>
			<tr>
				<td> TInh ung dung </td>
				<td>${e.evaluationApplicability}</td>
				<td> Phuong phap nghien cuu </td>
				<td>${e.evaluationResearchMethod}</td>
			</tr>
			<tr>
				<td> Noi dung </td>
				<td>${e.evaluationResearchContent}</td>
				<td> Bai bao </td>
				<td>${e.evaluationPaper}</td>
			</tr>
			<tr>
				<td> San pham </td>
				<td>${e.evaluationProduct}</td>
				<td> Sang che </td>
				<td>${e.evaluationPatent}</td>
			</tr>
			<tr>
				<td> Dao tao Sau dai hoc </td>
				<td>${e.evaluationGraduateStudent}</td>
				<td> Nhom nghien cuu tre </td>
				<td>${e.evaluationYoungResearcher}</td>
			</tr>
			<tr>
				<td> Dao tao </td>
				<td>${e.evaluationEducation}</td>
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
<tr>
	<td>-----------------------------------------------------------------------------------</td>
</tr>
</#list>
</table>
</div>