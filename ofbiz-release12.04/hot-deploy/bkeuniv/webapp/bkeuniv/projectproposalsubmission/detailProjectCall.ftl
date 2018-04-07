<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<script src="/resource/bkeuniv/js/lib/dropify.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dropify.min.css">
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:15px
	}
	.modal-dialog {
		width: 60%!important;
		margin-left: 20%!important;
	}
</style>

<div class="info-table">
<table>
	<tr>
		<td>
			<div class="info-box">
			${uiLabel.ProjectCallName}: ${resultProjectCall.projectCall.projectCallName}
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="info-box">
			${uiLabel.Category}: ${resultProjectCall.projectCall.projectCategoryName}
			</div>
		</td>
	</tr>
	
	<tr>
		<td>		
		<div class="info-box">
			${uiLabel.numberSubmissions}: ${resultProjectCall.numberSubmissions}
		</div>
		</td>
	</tr>
	
	<tr>
		<td>		
		<div class="info-box">
			${uiLabel.Status}: ${resultProjectCall.projectCall.statusName}
		</div>
		</td>
	</tr>
	
	<tr>
		<td>
			<table>
				<tr>
					<td>
					<@buttonCommand text="${uiLabelMap.BkEunivOpenProjectCallForSubmission}" action="openProjectCallForSubmission"/>
					</td>
				</tr>
				<tr>
					<td>
					<@buttonCommand text="${uiLabelMap.BkEunivCloseProjectCall}" action="closeProjectCall"/>
					</td>
				</tr>
				<tr>	
					<td>
					<@buttonCommand text="${uiLabelMap.BkEunivOpenProjectCallForRevision}" action="openProjectCallForRevision"/>
					</td>
				</tr>
				<tr>	
					<td>
					<@buttonCommand text="${uiLabelMap.BkEunivCloseProjectCallForRevision}" action="closeProjectCallForRevision"/>
					</td>
				</tr>
				<tr>	
					<td>
					<@buttonCommand text="${uiLabelMap.BkEunivOpenEvaluation}" action="openEvaluation"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
</table>

<script>
	function openProjectCallForSubmission(){
		//alert("Dong dot goi de tai" + data.projectCallId);
		
		alertify.confirm('Xac nhan mo de tai', "Ban co muon thuc su muon mo khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/open-project-call-for-submission",
					type: 'POST',
					data: {
						"projectCallId": ${resultProjectCall.projectCall.projectCallId}
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/project-call-management";
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});
		
	}


function closeProjectCall(){
			alertify.confirm('Xac nhan dong de tai', "Ban co muon thuc su dong khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/close-project-call",
					type: 'POST',
					data: {
						"projectCallId": ${resultProjectCall.projectCall.projectCallId}
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/project-call-management";
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});

}

function openProjectCallForRevision(){
		alertify.confirm('Xac nhan mo lai dot goi de tai', "Ban co muon thuc su muon mo khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/open-project-call-for-revision",
					type: 'POST',
					data: {
						"projectCallId": ${resultProjectCall.projectCall.projectCallId}
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/project-call-management";
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});

}
function closeProjectCallForRevision(){
		//alert("Dong dot goi de tai" + data.projectCallId);
		
		alertify.confirm('Xac nhan dong dot goi de tai', "Ban co muon thuc su muon dong khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/close-project-call-for-revision",
					type: 'POST',
					data: {
						"projectCallId":  ${resultProjectCall.projectCall.projectCallId}
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/project-call-management";
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});

}
	function openEvaluation(){
		alertify.confirm('Xac nhan cong bo ket qua danh gia thuyet minh de tai', "Ban co muon thuc su muon cong bo khong?",
		function(){
				//alert("Dong dot goi de tai" + data.projectCallId);
				$.ajax({
					url: "/bkeuniv/control/open-evaluation-result",
					type: 'POST',
					data: {
						"projectCallId": ${resultProjectCall.projectCall.projectCallId}
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/project-call-management";
					}
				})
		},
		function(){
			//alert("ban da chon cancel");
		});
	
	}

</script>