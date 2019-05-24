					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							KINH PHI
						</div>
						<table>
							<thead>
								<tr>
									<th>Kinh phi thiet bi</th>
									<th>Kinh phi quan ly</th>
									<th>Tong kinh phi</th>
									<th>Tong kinh phi thiet bi & quan ly</th>
									<th>Chi so khoi luong</th>
									<th>Nam ke khai</th>
									
								</tr>
							</thead>
							<tbody>
							<#list resultBudgetDeclaration.projectDeclarations as p>
								<tr>
									<#if p.equipmentBudget?exists>
										<td>${p.equipmentBudget}</td>
									</#if>
									
									<#if p.managementBudget?exists>
										<td>${p.managementBudget}</td>
									</#if>
									
									<#if p.allocatedBudgetYear?exists>
										<td>${p.allocatedBudgetYear}</td>
									</#if>

									<#if p.budget?exists>
										<td>${p.budget}</td>
									</#if>
									<#if p.rate?exists>
										<td>${p.rate}</td>
									</#if>
										
									<#if p.academicYearId?exists>
										<td>${p.academicYearId}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>
					

					
<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							MUC DO DONG GOP CUA THANH VIEN
						</div>
						<table>
							<thead>
								<tr>
									<th>Ho ten Thanh vien</th>
									<th>Muc do dong gop</th>
									<th>Nam ke khai</th>
									
								</tr>
							</thead>
							<tbody>
							<#list resultParticipationDeclaration.projectDeclarations as p>
								<tr>
									<#if p.staffName?exists>
										<td>${p.staffName}</td>
									</#if>
									
									<#if p.staffParticipationPercentage?exists>
										<td>${p.staffParticipationPercentage}</td>
									</#if>
									
									<#if p.academicYearId?exists>
										<td>${p.academicYearId}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>



					<div class="row inline-box" style="margin-bottom: 0px; position: relative; padding: 10px 16px 10px 16px;">
						<div style="line-height: 2; font-size: 18px; text-align: center; font-weight: 700;">
							${uiLabel.WorkingHourProject}
						</div>
						<table>
							<thead>
								<tr>
									<th>${uiLabel.MemberProject}</th>
									<th>${uiLabel.Workinghour}</th>
									<th>${uiLabel.AcademicYear}</th>
								</tr>
							</thead>
							<tbody>
							<#list resultProjectWorkingHourDeclaration.projectDeclarations as p>
								<tr>
									<#if p.staffName?exists>
										<td>${p.staffName}</td>
									</#if>
									
									<#if p.workinghours?exists>
										<td>${p.workinghours}</td>
									</#if>
									
									<#if p.academicYearName?exists>
										<td>${p.academicYearName}</td>
									</#if>
								</tr>
							</#list>
							</tbody>
						</table>
					</div>
