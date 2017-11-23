<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

<@TextField />
<@ListItem primaryText="Test">
    <@ListItem primaryText="Test" level=1/>
</@ListItem>

<@Drawer open=true handleToggle="menu" docked=false openSecondary=false>
    <@ListItem primaryText="Test">
        <@ListItem primaryText="Test" level=1/>
    </@ListItem>
</@Drawer>

