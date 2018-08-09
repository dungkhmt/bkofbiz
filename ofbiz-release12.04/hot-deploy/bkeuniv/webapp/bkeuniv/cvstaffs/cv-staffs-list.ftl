<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	var modalChangePassword;
	var staffIdTemp;
	function createContextMenu(id) {
	
	}
				
	function jqPDF(data){
		console.log(data);
	}

	function openSetting(staffId) {
		staffIdTemp = staffId;
		$("#setting-export-pdf").modal();
	}

	$("#setting-export-pdf").ready(function(){
		var maxHeight = parseInt(window.innerHeight*0.6)
		
		if($(this).height() >= maxHeight) {
	        $('.modal-body').css('max-height', maxHeight);
	    }else{
	        $('.modal-body').css('max-height', '');
	    }
	});
	
</script>

<div class="body">
	<div id = "modelChangePassword">
	
	</div>
	
	<#assign columns=[
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivFaculty?j_string,
			"data": "facultyName"
		},
    {
			"name": "CV"?j_string,
			"data": "staffId",
      "render": 'function(value, name, dataColumns, id) {
				return \'<button type="button" onClick=\\\'openSetting("\'+value+\'")\\\' class="btn btn-primary">CV PDF</button>\';
			}'
		}
	] />
	<#--  return \'<a href="/bkeuniv/control/profile-science-pdf?sections=education-progress&sections=work-progress&sections=publications&sections=recent-5-year-projects&sections=award&sections=scientific-service&sections=projects-applied&sections=patent&sections=phd-defensed&sections=graduate-students&staffId=\'+value+\'" target="_blank">CV PDF</a>\';  -->
	<#assign fields=[
		"staffId",
		"staffName",
		"staffEmail",
		"genderName",
		"staffGenderId",
		"staffDateOfBirth",
		"staffPhone",
		"departmentName",
		"departmentId",
		"facultyName",
		"facultyId"
		
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		urlDelete="/bkeuniv/control/remove-a-staff" 
		keysId=["staffId"] 
		fieldDataResult = "results" 
		titleChange=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleNew=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleDelete=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		jqTitle=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>

	<div class="modal fade" id="setting-export-pdf" style="margin-top: 5%;" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">${uiLabelMap.BkEunivSetting}</h4>
				</div>
				<div class="modal-body" style="max-height: 500px; overflow-y: auto;">
					<div class='wrapper row'>
						<div class="col-sm-6" style="box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; text-align: center; font-size: 16px; font-weight: 500; padding: 0.7em;">
							${uiLabelMap.BkEunivSectionOrder}
						</div>
						<div class="col-sm-6" style="box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; text-align: center; font-size: 16px; font-weight: 500; padding: 0.7em;">
							${uiLabelMap.BkEunivSectionList}
						</div>
						<ul id='left-events' style="margin: 0;border: solid 1px #00000038; padding-right: 0px; min-height: 600px;" class="col-sm-6 collapsible collapsible-accordion" data-collapsible="accordion">
							<li style="list-style-type: none;" id="information">
								<div class="collapsible-header" style="padding: 0.7rem; background-color: #0000000d;"><div style="font-weight: bold;">1.&nbsp;</div>${uiLabelMap.BkEunivBasicInformation}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/Infor.png" alt="${uiLabelMap.BkEunivBasicInformation}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="education-progress">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">2.&nbsp;</div>${uiLabelMap.BkEunivEducationProgress}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/education-progress.png" alt="${uiLabelMap.BkEunivEducationProgress}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="work-progress">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">3.&nbsp;</div>${uiLabelMap.BkEunivWorkProgress}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/work-progress.png" alt="${uiLabelMap.BkEunivWorkProgress}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="publications">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">4.&nbsp;</div>${uiLabelMap.BkEunivPublications}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/publications.png" alt="${uiLabelMap.BkEunivPublications}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="recent-5-year-projects">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">5.&nbsp;</div>${uiLabelMap.BkEunivRecent5YearProjects}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/recent5year-projects.png" alt="${uiLabelMap.BkEunivRecent5YearProjects}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="award">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">6.&nbsp;</div>${uiLabelMap.BkEunivAward}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/award.png" alt="${uiLabelMap.BkEunivAward}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="scientific-service">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">7.&nbsp;</div>${uiLabelMap.BkEunivScientificService1}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/scientific-service.png" alt="${uiLabelMap.BkEunivScientificService1}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="projects-applied">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">8.&nbsp;</div>${uiLabelMap.BkEunivProjectsApplied}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/projects-applied.png" alt="${uiLabelMap.BkEunivProjectsApplied}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="patent">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">9.&nbsp;</div>${uiLabelMap.BkEunivPatent1}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/patent.png" alt="${uiLabelMap.BkEunivPatent1}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="phd-defensed">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">10.&nbsp;</div>${uiLabelMap.BkEunivPHDDefensed}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/phd-defensed.png" alt="${uiLabelMap.BkEunivPHDDefensed}" height="auto" width="100%">
								</div>
							</li>

							<li style="list-style-type: none;" id="graduate-students">
								<div class="collapsible-header" style="padding: 0.7rem;" ><div style="font-weight: bold;">11.&nbsp;</div>${uiLabelMap.BkEunivGraduateStudents}</div>
								<div class="collapsible-body">
									<img src="/resource/bkeuniv/image/current-graduate-students.png" alt="${uiLabelMap.BkEunivGraduateStudents}" height="auto" width="100%">
								</div>
							</li>
						</ul>
						<div id='right-events' style="margin: 0;border: solid 1px #00000038; padding-right: 0px; padding-left: 0px; min-height: 600px;" class="col-sm-6 collapsible collapsible-accordion" data-collapsible="accordion">
						</div>
					</div>
					<script>
						dragula([document.getElementById('left-events'), document.getElementById('right-events')], {
							accepts: function (el, target) {
								return el !== document.getElementById("information");
							}
						})
						.on('drag', function (el) {
							sortIndex("left-events");
							sortIndex("right-events", false);
						}).on('drop', function (el) {
							sortIndex("left-events");
							sortIndex("right-events", false);
						}).on('over', function (el, container) {
							sortIndex("left-events");
							sortIndex("right-events", false);
						}).on('out', function (el, container) {
							sortIndex("left-events");
							sortIndex("right-events", false);
						});
					
						function sortIndex(id, numberT=true) {
							Array.from(document.getElementById(id).children).forEach(function(el, index) {
								el.firstElementChild.firstElementChild.innerHTML = !!numberT?(index + 1) + ".&nbsp;":"";
							});
						}

						function exportPDF() {
							params=[];
							Array.from(document.getElementById("left-events").children).forEach(function(el, index) {
								params.push('sections=' + el.id);
							});
							window.open("/bkeuniv/control/profile-science-pdf?" + params.join("&") + "&staffId=" + staffIdTemp, "_blank");
						}
					</script>

					<script>
						$(document).ready(function(){
							$('.collapsible').collapsible();
						});
					</script>
				</div>
				<div class="modal-footer">
				<div style="position: relative; z-index: 2; display: inline-block; justify-content: flex-end; flex-wrap: wrap;">
					<@FlatButton id="export-pdf" onClick="exportPDF()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 130px">
						<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
							<path d="M20 2H8c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-8.5 7.5c0 .83-.67 1.5-1.5 1.5H9v2H7.5V7H10c.83 0 1.5.67 1.5 1.5v1zm5 2c0 .83-.67 1.5-1.5 1.5h-2.5V7H15c.83 0 1.5.67 1.5 1.5v3zm4-3H19v1h1.5V11H19v2h-1.5V7h3v1.5zM9 9.5h1v-1H9v1zM4 6H2v14c0 1.1.9 2 2 2h14v-2H4V6zm10 5.5h1v-3h-1v3z"></path>
						</svg>
						${uiLabelMap.BkEunivExportPDF}
					</@FlatButton>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>