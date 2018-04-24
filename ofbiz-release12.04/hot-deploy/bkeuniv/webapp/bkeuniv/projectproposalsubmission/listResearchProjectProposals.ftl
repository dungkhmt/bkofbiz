
<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<body>
<div class="body">

	<#assign columns=[
		{
			"name": uiLabel.ProjectProposalName?j_string,
			"data": "researchProjectProposalName",
			"render": 'function(value, name, dataColumns, id) {
                return "<a href=\\"/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId="+dataColumns.researchProjectProposalId+"\\">" + dataColumns.researchProjectProposalName + "</a>";
			}'
		},
		{
			"name": uiLabel.ProjectDirector?j_string,
			"data": "createStaffName",
			"render": 'function(value, name, dataColumns, id) {
                return "<a href=\\"/bkeuniv/control/detail-scientific-cv?staffId="+dataColumns.createStaffId+"\\">" + dataColumns.createStaffName + "</a>";
			}'
		},
		{
			"name": uiLabel.ProjectCallName?j_string,
			"data": "projectCallName"
		},
		{
			"name": uiLabel.Faculty?j_string,
			"data": "facultyName"
		},
		{
			"name": uiLabel.Status?j_string,
			"data": "statusName"
		},
		
		{
			"name": uiLabel.AverageEvaluation?j_string,
			"data": "numberEvaluations",
			"pWidth":"150px",
			"render": 'function(value, name, dataColumns, id) {
                if(dataColumns.totalEvaluation>0&&(!!value||value===0)) {
                    return parseFloat(value/dataColumns.totalEvaluation).toFixed(2);
                } else {
					return "N/A"
				}
			}'
		},
		{
			"name": "",
			"data": "researchProjectProposalId",
			"render": 'function(value, name, dataColumns, id) {
                return \'<button onClick="openMessage(\'+value+\')" type="button" class="btn btn-primary waves-effect waves-light" style="outline: none; font-size: 11px; outline: none; height: 30px; padding: 1px 20px 1px 20px; line-height: 30px; top: 7px">${uiLabel.RequireUpdateProposal}</button>\';
			}'
		}
	] />
	
	<#assign fields=[
		"researchProjectProposalId",
		"researchProjectProposalName",
		"createStaffName",
		"createStaffId",
		"projectCallName",
		"facultyName",
		"statusName",
		"totalEvaluation",
		"numberEvaluations"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-filtered-project-proposals"
		urlData="/bkeuniv/control/get-list-filtered-project-proposals"
		optionData={
			"data": {
				"facultyId": parameters.facultyId?j_string,
				"projectCallId": parameters.projectCallId?j_string,
				"projectProposalStatusId": parameters.projectProposalStatusId?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["researchProjectProposalId"] 
		fieldDataResult = "projectproposals" 
		contextmenu=false
	/>
</div>
<script>
	var titlePage='${titlePage}';
	function sendEmail(e) {
		var subject = document.getElementById("subject-message").value;
		var to = document.getElementById("recipients-message").value;
		var body = $('textarea#content').val();

		if(!to) {
			alertify
			.alert("Lỗi", "Mục người gửi không được để trống", function(){
				
			});

			return;
		}

		if(!subject) {
			alertify
			.alert("Lỗi", "Tiêu đề thư không được để trống", function(){

			});

			return;
		}

		if(!body) {
			alertify
			.alert("Lỗi", "Nội dung thư không được để trống", function(){

			});

			return;
		}

		loader.open();
		$.ajax({
			url: "/bkeuniv/control/test-send-email",
			type: 'POST',
			data: {
				"to": to,
				"subject": subject,
				"body": body
			},
			success:function(rs){
				alertify.success('Gửi email thành công');
				setTimeout(function(){ 
				loader.close();
				closeMessage(e);
			}, 300);
			},
			error: function(err) {
				alertify.error('Lỗi hệ thống <br>Vui lòng thử lại sau 5 phút');
				loader.close();
			}
		});
	}

	function minimizeMessage(e) {
		event.stopPropagation();
		var top = document.getElementById("message").clientHeight - 30;
		if(document.getElementById("message").style.transform != 'translate(0px, 0px)') {
			expandMessage()
		} else {
			document.getElementById("message").style.transform = 'translate(0px,'+ top + "px"+')';
			//document.getElementById("message").style.top=top + "px";
		}
	}

	function expandMessage() {
		document.getElementById("message").style.transform = 'translate(0px, 0px)';
	}

	function closeMessage(e) {
		e.stopPropagation();
		document.getElementById("message").style.transform = "translate(150%, 0px)";
	}

	function openMessage(id) {
		event.stopPropagation();
		
		var table = jqDataTable.table;
		var elementIndex = Array.from(table.rows().indexes().data()).findIndex(function(e, index) {
			return e.researchProjectProposalId==id
		});
		
		var element = table.rows().indexes().data()[elementIndex];
		
		console.log(element);
		
		document.getElementById("recipients-message").value = element.createStaffName;
		document.getElementById("message").style.transform = "translate(0px, 0px)";
	}

	function onKeyUpSubject(e) {
		var subject = document.getElementById("subject-message").value;
		if(!!subject) {
			document.getElementById("title-message").innerHTML = subject;
		} else {
			document.getElementById("title-message").innerHTML = "New Message";
		}
	}
</script>
<style>
	.task-title {
		position: absolute;
    	right: 0px;
	}

	.task-title img {
		opacity: .6;
		position: relative;
		top: 2px;
		height: 30px;
	}

	.task-title i {
		opacity: .6;
		position: relative;
		top: 2px;
	}

	.task-title .minimize {
		background-size: 24px 882px;
		width: 24px;
    	height: 24px;
		color: #fff;
	}

	.task-title .minimize:hover {
		opacity: 1;
	}

	.task-title .close_window:hover {
		opacity: 1;
	}

	.task-title .close_window {
		background-size: 24px 882px;
		width: 24px;
    	height: 24px;
		color: #fff;
	}

	.task-title .expand_window {
		background-size: 24px 882px;
		width: 24px;
    	height: 24px;
		color: #fff;
	}

	.header-message {
		display: block;
		width: 100%;
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857143;
		background-color: #fff;
		background-image: none;
		border-radius: 4px;
		-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		font-family: arial,sans-serif;
		color: #222;
		background-color: white;
		font-size: 13px;
	}

	.header-message:focus {
		border-bottom: 1px solid #66afe9!important;
		outline: 0;
	}
	
</style>

<div style="position: relative; top: 0; left: 0; right: 0; bottom: 0;">
	<div style="width: 50%; float: right; height: 100%;">
		<div id="message" style="transform: translate(150%, 0px);z-index: 1000; transition: transform 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; position: absolute; bottom: 0; right: 0;margin: 0px 5px 2px 0px; -webkit-box-shadow: rgba(0,0,0,0.2) 0 2px 6px; box-shadow: rgba(0,0,0,0.2) 0 2px 6px;">	
			<div class="title-message row" style="line-height: 2; margin: 0; background-color: #404040; height: 30px;cursor: pointer;" onClick="expandMessage()">
				<div id="title-message" class="col-xs-9" style="vertical-align: middle; color: #ffffff;">
					New Message
				</div>
				<div class="col-xs-3">
					<div class="task-title">
						<i class="fa fa-window-minimize minimize" style="cursor: pointer;" title="minimize" onClick="minimizeMessage(event)"></i>
						<i class="fa fa-expand expand_window" ></i>
						<i class="fa fa-times close_window" style="cursor: pointer;" title="close" onClick="closeMessage(event)" ></i>
					</div>
				</div>
			</div>
			<div class="content-message" style="background: #fff; border: 1px solid #ccc; overflow: hidden;">
				<input type="text" class="header-message" placeholder="To" style="border-radius: unset; border: none; border-bottom: 1px solid #cfcfcf;" id="recipients-message">
				<input type="text" class="header-message" onkeyup="onKeyUpSubject(event)" placeholder="Subject" style="border-radius: unset; border: none;" id="subject-message">
				<textarea id="content">
				</textarea>
				<script>
					$(function() {
						$('textarea#content').froalaEditor({
							charCounterCount : false,
							language: 'vi',
							height: 300,
							width: '500',
							toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', '|', 'fontFamily', 'fontSize', 'color', '|', 'align', 'formatOL', 'formatUL', 'quote', '-', 'insertLink', 'insertImage', 'embedly', 'insertFile', 'insertTable', '|', 'clearFormatting', '|', 'spellChecker', 'help', 'html', '|', 'undo', 'redo']
						});
					});
				</script>
				<div class="action-message row" style="margin: 0; background-color: #f5f5f5; height: 45px;">
					<div class="col-xs-1">
						<button onClick="sendEmail(event)" type="button" class="btn btn-primary waves-effect waves-light" style="outline: none; font-size: 11px; outline: none; height: 30px; padding: 1px 20px 1px 20px; line-height: 30px; top: 7px">Send <i class="fa fa-paper-plane" aria-hidden="true"></i></button>
					</div>
					<div class="col-xs-10">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>