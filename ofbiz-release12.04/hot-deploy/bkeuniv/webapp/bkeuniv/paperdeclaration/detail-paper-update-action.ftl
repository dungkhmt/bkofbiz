
<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/loader.ftl"/>

<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:15px
	}
</style>


<hr class="side-bar-separator">
<table>
	<tr>
		<td>
		<table>
			<tr>
				<td><a href = "/bkeuniv/control/members-of-a-paper?paperId=${paper.paper.paperId}">${paperDeclarationUiLabelMap.BkEunivPaperMembersHUST}</a></td>
				<td><a href = "/bkeuniv/control/external-members-of-a-paper?paperId=${paper.paper.paperId}">${paperDeclarationUiLabelMap.BkEunivPaperExternalMembersHUST}</a></td>
				<td><@buttonStore text="Upload File" action="uploadFileProposal"/></td>
			</tr>
			
		</table>
		
		</td>
	</tr>
</table>


<div class="modal fade" style="margin-top: 5%;" id="modal-upload" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" id="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Chon file de upload</h4>
			</div>
			<div class="modal-body" id="modal-body">
				<input type="file" id="input-upload-file" class="dropify" accept=".doc, .docx, .pdf, .csv, .xls, .xlsx" data-default-file="" />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onClick="uploadFile()">${uiLabel.BkEunivUpload}</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">${uiLabel.BkEunivClose}</button>
			</div>
		</div>

	</div>
</div>

<@Loader handleToggle="loading" backgroundColor="rgba(0, 0, 0, 0.6)">
	<div style="margin-left: 17%; margin-right: 17%;">
		<div class="progress">
			<div class="determinate" id="liner-upload" style="width: 0%"></div>
		</div>
	</div>
	<div style="font-size: 20px; text-align: center; color: #fffffff2; font-weight: 400;" id="infor-liner-upload">
		${uiLabel.BkEunivLoading} ...
	</div>
</@Loader>

<script>
	var uploadDropify;

	$(document).ready(function(){
		uploadDropify = $('#input-upload-file').dropify({
			messages: {
				default: '${uiLabel.BkEunivDropifyDefault}',
				replace: '${uiLabel.BkEunivDropifyReplace}',
				remove:  '${uiLabel.BkEunivDropifyRemove}',
				error:   '${uiLabel.BkEunivDropifyError}'
			}
		});
		uploadDropify = uploadDropify.data('dropify');
	})
	 
	function uploadFileProposal(){
		//alert('upload file thuyet minh');
		$("#modal-upload").modal("show");
	}
	function uploadFile(){
		var files = document.getElementById("input-upload-file").files;
		
		var reader = new FileReader();
		if(files.length !== 0) {
			var formData = new FormData();
			formData.append("paperId", ${paper.paper.paperId});
			formData.append("file", files[0]);

			$.ajax({
					url: "/bkeuniv/control/upload-file-paper",
					type: 'POST',
					method: 'POST',
					contentType: false,
    				processData: false,
					data: formData,
					xhr: function() {
							var myXhr = $.ajaxSettings.xhr();
							if(myXhr.upload){
								loading.open();
								myXhr.upload.addEventListener('progress',progress, false);
							}
							console.log(myXhr)
							return myXhr;
					},
					success:function(rs){
						alertify.success('${uiLabel.BkEunivLoaded}');
						uploadDropify.resetPreview();
						uploadDropify.clearElement();
						setTimeout(function(){
							$("#modal-upload").modal("hide");
						}, 500);
					}
				})
		}
	}


	function progress(e){

		if(e.lengthComputable){
			var max = e.total;
			var current = e.loaded;

			var Percentage = Math.floor((current * 100)/max);

			if(Percentage >= 100)
			{
				document.getElementById("liner-upload").style.width="100%";
				document.getElementById("infor-liner-upload").innerHTML='${uiLabel.BkEunivLoaded}';
				setTimeout(function(){
					loading.close();
				}, 300);
			} else {
				document.getElementById("liner-upload").style.width=Percentage+"%";
				document.getElementById("infor-liner-upload").innerHTML='${uiLabel.BkEunivUpload} ' + Percentage+"%";
			}
		}  
	}
	
</script>


