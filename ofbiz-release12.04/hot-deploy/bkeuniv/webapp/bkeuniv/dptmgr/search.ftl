<div id="search">
  <form class="row search-form" ng-submit="searchNew()">
    <div class="col-md-12">
      <h3>${uiLabelMap.CommonSearch}</h3>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentId}</div>
            <div class="form-value">
              <input ng-model="searchForm.departmentId" type="text">
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivDepartmentName}</div>
            <div class="form-value">
              <input ng-model="searchForm.departmentName" type="text">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyId}</div>
            <div class="form-value">
              <select ng-model="searchForm.facultyId">
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
              <input ng-model="searchForm.facultyName" type="text">
            </div>
          </div>
        </div>
      </div>
      <button class="btn btn-primary">${uiLabelMap.CommonSearch}</button>
    </div>
  </form>
  <div class="tabledata">
    <div class="tabledata-title" ng-show="items.length !== 0">
      Display {{paging.from}} - {{paging.to}} of {{paging.totalSize}}
    </div>
    <table class="table table-bordered table-hover table-view">
      <thead>
        <tr>
          <th>${uiLabelMap.BkEunivSTT}</th>
          <th>${uiLabelMap.BkEunivDepartmentId}</th>
          <th>${uiLabelMap.BkEunivDepartmentName}</th>
          <th>${uiLabelMap.BkEunivFacultyName}</th>
          <td></td>
        </tr>
      </thead>
      <tbody>
        <tr ng-repeat="item in items">
          <td>{{getItemIndex($index)}}</td>
          <td>{{item.departmentId}}</td>
          <td>{{item.departmentName}}</td>
          <td>{{item.facultyName}}</td>
          <td>
            <button class="btn btn-primary" ng-click="openEditModal(item)"><i class="fa fa-edit"></i></button>
            <button class="btn btn-danger" ng-click="openDeleteModal(item)"><i class="fa fa-trash"></i></button>
          </td>
        </tr>
      </tbody>
    </table>
    <div ng-show="items.length === 0" class="alert alert-warning text-center">No data</div>
    <div class="tabledata-pagination" ng-hide="items.length === 0">
      <ul class="pagination">
        <li ng-click="fisrtPage()"><a href="">First</a></li>
        <li ng-click="prevPage()"><a href="">Prev</a></li>
        <li ng-click="nextPage()"><a href="">Next</a></li>
        <li ng-click="lastPage()"><a href="">Last</a></li>
      </ul>
    </div>
  </div>
</div>