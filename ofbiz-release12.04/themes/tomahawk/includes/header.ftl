<#assign externalKeyParam = "&amp;externalLoginKey=" + requestAttributes.externalLoginKey?if_exists>

<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.partyGroup)?exists><#assign partyGroup = requestAttributes.partyGroup></#if>
<#assign docLangAttr = locale.toString()?replace("_", "-")>
<#assign langDir = "ltr">
<#if "ar.iw"?contains(docLangAttr?substring(0, 2))>
    <#assign langDir = "rtl">
</#if>
<html lang="${docLangAttr}" dir="${langDir}" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>H&#x1EC7; th&#x1ED1;ng qu&#x1EA3;n l&#xFD; khoa h&#x1ECD;c c&#xF4;ng ngh&#x1EC7;</title>
    
    <#if layoutSettings.shortcutIcon?has_content>
      <#assign shortcutIcon = layoutSettings.shortcutIcon/>
    <#elseif layoutSettings.VT_SHORTCUT_ICON?has_content>
      <#assign shortcutIcon = layoutSettings.VT_SHORTCUT_ICON.get(0)/>
    </#if>
    
    <link rel="shortcut icon" href="/resource/bkeuniv/image/logo_soict2-75x75.png" />



    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
    
    <!-- import js -->
    <script src="/resource/bkeuniv/js/lib/jquery.min.js"></script>
    <script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
    <script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>

    <#if layoutSettings.javaScripts?has_content>
        <#assign javaScriptsSet = Static["org.ofbiz.base.util.UtilMisc"].toSet(layoutSettings.javaScripts)/>
        <#list layoutSettings.javaScripts as javaScript>
            <#if javaScriptsSet.contains(javaScript)>
                <#assign nothing = javaScriptsSet.remove(javaScript)/>
                <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
            </#if>
        </#list>
    </#if>
    <#if layoutSettings.VT_HDR_JAVASCRIPT?has_content>
        <#list layoutSettings.VT_HDR_JAVASCRIPT as javaScript>
            <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
        </#list>
    </#if>
    <#if layoutSettings.styleSheets?has_content>
        <#list layoutSettings.styleSheets as styleSheet>
            <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
        </#list>
    </#if>
    <#if layoutSettings.VT_STYLESHEET?has_content>
        <#list layoutSettings.VT_STYLESHEET as styleSheet>
            <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
        </#list>
    </#if>
    <#if layoutSettings.rtlStyleSheets?has_content && langDir == "rtl">
        <#list layoutSettings.rtlStyleSheets as styleSheet>
            <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
        </#list>
    </#if>

    <#if layoutSettings.VT_RTL_STYLESHEET?has_content && langDir == "rtl">
        <#list layoutSettings.VT_RTL_STYLESHEET as styleSheet>
            <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
        </#list>
    </#if>
    <#if layoutSettings.VT_EXTRA_HEAD?has_content>
        <#list layoutSettings.VT_EXTRA_HEAD as extraHead>
            ${extraHead}
        </#list>
    </#if>



    <#if layoutSettings.WEB_ANALYTICS?has_content>
      <script language="JavaScript" type="text/javascript">
        <#list layoutSettings.WEB_ANALYTICS as webAnalyticsConfig>
          ${StringUtil.wrapString(webAnalyticsConfig.webAnalyticsCode?if_exists)}
        </#list>
      </script>
    </#if>

    

</head>
<body>
  <div class="nav">
	<div class="app-name">
		<h1>H&#x1EC7; th&#x1ED1;ng qu&#x1EA3;n l&#xFD; khoa h&#x1ECD;c c&#xF4;ng ngh&#x1EC7;</h1>
	</div>
	
	<div class="action">
    <div class="change-language">
      Thay &#x111;&#x1ED5;i ng&#xF4;n ng&#x1EEF;
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