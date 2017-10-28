$(function() {
  var $actions = $('.main-action');
  var $searchTab = $actions.find('*[data-target="#search"]');
  var $editTab = $actions.find('*[data-target="#edit"]');
  var $createTab = $actions.find('*[data-target="#create"]');

  var searchCtx = {};

  /*Search tab*/
  var $tableElem = $('#search .tabledata');
  var tableData = new TableData($tableElem, [
    'departmentId', 'departmentName', 'facultyName'
  ], getDpt, function(newCtx) {
    searchCtx = newCtx;
  }, function(item) {
    $editTab.tab('show');
    dpt.edit(item);
  });
  tableData.print(searchCtx);

  var $searchForm = $('.search-form');
  $searchForm.submit(function(e) {
    e.preventDefault();
    tableData.print(getSearchFormData());
  });
  function getSearchFormData() {
    var data = {};
    var searchForm = $searchForm.get(0);
    data.departmentId = searchForm.departmentId.value;
    data.departmentName = searchForm.departmentName.value;
    data.facultyId = searchForm.facultyId.value;
    data.facultyName = searchForm.facultyName.value;
    return data;
  }

  /*Edit Tab*/
  var $editForm = $('.edit-form');
  var dpt = new CRUD([
    'departmentId', 'departmentName', 'facultyId'
  ]);
  dpt.edit = function(data) {
    this.fields.forEach(function(key) {
      $editForm.get(0)[key].value = data[key];
    });
  }
  $editForm.submit(function(e) {
    var body = {};
    e.preventDefault();
    dpt.fields.forEach(function(key) {
      body[key] = $editForm.get(0)[key].value;
    });
    updateDpt(body).then(function(json) {
      console.log('UPdate ', json);
      if (json.status === 'ok') {
        $searchTab.tab('show');
        tableData.print(searchCtx);
      } else {
        alert(json.message);
      }
    });
  })

  /*Create tab*/
  var $createForm = $('.create-form');
  dpt.create = function() {
    dpt.fields.forEach(function(key) {
      $editForm.get(0)[key].value = '';
    });
  }
  $createForm.submit(function(e) {
    var body = {};
    e.preventDefault();
    dpt.fields.forEach(function(key) {
      body[key] = $createForm.get(0)[key].value;
    });
    createDpt(body).then(function(json) {
      console.log('Create ', json);
      if (json.status === 'ok') {
        $searchTab.tab('show');
        tableData.print(searchCtx);
      } else {
        alert(json.message);
      }
    });
  })
});

function getDpt(body) {
  return ajax('getDepartmentAjax', body);
}

function updateDpt(body) {
  return ajax('updateDepartmentAjax', body);
}

function createDpt(body) {
  return ajax('createDepartmentAjax', body);
}

