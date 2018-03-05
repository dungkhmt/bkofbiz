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
#form-add-workpackage-project {
    margin-top: 60px;
    margin-left: 100px;
}
</style>


<div id="form-add-project-call">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			project call name
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="projectCallName" style="width: 100%" type="text" width="1000" value=""/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		product category id
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="projectCategoryId" style="width: 100%" type="text" width="1000">
		 	<#list resultProjectCategory.projectCategorys as pc>
		 		<option value="${pc.projectCategoryId}">${pc.projectCategoryName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Nam
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="year" style="width: 100%" type="text" width="1000" value=""/>
	</div>
</div>



<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addProjectCall"/>
</div>


<script>


function addProjectCall(){
	var projectCategoryId = document.getElementById("projectCategoryId").value;
	var projectCallName = document.getElementById("projectCallName").value;
	var year = document.getElementById("year").value;
	
	$.ajax({
			url: "/bkeuniv/control/add-a-project-call",
			type: 'POST',
			data: {
				"projectCategoryId": projectCategoryId,
				"projectCallName": projectCallName,
				"year": year
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/project-call-management";
				console.log(rs.result);
			}
		})
		

}
</script>