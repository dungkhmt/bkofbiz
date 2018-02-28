<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/button.ftl"/>
<#assign pageName = "H&#x1EC7; th&#x1ED1;ng qu&#x1EA3;n l&#xFD; khoa h&#x1ECD;c c&#xF4;ng ngh&#x1EC7;" />

<#if title??>
	<#assign pageName = uiLabelMap[title] />
</#if>
<div class="nav">
	<span class="icon-side-bar" onClick="openSideBar()">
		<@IconButton id="side-bar-199921" size="40px" icon="M4 15h16v-2H4v2zm0 4h16v-2H4v2zm0-8h16V9H4v2zm0-6v2h16V5H4z" style="height: 40px">
		</@IconButton>
		<#--  <span class="glyphicon glyphicon-menu-hamburger"></span>  -->
	
	</span>

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
					<a class="dropdown-item" onClick="changePassWord()">Doi mat khau</a>
					<a class="dropdown-item" onClick="logout()">${uiLabelMap.BkEunivLogout}</a>
					
					
				</div>
			</div>
		</div>
	</div>
</div>
<script>
function changePassWord(){
	//alert('Doi mat khau');
	window.location.href = "/bkeuniv/control/formchangepassword";
}

</script>
