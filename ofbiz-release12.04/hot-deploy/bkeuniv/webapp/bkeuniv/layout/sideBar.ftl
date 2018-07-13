<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

<@SideBar open=true handleToggle="sideBar" width="300px">
	<#assign targetCurr=Request.targetRequestUri />
	<@List height="100%" width="300px">
		<#if "main"?index_of(targetCurr)!= -1 || targetCurr?index_of("main")!= -1>
			<#assign styleMain="background-color: rgba(0, 0, 0, 0.1);" />
			<#if !titlePage??>
				<#assign titlePage=uiLabelMap.BkEunivHomePage />
			</#if>
		<#else>
			<#assign styleMain="" />
		</#if>
		<#assign autoURLTitlePage=false />
		<#if "user"?index_of(targetCurr)!= -1 || targetCurr?index_of("user")!= -1>
			<#assign styleUser="background-color: rgba(0, 0, 0, 0.1);" />
			<#if !titlePage??>
				<#assign autoURLTitlePage=true />
				<#assign titlePage=uiLabelMap.BkEunivPersonalInformation />
			</#if>
		<#else>
			<#assign styleUser="" />
		</#if>
		<@ListItem id="home-112101" style=styleMain primaryText=uiLabelMap.BkEunivHomePage leftIcon="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" linkTo="main" />
		<@ListItem id="user-991011" style=styleUser primaryText=uiLabelMap.BkEunivPersonalInformation linkTo="user"leftIcon="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" linkTo="user"/>
		<hr class="side-bar-separator" />

		<#list functions.permissionFunctions as f>
			<#if f.function.target??>
				<#if f.function.target?index_of(targetCurr)!= -1 || targetCurr?index_of(f.function.target)!= -1>
					<#assign styleItem="background-color: rgba(0, 0, 0, 0.1);" />
					<#if !titlePage?? || autoURLTitlePage>
						<#assign titlePage=f.function.vnLabel />
					</#if>
				<#else>
					<#assign styleItem="" />
				</#if>
			<#else>
				<#assign styleItem="" />
			</#if>
			<@ListItem id=f.function.functionId style=styleItem primaryText=f.function.vnLabel leftIcon=f.function.icon linkTo=f.function.target open=isOpen(f, Request.targetRequestUri)>
				<#if f.children??>
					<#list f.children as cf>
						<#if cf.target??>
							<#assign pathT = targetCurr?split("/", "r") />
							<#assign pathCFT = cf.target?split("/", "r") />
							<#if (cf.target?index_of(targetCurr)!= -1 || targetCurr?index_of(cf.target)!= -1)&&(pathT[pathT?size - 1]==pathCFT[pathCFT?size - 1])>
								<#assign styleItem="background-color: rgba(0, 0, 0, 0.1);" />
								<#if !titlePage?? || autoURLTitlePage>
									<#assign titlePage=cf.vnLabel />
								</#if>
							<#else>
								<#assign styleItem="" />
							</#if>
						<#else>
							<#assign styleItem="" />
						</#if>
						
						<@ListItem style=styleItem level=1 id=cf.functionId primaryText=cf.vnLabel leftIcon=cf.icon linkTo=cf.target open=isOpen(cf, Request.targetRequestUri)>
						</@ListItem>
					</#list>
				</#if>
			</@ListItem>
		</#list>
	</@List>
</@SideBar>

<#function isOpen parent target=Request.targetRequestUri>
	<#if parent??&&parent?is_hash_ex>
		<#if parent.children??&&parent.children?is_hash_ex>
			<#list parent.children as cf>
				<#if cf.target??>
					<#assign pathT = target?split("/", "r") />
					<#assign pathCFT = cf.target?split("/", "r") />
					<#if (cf.target?index_of(target)!= -1 || target?index_of(cf.target)!= -1)&&(pathT[pathT?size - 1]==pathCFT[pathCFT?size - 1])>
						<#return true/>
					</#if>
				</#if>

				<#if cf.children??>
					<#assign _check=isOpen(cf, target) />
					<#if _check>
						<#return true/>
					</#if>
				</#if>
			</#list>
		</#if>
	</#if>
	<#return false/>
</#function>
<script>
	var titlePage = "${titlePage?if_exists}"
</script>