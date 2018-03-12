<#escape x as x?xml>
          <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid">
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
		        <fo:table-cell>
			      	<fo:block font-weight="bold"><#if indexS??>${indexS}</#if>.</fo:block>
		    	</fo:table-cell>

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ResearchConstruction}</fo:block>
		        </fo:table-cell>
  	          </fo:table-row>
		      
		      <fo:table-row>
				<fo:table-cell />
		        <fo:table-cell >
		          <fo:block font-weight="bold">${Construction}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(8)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${ConstructionName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Author}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Public}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-bottom-style="dotted">
						<fo:block>${Year}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.papers as p>
					<#assign index = index + 1>
				
					<fo:table-row height="20pt" >
						//STT
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${index}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.paperName?exists>
								<fo:block>${p.paperName}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.paperName?exists>
								<fo:block></fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.journalConferenceName?exists>
								<fo:block>${p.journalConferenceName}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted">
							<#if p.year?exists>
								<fo:block>${p.year}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>	
					
				</#list>

					<fo:table-row height="20pt" >
						//STT
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted"/>
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