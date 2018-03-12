
<#escape x as x?xml>
	
	<@macro NumericalOrder numericalOrder=1>		
	</@macro>
	
		


  <fo:table table-layout="fixed">
  	<fo:table-column column-width="proportional-column-width(1)"/>
    <fo:table-body>
  	
				
  	
  	<#include "pdf-business-progress-test.fo.ftl"/>
  	
    </fo:table-body>
  </fo:table>

</#escape>

