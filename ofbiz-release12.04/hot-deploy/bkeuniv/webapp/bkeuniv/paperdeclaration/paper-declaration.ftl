<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>

<style>
	.upload {
		box-shadow: 0 0 1px rgba(34, 25, 25, 0.4);
	    margin-top: 20px;
        padding: 5px;
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
<script>
	function createUploadFile(data, name, dataRow, id) {
		var script = 'let filedrag = this.filedrag;
		filedrag.addEventListener('dragleave', (e) => { this.fileDragLeave(e) }, false);
		filedrag.addEventListener('drop', (e) => { this.fileSelectHandler(e) }, false);
		filedrag.addEventListener('dragenter', (e) => { this.fileOndragenter(e) }, false);
		filedrag.addEventListener('dragover', (e) => { this.fileDragHover(e) }, false);
	
		return '<div class="upload" id="'+id+'">'+
					'<div class="upload-file" >'+
                    	'<i class="fa fa-cloud-upload" aria-hidden="false"></i>'+
						'<input class="input" type="file" onChange="fileSelectHandler(e)">'+
					'</div>'+
					'<div class="drop-file">'+
                    	'Drop file here'+
					'</div>'+
				'</div>';
	};

	function componentDidMount() {
		

	}

	fileOndragenter(e) {
		let filedrag = this.filedrag;
		let childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = -2; 
		}
		e.target.classList.add('drag-over');
	}

	fileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
	}

	fileDragLeave(e) {
		e.target.classList.remove('drag-over');
		let filedrag = this.filedrag;
		let childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = ''; 
		}
		e.stopPropagation();
		e.preventDefault();
	}

	fileSelectHandler(e) {
		let file = e.target.files || e.dataTransfer.files;
        
		e.preventDefault();
		let reader = new FileReader();

		reader.readAsText(file[0], 'utf-8');
		reader.onload = (event) => {
			let data = event.target.result
			let fileName = file[0].name
			let lastModified = file[0].lastModified;
			this.props.dispatch(name(assignFunc({
				data, fileName, lastModified
			}), 'ON_DROP_JSON_FILE'))
		}

		reader.onerror = (event) => {
			this.props.onError(event);
		}
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
					return "<div id=\\"download-papger\\"><span class=\\"glyphicon glyphicon-download-alt\\"></span>Tải xuống</div>";
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
	
	<#assign fields=[
		"paperId",
		"staffName",
		"volumn",
		"authors",
		"journalConferenceName",
		"paperCategoryId",
		"paperName",
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
			"name": "authors",
			"value": "authors"
			
		},
		{
			"name": "paperCategoryId",
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		},
		{
			"name": "journalConferenceName",
			"value": "journalConferenceName"
			
		},
		{
			"name": "volumn",
			"value": "volumn"
		},
		{
			"name": "month",
			"value": "month"
			
		},
		{
			"name": "year",
			"value": "year"
		},
		{
			"name": "ISSN",
			"value": "ISSN"
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
		urlAdd="/bkeuniv/control/create-education-progress" 
		urlDelete="/bkeuniv/control/delete-education-progress" 
		keysId=["paperId"] 
		fieldDataResult = "papers" 
		titleChange="test"
		titleNew="test"
		titleDelete="test"
		jqTitle="test"
	/>
</div>
