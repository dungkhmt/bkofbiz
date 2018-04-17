<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<body>

	<div class="body">

		<style>
			#form-security-group-function {
				margin-top: 60px;
				margin-left: 100px;
			}
		</style>

		<div id="form-security-group-function">
			${bkEunivUiLabelMap.SecurityGroup}
						<select id="groupId" name="groupId" onchange='changeGroup(this)'>
							<#list securityGroup.securityGroups as sg>
								<option value="${sg.groupId}">${sg.description}</option>
							</#list>
						</select>		

		</div>

		<div id="form-security-group-function">	
			<div id="list-functions" style="overflow-y: auto; height:500px;">
			</div>

			<#--  <table>
				<tr>	
					<td>
						<table>
						<#list resultFunctions.functions as f>
							<tr>
								<td>
								<input type="checkbox" class="functions_bkeuniv" name="functions" value="${f.functionId}"/>${f.vnLabel}<br>
								</td>
							</tr>
						</#list>
						</table>
					</td>
				</tr>
				
			</table>  -->

		<@buttonStore text="${bkEunivUiLabelMap.BkEunivAdd}" action="updatePermission"/>
		</div>
		<script>
		function changeGroup(group){
			var groupId = group.value;
			$.ajax({
				url: "/bkeuniv/control/get-functions-of-security-group",
					type: 'POST',
					data: {
						"groupId": groupId				
					},
					success: function(rs){
						//window.location.href="/bkeuniv/control/enable-security-group-function";
						/*
						var html = '';
						for(i = 0; i < rs.functions.length; i++){
							html += '<input type=' + '"' + 'checkbox' + '"' + 
							'class=' + '"' + 'functions_bkeuniv' + '"' + 'name=' + '"' + 'functions' + '"'
							+ 'value=' + '"' + rs.functions[i].functionId + '"';// 
							if(rs.functions[i].checked === 1) html += ' checked';
							html += '/>' + rs.functions[i].vnLabel + '<br>';
						}
						*/
						var html = '<table>';
						for(i = 0; i < rs.functions.length; i++){
							html += '<tr>';
							html += '<td>';
							html += '<input type=' + '"' + 'checkbox' + '"' + 
							'class=' + '"' + 'functions_bkeuniv' + '"' + 'name=' + '"' + 'functions' + '"'
							+ 'value=' + '"' + rs.functions[i].functionId + '"';// 
							if(rs.functions[i].checked === 1) html += ' checked';
							html += '/>' + rs.functions[i].vnLabel + '<br>';
							html += '</td>';
							
							html += '<td>';
							if(rs.functions[i].parentFunctionId != null)
								html += rs.functions[i].parentFunctionId;
							else
								html += ''
							html += '</td>';
							html += '</tr>';
						}
						html += '</table>'
						console.log(html);
						document.getElementById("list-functions").innerHTML = html;
					}
			})
		}

		function updatePermission(){
			var checked_functions = Array.from($('.functions_bkeuniv').map(function(index) {
				return {
					name: this.value,
					checked: this.checked
				}
			})).filter(function(el) {
				return el.checked
			})
			console.log(checked_functions)

			if(checked_functions.length === 0) return;
			
			var groupId = document.getElementById("groupId").value;
			var funcs = checked_functions[0].name;
			for(i = 1; i < checked_functions.length; i++){
				funcs = funcs + ',' + checked_functions[i].name;
			}
			console.log(funcs);
			
			$.ajax({
					url: "/bkeuniv/control/store-security-group-functions",
					type: 'POST',
					data: {
						"groupId": groupId,
						"functions": funcs
					},
					success: function(rs){
						window.location.href="/bkeuniv/control/enable-security-group-function";
					}
				})
				
		}
		</script>
	</div>