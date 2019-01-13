
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
				<td> ${uiLabel.JuryMembers} </td>
				<!--
				<td>${e.staffName}</td>
				-->
				<td>Anonymous</td>
			</tr>
			<tr>	
				<td> ${uiLabel.Motivation} </td>
				<#if e.evaluationMotivation?exists>
					<td>${e.evaluationMotivation}</td>
				<#else>
					<td>0</td>
				</#if>
				
				<td> ${uiLabel.Innovation} </td>
				<#if e.evaluationInnovation?exists>
					<td>${e.evaluationInnovation}</td>
				<#else>
					<td>0</td>
				</#if>
			
			<tr>
				<td> ${uiLabel.ResearchMethod} </td>
				<#if e.evaluationResearchMethod?exists>
					<td>${e.evaluationResearchMethod}</td>
				<#else>
					<td>0</td>
				</#if>
				
				<td> ${uiLabel.ResearchContent} </td>
				<#if e.evaluationResearchContent?exists>
					<td>${e.evaluationResearchContent}</td>
				<#else>
					<td>0</td>
				</#if>
				
			</tr>
			<tr>
				<td> ${uiLabel.Product1} </td>
				<#if e.evaluationProduct1?exists>
					<td>${e.evaluationProduct1}</td>
				<#else>
					<td>0</td>
				</#if>
				
				<td> ${uiLabel.Product2} </td>
				<#if e.evaluationProduct2?exists>
					<td>${e.evaluationProduct2}</td>
				<#else>
					<td>0</td>
				</#if>
				
			</tr>
			
			<tr>
				<td> ${uiLabel.Promote1} </td>
				<#if e.evaluationPromte1?exists>
					<td>${e.evaluationPromote1}</td>
				<#else>
					<td>0</td>
				</#if>
				
				<td> ${uiLabel.Promote2} </td>
				<#if e.evaluationPromte2?exists>
					<td>${e.evaluationPromote2}</td>
				<#else>
					<td>0</td>
				</#if>
			
				<td> ${uiLabel.Promote3} </td>
				<#if e.evaluationPromte3?exists>
					<td>${e.evaluationPromote3}</td>
				<#else>
					<td>0</td>
				</#if>
				
			</tr>
			
			<tr>
				<td> ${uiLabel.TotalEvaluation} </td>
				<#if e.totalEvaluation?exists>
					<td>${e.totalEvaluation}</td>
				<#else>
					<td>0</td>
				</#if>
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