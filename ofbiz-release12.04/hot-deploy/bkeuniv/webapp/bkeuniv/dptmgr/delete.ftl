<div id="deletemodal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">${uiLabelMap.CommonDelete} <strong>{{deleteForm.departmentId}}</strong>?</h4>
      </div>
      <div class="modal-footer">
        <button class="btn btn-default" data-dismiss="modal">${uiLabelMap.CommonCancel}</button>
        <button class="btn btn-primary" ng-click="delete()">${uiLabelMap.CommonDelete}</button>
      </div>
    </div>
  </div>
</div>