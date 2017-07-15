<#escape x as x?xml>

  <fo:table table-layout="fixed">
    <fo:table-column column-width="proportional-column-width(1)"/>
    <fo:table-column column-width="proportional-column-width(1)"/>
    <fo:table-column column-width="proportional-column-width(1)"/>
    <fo:table-column column-width="proportional-column-width(2)"/>
    <fo:table-column column-width="proportional-column-width(1)"/>

    <fo:table-header>
      <fo:table-row font-weight="bold">
        <fo:table-cell background-color="#D4D0C8" height="20pt" display-align="center" border-top-style="solid" border-bottom-style="solid">
          <fo:block>Cot 1</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="#D4D0C8" height="20pt" display-align="center" border-top-style="solid" border-bottom-style="solid">
          <fo:block>Cot 2</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="#D4D0C8" text-align="right" height="20pt" display-align="center" border-top-style="solid" border-bottom-style="solid">
          <fo:block>Cot 3</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="#D4D0C8" text-align="right" height="20pt" display-align="center" border-top-style="solid" border-bottom-style="solid">
          <fo:block>Cot 4</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="#D4D0C8" text-align="right" height="20pt" display-align="center" border-top-style="solid" border-bottom-style="solid">
          <fo:block>Cot 5</fo:block>
        </fo:table-cell>
      </fo:table-row>
    </fo:table-header>
    
    <fo:table-body>
      <#list listRoutes as r>
      	<#if ((r_index % 2) == 0)>
          <#assign rowColor = "white">
        <#else>
          <#assign rowColor = "#CCCCCC">
        </#if>
        
      <fo:table-row>
        <fo:table-cell background-color="${rowColor}">
          <fo:block>${r.id}</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="${rowColor}">
          <fo:block>${r.date}</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="${rowColor}">
          <fo:block text-align="right">${r.shipperId}</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="${rowColor}">
          <fo:block text-align="right">${r.shipperName}</fo:block>
        </fo:table-cell>
        <fo:table-cell background-color="${rowColor}">
          <fo:block text-align="right">Note</fo:block>
        </fo:table-cell>

      </fo:table-row>
      </#list>
      	
    </fo:table-body>
  </fo:table>

</#escape>
