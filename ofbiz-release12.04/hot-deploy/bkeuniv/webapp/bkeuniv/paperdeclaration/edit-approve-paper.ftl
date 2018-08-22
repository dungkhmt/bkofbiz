<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<script>
	function download() {
		window.open("/bkeuniv/control/download-file-paper?id-paper=${resultPaper.paper.paperId}", "_blank")
	}

	function unblockChangeStatus() {
		$('#paperDeclarationStatus').prop('disabled', false);
		$("#editStatus").css("display", "none");
		$("#saveStatus").css("display", "inline-block");
	}

	function unblockChangePaperCategoryKNC() {
		$('#paperCategoryKNC').prop('disabled', false);
		$("#editCategoryKNC").css("display", "none");
		$("#saveCategoryKNC").css("display", "inline-block");
	}

	function saveChangeStatus() {
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/update-paper-declaration-status",
			type: 'post',
			dataType: "json",
			data: {
				paperId: '${resultPaper.paper.paperId}',
				statusId: $('#paperDeclarationStatus').val()
			},
			success: function(data) {
				alertify.success(data.message);
				$('#paperDeclarationStatus').prop('disabled', true);
				$("#editStatus").css("display", "inline-block");
				$("#saveStatus").css("display", "none");
				loader.close();
			},
			error: function(message) {
				alertify.error(message);
				loader.close();
			}
		});
	}

	function saveChangeCategoryKNC() {
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/update-paper-categoryKNC",
			type: 'post',
			dataType: "json",
			data: {
				paperId: '${resultPaper.paper.paperId}',
				paperCategoryKNCId: $('#paperCategoryKNC').val()
			},
			success: function(data) {
				alertify.success(data.message);
				$('#paperCategoryKNC').prop('disabled', true);
				$("#editCategoryKNC").css("display", "inline-block");
				$("#saveCategoryKNC").css("display", "none");
				loader.close();
			},
			error: function(message) {
				alertify.error(message);
				loader.close();
			}
		});
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
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperName}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.paperName?exists>
								${resultPaper.paper.paperName}
							<#else>
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivAuthors}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.authors?exists>
								${resultPaper.paper.authors}
							<#else>
								
							</#if>
							
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperCategory}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.paperCategoryName?exists>
								${resultPaper.paper.paperCategoryName}
							<#else>
							
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperJournalConference}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.journalConferenceName?exists>
								${resultPaper.paper.journalConferenceName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperMonth}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.month?exists>
								${resultPaper.paper.month}
							</#if>
						</span>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperYear}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.year?exists>
								${resultPaper.paper.year}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 10px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivResearchProjectOfPaper}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.researchProjectProposalName?exists>
								${resultPaper.paper.researchProjectProposalName}
							</#if>
						</span>
					</div>
					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 10px 16px 0px 16px;">
						<label style="padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperLink}
						</label>
						<span style="width: 70%; display: block;" >
							<#if resultPaper.paper.link?exists>
								${resultPaper.paper.link}
							</#if>
						</span>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 0px 16px 0px 16px;">
						<label style="margin-top: 40px; padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperDeclarationCategoryKNC}
						</label>
						<span style="width: 70%; display: block;" >
							<@FormInput id="paperCategoryKNC" field="" width="256px">
								<select id="paperCategoryKNC" disabled>
									<#if paperCategoryKNC??&&paperCategoryKNC.listPaperCategoryKNC??>
										<#list paperCategoryKNC.listPaperCategoryKNC as category>
											<option value="${category.paperCategoryKNCId}"
											<#if resultPaper.paper.paperCategoryKNCId?? && resultPaper.paper.paperCategoryKNCId==category.paperCategoryKNCId>
												selected
											</#if>
											>${category.paperCategoryKNCName}</option>
										</#list>
									</#if>
								</select>
							</@FormInput>
							<@FlatButton id="editCategoryKNC" onClick="unblockChangePaperCategoryKNC()" style="top: 10px;color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
								</svg>
								${uiLabelMap.BkEunivEdit}
							</@FlatButton>
							<@FlatButton id="saveCategoryKNC" onClick="saveChangeCategoryKNC()" style="top: 10px;color: rgb(0, 188, 212); text-transform: uppercase;width: 150px;display: none">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z"></path>
								</svg>
								${uiLabelMap.BkEunivSave}
							</@FlatButton>
						</span>
					</div>

					<div class="row inline-box" style="margin-bottom: 0px; position: relative; display: -webkit-box; padding: 0px 16px 10px 16px;">
						<label style="margin-top: 40px; padding: 5px; line-height: 1.5; flex: 1 1 auto; display: block; width: 30%;">
							${uiLabelMap.BkEunivPaperDeclarationStatus}
						</label>
						<span style="width: 70%; display: block;" >
							<@FormInput id="paperDeclarationStatus" field="" width="256px">
								<select id="paperDeclarationStatus" disabled>
									<#if paperDeclaration??&&paperDeclaration.listPaperDeclaration??>
										<#list paperDeclaration.listPaperDeclaration as paperDeclaration>
											<option value="${paperDeclaration.paperDeclarationStatusId}"
											<#if resultPaper.paper.approveStatusId?? && resultPaper.paper.approveStatusId==paperDeclaration.paperDeclarationStatusId>
												selected
											</#if>
											>${paperDeclaration.paperDeclarationStatusName}</option>
										</#list>
									</#if>
								</select>
							</@FormInput>
							<@FlatButton id="editStatus" onClick="unblockChangeStatus()" style="top: 10px;color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
								</svg>
								${uiLabelMap.BkEunivEdit}
							</@FlatButton>
							<@FlatButton id="saveStatus" onClick="saveChangeStatus()" style="top: 10px;color: rgb(0, 188, 212); text-transform: uppercase;width: 150px; display: none">
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
									<path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z"></path>
								</svg>
								${uiLabelMap.BkEunivSave}
							</@FlatButton>
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
							
							<#if m.sequence?exists>
								<td>${m.sequence}</td>
							<#else>
								<td></td>
							</#if>
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
							
							<#if m.sequence?exists>
								<td>${m.sequence}</td>
							<#else>
								<td></td>
							</#if>
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

			</div>
		</div>
	</div>
	<@Loader handleToggle="loader">
		<@IconSpinner/>
	</@Loader>
