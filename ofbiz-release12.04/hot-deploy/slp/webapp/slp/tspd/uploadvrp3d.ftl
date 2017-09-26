<link href="/resource/slp/css/fileinput.min.css" rel="stylesheet" type="text/css">

<div id="page-wrapper">
	<form action="upload-file-vrp3d" method="POST" commandName="vrpInputFile" enctype="multipart/form-data" role="form">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Upload Data File</h1>
			</div>
			<div class="col-lg-12">
				<input id="input-file" path="vrpInputRequest" name="vrpInputRequest" type="file" class="file file-loading" data-allowed-file-extensions='["json"]'/>
			</div>
		</div>
	</form>
</div>

<script src="/resource/slp/js/fileinput.min.js"></script>
<script>
$(document).ready(function(){
	 $("#input-file").fileinput();
 });
</script>