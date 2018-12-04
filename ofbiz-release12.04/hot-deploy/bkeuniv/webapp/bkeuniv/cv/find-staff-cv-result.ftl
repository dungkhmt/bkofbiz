<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<script>

<#if parameters?has_content>
	
	<#assign urlGetData = "/bkeuniv/control/jqxGeneralServicer?sname=jqGetListStaffWithResearchSpeciality" />

	<#if parameters.researchDomainId?has_content>
		<#assign urlGetData = urlGetData + "&researchDomainId="+ parameters.researchDomainId />
		var researchDomainId = '${parameters.researchDomainId}';
	</#if>
	
	<#if parameters.researchSubDomainSeqId?has_content>
		<#assign urlGetData =  urlGetData + "&researchSubDomainSeqId="+ parameters.researchSubDomainSeqId />
		var researchSubDomainSeqId = '${parameters.researchSubDomainSeqId}';
	</#if>
	
	<#if parameters.researchSpecialitySeqId?has_content>
		<#assign urlGetData =  urlGetData + "&researchSpecialitySeqId="+ parameters.researchSpecialitySeqId />
		var researchSpecialitySeqId = '${parameters.researchSpecialitySeqId}';
	</#if>

</#if>

</script>

<body>
<div class="body">
	<#assign columns=[
		{
			"name": bkEunivUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffId",
			"render": 'function(value, name, dataColumns, id) {
				return 
				"<a href=javascript:void(SelectOption.openModal("+ "\'" + value + "\'" +"));> "+ value +"</a>"
				;
			}'
		},
		{
			"name": bkEunivUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": bkEunivUiLabelMap.BkEunivResearchSpeciality?j_string,
			"data": "researchSpecialityName"
		}
	] />
	
	<#assign fields=[
		"researchSpecialityName",
		"staffId",
		"staffName"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData = urlGetData 
		optionData={
			"data": {
				"pagesize": "-1"?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["staffId"] 
		fieldDataResult = "results" 
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
	/>
</div>

<div id="optionToReport" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">${bkEunivUiLabelMap.Listing?if_exists}</h4>
            </div>
            <div class="modal-body">
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
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">${bkEunivUiLabelMap.CVClose}</button>
                <button type="button" disabled id="sumbitData" class="btn btn-success">${bkEunivUiLabelMap.Listing}</button>
            </div>
        </div>
    </div>
</div>

</body>

<script>


	$(document).ready(()=>{
		SelectOption.init()
	});

	var SelectOption = (function(){
		urlRedirect = 'find-cv-results';
		var staffIdQueryString = '?staffId=';
		var listSections = [];

		init = () =>{
			initEvent();
		}

		initEvent = () => {

			$(".checkBox").on('change', (e)=>{
				var listSections = document.getElementsByClassName('checkBox');
				var count = 0;
				for(let x of listSections){
					if(x.checked){
						count++;
					}
				}
				count >= 2 ? $('#sumbitData').removeAttr('disabled') : $('#sumbitData').attr('disabled','disabled');
			});

			$('#sumbitData').on('click', ()=>{
				urlRedirect += staffIdQueryString;
				var listSections = document.getElementsByClassName('checkBox');
				for(let section of listSections){
					if(section.checked){
						urlRedirect = urlRedirect + "&sections=" + section.value; 
					}
				}
				window.location.href = urlRedirect;
			});

			$('#optionToReport').on('hide.bs.modal', ()=>{
				$('input:checkbox').removeAttr('checked');
				listSections = [];
				urlRedirect = 'find-cv-results';
				staffIdQueryString = '?staffId=';
			});


		}

		openModal = (id) => {
			$('#optionToReport').modal('show');
			staffIdQueryString += id;
		}

		return {
			init: init,
			openModal: openModal
		}

	}());

</script>
