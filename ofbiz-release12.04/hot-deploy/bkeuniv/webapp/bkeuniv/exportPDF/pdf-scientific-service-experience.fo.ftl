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
		          <fo:block font-weight="bold">${ExperienceManagement1}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
						//STT
		        <fo:table-cell />
			      	

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ExperienceManagement2}</fo:block>
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
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid" text-align = "center">
						<fo:block>${ModelCouncil}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-align = "center">
						<fo:block>${Times}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.scientificExperiences as se>
					<#assign index = index + 1>
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${index}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${se.description}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${se.descriptionDetail}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 
</#escape>
