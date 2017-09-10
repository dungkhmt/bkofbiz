<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<style>
	.upload {
		box-shadow: 0 0 1px rgba(34, 25, 25, 0.4);
	    margin-top: 20px;
        padding: 5px;
        z-index: 9999;
	}
	
	.upload.drag-over {
		border: 2px dashed gray;
	}
	
	.upload .upload-file {
		position: relative;
		text-align: center;

		width: 80px;
		color: #53a7df;
		font-size: 60px;
		padding: 10px;
	    margin: auto;

		cursor: pointer;
	}
	
	.upload .upload-file:hover {
		color: rgba(83, 167, 223, 0.9);
	}
	
	.icon-upload input, .upload-file input {
		cursor: pointer;
		opacity: 0.0;
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
		width: 100%;
		height: 100%;
	}
	
	.drop-file {
		text-align: center;
		background: transparent;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 30px;
		color: gray;

		margin-bottom: 20px;
	}
	
	
	
	#download-papger{
		    position: relative;
		    width: 80px;
		    padding: 6px;
		    font-size: 12px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #53a7df;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		
		#download-papger:hover {
			background-color: #3d9cdb;
		}
</style>
<script type="text/javascript">
	var dataFilePaper = {};
	function createUploadFile(data, name, dataRow, id) {
		return '<div class="upload" id="'+id+'">'+
					'<div class="upload-file" >'+
                    	'<i class="fa fa-cloud-upload" aria-hidden="false"></i>'+
						'<input class="input" id="input-file" type="file">'+
					'</div>'+
					'<div class="drop-file">'+
                    	'Drop file here'+
					'</div>'+
					'<script type="text/javascript">'+
						'$(function () {componentDidMount("'+id+'")});'+
					"<\/script>"+
			'</div>';
	};

	function componentDidMount(id) {
		var filedrag = document.getElementById(id);
		filedrag.addEventListener('dragleave', (e) => { this.fileDragLeave(e, filedrag) }, false);
		filedrag.addEventListener('drop', (e) => { this.fileSelectHandler(e, filedrag) }, false);
		filedrag.addEventListener('dragenter', (e) => { this.fileOndragenter(e, filedrag) }, false);
		filedrag.addEventListener('dragover', (e) => { this.fileDragHover(e, filedrag) }, false);
		document.getElementById("input-file").addEventListener('change', (e) => { this.fileSelectHandler(e, filedrag) }, false);
	}
	function fileOndragenter(e, filedrag) {
		var childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = 1049; 
		}
		e.target.classList.add('drag-over');
		
	}

	function fileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
	}

	function fileDragLeave(e, filedrag) {
		e.target.classList.remove('drag-over');
		var childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = ''; 
		}
		e.stopPropagation();
		e.preventDefault();
	}

	function fileSelectHandler(e, filedrag) {
		console.log(e)
		var file = e.target.files || e.dataTransfer.files;
        
		e.preventDefault();
		var reader = new FileReader();

		reader.readAsText(file[0], 'utf-8');
		reader.onload = (event) => {
			var data = event.target.result
			var fileName = file[0].name
			var lastModified = file[0].lastModified;
			dataFilePaper.fileName = fileName;
			dataFilePaper.data = data;			
		}

		reader.onerror = (event) => {
			this.props.onError(event);
		}
		filedrag.classList.remove('drag-over');
	}
	
	function getDataFile(id) {
		return dataFilePaper||{};
	}

</script>

<div class="body">
	<#assign columns=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"data": "paperName"
		},
		{
			"name": "volumn",
			"data": "volumn"
		},
		{
			"name": "paperCategoryId",
			"data": "paperCategoryId"
		},
		{
			"name": "authors",
			"data": "authors"
		},
		{
			"name": "journalConferenceName",
			"data": "journalConferenceName"
		},
		{
			"name": "File",
			"data": "sourcePath",
			"width": "5%",
			"render": 'function(data, type, row) {
				if(!!data) {
					return "<div id=\\"download-papger\\"><span class=\\"glyphicon glyphicon-download-alt\\"></span>paperDeclarationUiLabelMap.BkEunivDownloadPaper?j_string</div>";
				} else {
					return "";
				}
			}'
		}
	] />
	
	<#assign source = [] />
	<#list paperCategory.result as paper>
		<#if paper?has_content>
             <#assign op = { "name": paper.paperCategoryName?j_string ,"value": paper.paperCategoryCode?j_string } />
						<#assign source = source + [op] />
		</#if>
	</#list>
	
	<#assign sourceAcademicYear = [] />
	<#list result.academicYears as y>
		<#if y?has_content>
             <#assign opy = { "name": y.academicYearName?j_string ,"value": y.academicYearId?j_string } />
						<#assign sourceAcademicYear = sourceAcademicYear + [opy] />
		</#if>
	</#list>
	
	<#assign fields=[
		"paperId",
		"staffName",
		"volumn",
		"authors",
		"journalConferenceName",
		"paperCategoryId",
		"paperName",
		"academicYearId",
		"sourcePath"
	] />
	
	<#assign columnsChange=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
			
		},
		{
			"name": "paperCategoryId",
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAuthors?j_string,
			"value": "authors"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperCategory?j_string,
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		},
		
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperJournalConference?j_string,
			"value": "journalConferenceName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperVolumn?j_string,
			"value": "volumn"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperMonth?j_string,
			"value": "month"
			
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperYear?j_string,
			"value": "year"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperISSN?j_string,
			"value": "ISSN"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		},
		{
			"name": "Up load file",
			"value": "fileName",
			"type": "render",
			"render": 'function(data, name, dataRow, id) {
				return createUploadFile(data, name, dataRow, id);
			}',
			"getData": 'function(id) {
				return getDataFile(id);
			}'
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-papers-of-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-paper" 
		urlAdd="/bkeuniv/control/create-paper-declaration" 
		urlDelete="" 
		keysId=["paperId"] 
		fieldDataResult = "papers" 
		titleChange="test"
		titleNew="test"
		titleDelete="test"
		jqTitle="test"
	/>
</div>
