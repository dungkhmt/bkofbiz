<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resource/bkeuniv/css/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>

</style>


<div id="form-setting-export-cv-pdf">
<table>
<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Qua trinh dao tao
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="education-progess" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Sang che
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="patent" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	De tai, du an co ung dung trong thuc te
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="projects-applied" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	NCS da bao ve thanh cong
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="phd-defensed" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	NCS, hoc vien cao hoc dang huong dan
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="current-graduate-students" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

</tr>

<tr>
<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Cong trinh khoa hoc cong bo
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="publications" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Qua trinh cong tac
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="work-progress" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	De tai, du an, nhiem vu KHCN da chu tri 5 nam gan day
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="recent5year-projects" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Giai thuong KHCN
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="award" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

<td>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
	Kinh nghiem quan ly, danh gia KHCN
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="scientific-service" style="width: 100%" type="text" width="1000" 
		value=""/>
	</div>
</div>
</td>

</tr>

</table>


<@buttonConfirm text="${uiLabelMap.viewPDF}" action="viewPDF"/>
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