<head>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/materialize.min.css">
</head>
<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

<body>
<style>
	.index {
		font-weight: bold;
	}
</style>

<div class="body">
	<div style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
		<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
			<div style="display: flex; justify-content: space-between;">
				<div class="title" style="padding: 16px; position: relative;">
					<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
						<span id="jqTitlePage">${titlePage?if_exists}</span>
						<script>
							$(document).ready(function(){
								document.getElementById("jqTitlePage").innerHTML = titlePage;
							})
						</script>
					</span>
					<span style="font-size: 14px; color: rgba(0, 0, 0, 0.54); display: block;"></span>
				</div>

				<div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
					<@FlatButton id="export-pdf" onClick="exportPDF()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
						<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
							<path d="M20 2H8c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-8.5 7.5c0 .83-.67 1.5-1.5 1.5H9v2H7.5V7H10c.83 0 1.5.67 1.5 1.5v1zm5 2c0 .83-.67 1.5-1.5 1.5h-2.5V7H15c.83 0 1.5.67 1.5 1.5v3zm4-3H19v1h1.5V11H19v2h-1.5V7h3v1.5zM9 9.5h1v-1H9v1zM4 6H2v14c0 1.1.9 2 2 2h14v-2H4V6zm10 5.5h1v-3h-1v3z"></path>
						</svg>
						${uiLabelMap.BkEunivExportPDF}
					</@FlatButton>
				</div>
			</div>
			
		<div class='wrapper row'>
			<div class="col-sm-6" style="box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; text-align: center; font-size: 16px; font-weight: 500; padding: 0.7em;">
				${uiLabelMap.BkEunivSectionOrder}
			</div>
			<div class="col-sm-6" style="box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; text-align: center; font-size: 16px; font-weight: 500; padding: 0.7em;">
				${uiLabelMap.BkEunivSectionList}
			</div>
			<ul id='left-events' style="margin: 0;border: solid 1px #00000038; padding-right: 0px; min-height: 600px;" class="col-sm-6 collapsible collapsible-accordion" data-collapsible="accordion">
				<li style="list-style-type: none;" id="information">
					<div class="collapsible-header" style="background-color: #0000000d;"><div class="index">1.&nbsp;</div>${uiLabelMap.BkEunivBasicInformation}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/Infor.png" alt="${uiLabelMap.BkEunivBasicInformation}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="education-progress">
					<div class="collapsible-header"><div class="index">2.&nbsp;</div>${uiLabelMap.BkEunivEducationProgress}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/education-progress.png" alt="${uiLabelMap.BkEunivEducationProgress}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="work-progress">
					<div class="collapsible-header"><div class="index">3.&nbsp;</div>${uiLabelMap.BkEunivWorkProgress}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/work-progress.png" alt="${uiLabelMap.BkEunivWorkProgress}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="publications">
					<div class="collapsible-header"><div class="index">4.&nbsp;</div>${uiLabelMap.BkEunivPublications}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/publications.png" alt="${uiLabelMap.BkEunivPublications}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="recent-5-year-projects">
					<div class="collapsible-header"><div class="index">5.&nbsp;</div>${uiLabelMap.BkEunivRecent5YearProjects}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/recent5year-projects.png" alt="${uiLabelMap.BkEunivRecent5YearProjects}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="award">
					<div class="collapsible-header"><div class="index">6.&nbsp;</div>${uiLabelMap.BkEunivAward}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/award.png" alt="${uiLabelMap.BkEunivAward}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="scientific-service">
					<div class="collapsible-header"><div class="index">7.&nbsp;</div>${uiLabelMap.BkEunivScientificService}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/scientific-service.png" alt="${uiLabelMap.BkEunivScientificService}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="projects-applied">
					<div class="collapsible-header"><div class="index">8.&nbsp;</div>${uiLabelMap.BkEunivProjectsApplied}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/projects-applied.png" alt="${uiLabelMap.BkEunivProjectsApplied}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="patent">
					<div class="collapsible-header"><div class="index">9.&nbsp;</div>${uiLabelMap.BkEunivPatent}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/patent.png" alt="${uiLabelMap.BkEunivPatent}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="phd-defensed">
					<div class="collapsible-header"><div class="index">10.&nbsp;</div>${uiLabelMap.BkEunivPHDDefensed}</div>
					<div class="collapsible-body">
						<img src="/resource/bkeuniv/image/phd-defensed.png" alt="${uiLabelMap.BkEunivPHDDefensed}" height="auto" width="100%">
					</div>
				</li>

				<li style="list-style-type: none;" id="graduate-students">
					<div class="collapsible-header"><div class="index">11.&nbsp;</div>${uiLabelMap.BkEunivGraduateStudents}</div>
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
				window.open("/bkeuniv/control/profile-science-pdf?" + params.join("&"), "_blank");
				window.location.href="/bkeuniv/control/profile-science-pdf?"+sections.map(function(s) { return "sections="+s.name }).join("&");
			}
		</script>
		</div>
	</div>

	<script>
		$(document).ready(function(){
			$('.collapsible').collapsible();
		});
	</script>

	<#--  <@buttonConfirm text="${uiLabelMap.viewPDF}" action="viewPDF"/>  -->
	</div>

	<script>
	function viewPDF(){

		var sections=[
			{
				name: "education-progress",
				index: document.getElementById("education-progess").value
			},
			{
				name: "patent",
				index: document.getElementById("patent").value
			},
			{
				name: "projects-applied",
				index: document.getElementById("projects-applied").value
			},
			{
				name: "phd-defensed",
				index: document.getElementById("phd-defensed").value
			},
			{
				name: "graduate-students",
				index: document.getElementById("current-graduate-students").value
			},
			{
				name: "publications",
				index: document.getElementById("publications").value
			},
			{
				name: "work-progress",
				index: document.getElementById("work-progress").value
			},
			{
				name: "recent-5-year-projects",
				index: document.getElementById("recent5year-projects").value
			},
			{
				name: "award",
				index: document.getElementById("award").value
			},
			{
				name: "scientific-service",
				index: document.getElementById("scientific-service").value
			}
		];

		sections = sections.map(function(section) {
			section.index=parseInt(section.index)
			return section
		}).filter(function(section) {
			return !!section.index
		}).sort(function(a, b) {
			return a.index - b.index
		})

		window.location.href="/bkeuniv/control/profile-science-pdf?"+sections.map(function(s) { return "sections="+s.name }).join("&");
	}

	</script>
</div>