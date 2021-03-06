<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#macro jqMinimumLib >
	<!-- import jqMinimumLib lib css-->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.default.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/selectize.default.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dropify.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/template-modal.css">

	<!-- import jqMinimumLib lib js -->
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery-ui.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.ui-contextmenu.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/selectize.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dropify.min.js"></script>
	
	<script src="/resource/bkeuniv/js/template-modal.js"></script>
</#macro>

<#macro pfArray array depth=0 maxDepth=10>
	[
		<#if depth < maxDepth>
			<#list array as a>
				<#if a?is_hash_ex>
					<@pfObject object=a depth=depth+1/>,
				<#elseif a?is_sequence>
					<@pfArray array=a depth=depth+1/>,
				<#elseif (a?string)?index_of("function")==0>
					${a?replace("\n|\t", "", "r")},
				<#elseif a?is_string>
					'${a?j_string?replace("\n|\t", "", "r")}',
				<#else>
					${a},
				</#if>
			</#list>
		</#if>
	]
</#macro>

<#macro pfObject object depth=0 maxDepth=10>
	{
		<#if depth < maxDepth>
			<#list object?keys as k>
				<#if object[k]?is_hash_ex>
					'${k}': <@pfObject object=object[k] depth=depth+1/>,
				<#elseif object[k]?is_sequence>
					'${k}': <@pfArray array=object[k] depth=depth+1/>,
				<#elseif (object[k]?string)?index_of("function") == 0>
					'${k}': ${object[k]?replace("\n|\t", "", "r")},
				<#elseif (object[k]?string)?last_index_of("#JS")!= -1&&((object[k]?string)?last_index_of("#JS") == object[k]?length - 3)>
					'${k}': ${object[k]?replace("\n|\t", "", "r")?substring(0, object[k]?length - 3)},
				<#elseif object[k]?is_string>
					'${k}': '${object[k]?j_string?replace("\n|\t", "", "r")}',
				<#else>
					'${k}': ${object[k]},
				</#if>
			</#list>
		</#if>
	}
</#macro>


