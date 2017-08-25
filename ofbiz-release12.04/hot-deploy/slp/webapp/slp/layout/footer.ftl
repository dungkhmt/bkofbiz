	<div class="footer">
			<div class="footer-content">
				<div class="sandbox">
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
			</div>
		</div>
		<script>
		function changeLanguage() {
	var selectBox = document.getElementById("select-language");
    var url = selectBox.options[selectBox.selectedIndex].value;
    $.ajax({
	    url: url,
	    type: 'post',
	    success: function(data) {
	    	location.reload(true)
	    },
	    error: function(err) {
	    	alertify.success(JSON.stringify(err));
	    }
    });
}
		</script>

</body>
</html>
