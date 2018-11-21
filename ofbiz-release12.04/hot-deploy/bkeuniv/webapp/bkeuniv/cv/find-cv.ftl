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
                ${bkEunivUiLabelMap.BkEunivResearchSpeciality?if_exists}
              </label>
              <div class="col-6 col-md-6">
                <select id="researchSpeciality" class="js-example-responsive" style="width: 100%"></select>
                <div class="help-block with-errors">Hey look, this one has feedback icons!</div>
              </div>
            </div>
            <div class="row form-group">
              <label class="col-6 col-md-6 control-label">
                ${bkEunivUiLabelMap.ListFindingOption?if_exists}
              </label>
              <div class="col-6 col-md-6">
                <div class="checkbox">
                  <label><input class="checkBox" type="checkbox" value="applied-project-declaration">${bkEunivUiLabelMap.BkEunivProjectsApplied}</label>
                </div>
                <div class="checkbox">
                  <label><input class="checkBox" type="checkbox" value="scientific-service">${bkEunivUiLabelMap.BkEunivScientificService1}</label>
                </div>
                <div class="checkbox">
                  <label><input class="checkBox" type="checkbox" value="recent-publications">${bkEunivUiLabelMap.BkEunivPublications}</label>
                </div>
                <div class="checkbox">
                  <label><input class="checkBox" type="checkbox" value="recent-projects">${bkEunivUiLabelMap.BkEunivRecent5YearProjects}</label>
                </div>
              </div>
            </div>
            <div class="row form-group" style="display: flex;">
              <div style="margin: auto;">
                <button id="findCVBtn" type="button" class="btn btn-success">	
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

      render = function(r){return {id: r.researchSpecialitySeqId, text: r.researchSpecialityName}};

      $("#researchSpeciality").select2({
        ajax: {
          url: "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListResearchSpecialityManagement",
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
              results: data.results.map(render),
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

      let sections = [];
      let redirectUrl = 'find-cv-results?';

      $('#findCVBtn').on('click', () => {

        redirectUrl = redirectUrl + '&researchSpecialityId=' + $('#researchSpeciality').val()
        let checkBoxList = document.getElementsByClassName('checkBox');
        
        for(let i=0; checkBoxList[i]; ++i){
          if(checkBoxList[i].checked){
            redirectUrl = redirectUrl + "&sections=" + checkBoxList[i].value;
          }
        }

        location.href = redirectUrl;
      
      });

    }
	
		return {
			init : init
		}

	}());
</script>


