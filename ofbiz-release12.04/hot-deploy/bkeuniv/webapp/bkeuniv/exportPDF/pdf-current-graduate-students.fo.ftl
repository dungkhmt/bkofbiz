<#escape x as x?xml>
     
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-top-style = "solid" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell>
			      	<fo:block font-weight="bold">${stt}.</fo:block>
			    	</fo:table-cell>
			    	<#assign stt=stt+1 />

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ExperienceGuide}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>	
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${ExperienceName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${MainGuide}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${NameTopic}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Specialize}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-aline = "center	" >
						<fo:block>${TimeEducation}</fo:block>
					</fo:table-cell>
					
					
					
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style = "solid" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID1}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 
</#escape>
