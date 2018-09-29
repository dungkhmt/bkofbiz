
<div id="updateCVModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
            <form>
                <div class="row">
                    <label for="academicRank" class="col-4 col-md-4 control-label">${bkEunivUiLabelMap.CVAcademicRank?if_exists}</label>
                    <div class="col-8 col-md-8">
                        <select id="academicRankId" class="form-control" style="width: 100%" type="text" width="1000">
                            <option value=""></option>
                            <#if academicRank.listAcademicRank?has_content>
                                <#list academicRank.listAcademicRank as ar>
                                    <option value="${ar.hocHamId}">${ar.hocHamName}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label for="academicRankYear" class="col-4 col-md-4 control-label">${bkEunivUiLabelMap.CVAcademicRankYear?if_exists}</label>
                    <div class="col-8 col-md-8">
                        <input type="number" class="form-control" id="academicRankYear" style="ime-mode: disabled" placeholder="yyyy" maxlength="4"/>
                    </div>
                </div>
                <div class="row">
                    <label for="degreeId" class="col-4 col-md-4 control-label">${bkEunivUiLabelMap.CVDegree?if_exists}</label>
                    <div class="col-8 col-md-8">
                        <select id="degreeId" class="form-control" style="width: 100%" type="text" width="1000" onChange='changeFaculty()'>
                            <option value=""></option>
                            <#if degree.listDegree?has_content>
                                <#list degree.listDegree as de>
                                    <option value="${de.hocViId}">${de.hocViName}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label for="academicRankYear" class="col-4 col-md-4 control-label">${bkEunivUiLabelMap.CVDegreeYear?if_exists}</label>
                    <div class="col-8 col-md-8">
                        <input type="number" class="form-control" id="degreeYear" style="ime-mode: disabled" placeholder="yyyy" maxlength="4"/>
                    </div>
                </div>
                <div class="row">
                    <label for="dutyId" class="col-4 col-md-4 control-label">${bkEunivUiLabelMap.CVDutyPosition?if_exists}</label>
                    <div class="col-8 col-md-8">
                        <input type="text" class="form-control" id="duty" placeholder="Chức vụ">
                    </div>
                </div>
            </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">${bkEunivUiLabelMap.CVClose}</button>
                <button type="button" id="sumbitData" class="btn btn-success">${bkEunivUiLabelMap.CVSave}</button>
            </div>
        </div>
    </div>
</div>

<script>
	
	<#if staffInfo.staffCVInfo?has_content>
		var staff = {
			staffId: '${StringUtil.wrapString(userLogin.userLoginId?if_exists)}',
			staffName: '${StringUtil.wrapString(staffInfo.staffCVInfo.staffName?if_exists)}',
			academicRankId: '${StringUtil.wrapString(staffInfo.staffCVInfo.academicRankId?if_exists)}',
			degreeId: '${StringUtil.wrapString(staffInfo.staffCVInfo.degreeId?if_exists)}',
			academicRankYear: '${staffInfo.staffCVInfo.academicRankYear?if_exists}', 
			degreeYear: '${staffInfo.staffCVInfo.degreeYear?if_exists}',
			duty: '${StringUtil.wrapString(staffInfo.staffCVInfo.duty?if_exists)}',
		}
	</#if>

    $(document).ready(function () {
        updateGeneralInfoObj.getInstance();
    });

    var updateGeneralInfoObj = (function () {

        function getInstance () {
			initData();
			initEvent();
        }
        
        function initData () {
			setData();
        }
        
        function initEvent () {
        	$('#sumbitData').on('click', () => {
        		updateCVInfo();
        	});
        	
        	$('#academicRankYear').on('blur', () => {
        		let regex = /^[0-9]{4}$/;
        		let inp = $(this).val();
        		console.log(inp)
        		let academicRankYearValid = regex.test(input.val());
        		console.log(academicRankYearValid);
        	});
        }
        
        function openModal () {
            $('#updateCVModal').modal('show');
            setData();
        }

        function hideModal () {
            $('#updateCVModal').modal('hide');
            clearData();
        }
        
        function setData () {
        	$('#academicRankId').val(staff.academicRankId);
        	$('#degreeId').val(staff.degreeId);
        	$('#academicRankYear').val(staff.academicRankYear);
        	$('#degreeYear').val(staff.degreeYear);
        	$('#duty').val(staff.duty);
        }
        
        function clearData () {
        	$('#academicRankId').val('');
        	$('#degreeId').val('');
        	$('#academicRankYear').val('');
        	$('#degreeYear').val('');
        	$('#duty').val('');
        }
        
        function getUserInfo () {
        	return {
        		staffId: staff.staffId,
        		academicRankId: $('#academicRankId').val(),
        		degreeId: $('#degreeId').val(),
        		academicRankYear: $('#academicRankYear').val(),
        		degreeYear: $('#degreeYear').val(),
        		duty: $('#duty').val()
        	}
        }
        
        async function updateCVInfo () {
        	let result = await $.ajax({
      			url: "/bkeuniv/control/update-cv-general-info",
				type: 'POST',
				data: getUserInfo(),	
        	});
        	hideModal();
        	location.reload();
        }

        return {
            getInstance: getInstance,
            openModal: openModal,
            hideModal: hideModal,
        }
    }());

</script>