<#macro jqDataTable 
		urlData="" 
		urlUpdate="" 
		urlAdd="" 
		urlDelete=""
		optionData={}
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
		advanceActionButton=[]
		getDataFilter=""
		filters=[]
		JqRefresh="JqRefresh()"
		backToUrl={}
		pretreatment=""
		drawCallback=""
		lengthMenu='[[10, 25, 50, -1], [10, 25, 50, "All"]]'
	>
	<@jqMinimumLib />
	
	<style>

		<#--  th {
			transition: .2s cubic-bezier(0.55, 0.06, 0.68, 0.19);
		}  -->
		
		#${id} .jqDataTable-title {
			padding: 30px 0px 30px 0px;
		}

		#jqDataTable-content_processing {
			padding: 30px 0px 30px 0px;
			z-index: 2;
			background-color: transparent;
			padding: 10px;
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
		
		#${id}-content tbody tr:hover td:first-child {
		     border-left: 2px #0014ff solid;			
		}

		.width100 {
			width: 100%!important;
		}
		
		.ui-menu-item {
		    min-width: 100px;
			list-style: none;
			padding: 5px 10px 5px 10px;
			padding-left: 2.7em;
			position: relative;
			cursor: pointer;
		}
		
		.ui-menu-item:hover {
			background-color: #0000001c;
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
			font-size: 16px;
			left: 0.7em;
			top: 0;
			color: #00000099;
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
		#jqDataTable-button-update{
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
		#jqDataTable-button-remove{
		    position: relative;
		    width: 70px;
		    padding: 6px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #FF0000;
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
		var BkEunivDropifyDefault = '${uiLabelMap.BkEunivDropifyDefault}',
			BkEunivDropifyReplace = '${uiLabelMap.BkEunivDropifyReplace}',
			BkEunivDropifyRemove = '${uiLabelMap.BkEunivDropifyRemove}',
			BkEunivDropifyError = '${uiLabelMap.BkEunivDropifyError}';

		var BkEunivLoading = "${uiLabelMap.BkEunivLoading} ...";
		var BkEunivLoaded = "${uiLabelMap.BkEunivLoaded}";
		var BkEunivAttachmentNotAdded = "${uiLabelMap.BkEunivAttachmentNotAdded}";
		var BkEunivUpload = "${uiLabelMap.BkEunivUpload}";

		var jqDataTable = new Object();
		jqDataTable.columns = [
			{
				name: "STT",
				orderable: false,
				"width": "50px",
				data: "index"
			}
		];
		<#assign sort=1 />
		<#assign noSort=true />
		<#assign index=0 />
		<#list columns as column>
			<#assign index=index+1>

			<#if keysId?size gt 0 && column.data==keysId[0]>
				<#assign sort=index />
			</#if>

			<#if keysId?size gt 0 && column.orderable?? && column.orderable!="false">
				<#assign noSort=false />
			</#if>

			var c${index} = {
				name: '${column.name}',
				<#if column.type??>
				type: '${column.type}',
				</#if>
				<#if column.orderable??>
				orderable: ${column.orderable},
				</#if>
				<#if column.width??>
				width: '${column.width}',
				</#if>
				data: '${column.data}'
			}
			jqDataTable.columns.push(c${index});
		</#list>

		function resizeDataTable() {
			var tbodyDataTable = $(".dataTables_scrollBody tbody")[0];
			var bodyDataTable = $(".dataTables_scrollBody")[0];
			var isOverflownX = bodyDataTable.scrollWidth <= bodyDataTable.clientWidth;

			var elements = [$(".dataTables_scrollHeadInner"), $(".dataTables_scrollHeadInner table"), $("#${id}-content")]
			if(isOverflownX) {
				elements.forEach(function(e) {
					if(!e.hasClass("width100")) {
						e.addClass("width100");
					}
				})
			} else {
				elements.forEach(function(e) {
					if(e.hasClass("width100")) {
						e.removeClass("width100");
					}
				})
			}
		}
		
		$(document).ready(function(){
			
			document.getElementById("side-bar").addEventListener("click", function() {
				resizeDataTable();
			})
			<#if !titlePage??>
				document.getElementById("jqTitlePage").innerHTML = titlePage;
			</#if>
            jqDataTable.table = $('#${id}-content').DataTable( {
                "processing": true,
                "serverSide": true,
				"scrollX": true,
				<#if !noSort>"order": [[ ${sort}, "asc" ]],</#if>
                "sAjaxSource": "${urlData}",
				searchDelay: 350,
				"lengthMenu": ${lengthMenu},
				"drawCallback": function( settings ) {
					resizeDataTable();
					<#if drawCallback!="">
						${drawCallback}(settings);
					</#if>
				},
                columns: jqDataTable.columns,
                deferRender: true,
                "columnDefs": [
					<#assign index= 1 />
					<#list columns as column>
					<#if column.type??>
						<#if !column.render??>
						{
							"targets": ${index},
							"render": function ( data, type, row, meta ) {
								return jqDataTable.buildColumn(data, '${column.type}', row, meta);					      
							}
						},
						</#if>
					</#if>
						<#assign index=index+1 />
					</#list>
                <#assign index = 1 />
                <#list columns as column>
                    <#assign c = {} />
                    
                    <#if column.render?has_content>
                            <#assign c = c + {"render": column.render} />
                    </#if>
					
                    <#if column.width?has_content>
                            <#assign c = c + {"width": column.width} />
                    </#if>

					<#if column.className?has_content>
                        <#assign c = c + {"className": column.className} />
                    </#if>
                    
                    <#if c?has_content>
                            <#assign c = c + {"targets": index} />
                            <@pfObject object=c />,
                    </#if>
                    
                    <#assign index = index + 1 />
                </#list>
                ],
                <#if fnInfoCallback?has_content>
                    "fnInfoCallback": ${fnInfoCallback?replace("\n|\t", "", "r")},
                </#if>
                "bJQueryUI": true,
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                    <#--  loader.open();  -->
                    //console.log(JSON.stringify(sSource), JSON.stringify(aoData), fnCallback)
                    var n_colunm = (aoData.find(function(data) {
                        return data.name=="iColumns";
                    })||{}).value||0;

                    var s_search = (aoData.find(function(data) {
                        return data.name=="sSearch";
                    })||{}).value||"";

                    var n_sort_field = (aoData.find(function(data) {
                        return data.name=="iSortCol_0";
                    })||{}).value||0;

                    n_sort_field = n_sort_field==0?1:n_sort_field;

                    var s_sort_field = (aoData.find(function(data) {
                        return data.name=="mDataProp_"+n_sort_field;
                    })||{}).value;

                    var s_sort_type = (aoData.find(function(data) {
                        return data.name=="sSortDir_0";
                    })||{}).value;

                    var i_start = (aoData.find(function(data) {
                        return data.name=="iDisplayStart";
                    })||{}).value||0;

                    var i_size = (aoData.find(function(data) {
                        return data.name=="iDisplayLength";
                    })||{}).value||0;
                    
                    var page = !i_size?0:i_start/i_size;
					$.ajax(deepmerge.all([,<@pfObject object=optionData />, {
                        "dataType": 'json', 
                        "type": "POST", 
                        "url": sSource, 
                        "data": {
                            "q": s_search,
                            "pagenum" : page.toString(),
                            "pagesize": i_size.toString(),
                            <#if getDataFilter!=''>"filter": JSON.stringify(${getDataFilter}),</#if>
							<#--  "filter": '{"field": "groupId", "value": "SUPER_ADMIN", "operation": "CONTAINS" }',  -->
                            <#if !noSort>"sort": JSON.stringify([{"field": s_sort_field, "type": s_sort_type}]),</#if>
                        },
                        "success": function (reponse) {
                            <#--  setTimeout(function(){ loader.close();}, 500);  -->
                            let data = {
								data: reponse.results,
								recordsFiltered: reponse.totalRows,
								recordsTotal: reponse.totalRows,
                                <#--  aaData: reponse.results,
                                iTotalDisplayRecords: reponse.totalRows,
                                iTotalRecords: reponse.totalRows,
                                recordsTotal: reponse.totalRows,  -->
                            }
							<#if pretreatment!="">
								data.data = ${pretreatment}(data.data);
							</#if>


							data.data = data.data.map(function(d, index) {
								var r = new Object();
								<#list dataFields as field>
									<#if field?is_hash_ex>
										<#if field.value??>
											r['${field.name}'] = d['${field.value}'];
										</#if>
										<#if field.generate??>
											r['${field.name}'] = ${field.generate}(d);
										</#if>
									<#else>
										if(d['${field}']==0) {
											r['${field}'] = d['${field}']
										} else {
											r['${field}'] = d['${field}']||"";
										}
									</#if>
								</#list>
								return r;
							})

                            data.data = data.data.map(function(d, index) {
                                d.index = page*i_size + index + 1;
                                return d;
                            })

                            fnCallback(data)
                        }
                    }]));
                    
                }
            } );

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
							if(ui.target.length > 0) {
								var el = ui.target.parent();
								var data = jqDataTable.table.row( el ).data();
								
								if(!!data) {
									var $menu = ui.menu,
									$target = ui.target,
									extraData = ui.extraData;
									ui.menu.zIndex(9999);
								} else {
									var $menu = ui.menu,
									$target = ui.target,
									extraData = ui.extraData;
									ui.menu.zIndex(-10);
								}
							}
                        }
                        });
                </#if>
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
						update: ${JqRefresh?substring(0, JqRefresh?length-2)},
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
						update: ${JqRefresh?substring(0, JqRefresh?length-2)},
						hidden: "auto"
					}
				}).render());
			}).then(function(modal) {
				jqDataTable.jqModalAdd = modal;
				$("#jqModalAdd #modal-template").modal('show');
			})
		}
		
		function jqDelete(data) {
			alertify.confirm('${uiLabelMap.BkEunivConfirm}', '${uiLabelMap.BkEunivConfirmToDelete}',
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
				    		<#--  var element = table.rows().indexes().data().filter(function(e, index) {
								
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
							}  -->

							${JqRefresh};
							
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
		
		function JqRefresh() {
			<#--  
			$.ajax({
			    url: "${urlData}",
			    type: 'post',
			    dataType: "json",
			    success: function(data) {
					setTimeout(function(){ loader.close();}, 500);
					jqDataTable.table.clear().draw();
					jqDataTable.data = data.${fieldDataResult}.map(function(d, index) {
			    		var r = new Object();
				    	<#list dataFields as field>
				    		r.${field} = d.${field}||"";
				    	</#list>		
				    	return r;
			    	});
			    	jqDataTable.table.rows.add(jqDataTable.data).draw();
			    }
			});  -->
			
			loader.open();
			setTimeout(function(){ jqDataTable.table.ajax.reload(function() {
				setTimeout(function(){ loader.close();}, 200);
			}, false); }, 100);
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

		jqDataTable.buildColumn = function(data, type, row, meta) {
			
			var value = data;
			switch(type) {
				case "date":
					value = parseDate(data);
					break;
				case "datetime":
					value = parseDateTime(data);
					break;
				default:
					value = data;
			}
			return value;
		}

		function parseDate(data) {
			var date = "";
			if(!!data) {
				date = new Date(data).toLocaleDateString('vi');
			}

			return date;
		}

		function parseDateTime(data) {
			var date = "";
			if(!!data) {
				options = {
					year: 'numeric', month: 'numeric', day: 'numeric',
					hour: 'numeric', minute: 'numeric', second: 'numeric',
					hour12: false,
					timeZone: 'Asia/Ho_Chi_Minh' 
				};
				date = new Intl.DateTimeFormat('vi', options).format(new Date(data));
			}

			return date;
		}

		function parseCurrency(data, locales="VND", currency="VND", maximumFractionDigits=2, minimumFractionDigits=2) {
			var price = ""
			if(!!data) {
				price= parseFloat(data).formatMoney(maximumFractionDigits, ',', '.') + "&#x20AB;";
			}

			return price;
		}

		function removeFilter(idInput, idLabel) {
			document.getElementById(idInput).style.display = "none";
			document.getElementById(idLabel).style.display = "";

			var numfilter = $("#filter-content").children().filter(function(el) {
				return this.style.display !== "none"
			}).length

			if(numfilter != 0 && document.getElementById("dropdown-filter").style.display == "none") {
				document.getElementById("dropdown-filter").style.display = "";
			}
			${JqRefresh};
			
		}

		function addFilter(idInput, idLabel) {
			document.getElementById(idInput).style.display = "inline-block";
			document.getElementById(idLabel).style.display = "none";

			var numfilter = $("#filter-content").children().filter(function(el) {
				return this.style.display !== "none"
			}).length

			if(numfilter == 0) {
				document.getElementById("dropdown-filter").style.display = "none";
			}
			${JqRefresh};
		}
	
	</script>
	<!-- html -->
	<@Loader handleToggle="loader">
		<@IconSpinner/>
	</@Loader>

	<div id="${id}" style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
		<#if backToUrl.href?? && backToUrl.text??>
			<@FlatButton id="back-to-url" href=backToUrl.href style="top: -2em; left: -3em; color: rgb(0, 188, 212); text-transform: uppercase; width: 200px">
				<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
					<path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"></path>
				</svg>
				${backToUrl.text}
			</@FlatButton>
		</#if>
		<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
			<div style="display: flex; justify-content: space-between;">
				<div class="title" style="padding: 16px; position: relative;">
					<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
						<span id="jqTitlePage">${titlePage?if_exists}</span>
					</span>
					<span style="font-size: 14px; color: rgba(0, 0, 0, 0.54); display: block;"></span>
				</div>

				<div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
					<#if urlAdd!="">
						<@FlatButton id="JqNewRecord" onClick="jqNew()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 100px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"></path>
							</svg>
							${uiLabelMap.BkEunivAdd}
						</@FlatButton>
					</#if>

					<#local conditions = []>
					<#list filters as filter>
						<#if !filter.require?? || !filter.require>
							<#local conditions = conditions + [filter]>
						</#if>
					</#list>

					<#if conditions?size gt 0>
						<@Dropdown id="filter" width="140px" style="margin: auto; height: 36px; line-height: 36px;">
							<@Ul id="filter-content">
								<#list conditions as condition>
									<#local addFilter = 'addFilter("' + condition.id + '-block", "' + condition.id + '")' />
									<@Li id=condition.id onClick=addFilter>
										${condition.label}
									</@Li>
								</#list>
							</@Ul>
						</@Dropdown>
					</#if>

					<#list advanceActionButton as button>
						<#if button.width??>
							<#local width = button.width>
						<#else>
							<#local width = "120px">
						</#if>
						
						<@FlatButton id="${button.id}" onClick="${button.onClick}" style="color: rgb(0, 188, 212); text-transform: uppercase;width: ${width}">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="${button.dImage}"></path>
							</svg>
							${button.text}
						</@FlatButton>
					</#list>

					<@FlatButton id="JqRefresh" onClick=JqRefresh style="color: rgb(0, 188, 212); text-transform: uppercase;width: 120px">
						<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
							<path d="M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z"></path>
						</svg>
						${uiLabelMap.BkEunivRefresh}
					</@FlatButton>
				</div>
			</div>

			<div style="margin-bottom: 20px!important;">
				<#list filters as filter>
					<#if filter.require?? && filter.require>
						<div style="display: inline-block;" id="${filter.id}-block">
						<#if filter.type=="select-render-html">
							<@FormInput id=filter.id field=filter.label width="256px">
								<select id="${filter.id}" <#if filter.onChange??>onChange="${filter.onChange}"</#if>>
									<#list filter.data as d>
										<option value="${d.value}">${d.text}</option>
									</#list>
								</select>
							</@FormInput>
						</#if>
						</div>
					</#if>
				</#list>


				<#if conditions?size gt 0>
					<#list conditions as condition>
						<div style="display: none;" id="${condition.id}-block">
						<#local idCondition=condition.id+"-input" />
						<#local removeFilter = 'removeFilter("' + condition.id + '-block", "' + condition.id + '")' />
						<@IconButton onClick=removeFilter color="rgb(0, 188, 212)" title=condition.title id="close-adsd" size="42px"  style="z-index: 100" styleParent="position: relative; top: 10px; margin-left: 10px; margin-right: -5px; height: 42px" icon='M14.59 8L12 10.59 9.41 8 8 9.41 10.59 12 8 14.59 9.41 16 12 13.41 14.59 16 16 14.59 13.41 12 16 9.41 14.59 8zM12 2C6.47 2 2 6.47 2 12s4.47 10 10 10 10-4.47 10-10S17.53 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z' >
						</@IconButton>
						<#if condition.type=="select-render-html">
							<@FormInput id=idCondition field=condition.label width="256px">
								<select id="${idCondition}" <#if condition.onChange??>onChange="${condition.onChange}"</#if>>
									<#list condition.data as d>
										<option value="${d.value}">${d.text}</option>
									</#list>
								</select>
							</@FormInput>
						<#else>
							<@FormInput id=idCondition field=condition.label width="256px" style="top: 1px">
								<input id="${idCondition}" placeholder="${condition.placeholder}" />
							</@FormInput>
						</#if>
						</div>
					</#list>
				</#if>
			</div>
			
			<table id="${id}-content" class="table table-striped">
			 <thead>
				<tr>
					<th>
						<@FlatButton id="STT" style="width: 100%; padding-right: 30px; padding-left: 10px;">
							STT
						</@FlatButton>
					</th>
					<#list columns as column>
						<th>
							<@FlatButton id="${column.data}" style="width: 100%; padding-right: 30px; padding-left: 10px;">
								${column.name}
							</@FlatButton>
						</th>
					</#list>
				</tr>
			</thead>
			</table>
		</div>
	</div>
	
	<div class="loader hidden-loading"></div>
	<#if urlAdd!="">
		<div id="jqModalAdd"></div>
	</#if>

	<#if urlUpdate!="">
		<div id="jqModalChange"></div>
	</#if>

	<@Loader handleToggle="loadingProcess" backgroundColor="rgba(0, 0, 0, 0.6)">
        <div style="margin-left: 17%; margin-right: 17%;">
            <div class="progress">
                <div class="determinate" id="liner-upload-template" style="width: 0%"></div>
            </div>
        </div>
        <div style="font-size: 20px; text-align: center; color: #fffffff2; font-weight: 400;" id="infor-liner-upload-template">
            ${uiLabelMap.BkEunivLoading} ...
        </div>
    </@Loader>
	
</#macro>