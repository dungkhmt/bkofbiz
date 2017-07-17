<a href="add-department">Them moi bo mon</a><br>

<table>

<#list listDepartments as d>
	<tr>
		<td>${d.departmentId}</td>
		<td>${d.departmentName}</td>
		<td>${d.facultyId}</td>
		<td><a href="request-view-for-update-department?idofdepartment=${d.departmentId}">Cap nhat</a></td>
		<td><a href="delete-department?idofdepartment=${d.departmentId}">Xoa</a></td>
	</tr>
</#list>

</table>