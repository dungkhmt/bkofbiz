<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
    $(document).ready(function(){
        document.getElementById("jqTitlePage").innerHTML = titlePage;

        uploadDropify = $('#input-upload-file').dropify({
			messages: {
				default: '${uiLabelMap.BkEunivDropifyDefault}',
				replace: '${uiLabelMap.BkEunivDropifyReplace}',
				remove:  '${uiLabelMap.BkEunivDropifyRemove}',
				error:   '${uiLabelMap.BkEunivDropifyError}'
			}
		});
		uploadDropify = uploadDropify.data('dropify');

        $("#official-document-type-name").select2({
            language: "vi",
            placeholder: "Chon loai tai lieu",
            ajax: {
                url: '/bkeuniv/control/jqxGeneralServicer?sname=JQGetListOfficialDocumentType',
                "method": "POST",
                "content-type": "application/json",
                delay: 250,
                cache: true,
                data: function (params) {
                    var query = {
                        q: params.term||"",
                        pagenum:params.page-1||0,
                        pagesize:10,
                    };
                    Object.keys(query).forEach(function(key){if(query[key] instanceof Object){query[key] = JSON.stringify(query[key]);}});
                    
                    return query;
                },
                processResults: function (data) {
                    return {
                        results: data.results.map(function(d) {
                            return {
                                id: d.officialDocumentTypeId,
                                text: d.officialDocumentTypeName
                            }
                        }),
                        "pagination": {
                            "more": !(data.results.length<10||data.results.length==data.totalRows),
                        }
                    };
                },
            }
        });
    });
    
    function uploadFile(){


		var files = document.getElementById("input-upload-file").files;
		
        var documentName = $("#official-document-name").val();
        var documentType = $("#official-document-type-name").val();

        if(!documentName) {
            alertify.error('Chua nhap ten tai lieu');
            return;
        }

        if(!documentType) {
            alertify.error('Chua chon loai tai lieu');
            return;
        }

		var reader = new FileReader();
		if(files.length !== 0) {
			var formData = new FormData();
			formData.append("officialDocumentName", $("#official-document-name").val());
            formData.append("officialDocumentTypeId", $("#official-document-type-name").val());
            formData.append("file", files[0]);

			$.ajax({
					url: "/bkeuniv/control/upload-file-official-document",
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
						alertify.success('${uiLabelMap.BkEunivLoaded}');
						uploadDropify.resetPreview();
						uploadDropify.clearElement();
						setTimeout(function(){
							
						}, 500);
					}
				})
		} else {
            alertify.error('Chua them tep');
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
				document.getElementById("infor-liner-upload").innerHTML='${uiLabelMap.BkEunivLoaded}';
				setTimeout(function(){
					loading.close();
				}, 300);
			} else {
				document.getElementById("liner-upload").style.width=Percentage+"%";
				document.getElementById("infor-liner-upload").innerHTML='${uiLabelMap.BkEunivUpload} ' + Percentage+"%";
			}
		}  
	}
	
</script>

<div class="body">
    <div style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
		<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
			<div style="display: flex; justify-content: space-between;">
				<div class="title" style="padding: 16px; position: relative;">
					<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
						<span id="jqTitlePage">${titlePage?if_exists}</span>
					</span>
				</div>
            </div>

            <form action="javascript:void(0);">
                <div class="form-group">
                    <label for="official-document-name">${uiLabelMap.officialDocumentName}</label>
                    <input type="text" class="form-control" id="official-document-name" placeholder="Nhập tên tài liệu">
                    <#--  <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>  -->
                </div>
                <div class="form-group">
                    <label for="official-document-type-name">${uiLabelMap.officialDocumentTypeName}</label>
                    <#--  <select class="form-control" id="official-document-type-name">  -->
                    <select class="form-control" id="official-document-type-name">
                    </select>
                </div>
                <div class="form-group">
                    <input type="file" id="input-upload-file" class="dropify" accept=".doc, .docx, .pdf, .csv, .xls, .xlsx" data-default-file="" />
                </div>

                <button class="btn btn-primary" onClick="uploadFile()">Tai len</button>
            </form>

        </div>
    </div>

    <@Loader handleToggle="loading" backgroundColor="rgba(0, 0, 0, 0.6)">
        <div style="margin-left: 17%; margin-right: 17%;">
            <div class="progress">
                <div class="determinate" id="liner-upload" style="width: 0%"></div>
            </div>
        </div>
        <div style="font-size: 20px; text-align: center; color: #fffffff2; font-weight: 400;" id="infor-liner-upload">
            ${uiLabelMap.BkEunivLoading} ...
        </div>
    </@Loader>
</div>