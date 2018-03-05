<#include "util.ftl"/>

<#macro Body width="100%" backgroundColor="rgb(237, 236, 236)" overflowY="hidden" overflowX="auto">
    <#local code=random(1, 999999)?string["000000"] />
    <div id="body-${code}" style="width:${width}; background-color: ${backgroundColor};display: flex;flex: 1 1 0%;overflow-y: ${overflowY};overflow-x: ${overflowX};">
        <#nested>
    </div>
</#macro>