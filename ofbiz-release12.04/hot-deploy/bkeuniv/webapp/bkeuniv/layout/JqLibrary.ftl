<#macro jqMinimumLib >
	<!-- import jqMinimumLib lib css-->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.default.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/selectize.default.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/template-modal.css">

	<!-- import jqMinimumLib lib js -->
	<script src="/resource/bkeuniv/js/lib/jquery.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery-ui.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.ui-contextmenu.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/selectize.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	<script src="/resource/bkeuniv/js/template-modal.js"></script>
</#macro>

<#macro pfArray array>
	[
	<#list array as a>
		<#if a?is_hash_ex>
			<@pfObject object=a />,
		<#elseif a?is_sequence>
			<@pfArray array=a />,
		<#elseif (a?string)?index_of("function")==0>
			${a?replace("\n|\t", "", "r")},
		<#elseif a?is_string>
			'${a?replace("\n|\t", "", "r")}',
		<#else>
			'${a}',
		</#if>
	</#list>
	]
</#macro>

<#macro pfObject object>
	{
	<#list object?keys as k>
		<#if object[k]?is_hash_ex>
			'${k}': <@pfObject object=object[k] />,
		<#elseif object[k]?is_sequence>
			'${k}': <@pfArray array=object[k] />,
		<#elseif (object[k]?string)?index_of("function") == 0>
			'${k}': ${object[k]?replace("\n|\t", "", "r")},
		<#elseif object[k]?is_string>
			'${k}': '${object[k]?string?replace("\n|\t", "", "r")}',
		<#else>
			'${k}': '${object[k]}',
		</#if>
	</#list>
	}
</#macro>


