<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

<@SideBar open=true handleToggle="sideBar">
	<@List height="100%">
		<@ListItem primaryText=uiLabelMap.BkEunivHomePage leftIcon="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" linkTo="main" />
		<@ListItem primaryText=uiLabelMap.BkEunivPersonalInformation linkTo="main"leftIcon="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" linkTo="user"/>
		<hr class="side-bar-separator">

		<#list functions.permissionFunctions as f>
			<@ListItem id=f.function.functionId primaryText=f.function.vnLabel leftIcon=f.function.icon linkTo=f.function.target>
				<#list f.children as cf>
					<@ListItem level=1 id=cf.functionId primaryText=cf.vnLabel leftIcon=cf.icon linkTo=cf.target>
					</@ListItem>
				</#list>
			</@ListItem>
		</#list>
	</@List>
</@SideBar>

<#--  
<div class="side-bar hide-side-bar">

	<button class="closeSideBar" onClick="closeSideBar()">
		<span class="glyphicon glyphicon-remove"></span>
	</button>
	<div class="side-bar-container">
		<div class="side-bar-selection">
			<div class="side-bar-item-container">
				<ul class="user-link">
					<li class="item">
						<a href="main" >
							<div class="item-icon-left icon fa fa-home">
							</div>
							<div class="item-name">
								<div>
									${uiLabelMap.BkEunivHomePage}
								</div>
							</div>
						</a>
					</li>
					<li class="item">
						<a href="user">
							<div class="item-icon-left icon fa fa-user">
							</div>
							<div class="item-name">
								<div>
									${uiLabelMap.BkEunivPersonalInformation}
								</div>
							</div>
						</a>
					</li>
				</ul>
			</div>
			<hr class="side-bar-separator">
		</div>
		
		<div class="side-bar-selection">
			<#list functions.permissionFunctions as f>
				<#assign icon="fa-list"/>
				<#if f.function.icon??>
					 <#assign icon=f.function.icon/>
				</#if>
				<div class="side-bar-item-container">
					<label id="${f.function.functionId}" class="tree-toggle nav-header item">
						<span class="item-icon-left icon fa ${icon}"></span>
						<div class="item-name">
							<div>${f.function.vnLabel}</div>
						</div>
						<span class="item-icon-right icon fa fa-chevron-down"></span>
					</label>
					<ul id="${f.function.functionId}_children" class="nav-list tree">
						<#list f.children as cf>
							<#assign icon="fa-tasks"/>
							<#if cf.icon??>
								<#assign icon=cf.icon/>
							</#if>
							<li title="${StringUtil.wrapString(cf.vnLabel)}" id="${cf.functionId}">
								<a href="${cf.target}" class="sub-item">
									<span class="item-icon-left icon fa ${icon}"></span>
									<div class="item-name">
										<div>
											${StringUtil.wrapString(cf.vnLabel)}
										</div>
									</div>
								</a>
							</li>							
						</#list>
					</ul>
				</div>
			</#list>
		</div>
		
	</div>
	<script type="text/javascript">
		var menu = {
			status: "block",
			totalItem: '${functions.permissionFunctions?size}',
			Items: [
				<#list functions.permissionFunctions as f>
				{
					id: '${f.function.functionId}',
					text: '${StringUtil.wrapString(f.function.vnLabel)}',
					status: "none",
					children:[
						
						<#list f.children as cf>
							{
								id: '${cf.functionId}',
								text: '${StringUtil.wrapString(cf.vnLabel)}',
								target: '${cf.target}',
								status: "none"
							},
						</#list>
					],
				},
				</#list>
			]
		}
	</script>
</div>  -->