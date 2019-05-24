<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>


<style>
  .boundary {
    flex: 1 1 0%;
    padding: 2em 3em 6em 3em;
    width: 100%;
    overflow-y: auto;
    height: 100%;
    background-color: rgb(237, 236, 236);
  }

  .content {
    color: rgba(0, 0, 0, 0.87);
    background-color: rgb(255, 255, 255);
    transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;
    box-sizing: border-box;
    font-family: Roboto, sans-serif;
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px;
    border-radius: 2px;
    z-index: 1;
    opacity: 1;
    padding: 1em;
  }
</style>

<body>
  <div class="boundary">
    <div class="content">

      <!-- title -->
      <div class="row">
        <div class="title" style="padding: 16px; position: relative;">
          <div style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
            <div id="test">${bkEunivUiLabelMap.CVGeneralInformation?if_exists}</div>
          </div>
        </div>
      </div>
      <!-- end-title -->

      <!-- form -->
      <div class="row form-group" style="display: flex">
        <div class="col-10 col-md-10" style="margin: auto">
          <!-- <form class="form-horizontal">0 -->
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVStaffName?if_exists}
            </label>
            <div class="col-6 col-md-6">
                <#if staffInfo.staffCVInfo.staffName?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.staffName)}
                </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVDepartment?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <div id="deparment">
              	<#if staffInfo.staffCVInfo.departmentName?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.departmentName)}
                </#if>
              </div>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVAcademicRank?if_exists}
            </label>
            <div class="col-6 col-md-6">
              	<#if staffInfo.staffCVInfo.academicName?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.academicName)}
                </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVAcademicRankYear?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.academicRankYear?has_content>
                  ${staffInfo.staffCVInfo.academicRankYear}
               </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVDegree?if_exists}
            </label>
            <div class="col-6 col-md-6">
              	<#if staffInfo.staffCVInfo.degreeName?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.degreeName)}
                 </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVDegreeYear?if_exists}
            </label>
            <div class="col-6 col-md-6">
            	<#if staffInfo.staffCVInfo.degreeYear?has_content>
                  ${staffInfo.staffCVInfo.degreeYear}
                </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVDutyPosition?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.duty?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.duty)}
               </#if>
            </div>
          </div>
           <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.CVResearchPosition?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.researchPosition?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.researchPosition?if_exists)}
               </#if>
            </div>
          </div>
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.BkEunivNameLeader?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.agencyWorkLeaderName?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.agencyWorkLeaderName?if_exists)}
               </#if>
            </div>
          </div>
          
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.BkEunivCompanyAddress?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.agencyWorkAddress?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.agencyWorkAddress?if_exists)}
               </#if>
            </div>
          </div>
          
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.BkEnuivPhone?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.agencyWorkPhone?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.agencyWorkPhone?if_exists)}
               </#if>
            </div>
          </div>
          
          <div class="row form-group">
            <label class="col-6 col-md-6 control-label">
            	${bkEunivUiLabelMap.BkEnuivFax?if_exists}
            </label>
            <div class="col-6 col-md-6">
              <#if staffInfo.staffCVInfo.agencyWorkFax?has_content>
                  ${StringUtil.wrapString(staffInfo.staffCVInfo.agencyWorkFax?if_exists)}
               </#if>
            </div>
          </div>
          <div class="row form-group" style="display: flex;">
            <div style="margin: auto;">
              <button id="updateInfoBtn" type="button" class="btn btn-success">	
            	${bkEunivUiLabelMap.CVUpdateInfo?if_exists}
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- end-form -->

    </div>
  </div>
</body>

<#include "update-cv-general-info.ftl" />

<script>

	$('#updateInfoBtn').on('click', function(){
		updateGeneralInfoObj.openModal();
	});

</script>
  