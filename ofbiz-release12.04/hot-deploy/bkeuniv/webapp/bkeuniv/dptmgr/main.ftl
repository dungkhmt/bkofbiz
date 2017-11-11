<div class="main-wrapper" ng-app="department" ng-controller="dptCtrl">
  <div class="main-header">
    <h2 class="main-title">${uiLabelMap.BkEunivDepartmentMgr}</h2>
    <div class="main-action">
      <div class="group-right">
        <button class="btn btn-primary" ng-click="openCreateModal()">
          ${uiLabelMap.BkEunivAddRow}
        </button>
      </div>
      <div class="group-center">
        <button class="btn btn-primary">${uiLabelMap.CommonPrint}</button>
        <button class="btn btn-primary">${uiLabelMap.CommonMore} <span class="caret"></span></button>
      </div>
      <div class="group-left"></div>
    </div>
  </div>
  <div class="main-body">
    <#include "./search.ftl"/>
    <#include "./edit.ftl"/>
    <#include "./delete.ftl"/>
  </div>
</div>

