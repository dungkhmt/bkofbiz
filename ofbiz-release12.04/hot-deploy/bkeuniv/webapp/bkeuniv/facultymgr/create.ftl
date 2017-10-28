<div id="create" class="tab-pane fade">
  <div class="item-detail">
    <form class="form-view create-form">
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivFacultyId}</div>
        <div class="form-value">
          <input name="facultyId" type="text">
        </div>
      </div>
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivFacultyName}</div>
        <div class="form-value">
          <input name="facultyName" type="text">
        </div>
      </div>
      <div class="form-item">
        <div class="form-key">${uiLabelMap.BkEunivUniversityName}</div>
        <div class="form-value">
          <select name="universityId">
            <#list availUniversity as univ>
              <option value="${univ.universityId}">${univ.universityName}</option>
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