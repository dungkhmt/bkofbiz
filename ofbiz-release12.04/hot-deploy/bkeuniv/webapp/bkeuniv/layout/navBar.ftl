
<#assign pageName = "H&#x1EC7; th&#x1ED1;ng qu&#x1EA3;n l&#xFD; khoa h&#x1ECD;c c&#xF4;ng ngh&#x1EC7;" />

<#if title??>
	<#assign pageName = uiLabelMap[title] />
</#if>
<div class="nav">
	<button class="icon-side-bar" onClick="openSideBar()">
		<span class="glyphicon glyphicon-menu-hamburger"></span>
	</button>

	<div class="app-name">
		<h1 style="font-size: 1.7vw;">${pageName}</h1>
	</div>
	<div class="change-language">
		<select id="select-language" onchange="changeLanguage()" placeholder="Select a language...">
			<#assign availableLocales = Static["org.ofbiz.base.util.UtilMisc"].availableLocales()/>
				<#if !locale?exists>
					<#assign locale = "en"/>
				</#if>

				<#list availableLocales as availableLocale>
					<option <#if locale == availableLocale.toString()> selected="true" </#if> value="<@ofbizUrl>setSessionLocale</@ofbizUrl>?newLocale=${availableLocale.toString()}">
						${availableLocale.getDisplayName(availableLocale)}
					</option>
				</#list>
		</select>
	</div>
	
	<div class="action">
		<div class="tab-group">
		</div>
		<div class="user">
			<div class="user-name" onClick="openDropdownUser()" onBlur="closeDropdownUser()" tabIndex="0">
				${userLogin.userLoginId}
				<div class="dropdown-information glyphicon glyphicon-chevron-down">         
				</div>
				<div class="dropdown-content hidden">
					<a class="dropdown-item" onClick='goto("/bkeuniv/control/user")'>${uiLabelMap.BkEunivPersonalInformation}</a>
					<a class="dropdown-item" onClick="logout()">${uiLabelMap.BkEunivLogout}</a>
				</div>
			</div>
		</div>
	</div>
</div>
