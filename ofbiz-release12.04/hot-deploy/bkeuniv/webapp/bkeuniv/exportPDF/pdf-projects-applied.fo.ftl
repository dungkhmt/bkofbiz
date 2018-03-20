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
		          <fo:block font-weight="bold">${NumberOfConstruction}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid" 	 border-bottom-style="dotted">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(15)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${NameOfConstruction}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Model}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell >
						<fo:block>${TimeConstruction}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.appliedProjects as p>
					<#assign index = index+1>
					<fo:table-row height="20pt" border-top-style="dotted" >
					//STT
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${index}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.name?exists>
								<fo:block>${p.name}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.description?exists>
								<fo:block>${p.description}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted">
							<#if p.period?exists>
								<fo:block>${p.period}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>	
					
				</#list>

				<fo:table-row height="20pt" border-top-style="dotted" >
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
					
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted"/>
					</fo:table-row>	
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 

</#escape>
