<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<script>
	function download() {
		window.open("/bkeuniv/control/download-file-paper?id-paper=${resultPaper.paper.paperId}", "_blank")
	}

	function editpaper() {
		window.location.href='/bkeuniv/control/form-edit-paper-declaration?paperId=${resultPaper.paper.paperId}';
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
							<#if resultPaper.paper.paperName?exists>
								${resultPaper.paper.paperName}
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
					<#--
					<#if userLogin.userLoginId==resultPaper.paper.staffId && 
					resultPaper.paper.approveStatusId?exists && resultPaper.paper.approveStatusId =="CREATED">
					-->
					
					<#if resultPaper.editable?exists && resultPaper.editable =="Y">
					
						<div class="row inline-box" style="float: right; margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
							<@FlatButton id="edit-paper" onClick="editpaper()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 250px">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
								</svg>
								${uiLabelMap.BkEunivEditPaperDeclaration}
							</@FlatButton>
						</div>
						<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
							<label style="width: calc(20% + 55px); padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block;">
								${paperDeclarationUiLabelMap.BkEunivPaperName}
							</label>
							<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
								<#if resultPaper.paper.paperName?exists>
									${resultPaper.paper.paperName}
								<#else>
								</#if>
							</span>
						</div>
					<#else>
						<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
							<label style="width: 20%; padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block;">
								${paperDeclarationUiLabelMap.BkEunivPaperName}
							</label>
							<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
								<#if resultPaper.paper.paperName?exists>
									${resultPaper.paper.paperName}
								<#else>
								</#if>
							</span>
						</div>
					</#if>
					
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivAuthors}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.authors?exists>
								${resultPaper.paper.authors}
							<#else>
								
							</#if>
							
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperCategory}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.paperCategoryName?exists>
								${resultPaper.paper.paperCategoryName}
							<#else>
							
							</#if>
						</span>
					</div>
					
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							Phan loai KNC
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.paperCategoryKNCName?exists>
								${resultPaper.paper.paperCategoryKNCName}
							<#else>
							
							</#if>
						</span>
					</div>
					
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperJournalConference}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.journalConferenceName?exists>
								${resultPaper.paper.journalConferenceName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperMonth}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.month?exists>
								${resultPaper.paper.month}
							</#if>
						</span>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperYear}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.year?exists>
								${resultPaper.paper.year}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivResearchProjectOfPaper}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.researchProjectProposalName?exists>
								${resultPaper.paper.researchProjectProposalName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperLink}
						</label>
						<span style="width: 80%; display: block; padding: 5px; line-height: 1.5; flex: 1 1 auto;" >
							<#if resultPaper.paper.link?exists>
								${resultPaper.paper.link}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivApproveStatus}
						</label>
						<span style="width: 80%; display: block;" >
							<#if resultPaper.paper.paperDeclarationStatusName?exists>
								${resultPaper.paper.paperDeclarationStatusName}
							</#if>
						</span>
					</div>
					
				</div>

				<table>
					<thead>
					<tr>
						<th style="width: 50px;">${uiLabelMap.BkEunivSTT}</th>
						<th style="min-width: 200px;">${uiLabelMap.BkEunivFirstAndLastName}</th>
						<th>${uiLabelMap.AffiliationOutsideUniversity}</th>
						<th>Corresponding author</th>
					</tr>
					</thead>

					<tbody>
					<#assign stt=1/>
					<#list members.staffPaperDeclaration as m>
						<tr>
							
							
							<td>${stt}</td>
							<#if m.staffName?exists>
								<td>${m.staffName}</td>
							<#else>
								<td></td>
							</#if>

							<#if m.affiliationOutsideUniversity?exists>
								<td>${m.affiliationOutsideUniversity}</td>
							<#else>
								<td></td>
							</#if>
							
							<td><#if m.correspondingAuthor??>${m.correspondingAuthor}</#if></td>
							<#assign stt=stt+1/>
						</tr>
					</#list>
					<#list members.externalMemberPaperDeclaration as m>
						<tr>
							
							
							<td>${stt}</td>
							
							<#if m.staffName?exists>
								<td>${m.staffName}</td>
							<#else>
								<td></td>
							</#if>
							<td>${uiLabelMap.BkEunivExternal}</td>
							<td><#if m.correspondingAuthor??>${m.correspondingAuthor}</#if></td>
							<#assign stt=stt+1/>
						</tr>
					</#list>
					</tbody>
				</table>

				
				<#--  <#include "component://bkeuniv/webapp/bkeuniv/paperdeclaration/detail-paper-update-action.ftl"/>  -->
			</div>
		</div>
	</div>
