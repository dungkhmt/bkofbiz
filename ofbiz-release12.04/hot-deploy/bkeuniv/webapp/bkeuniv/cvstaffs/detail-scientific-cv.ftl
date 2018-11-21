<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<script src="/resource/bkeuniv/js/lib/dropify.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dropify.min.css">
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:30px
	}
	.modal-dialog {
		width: 60%!important;
		margin-left: 20%!important;
	}
	
	thead {
		border-bottom: 1px solid #000000;
	}
	
	.body-table > tr {
		border-bottom: 1px solid #d0d0d0;
	}
</style>



<div class="info-table">
	<h5 style="margin-top:10px !important; margin:auto; text-align:center">${uiLabelMap.BkEunivCVPDFTitle}</h5>
	
<table>

	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivBasicInformation}<a href = "/bkeuniv/control/cv-general-information">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
					</tr>
					</thead>
					<tbody class="body-table">
					<tr>
						<td>
							<div style="display: flex; flex-direction: column">
								<p><b>${uiLabelMap.BkEunivFullName}</b>: ${resultCV.cv.info.staffName}</p>
								<p><b>${uiLabelMap.BkEunivBirthday}</b>: ${resultCV.cv.info.staffDateOfBirth}</p>
								<p><b>${uiLabelMap.BkEunivBirthday}</b>: ${genderName?if_exists}</p>
								<p><b>${uiLabelMap.CVResearchPosition}</b>: ${researchPosition?if_exists}</p>
								<p><b>${uiLabelMap.BkEunivCurrentPosition}</b>: ${duty?if_exists}</p>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</td>
	</tr>

	<#--	
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivAgencyWork}
				</div>
				<table>
					<thead>
					<tr>
					</tr>
					</thead>
					<tbody class="body-table">
					<tr>
						<td>
							<div style="display: flex; flex-direction: column">
								<p><b>${uiLabelMap.BkEunivCompanyName}</b>: ${departmentName?if_exists} - ${facultyName?if_exists} - ${universityName?if_exists}</p>
								<p><b>${uiLabelMap.BkEunivNameLeader}</b>: ${leaderName?if_exists}</p>
								<p><b>${uiLabelMap.BkEunivCompanyAddress}</b>: ${companyAddress?if_exists}</p>
								<p><b>${uiLabelMap.BkEnuivPhone}</b>: ${companyPhone?if_exists}</p>
								<p><b>${uiLabelMap.BkEnuivFax}</b>: ${companyFax?if_exists}</p>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	
	<#assign listResearchDomain = resultCV.cv.researchDomain />
	<#if listResearchDomain?has_content>
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivResearchDomain}
				</div>
				<table>
					<thead>
					<tr>
					</tr>
					</thead>
					<tbody class="body-table">
					<#list listResearchDomain as reserchDomain>
					<tr>
						<td>${reserchDomain.researchDomainName?if_exists}</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	</#if>
	-->
	
	<#--  education progress  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivEducationProgress}<a href = "/bkeuniv/control/education-progress">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivEducationType}</th>
						<th>${uiLabelMap.BkEunivInstitution}</th>
						<th>${uiLabelMap.BkEunivSpeciality}</th>
						<th>${uiLabelMap.BkEunivGraduateDate}</th>
					</tr>
					</thead>
					<tbody class="body-table">
					<#assign index = 0>
					<#list resultCV.cv.educationProgress as eduProg>
					<tr>
						<#assign index = index+1>

						<td>${index}</td>
						<td>
							${eduProg.educationType}
						</td>
						
						<td>
							${eduProg.institution}
						</td>
					
						<td>
							${eduProg.speciality}
						</td>
						<td>
							${eduProg.graduateDate}
						</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	
	<#-- foreign language -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivForeignLanguageSuggest}<a href = "/bkeuniv/control/foreign-language-staff">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.ForeignLanguageName}</th>
						<th>${uiLabelMap.Listen}</th>
						<th>${uiLabelMap.Speaking}</th>
						<th>${uiLabelMap.Reading}</th>
						<th>${uiLabelMap.Writting}</th>
					</tr>
					</thead>
					<tbody class="body-table">
					<#assign index = 0>
					<#list listForeignLanguage as foreignLanguage>
					<tr>
						<#assign index = index+1>

						<td>${index}</td>
						<td>
							${StringUtil.wrapString(foreignLanguage.foreignLanguageCatalogName?if_exists)}
						</td>
						
						<td>
							${StringUtil.wrapString(foreignLanguage.listen?if_exists)}
						</td>
					
						<td>
							${StringUtil.wrapString(foreignLanguage.speaking?if_exists)}
						</td>
						<td>
							${StringUtil.wrapString(foreignLanguage.reading?if_exists)}
						</td>
						<td>
							${StringUtil.wrapString(foreignLanguage.writing?if_exists)}
						</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>

	<#--  working progress  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivWorkProgress}<a href = "/bkeuniv/control/work-progress">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivPeriod}</th>
						<th>${uiLabelMap.BkEunivPositionBussiness}</th>
						<th>${uiLabelMap.BkEunivDomain}</th>
						<th>${uiLabelMap.BkEunivCompany}</th>
					</tr>
					</thead>
					<tbody class="body-table">
					<#assign index = 0>
					<#list resultCV.cv.workProgress as wp>
					<tr>
						<#assign index = index+1>

						<td>${index}</td>
						<td>
							<#if wp.period?exists>
							${wp.period}
							<#else>
							
							</#if>
						</td>
						<td>
							<#if wp.position?exists>
							${wp.position}
							<#else>
							
							</#if>
						</td>
					
						<td>
							<#if wp.specialization?exists>
							${wp.specialization}
							<#else>
							
							</#if>
						</td>
						<td>
							<#if wp.institution?exists>
							${wp.institution}
							<#else>
							
							</#if>
						</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>

	<#--  applied project  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivNumberOfConstruction}<a href = "/bkeuniv/control/applied-project-declaration-staff">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivNameOfConstruction}</th>
						<th>${uiLabelMap.BkEunivModel}</th>
						<th>${uiLabelMap.BkEunivTimeConstruction}</th>
					</tr>
					</thead>
					<tbody class="body-table">
					<#assign index = 0>
					<#list resultCV.cv.appliedProjects as p>
					<tr>
						<#assign index = index+1>

						<td>${index}</td>
						<td>
							<#if p.name?exists>
								${p.name}
							<#else>
								
							</#if>
						</td>
						
						<td>
							<#if p.description?exists>
								${p.description}
							<#else>
								
							</#if>
						</td>
					
						<td>
							<#if p.period?exists>
								${p.period}
							<#else>

							</#if>
						</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	
	<#--  science service  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivScientificService1} ${uiLabelMap.BkEunivScientificService2} <a href = "/bkeuniv/control/scientificserviceexperience">     [Update]</a>
				</div>
					
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivModelCouncil}</th>
						<th>${uiLabelMap.BkEunivDetail}</th>
						
					</tr>
					</thead>
					<tbody class="body-table">	
					<#assign index = 0>
					<#list resultCV.cv.scientificExperiences as se>
					<tr>
						<#assign index = index + 1>
						<td>
							${index}
						</td>
						<td>
							<#if se.description?exists>
							${se.description}
							</#if>
						</td>
						<td>
							<#if se.descriptionDetail?exists>
							${se.descriptionDetail}
							</#if>
						</td>
					</tr>
					</#list>
					</tbody>	
				</table>
			</div>
		</td>
	</tr>
	
	<#--  Papers Pulication -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivPublications} ${uiLabelMap.BkEunivPublic}<a href = "/bkeuniv/control/recent-publications-new">     [Update]</a>
				</div>
				<table border="1" rules="rows">
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivConstructionName}</th>
						<th>${uiLabelMap.BkEunivAuthor}</th>
						<th>${uiLabelMap.BkEunivPublic}</th>
						<th>${uiLabelMap.BkEunivYear}</th>
					</tr>
					</thead>

					<tbody class="body-table">
					<#assign index = 0>
					<#list resultCV.cv.papers as p>
						<#assign index = index + 1>
						<tr>
							<td>
								${index}
							</td>
							<td>
								<#if p.paperName?exists>
									${p.paperName}
								<#else>
									
								</#if>
							</td>
							<td>
								<#if p.roleName?exists>
									${p.roleName}
								<#else>
								
								</#if>
							</td>
							<td>
								<#if p.journalConferenceName?exists>
									${p.journalConferenceName}
								<#else>
									
								</#if>
							</td>
							<td>
								<#if p.year?exists>
									${p.year}
								<#else>
								
								</#if>
							</td>
						</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>

	<#--  Science & Technology Award  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivAward}<a href = "/bkeuniv/control/award">     [Update]</a>
				</div>
				<table border="1" rules="rows">
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivNameAndContentAward}</th>
						<th>${uiLabelMap.BkEunivYearAward}</th>
					</tr>
					</thead>

					<tbody class="body-table">
					<#assign index = 0>
					<#list resultCV.cv.awards as aw>
						<#assign index = index + 1>
						<tr>
							<td>
								${index}
							</td>
							<td>
								<#if aw.description?exists>
								${aw.description}
								<#else>
								
								</#if>
							</td>
							<td>
								<#if aw.year?exists>
								${aw.year}
								<#else>
								
								</#if>
							</td>
						</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>


	<#--  Patents  -->
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivPatent1} ${uiLabelMap.BkEunivPatent2}<a href = "/bkeuniv/control/patent-staff">     [Update]</a>
				</div>
				<table border="1" rules="rows">
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivNameAndContent}</th>
						<th>${uiLabelMap.BkEunivYearPatent}</th>
					</tr>
					</thead>

					<tbody class="body-table">
					<#assign index = 0>
					<#if listPatents?exists>
					<#list listPatents as pt>
						<#assign index = index + 1>
						<tr>
							<td>
								<#if index?exists>
								${index}
								<#else>
								</#if>
							</td>
							<td>
								<#if pt.patentName?exists>
								${pt.patentName}
								<#else>
								</#if>
							</td>
							<td>
								<#if pt.year?exists>
								${pt.year}
								<#else>
								</#if>
							</td>
						</tr>
					</#list>
					</#if>
					</tbody>
				</table>
			</div>
		</td>
	</tr>


	<#--  phd student  -->
	<#if listPhdStudentSupervision?has_content>
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivPHDDefensed}<a href = "/bkeuniv/control/phd-student-supervision">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
						<th>STT</th>
						<th>${uiLabelMap.BkEunivFirstAndLastName}</th>
						<th>${StringUtil.wrapString(uiLabelMap.PhDCoSupervision)}</th>
						<th>${StringUtil.wrapString(uiLabelMap.PhDTheSisName)}</th>
						<th>${uiLabelMap.BkEunivYearSuccess}</th>
					</tr>
					</thead>
					<tbody class="body-table">
					<#assign index = 0>
					<#list listPhdStudentSupervision as phdStudentSupervision>
					<tr>
						<#assign index = index+1>

						<td>${index}</td>
						<td>
							${StringUtil.wrapString(phdStudentSupervision.studentName?if_exists)}
						</td>
						
						<td>
							<#if phdStudentSupervision.coSupervion == "YES">
							${StringUtil.wrapString(uiLabelMap.CommonYes?if_exists)}
							<#else>
							${StringUtil.wrapString(uiLabelMap.CommonNo?if_exists)}
							</#if>
						</td>
					
						<td>
							${StringUtil.wrapString(phdStudentSupervision.thesisName?if_exists)}
						</td>
						<td>
							${phdStudentSupervision.graduateYear?if_exists}
						</td>
					</tr>
					</#list>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	</#if>
	
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivInformation2}<a href = "/bkeuniv/control/recent-research-direction">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
					</tr>
					</thead>
					<tbody class="body-table">
					<#if listRecentResearchDirection?has_content>
					<#list listRecentResearchDirection as researchDirection>
					<tr>
						<td>${researchDirection.keywords?if_exists} (${researchDirection.startYear?if_exists})</td>
					</tr>
					</#list>
					</#if>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="info-box">
				<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
					${uiLabelMap.BkEunivInformation3}<a href = "/bkeuniv/control/cv-thesis-subject-for-student">     [Update]</a>
				</div>
				<table>
					<thead>
					<tr>
					</tr>
					</thead>
					<tbody class="body-table">
					<#if listThesisSubjects?has_content>
					<#list listThesisSubjects as thesis>
					<tr>
						<td>${thesis.thesisSubjectPhDMasterName?if_exists} (${thesis.educationLevelName?if_exists})</td>
					</tr>
					</#list>
					</#if>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
</table>
