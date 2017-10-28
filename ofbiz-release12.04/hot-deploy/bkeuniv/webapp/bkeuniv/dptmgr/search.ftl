<div id="search" class="tab-pane fade in active">
  <form class="row search-form">
    <div class="col-md-12">
      <h3>${uiLabelMap.CommonSearch}</h3>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentId}</div>
            <div class="form-value">
              <input name="departmentId" type="text">
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentName}</div>
            <div class="form-value">
              <input name="departmentName" type="text">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyId}</div>
            <div class="form-value">
              <select name="facultyId">
                <option value="">---</option>
                <#list availFaculty as fcl>
                  <option value="${fcl.facultyId}">${fcl.facultyId}</option>
                </#list>
              </select>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyName}</div>
            <div class="form-value">
              <input name="facultyName" type="text">
            </div>
          </div>
        </div>
      </div>
      <button class="btn btn-primary">${uiLabelMap.CommonSearch}</button>
    </div>
  </form>
  <div class="tabledata">
    <div class="tabledata-title"></div>
    <table class="table table-bordered table-hover table-view">
      <thead>
        <tr>
          <th>${uiLabelMap.BkEunivSTT}</th>
          <th>${uiLabelMap.BkEunivDepartmentId}</th>
          <th>${uiLabelMap.BkEunivDepartmentName}</th>
          <th>${uiLabelMap.BkEunivFacultyName}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
    <div class="tabledata-pagination"></div>
  </div>
</div>