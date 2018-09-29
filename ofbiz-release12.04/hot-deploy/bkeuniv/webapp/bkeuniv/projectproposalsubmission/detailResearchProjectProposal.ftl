<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<script>
	function download() {
		window.open("/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=${researchProjectProposalId}", "_blank")
	}

	function openDetailEvaluation() {
		window.open("/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}", "_blank")
	}

	<#if resultProjectProposal.projectproposal.parentResearchProjectProposalId?exists>
		function openBackVersion() {
			window.open("/bkeuniv/control/detail-research-project-proposal-update?researchProjectProposalId=${resultProjectProposal.projectproposal.parentResearchProjectProposalId}", "_blank")
		}
	</#if>

</script>

<#assign edit=false/>
<#if resultProjectProposal.projectproposal.createStaffId==userLogin.userLoginId>
	<#assign edit=true/>
</#if>

<body>
	<div class="body">
		<div id="information-paper" style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
			<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
				<div style="display: flex; justify-content: space-between;">
					<div class="title" style="padding: 16px; position: relative;">
						<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
							<span id="titlePage">
							<#if resultProjectProposal.projectproposal.researchProjectProposalName?exists>
								${resultProjectProposal.projectproposal.researchProjectProposalName}
							<#else>
							
							</#if>
							
							</span>
						</span>
					</div>
					<div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
						<@FlatButton id="download" onClick="download()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"></path>
							</svg>
							${uiLabelMap.BkEunivDownload}
						</@FlatButton>
					</div>
				</div>
				<hr class="side-bar-separator">
				<div class="content" style="position: relative; margin-bottom: 10px; ">
						<#assign evaluation = "NO">
						<#if resultProjectProposal.projectproposal.evaluationOpenFlag?exists>
							<#if resultProjectProposal.evaluation == "YES" && resultProjectProposal.projectproposal.evaluationOpenFlag == "Y">
								<#assign evaluation = "YES">
							</#if>
						</#if>
						<#if evaluation == "YES">
							<@FlatButton id="openDetailEvaluation" onClick="openDetailEvaluation()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 250px">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M20 2H4c-1.1 0-1.99.9-1.99 2L2 22l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zM6 14v-2.47l6.88-6.88c.2-.2.51-.2.71 0l1.77 1.77c.2.2.2.51 0 .71L8.47 14H6zm12 0h-7.5l2-2H18v2z"></path>
								</svg>
								${uiLabel.DetailEvaluation}
							</@FlatButton>
						</#if>

						<#if resultProjectProposal.projectproposal.parentResearchProjectProposalId?exists>
							<@FlatButton id="back-version" onClick="openBackVersion()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 250px">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M14 12c0-1.1-.9-2-2-2s-2 .9-2 2 .9 2 2 2 2-.9 2-2zm-2-9c-4.97 0-9 4.03-9 9H0l4 4 4-4H5c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.51 0-2.91-.49-4.06-1.3l-1.42 1.44C8.04 20.3 9.94 21 12 21c4.97 0 9-4.03 9-9s-4.03-9-9-9z"></path>
								</svg>
								Phien ban truoc do
							</@FlatButton>
						</#if>
					
					
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${uiLabel.Status}
						</label>
						<span style="width: 80%; padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block;" >
							<#if resultProjectProposal.projectproposal.statusName?exists>
								${resultProjectProposal.projectproposal.statusName}
							<#else>
							</#if>
						</span>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							${uiLabel.MembersOfProject}
						</div>
						<table>
							<thead>
								<tr>
									<th>${uiLabel.FullName}</th>
									<th>${uiLabel.Role}</th>
								</tr>
							</thead>
							<tbody>
							<#list resultMembers.members as m>
								<tr>
									<td>${m.staffName}</td>
									<#if m.roleTypeName?exists>
										<td>${m.roleTypeName}</td>
									<#else>
										<td></td>	
									</#if>
								<tr>
							</#list>
							<tbody>
						</table>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							${uiLabel.ListWorkPackages}
						</div>
						<table>
							<thead>
								<tr>
								<th width="200">${uiLabel.FullName}</th>
								<th width="400">${uiLabel.WorkPackage}</th>
								<th width="100">${uiLabel.Workingdays}</th>
								<th width="100">${uiLabel.Budget}</th>
								</tr>
							</thead>
							<tbody>
							<#list resultContentItems.projectProposalContentItems as ci>
								<tr>
									<#if ci.staffName?exists>
										<td width="200">${ci.staffName}</td>
									<#else>
										<td width="200"></td>
									</#if>
									<#if ci.content?exists>
										<td width="400">${ci.content}</td>
									<#else>
										<td width="400"></td>
									</#if>
									<#if ci.workingDays?exists>
										<td width="100">${ci.workingDays}</td>
									<#else>
										<td width="100"></td>
									</#if>
									<#if ci.budget?exists>
										<td width="100">${ci.budget}</td>
									<#else>
										<td width="100"></td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							<#--  <#if edit>
								<a href="/bkeuniv/control/manage-research-project-proposal-product?researchProjectProposalId=${researchProjectProposalId}">
									${uiLabel.ListProducts}
								</a>
							<#else>  -->
							${uiLabel.ListProducts}
						</div>
						<table>
							<thead>
								<tr>
									<th>${uiLabel.Products}</th>
									<th>${uiLabel.Quantity}</th>
								</tr>
							</thead>
							<tbody>
							<#list resultProducts.projectProposalProducts as p>
								<tr>
									<td>${p.researchProductTypeName}</td>
									<td>${p.quantity}</td>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							${uiLabel.WorkingHourProject}
						</div>
						<table>
							<thead>
								<tr>
									<th>${uiLabel.MemberProject}</th>
									<th>${uiLabel.Workinghour}</th>
									<th>${uiLabel.AcademicYear}</th>
								</tr>
							</thead>
							<tbody>
							<#list resultProjectWorkingHourDeclaration.projectDeclarations as p>
								<tr>
									<#if p.staffName?exists>
										<td>${p.staffName}</td>
									</#if>
									
									<#if p.workinghours?exists>
										<td>${p.workinghours}</td>
									</#if>
									
									<#if p.academicYearName?exists>
										<td>${p.academicYearName}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>
					
					
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							KINH PHI
						</div>
						<table>
							<thead>
								<tr>
									<th>Kinh phi thiet bi</th>
									<th>Kinh phi quan ly</th>
									<th>Tong kinh phi</th>
									<th>Tong kinh phi thiet bi & quan ly</th>
									<th>Chi so khoi luong</th>
									<th>Nam ke khai</th>
									
								</tr>
							</thead>
							<tbody>
							<#list resultBudgetDeclaration.projectDeclarations as p>
								<tr>
									<#if p.equipmentBudget?exists>
										<td>${p.equipmentBudget}</td>
									</#if>
									
									<#if p.managementBudget?exists>
										<td>${p.managementBudget}</td>
									</#if>
									
									<#if p.allocatedBudgetYear?exists>
										<td>${p.allocatedBudgetYear}</td>
									</#if>

									<#if p.budget?exists>
										<td>${p.budget}</td>
									</#if>
									<#if p.rate?exists>
										<td>${p.rate}</td>
									</#if>
										
									<#if p.academicYearId?exists>
										<td>${p.academicYearId}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>
					

					
<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							MUC DO DONG GOP CUA THANH VIEN
						</div>
						<table>
							<thead>
								<tr>
									<th>Ho ten Thanh vien</th>
									<th>Muc do dong gop</th>
									<th>Nam ke khai</th>
									
								</tr>
							</thead>
							<tbody>
							<#list resultParticipationDeclaration.projectDeclarations as p>
								<tr>
									<#if p.staffName?exists>
										<td>${p.staffName}</td>
									</#if>
									
									<#if p.staffParticipationPercentage?exists>
										<td>${p.staffParticipationPercentage}</td>
									</#if>
									
									<#if p.academicYearId?exists>
										<td>${p.academicYearId}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>
					
					<#include "component://bkeuniv/webapp/bkeuniv/projectproposalsubmission/detailResearchProjectProposalUpdateAction.ftl"/>										
																																			
				</div>
			</div>
			
		</div>
	</div>
