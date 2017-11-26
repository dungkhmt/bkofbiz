<#include "util.ftl"/>
<#macro List width="256px" height="auto" border="none" >
    <div style="border: ${border}; width: ${width}; height: ${height}; overflow-y: auto; overflow-x: hidden;">
        <div style="padding: 0px 0px 8px;">
            <#nested />
        </div>
    </div>
</#macro>