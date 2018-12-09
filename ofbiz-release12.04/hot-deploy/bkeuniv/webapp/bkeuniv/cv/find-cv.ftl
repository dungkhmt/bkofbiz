<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<link rel="stylesheet" href="/resource/bkeuniv/css/lib/selectize.default.css">
<script src="/resource/bkeuniv/js/lib/selectize.js"></script>

<style>
  .boundary {
    flex: 1 1 0%;
    padding: 2em 2em 6em 3em;
    width: 100%;
    overflow-y: auto;
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
            <div>${bkEunivUiLabelMap.FindCV?if_exists}</div>
          </div>
        </div>
      </div>
      <!-- end-title -->

      <!-- form -->
      <form id="testForm" data-toggle="validator">
      <div class="row form-group" style="display: flex">
          <div class="col-12 col-md-10 " style="margin: auto">
            <!-- <form class="form-horizontal">0 -->
            <div class="row form-group">
              <label class="col-6 col-md-6 control-label">
                ${bkEunivUiLabelMap.BkEunivResearchDomain?if_exists}
              </label>
              <div class="col-6 col-md-6">
                <select id="researchDomain" class="js-example-responsive" style="width: 100%"></select>
              </div>
            </div>
            <div class="row form-group">
              <label class="col-6 col-md-6 control-label">
                ${bkEunivUiLabelMap.BkEunivResearchSubDomain?if_exists}
              </label>
              <div class="col-6 col-md-6">
                <select disabled id="researchSubDomain" class="js-example-responsive" style="width: 100%"></select>
              </div>
            </div>
            <div class="row form-group">
              <label class="col-6 col-md-6 control-label">
                ${bkEunivUiLabelMap.BkEunivResearchSpeciality?if_exists}
              </label>
              <div class="col-6 col-md-6">
                <select disabled id="researchSpeciality" class="js-example-responsive" style="width: 100%"></select>
              </div>
            </div>
            <div class="row form-group">
              <label for="dutyId" class="col-6 col-md-6 control-label">${bkEunivUiLabelMap.BkEunivProjectsApplied}</label>
                <div class="col-6 col-md-6">
                  <input type="number" class="form-control" id="numberProjectApplied" value="0">
                </div>
            </div>
            <div class="row form-group">
              <label for="dutyId" class="col-6 col-md-6 control-label">${bkEunivUiLabelMap.BkEunivScientificService1?if_exists}</label>
                <div class="col-6 col-md-6">
                  <input type="number" class="form-control" id="numberScientificService" value="0">
                </div>
            </div>
            <div class="row form-group">
              <label for="dutyId" class="col-6 col-md-6 control-label">${bkEunivUiLabelMap.BkEunivPublications?if_exists}</label>
                <div class="col-6 col-md-6">
                  <input type="number" class="form-control" id="numberPublications" value="0">
                </div>
            </div>
            <div class="row form-group">
              <label for="dutyId" class="col-6 col-md-6 control-label">${bkEunivUiLabelMap.BkEunivRecent5YearProjects?if_exists}</label>
                <div class="col-6 col-md-6">
                  <input type="number" class="form-control" id="numberRecent5YearProjects" value="0">
                </div>
            </div>
            <div class="row form-group" style="display: flex; margin-top: 10px">
              <div style="margin: auto;">
                <button disabled id="findCVBtn" type="button" class="btn btn-success">	
                  <span class="glyphicon glyphicon-search"></span>
                  ${bkEunivUiLabelMap.Find?if_exists}
                </button>
              </div>
            </div>
            
          </div>
        </div>
        </form>
        <!-- end-form -->
	
	  </div>
  </div>

</body>

<script>

	$(document).ready(function(){
		CVFindObject.init();
	});
	
	var CVFindObject = (function(){
		
		init = () => {
			initSelectBox();
      initEvents();
		}
		
	  initSelectBox = () => {

      render1 = function(r){return {id: r.researchDomainId, text: r.researchDomainName}};
      render2 = function(r){return {id: r.researchSubDomainSeqId, text: r.researchSubDomainName}};
      render3 = function(r){return {id: r.researchSpecialitySeqId, text: r.researchSpecialityName}};

      $("#researchDomain").select2({
        ajax: {
          url: "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchDomainManagement",
          cache: true,
          data: function (params) {
            var query = {
              q: params.term || "",
              pagenum: 0,
              pagesize: 10,
            };
            return query;
          },
          processResults: function (data) {
            return {
              results: data.results.map(render1),
              pagination: {
                more: !(data.results.length < 10 || data.results.length == data.totalRows),
              }
            }
          }
        },
        minimumInputLength: 1,
      });

      $("#researchSubDomain").select2({
        ajax: {
          url: function (params) {
            return "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSubDomainManagement&researchDomainId="
            + $('#researchDomain').val();
          },
          cache: true,
          data: function (params) {
            var query = {
              q: params.term || "",
              pagenum: 0,
              pagesize: 10,
            };
            return query;
          },
          processResults: function (data) {
            return {
              results: data.results.map(render2),
              pagination: {
                more: !(data.results.length < 10 || data.results.length == data.totalRows),
              }
            }
          }
        },
        minimumInputLength: 1,
      });

      

      $("#researchSpeciality").select2({
        ajax: {
          url: function (params) {
            return "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement&researchDomainId="
             + $('#researchDomain').val() + "&researchSubDomainSeqId=" + $("#researchSubDomain").val() ;
          },
          cache: true,
          data: function (params) {
            var query = {
              q: params.term || "",
              pagenum: 0,
              pagesize: 10,
            };
            return query;
          },
          processResults: function (data) {
            return {
              results: data.results.map(render3),
              pagination: {
                more: !(data.results.length < 10 || data.results.length == data.totalRows),
              }
            }
          }
        },
        minimumInputLength: 1,
      });

    }

    initEvents = () => {

      $('#researchDomain').on('select2:select', function (e) {
        if($('#researchDomain').val()){
          $("#researchSubDomain").removeAttr('disabled');
        }else{
          $("#researchSubDomain").attr('disabled','disabled');
        }
      });

      $('#researchSubDomain').on('select2:select', function (e) {
        if($('#researchSubDomain').val()){
          $("#researchSpeciality").removeAttr('disabled');
        }else{
          $("#researchSpeciality").attr('disabled','disabled');
        }
      });

      $('#researchSpeciality').on('select2:select', (e)=>{
        if($("#researchSpeciality").val()){
          $("#findCVBtn").removeAttr('disabled');
        }else{
          $("#findCVBtn").attr('disabled','disabled');
        }
      });
      

      let redirectUrl = 'find-cv-results?';

      $('#findCVBtn').on('click', () => {
        redirectUrl = redirectUrl
         + 'researchDomainId=' + $('#researchDomain').val()
         + '&researchSubDomainSeqId=' + $('#researchSubDomain').val()
         + '&researchSpecialitySeqId=' + $('#researchSpeciality').val()
         + '&numberProjectApplied=' + $('#numberProjectApplied').val()
         + '&numberScientificService=' + $('#numberScientificService').val()
         + '&numberPublications=' + $('#numberPublications').val()
         + '&numberRecent5YearProjects=' + $('#numberRecent5YearProjects').val()
        location.href = redirectUrl;
      });

    }
	
		return {
			init : init
		}

	}());
</script>


