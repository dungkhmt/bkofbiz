<div id="editmodal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" ng-show="editMode">${uiLabelMap.CommonEdit}</h4>
        <h4 class="modal-title" ng-show="!editMode">${uiLabelMap.CommonCreate}</h4>
      </div>
      <div class="modal-body">
        <form class="form-view edit-form">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentId}</div>
            <div class="form-value">
              <input ng-show="editMode" ng-model="editForm.departmentId" type="text" disabled>
              <input ng-show="!editMode" ng-model="editForm.departmentId" type="text">
            </div>
          </div>
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentName}</div>
            <div class="form-value">
              <input ng-model="editForm.departmentName" type="text">
            </div>
          </div>
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyName}</div>
            <div class="form-value">
              <select ng-model="editForm.facultyId" style="max-width:150px">
                <#list availFaculty as fcl>
                  <option value="${fcl.facultyId}">${fcl.facultyName}</option>
                </#list>
              </select>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-default" data-dismiss="modal">${uiLabelMap.CommonCancel}</button>
        <button ng-show="editMode" class="btn btn-primary" ng-click="update()">${uiLabelMap.BkEunivUpdate}</button>
        <button ng-show="!editMode" class="btn btn-primary" ng-click="create()">${uiLabelMap.CommonCreate}</button>
      </div>
    </div>
  </div>
</div>