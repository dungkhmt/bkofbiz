<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">TSPD</h1>
		</div>
	</div>
	<div class="row">
		<form:form action="tspd/uploadSolution" method="POST" id="tspdsolution" commandName="tspdsolution" enctype="multipart/form-data" role="form" class="form-horizontal">
			<input id="input-solution" path="tspdSolutionFile" name="tspdSolutionFile" type="file" class="file file-loading " style="display:none" />
			<a class="btn btn-primary " id="submit-button-solution" onclick="uploadSolution()" >Upload Solution</a>
		</form/form:
		<div id="googleMap" style="width:100%;height:100%"></div>
		<form action="tsp-drone/tspd-solve" method="POST" commandName="tspd" role="form" class="form-horizontal">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="form-group">
						<label class="control-label col-lg-3">Truck speed (km/h)</label>
						<div class="col-lg-3">
							<input path="truckSpeed" name="truckSpeed" id="truckSpeed" class="form-control" placeholder="Truck speed"/>
						</div>
						<label class="control-label col-lg-3">Drone speed (km/h)</label>
						<div class="col-lg-3">
							<input path="droneSpeed" name="droneSpeed" id="droneSpeed" class="form-control" placeholder="Drone speed"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<div class="form-group">
						<label class="control-label col-lg-3">Cost per unit (km) of truck</label>
						<div class="col-lg-3">
							<input path="truckCost" name="truckCost" id="truckCost" class="form-control" placeholder="Cost per unit of truck"/>
						</div>
						<label class="control-label col-lg-3">Cost per unit (km) of drone</label>
						<div class="col-lg-3">
							<input path="droneCost" name="droneCost" id="droneCost" class="form-control" placeholder="Cost per unit of drone"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<div class="form-group">
						<label class="control-label col-lg-3">Wait time (Delta) (minute)</label>
						<div class="col-lg-3">
							<input path="delta" name="delta" id="delta" class="form-control" placeholder="Wait time" />
						</div>
						<label class="control-label col-lg-3">Drone endurance (e) (km)</label>
						<div class="col-lg-3">
							<form:input path="endurance" name="endurance" id="endurance" class="form-control" placeholder="endurance"/>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
					<form:input path="listPoints" name="listPoints" id="listPoints" type="hidden"/>
					<button class="btn btn-primary col-lg-offset-5" onclick="run_algorithm();" type="submit">Run</button>
					<a class="btn btn-primary" onclick="save_file(this);">Save</a>
					<a class="btn btn-primary" onclick="upload_file();">Upload</a>
					<input id="file-tsp-data" class="file file-loading" type="file" style="display:none"/>	
				</div>
			</div>		
		</div>
		</form>
		
	</div>
</div>
