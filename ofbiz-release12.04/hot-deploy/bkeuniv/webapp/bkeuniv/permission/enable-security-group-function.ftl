
<style>
#form-security-group-function {
    margin-top: 60px;
}
</style>

<div id="form-security-group-function">
<form action="<@ofbizUrl>update-security-group-function</@ofbizUrl>" method="post">
	<table>
		<tr>
			<td>
				<select id="groupId" name="groupId">
					<#list securityGroup.securityGroups as sg>
						<option value="${sg.groupId}">${sg.description}</option>
					</#list>
				</select>		
			</td>
			
			<td>
				<table>
				<#list resultFunctions.functions as f>
					<tr>
						<td>
						<input type="checkbox" id="functions" name="functions" value="${f.functionId}"/>${f.vnLabel}<br>
						</td>
					</tr>
				</#list>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			<input type="submit" value="Submit"/>
			</td>
		</tr>
	</table>
</form>
</div>