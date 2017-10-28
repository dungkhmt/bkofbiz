<div id="search" class="tab-pane fade in active">
  <form class="row search-form">
    <div class="col-md-12">
      <h3>${uiLabelMap.CommonSearch}</h3>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyId}</div>
            <div class="form-value">
              <input name="facultyId" type="text">
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
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivUniversityId}</div>
            <div class="form-value">
              <select name="universityId">
                <option value="">---</option>
                <#list availUniversity as univ>
                  <option value="${univ.universityId}">${univ.universityId}</option>
                </#list>
              </select>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivUniversityName}</div>
            <div class="form-value">
              <input name="universityName" type="text">
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
          <th>${uiLabelMap.BkEunivFacultyId}</th>
          <th>${uiLabelMap.BkEunivFacultyName}</th>
          <th>${uiLabelMap.BkEunivUniversityName}</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
    <div class="tabledata-pagination"></div>
  </div>
</div>