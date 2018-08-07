<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<script>
	function download() {
		window.open("/bkeuniv/control/download-file-paper?id-paper=${resultPaper.paper.paperId}", "_blank")
	}
</script>
<body>
	<div class="body">
		<div id="information-paper" style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
			<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
				<div style="display: flex; justify-content: space-between;">
					<div class="title" style="padding: 16px; position: relative;">
						<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
							<span id="titlePage">${resultPaper.paper.paperName}</span>
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
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperName}
						</label>
						<span style="width: 80%; display: block;" >
							${resultPaper.paper.paperName}
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivAuthors}
						</label>
						<span style="width: 80%; display: block;" >
							${resultPaper.paper.authors}
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperCategory}
						</label>
						<span style="width: 80%; display: block;" >
							${resultPaper.paper.paperCategoryName}
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperJournalConference}
						</label>
						<span style="width: 80%; display: block;" >
							<#if resultPaper.paper.journalConferenceName?exists>
								${resultPaper.paper.journalConferenceName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperYear}
						</label>
						<span style="width: 80%; display: block;" >
							<#if resultPaper.paper.year?exists>
								${resultPaper.paper.year}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivResearchProjectOfPaper}
						</label>
						<span style="width: 80%; display: block;" >
							<#if resultPaper.paper.researchProjectProposalName?exists>
								${resultPaper.paper.researchProjectProposalName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 20%;">
							${paperDeclarationUiLabelMap.BkEunivPaperLink}
						</label>
						<span style="width: 80%; display: block;" >
							<#if resultPaper.paper.link?exists>
								${resultPaper.paper.link}
							</#if>
						</span>
					</div>
				</div>

				<table>
					<thead>
					<tr>
						<th style="width: 50px;">${uiLabelMap.BkEunivSTT}</th>
						<th style="min-width: 200px;">${uiLabelMap.BkEunivFirstAndLastName}</th>
						<th>${uiLabelMap.BkEunivInternally}/${uiLabelMap.BkEunivExternal}</th>
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

							<td>${uiLabelMap.BkEunivInternally}</td>
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

				<#if resultPaper.paper.staffId==userLogin.userLoginId>
					<#include "component://bkeuniv/webapp/bkeuniv/paperdeclaration/detail-paper-update-action.ftl"/>
				</#if>
			</div>
		</div>
	</div>
