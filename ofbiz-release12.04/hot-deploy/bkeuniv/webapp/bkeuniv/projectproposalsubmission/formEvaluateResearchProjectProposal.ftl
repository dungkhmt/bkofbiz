<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<body>
<div class="body" style="overflow-y: scroll;">
	<style>
	#form-style {
		margin-top: 20px;
		margin-left: 20px;
		width: 100%;
		overflow-y: scroll;
	}
	</style>

	<div id="form-style">
	<!--
	${reviewerResearchProposalId}<br>
	-->

	<div id="form-style">
	<table>
	<tr>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Motivation}
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
		${uiLabel.Innovation}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationInnovation" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationInnovation?exists>"${resultReviewProposal.reviewprojectproposal.evaluationInnovation}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<!--
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Applicability}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationApplicability" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationApplicability?exists>"${resultReviewProposal.reviewprojectproposal.evaluationApplicability}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	-->
	
	</tr>

	<tr>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.ResearchMethod}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationResearchMethod" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationResearchMethod?exists>"${resultReviewProposal.reviewprojectproposal.evaluationResearchMethod}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.ResearchContent}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationResearchContent" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationResearchContent?exists>"${resultReviewProposal.reviewprojectproposal.evaluationResearchContent}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<!--
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Papers}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationPaper" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationPaper?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPaper}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Products}
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
		${uiLabel.Patent}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationPatent" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationPatent?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPatent}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	-->
	</tr>
	<tr>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Product1}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationProduct1" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationProduct1?exists>"${resultReviewProposal.reviewprojectproposal.evaluationProduct1}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>

	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Product2}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationProduct2" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationProduct2?exists>"${resultReviewProposal.reviewprojectproposal.evaluationProduct2}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>

	</tr>

	<tr>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Promote1}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationPromote1" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationPromote1?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPromote1}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Promote2}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationPromote2" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationPromote2?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPromote2}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Promote3}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationPromote3" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationPromote3?exists>"${resultReviewProposal.reviewprojectproposal.evaluationPromote3}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
		
	</tr>

	<!--
	<tr>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.GraduateStudentTraining}
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
		${uiLabel.YoungResearcher}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationYoungResearcher" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationYoungResearcher?exists>"${resultReviewProposal.reviewprojectproposal.evaluationYoungResearcher}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	<td>
	<div class="inline-box" style="width: 50%; padding: 10px 0px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Training}
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
		${uiLabel.ReasonableBudget}
		</div>
		<div style="display: inline-block;width: 69%;">
			<input id="evaluationReasonableBudget" style="width: 100%" type="text" width="1000" 
			value=<#if resultReviewProposal.reviewprojectproposal.evaluationReasonableBudget?exists>"${resultReviewProposal.reviewprojectproposal.evaluationReasonableBudget}"<#else>"0"</#if>/>
		</div>
	</div>
	</td>
	</tr>
	-->
	
	
	</table>
	<div class="inline-box" style="width: 90%; padding: 10px 5px;">
		<div style="display: inline-block;width: 100%;">
		${uiLabel.Comments}
		</div>
		<div style="display: inline-block;width: 100%;">
			<textarea id="comments"><#if resultReviewProposal.reviewprojectproposal.comments??>${resultReviewProposal.reviewprojectproposal.comments}</#if></textarea>
		</div>
		<script>
		$( document ).ready(function() {
			$(function() {
				$('textarea#comments').bkeunivEditor({
					charCounterCount : false,
					language: 'vi',
					height: 200,
				});
			});
		});
		</script>
	</div>	
	<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateReviewProjectProposal"/>
	<@buttonConfirm text="${bkEunivUiLabelMap.BkEunivConfirm}" action="confirmReviewProjectProposal"/>
	</div>


	<script>


	function updateReviewProjectProposal(){
		var evaluationMotivation = document.getElementById("evaluationMotivation").value;
		var evaluationInnovation = document.getElementById("evaluationInnovation").value;
		//var evaluationApplicability = document.getElementById("evaluationApplicability").value;
		var evaluationResearchMethod = document.getElementById("evaluationResearchMethod").value;
		var evaluationResearchContent = document.getElementById("evaluationResearchContent").value;
		var evaluationProduct1 = document.getElementById("evaluationProduct1").value;
		var evaluationProduct2 = document.getElementById("evaluationProduct2").value;
		
		var evaluationPromote1 = document.getElementById("evaluationPromote1").value;
		var evaluationPromote2 = document.getElementById("evaluationPromote2").value;
		var evaluationPromote3 = document.getElementById("evaluationPromote3").value;
				
		
		//var evaluationPaper = document.getElementById("evaluationPaper").value;
		//var evaluationProduct = document.getElementById("evaluationProduct").value;
		//var evaluationPatent = document.getElementById("evaluationPatent").value;
		//var evaluationGraduateStudent = document.getElementById("evaluationGraduateStudent").value;
		//var evaluationYoungResearcher = document.getElementById("evaluationYoungResearcher").value;
		//var evaluationEducation = document.getElementById("evaluationEducation").value;
		//var evaluationReasonableBudget = document.getElementById("evaluationReasonableBudget").value;
		
		var comments = $('textarea#comments').val();
		
		$.ajax({
				url: "/bkeuniv/control/update-review-project-proposal",
				type: 'POST',
				data: {
					"reviewerResearchProposalId": ${reviewerResearchProposalId},
					"evaluationInnovation": evaluationInnovation,
					"evaluationMotivation": evaluationMotivation,
					//"evaluationApplicability": evaluationApplicability,
					"evaluationResearchMethod": evaluationResearchMethod,
					"evaluationResearchContent": evaluationResearchContent,
					"evaluationProduct1": evaluationProduct1,
					"evaluationProduct2": evaluationProduct2,
					"evaluationPromote1": evaluationPromote1,
					"evaluationPromote2": evaluationPromote2,
					"evaluationPromote3": evaluationPromote3,
							
					//"evaluationPaper": evaluationPaper,
					//"evaluationProduct": evaluationProduct,
					//"evaluationPatent": evaluationPatent,
					//"evaluationGraduateStudent": evaluationGraduateStudent,
					//"evaluationYoungResearcher": evaluationYoungResearcher,
					//"evaluationEducation": evaluationEducation,
					//"evaluationReasonableBudget": evaluationReasonableBudget,
					"comments": comments
				},
				success: function(rs){
					window.location.href = "/bkeuniv/control/research-project-jury-members-evaluate";
					//console.log(rs.result);
					//alert('Da luu diem danh gia');
					
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
					window.location.href = "/bkeuniv/control/research-project-jury-members-evaluate";
					//console.log(rs.result);
					//alert('Da confirm diem danh gia');
				}
			})
	}
	</script>
</div>