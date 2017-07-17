
<form action="<@ofbizUrl>createADepartment</@ofbizUrl>" method="post">
<table>
	<tr>
		<td>Ten bo mon</td>
		<td><input type="text" name="departmentName" id="departmentName" value="" style="width:200px"/></td>
	</tr>
	<tr>
		<td>Khoa</td>
		<td>
			<select id="facultyId" name="facultyId" style="width:200px">
        	<#list listFaculty as f>
        		<option value="${f.facultyId}">${f.facultyName}</option>
        	</#list>        	
       		</select>
		</td>
	</tr>
	
	<tr>
        <td>
        	 <div class="button-bar">
          	 <input type="submit" value="Them"/>
       		 </div>
        </td>
    </tr>
        
</table>

</form>