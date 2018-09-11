<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>

<script>
	function download() {
		window.open("/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=${researchProjectProposalId}", "_blank")
	}

	$(document).ready(function(){
		$('.collapsible-content').collapsible();
	});

	function stopPropagation(event) {
		event.stopPropagation();
	}
	
</script>

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
				<ul style="position: relative; margin-bottom: 10px; " class="collapsible-content" data-collapsible="expandable">
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

					<li style="list-style-type: none; margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div class="collapsible-header active" style="padding: 0.7rem; background-color: #0000000d;">
							<div style="font-weight: bold;">1.&nbsp; </div>
							<a onClick="stopPropagation(event)" href="/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}" target="_blank">
								${uiLabel.MembersOfProject}
							</a>
						</div>

						<div class="collapsible-body" style="padding: 1rem">
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
											<td>${m.roleTypeName}</td>
										<tr>
									</#list>
								<tbody>
							</table>
						</div>
					</li>

					<li style="list-style-type: none; margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div class="collapsible-header active" style="padding: 0.7rem; background-color: #0000000d;">
							<div style="font-weight: bold;">2.&nbsp; </div>
							<a onClick="stopPropagation(event)" href="/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}" target="_blank">
								${uiLabel.ListWorkPackages}
							</a>
						</div>

						<div class="collapsible-body" style="padding: 1rem">
							<table>
								<thead>
									<tr>
										<th width="30%">${uiLabel.FullName}</th>
										<th width="25%">${uiLabel.WorkPackage}</th>
										<th width="25%">${uiLabel.Workingdays}</th>
										<th width="20%">${uiLabel.Budget}</th>
									</tr>
								</thead>
								<tbody>
								<#list resultContentItems.projectProposalContentItems as ci>
									<tr>
										<#if ci.staffName?exists>
											<td>${ci.staffName}</td>
										<#else>
											<td></td>
										</#if>
										<#if ci.content?exists>
											<td>${ci.content}</td>
										<#else>
											<td></td>
										</#if>
										<#if ci.workingDays?exists>
											<td>${ci.workingDays}</td>
										<#else>
											<td></td>
										</#if>
										<#if ci.budget?exists>
											<td>${ci.budget}</td>
										<#else>
											<td></td>
										</#if>
									</tr>
								</#list>
								</tbody>
							</table>
						</div>
					</li>

					<li style="list-style-type: none; margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div class="collapsible-header active" style="padding: 0.7rem; background-color: #0000000d;">
							<div style="font-weight: bold;">3.&nbsp; </div>
							<a onClick="stopPropagation(event)" href="/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}" target="_blank">
								${uiLabel.ListOfRegisteredProducts}
							</a>
						</div>

						<div class="collapsible-body" style="padding: 1rem">
							<table>
								<thead>
									<tr>
										<th width="70%">${uiLabel.Products}</th>
										<th width="30%">${uiLabel.Quantity}</th>
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
					</li>

					<li style="list-style-type: none; margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div class="collapsible-header active" style="padding: 0.7rem; background-color: #0000000d;">
							<div style="font-weight: bold;">4.&nbsp; </div>
							<a onClick="stopPropagation(event)" href="/bkeuniv/control/manage-research-project-proposal-product?researchProjectProposalId=${researchProjectProposalId}" target="_blank">
								${uiLabel.ListOfFinishedProducts}
							</a>
						</div>

						<div class="collapsible-body" style="padding: 1rem">
							<table>
								<thead>
									<tr>
										<th width="70%">${uiLabel.ProductName}</th>
										<th width="30%">${uiLabel.ProductType}</th>
									</tr>
								</thead>
								<tbody>
								<#list listResearchProjectProduct.list as p>
									<tr>
										<td>${p.researchProductName}</td>
										<td>${p.researchProductTypeName}</td>
									</tr>
								</#list>
								</tbody>
							</table>
						</div>
					</li>

					<li style="list-style-type: none; margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div class="collapsible-header active" style="padding: 0.7rem; background-color: #0000000d;">
							<div style="font-weight: bold;">5.&nbsp; </div>
							<a onClick="stopPropagation(event)" href="/bkeuniv/control/manage-research-project-proposal-product?researchProjectProposalId=${researchProjectProposalId}" target="_blank">
								${uiLabel.WorkingHourProject}
							</a>
						</div>

						<div class="collapsible-body" style="padding: 1rem">
							<table>
								<thead>
									<tr>
										<th width="50%">${uiLabel.MemberProject}</th>
										<th width="25%">${uiLabel.Workinghour}</th>
										<th width="25%">${uiLabel.AcademicYear}</th>
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
					</li>
				</ul>
			</div>
		</div>
	</div>
