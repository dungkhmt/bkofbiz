<div id="search">
  <form class="row search-form" ng-submit="searchNew()">
    <div class="col-md-12">
      <h3>${uiLabelMap.CommonSearch}</h3>
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivFacultyId}</div>
            <div class="form-value">
              <input ng-model="searchForm.facultyId" type="text">
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
      <div class="row">
        <div class="col-md-6">
          <div class="form-item">
            <div class="form-key">${uiLabelMap.BkEunivUniversityId}</div>
            <div class="form-value">
              <select ng-model="searchForm.universityId">
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
              <input ng-model="searchForm.universityName" type="text">
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
          <th>${uiLabelMap.BkEunivFacultyId}</th>
          <th>${uiLabelMap.BkEunivFacultyName}</th>
          <th>${uiLabelMap.BkEunivUniversityName}</th>
          <td></td>
        </tr>
      </thead>
      <tbody>
        <tr ng-repeat="item in items">
          <td>{{getItemIndex($index)}}</td>
          <td>{{item.facultyId}}</td>
          <td>{{item.facultyName}}</td>
          <td>{{item.universityName}}</td>
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