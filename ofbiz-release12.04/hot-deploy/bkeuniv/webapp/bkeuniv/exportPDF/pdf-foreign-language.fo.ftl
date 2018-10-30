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

		        <fo:table-cell>
		          <fo:block font-weight="bold">${ForeignLanguage}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${ForeignLanguageName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Listen}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Speak}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Read}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${Write}</fo:block>
					</fo:table-cell>
				</fo:table-row>	

				<#assign languageId = 0 />
				<#list listForeignLanguage as foreignLanguage>
					<#assign languageId = languageId+1 />

				<fo:table-row height="20pt" border-top-style="dotted" >
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted">
						<fo:block >${languageId}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(foreignLanguage.foreignLanguageCatalogName?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(foreignLanguage.listen?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(foreignLanguage.speaking?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(foreignLanguage.reading?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell>
						<fo:block>${StringUtil.wrapString(foreignLanguage.writing?if_exists)}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell  />
					
				</fo:table-row>	
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row>
