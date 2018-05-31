<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
#form-style {
    margin-top: 20px;
    margin-left: 20px;
    width: 100%;
    overflow: scroll;
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
</tr>

<tr>
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
</tr>

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
<tr>
	<td>
		<div class="inline-box" style="width: 200%; padding: 10px 0px;">
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
	</td>
	
</tr>
</table>

<@buttonBack text="${bkEunivUiLabelMap.BkEunivBack}" action="back"/>

</div>


<script>



function back(){
	
	window.location.href = "/bkeuniv/control/research-project-jury-members-evaluate";
}
</script>