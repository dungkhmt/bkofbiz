<div id="create" class="tab-pane fade">
  <div class="item-detail">
    <form class="form-view create-form">
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivDepartmentId}</div>
        <div class="form-value">
          <input name="departmentId" type="text">
        </div>
      </div>
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivDepartmentName}</div>
        <div class="form-value">
          <input name="departmentName" type="text">
        </div>
      </div>
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivFacultyName}</div>
        <div class="form-value">
          <select name="facultyId">
            <#list availFaculty as fcl>
              <option value="${fcl.facultyId}">${fcl.facultyName}</option>
            </#list>
          </select>
        </div>
      </div>
      <p>
        <button class="btn btn-primary">${uiLabelMap.BkEunivAdd}</button>
      </p>
    </form>
  </div>
</div>