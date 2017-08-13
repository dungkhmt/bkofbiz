<div class="side-bar hide-side-bar">

	<button class="closeSideBar" onClick="closeSideBar()">
		<span class="glyphicon glyphicon-remove"></span>
	</button>
	<div class="side-bar-container">
		<div class="side-bar-selection">
			<div class="side-bar-item-container">
				<ul class="user-link">
					<li>
						<a href="main"><span class="glyphicon glyphicon-home"></span>${uiLabelMap.BkEunivHomePage}</a>
					</li>
					<li>
						<a href="user"><span class="glyphicon glyphicon-user"></span>Thong tin ca nhan</a>
					</li>
					<li>
						<a href="404"></a>
					</li>
				</ul>
			</div>
			<hr class="side-bar-separator">
		</div>
		
		<div class="side-bar-selection">
			<div class="side-bar-item-container">
				<#list functions.permissionFunctions as f>
					<label class="tree-toggle nav-header">
						<span class="glyphicon glyphicon-minus"></span>
						${f.function.vnLabel}
					</label>
					<ul class="researchdeclaration nav-list tree">
					
					<#list f.children as cf>
						<li>
						<a href="${cf.target}">${cf.vnLabel}</a>
						</li>							
					</#list>
					
					</ul>
					
				</#list>
			
			</div>
			<hr class="side-bar-separator">
		</div>
		
	</div>
</div>