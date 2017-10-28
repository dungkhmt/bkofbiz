<div class="main-wrapper">
  <div class="main-header">
    <h2 class="main-title">${uiLabelMap.BkEunivDepartmentMgr}</h2>
    <div class="main-action">
      <div class="group-right">
        <button class="btn btn-primary" data-toggle="tab" data-target="#search">
          ${uiLabelMap.CommonSearch}
        </button>
        <button class="btn btn-primary" data-toggle="tab" data-target="#edit">
          ${uiLabelMap.CommonEdit}
        </button>
        <button class="btn btn-primary" data-toggle="tab" data-target="#create">
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
    <div class="tab-content">

      <!--Search-->
      <#include "./search.ftl"/>

      <!--Edit-->
      <#include "./edit.ftl"/>

      <!--Create-->
      <#include "./create.ftl"/>
    </div>
  </div>
</div>