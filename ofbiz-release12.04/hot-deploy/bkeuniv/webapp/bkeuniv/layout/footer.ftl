		<div class="footer">
			<div class="footer-content">
				<div class="sandbox">
					<select id="select-language" onchange="changeLanguage()" placeholder="Select a language...">
						<#assign availableLocales = Static["org.ofbiz.base.util.UtilMisc"].availableLocales()/>
						<#list availableLocales as availableLocale>
							<option <#if locale == availableLocale.toString()> selected="true" </#if> value="<@ofbizUrl>setSessionLocale</@ofbizUrl>?newLocale=${availableLocale.toString()}">
								${availableLocale.getDisplayName(availableLocale)}
							</option>
						</#list>
					</select>
				</div>
			</div>
		</div>
	</body>
</html>