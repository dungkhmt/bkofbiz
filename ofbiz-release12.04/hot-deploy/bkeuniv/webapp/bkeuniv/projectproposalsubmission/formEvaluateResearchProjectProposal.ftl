<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resource/bkeuniv/css/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>

</style>

${reviewerResearchProposalId}<br>


<div id="form-evaluate-project-proposal">
<table>
<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Li do de tai
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationMotivation" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationMotivation?exists>"${resultReviewProposal.reviewprojectproposal.evaluationMotivation}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Tinh moi
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationInnovation" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationInnovation?exists>"${resultReviewProposal.reviewprojectproposal.evaluationInnovation}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Tinh ung dung
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationApplicability" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationApplicability?exists>"${resultReviewProposal.reviewprojectproposal.evaluationApplicability}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Phuong phap nghien cuu
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationResearchMethod" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationResearchMethod?exists>"${resultReviewProposal.reviewprojectproposal.evaluationResearchMethod}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Noi dung nghien cuu
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationResearchContent" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationResearchContent?exists>"${resultReviewProposal.reviewprojectproposal.evaluationResearchContent}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Bai bao
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationPaper" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationPaper?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPaper}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	San pham
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationProduct" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationProduct?exists>"${resultReviewProposal.reviewprojectproposal.evaluationProduct}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Sang che
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationPatent" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationPatent?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPatent}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Dao tao sau DH
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationGraduateStudent" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationGraduateStudent?exists>"${resultReviewProposal.reviewprojectproposal.evaluationGraduateStudent}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	nghien cuu tre
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationYoungResearcher" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationYoungResearcher?exists>"${resultReviewProposal.reviewprojectproposal.evaluationYoungResearcher}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Dao tao
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationEducation" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationEducation?exists>"${resultReviewProposal.reviewprojectproposal.evaluationEducation}"<#else>"0"</#if>/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Kinh phi hop li
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="evaluationReasonableBudget" style="width: 100%" type="text" width="1000" 
		value=<#if resultReviewProposal.reviewprojectproposal.evaluationReasonableBudget?exists>"${resultReviewProposal.reviewprojectproposal.evaluationReasonableBudget}"<#else>"0"</#if>/>
	</div>
</div>
</td>
</tr>

</table>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateReviewProjectProposal"/>
<@buttonConfirm text="${bkEunivUiLabelMap.BkEunivConfirm}" action="confirmReviewProjectProposal"/>
</div>


<script>


function updateReviewProjectProposal(){
	var evaluationMotivation = document.getElementById("evaluationMotivation").value;
	var evaluationInnovation = document.getElementById("evaluationInnovation").value;
	var evaluationApplicability = document.getElementById("evaluationApplicability").value;
	var evaluationResearchMethod = document.getElementById("evaluationResearchMethod").value;
	
	var evaluationResearchContent = document.getElementById("evaluationResearchContent").value;
	var evaluationPaper = document.getElementById("evaluationPaper").value;
	var evaluationProduct = document.getElementById("evaluationProduct").value;
	var evaluationPatent = document.getElementById("evaluationPatent").value;
	var evaluationGraduateStudent = document.getElementById("evaluationGraduateStudent").value;
	var evaluationYoungResearcher = document.getElementById("evaluationYoungResearcher").value;
	var evaluationEducation = document.getElementById("evaluationEducation").value;
	var evaluationReasonableBudget = document.getElementById("evaluationReasonableBudget").value;
	
	
	$.ajax({
			url: "/bkeuniv/control/update-review-project-proposal",
			type: 'POST',
			data: {
				"reviewerResearchProposalId": ${reviewerResearchProposalId},
				"evaluationInnovation": evaluationInnovation,
				"evaluationMotivation": evaluationMotivation,
				"evaluationApplicability": evaluationApplicability,
				"evaluationResearchMethod": evaluationResearchMethod,
				
				"evaluationResearchContent": evaluationResearchContent,
				"evaluationPaper": evaluationPaper,
				"evaluationProduct": evaluationProduct,
				"evaluationPatent": evaluationPatent,
				"evaluationGraduateStudent": evaluationGraduateStudent,
				"evaluationYoungResearcher": evaluationYoungResearcher,
				"evaluationEducation": evaluationEducation,
				"evaluationReasonableBudget": evaluationReasonableBudget
			},
			success: function(rs){
				//window.location.href = "/bkeuniv/control/research-project-jury-members-evaluate";
				//console.log(rs.result);
			}
		})
}

function confirmReviewProjectProposal(){
	
	$.ajax({
			url: "/bkeuniv/control/confirm-review-project-proposal",
			type: 'POST',
			data: {
				"reviewerResearchProposalId": ${reviewerResearchProposalId}
			},
			success: function(rs){
				//window.location.href = "/bkeuniv/control/research-project-jury-members-evaluate";
				//console.log(rs.result);
			}
		})
}
</script>