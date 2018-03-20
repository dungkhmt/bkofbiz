<#escape x as x?xml>

          <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid">
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell>
			      	<fo:block font-weight="bold"><#if indexS??>${indexS}</#if>.</fo:block>
			    	</fo:table-cell>

		        <fo:table-cell >
		          <fo:block font-weight="bold">${NumberOfDegree1}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
				<fo:table-cell />
		        <fo:table-cell >
		          <fo:block font-weight="bold">${NumberOfDegree2}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid" border-top-style="dotted" border-bottom-style="dotted">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell border-bottom-style="solid"/>
					
					<fo:table-cell border-right-style="solid" border-bottom-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="solid"/>
					
					<fo:table-cell border-right-style="solid" border-bottom-style="solid">
						<fo:block>${NameAndContent}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="solid"/>
					
					<fo:table-cell border-bottom-style="solid">
						<fo:block text-align = "center">${YearDegree}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
			
				<#assign index = 0>
				<#list resultCV.cv.patents as pt>
					<#assign index = index + 1>
					<fo:table-row height="20pt" >
				
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if index?exists>
								<fo:block >${index}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if pt.patentName?exists>
								<fo:block >${pt.patentName}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted">
							<#if pt.year?exists>
								<fo:block >${pt.year}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>
				</#list>

					<fo:table-row height="20pt" >
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted"/>
					</fo:table-row>
				
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 
</#escape>