<#macro jqDataTable 
		urlData="" 
		urlUpdate="" 
		urlAdd="" 
		urlDelete="" 
		keysId=[]
		fnInfoCallback=""
		dataFields=[]
		columns=""
		columnsChange = []
		columnsNew=[]
		id="jqDataTable" 
		sizeTable="500"
		bJQueryUI="true"
		fieldDataResult="result"
		titleChange=""
		titleNew=""
		titleDelete=""
		jqTitle=""
		contextmenu=true
	>
	<@jqMinimumLib />
	
	<style>
		#${id} {
			width: 96%;
			top: 0;
			bottom: 0;
			left: 2%;
			right: 2%;
			
			position: absolute;
		}
		
		#${id} .jqDataTable-title {
			padding: 30px 0px 30px 0px;
		}
		
		#${id} .jqDataTable-title .jqDataTable-title-hyperlink {
			font-size: 24px;
		    line-height: 1.35;
		    font-weight: normal;
		    margin-bottom: .5em;
		    padding: 0;
		    border: 0;
		    font: Helvetica, Arial, sans-serif;
		    vertical-align: baseline;
		    
			text-decoration: none;
			color: #000;
			cursor: pointer;
		}
		
		#${id}-content tbody tr:hover {
		    background-color: #b3b3b3;
		}
		
		
		.ui-menu-item {
		    list-style: none;
		    padding: 5px 10px 5px 10px;
		    width: 100px;
		    
		    padding-left: 2em;
		    position: relative;
		    
		    cursor: pointer;
		}
		
		.ui-menu-item:hover {
			background-color: hsla(0, 0%, 93.3%, .4);
		}
		
		.ui-menu {
			padding: 0;
			margin: 0;
			
			background-color: #fff;
			border-radius: 2px;
			border: 1px solid transparent;
			box-shadow: 0 3px 12px rgba(27,31,35,0.15);
		}
		
		.ui-icon {
			height: 16px;
		    width: 16px;
		    left: 0.4em;
		    top: 0;
		    position: absolute;
		    bottom: 0;
		    margin: auto 0;
		}
		
		#jqDataTable-button-add{
		    position: relative;
		    width: 70px;
		    padding: 6px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #53a7df;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		
		#jqDataTable-button-add:hover {
			background-color: #3d9cdb;
		}
		
		.loader {
			background: url('/resource/bkeuniv/image/rolling.gif') 48% 43% no-repeat;
			background-color: rgba(255, 255, 255, 0.7);
			position: fixed;
			left: 0px;
			top: 0px;
			width: 100%;
			height: 100%;
			z-index: 9999;
		}
		
		.hidden-loading {
			background: transparent;
			visibility: hidden;
		}
	
	</style>
	<script type="text/javascript">
		var jqDataTable = new Object();
		jqDataTable.columns = [
			{
				name: "STT",
				data: "index"
			}
		];
		<#assign index=0 />
		<#list columns as column>
			<#assign index=index+1>
			var c${index} = {
				name: '${column.name}',
				data: '${column.data}'
			}
			jqDataTable.columns.push(c${index});
		</#list>
		
		$(document).ready(function(){
			$.ajax({
			    url: "${urlData}",
			    type: 'post',
			    dataType: "json",
			    success: function(data) {
			    	jqDataTable.data = data.${fieldDataResult}.map(function(d, index) {
			    		var r = new Object();
				    	<#list dataFields as field>
				    		r.${field} = d.${field}||"";
				    	</#list>		
				    	return r;
			    	})
			    	
			    	jqDataTable.table = $('#${id}-content').DataTable({
			   		data: jqDataTable.data,
					columns: jqDataTable.columns,
					"columnDefs": [
					{
						"targets": 0,
						"render": function ( data, type, row, meta ) {
							
							var row = meta.row;
							if( Object.prototype.toString.call( row ) === '[object Array]' ) {
								if(row.length > 0) {
									return row[0] + 1
								} else {
									return jqDataTable.data.length
								}
							}
							
							return meta.row + 1;					      
					    }
					},
					<#assign index = 1 />
					<#list columns as column>
						<#assign c = {} />
						
						<#if column.render?has_content>
				             <#assign c = c + {"render": column.render} />
						</#if>
						<#if column.width?has_content>
				             <#assign c = c + {"width": column.width} />
						</#if>
						
						<#if c?has_content>
				             <#assign c = c + {"targets": index} />
				             <@pfObject object=c />,
						</#if>
						
						<#assign index = index + 1 />
					</#list>
					],
					"scrollY": ${sizeTable}- $(".jqDataTable-title").innerHeight() - 165,
					"scrollCollapse": true,
					<#if fnInfoCallback?has_content>
						"fnInfoCallback": ${fnInfoCallback?replace("\n|\t", "", "r")},
					</#if>
					"bJQueryUI": true
			       });
			       <#if contextmenu>
				       $(document).contextmenu({
						    delegate: "#${id}-content td",
						menu: [
						  {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
						  {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
						],
						select: function(event, ui) {
							var el = ui.target.parent();
							var data = jqDataTable.table.row( el ).data();
							switch(ui.cmd){
								case "edit":
									jqChange(data)
									break;
								case "delete":
									jqDelete(data);
									break;
								}
							},
							beforeOpen: function(event, ui) {
								var $menu = ui.menu,
									$target = ui.target,
									extraData = ui.extraData;
								ui.menu.zIndex(9999);
						    }
						  });
					</#if>
			    }
			});
		});
		
		function jqChange(data) {
			new Promise(function(resolve, reject) {
				resolve(new modal("#jqModalChange").setting({
					data: data,
					columns: <@pfArray array=columnsChange />,
			        title: '${titleChange}',
			        action: {
						name: '${uiLabelMap.BkEunivUpdate}',
						url: '${urlUpdate}',
						dataTable: jqDataTable.table,
						keys: <@pfArray array=keysId />,
						fieldDataResult: '${fieldDataResult}',
						hidden: "auto"
					}
				}).render());
			}).then(function(modal) {
				jqDataTable.jqModalChange = modal;
				$("#jqModalChange #modal-template").modal('show');
			})
		}
		
		function jqNew() {
			new Promise(function(resolve, reject) {
				//TODO select
				resolve(new modal("#jqModalAdd").setting({
					data: {},
					columns: <@pfArray array=columnsNew />,
					
			        title: '${titleNew}',
			        action: {
						name: '${uiLabelMap.BkEunivAddRow}',
						url: '${urlAdd}',
						dataTable: jqDataTable.table,
						keys: <@pfArray array=keysId />,
						fieldDataResult: '${fieldDataResult}',
						hidden: "show"
					}
				}).render());
			}).then(function(modal) {
				jqDataTable.jqModalAdd = modal;
				$("#jqModalAdd #modal-template").modal('show');
			})
		}
		
		function jqDelete(data) {
			alertify.confirm("Confirm", '${titleDelete}',
			function(){
				openLoader();
				$.ajax({
				    url: '${urlDelete}',
				    type: 'post',
				    data: data,
				    datatype:"json",
				    success: function(d) {
				    	var table = jqDataTable.table;
				    	if(!!d) {
				    		var element = table.rows().indexes().data().filter(function(e, index) {
								
								var check = <@pfArray array=keysId />.reduce(function(acc, curr) {
									return acc&&(e[curr]==data[curr]);
								}, true);
								
								if(check) {
									e.index = index;
									return true;
								}
							})[0]
							
							if(!!element) {
								table.row(element.index).remove().draw();
							}
							
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(d.result);
				    		}, 500);
				    	} else {
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(JSON.stringify(d));
				    		}, 500);
				    	}
				    },
				    error: function(err) {
				    	setTimeout(function() {
				    		closeLoader();
				    		alertify.success(JSON.stringify(err));
				    	}, 500);
				    	console.log(err);
				    	
				    }
				})
			},
			function(){
			});
		}
		
		function openLoader() {
			if($(".loader").hasClass("hidden-loading")) {
				$(".loader").removeClass("hidden-loading");
			}
		}
		
		function closeLoader() {
			if(!$(".loader").hasClass("hidden-loading")) {
				$(".loader").addClass("hidden-loading");
			}
		}
	
	</script>
	<!-- html -->
	<div id="${id}">
		<div class="jqDataTable-title">
			<a href="#" class="jqDataTable-title-hyperlink">
				${jqTitle}
			</a>
		</div>
		<div id="jqDataTable-button-add" onClick="jqNew()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="${id}-content" class="table table-striped table-bordered">
			<thead>
				<td>STT</td>
				<#list columns as column>
					<td>${column.name}</td>
				</#list>
			</thead>
		</table>
	</div>
	
	<div class="loader hidden-loading"></div>
	<div id="jqModalAdd"></div>
	<div id="jqModalChange"></div>
	
</#macro>