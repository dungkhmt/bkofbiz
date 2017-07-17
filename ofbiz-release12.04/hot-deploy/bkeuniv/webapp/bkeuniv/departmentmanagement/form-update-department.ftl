
<form action="<@ofbizUrl>updateADepartment</@ofbizUrl>" method="post">
<table>
	<tr>
		<td>Ten bo mon</td>
		<td><input type="text" name="departmentName" id="departmentName" value="${deptgv.departmentName}" style="width:200px"/></td>
	</tr>
	<tr>
		<td>Khoa</td>
		<td>
			<select id="facultyId" name="facultyId" style="width:200px">
        	<#list listFaculty as f>
        		<option value="${f.facultyId}" <#if (f.facultyId == deptgv.facultyId)> selected="selected"</#if>>${f.facultyName}</option>
        	</#list>        	
       		</select>
		</td>
	</tr>
	
	<tr>
        <td>
        	 <div class="button-bar">
          	 <input type="submit" value="Cap nhat"/>
       		 </div>
        </td>
    </tr>
        
</table>
<input type="hidden" name="department-id" value="${deptgv.departmentId}"/>
</form>