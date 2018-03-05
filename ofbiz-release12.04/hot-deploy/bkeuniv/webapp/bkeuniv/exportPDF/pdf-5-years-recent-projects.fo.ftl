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
		          <fo:block font-weight="bold">${Topic}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(10)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(10)"/>
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${SubTopic1}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${TimeStart}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Program}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell >
						<fo:block>${Status1}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
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
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
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
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
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
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${SubTopic2}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${TimeStart}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Program}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${Status2}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
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
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
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
