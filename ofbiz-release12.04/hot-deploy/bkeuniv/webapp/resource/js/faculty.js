$(function() {
  var $actions = $('.main-action');
  var $searchTab = $actions.find('*[data-target="#search"]');
  var $editTab = $actions.find('*[data-target="#edit"]');
  var $createTab = $actions.find('*[data-target="#create"]');

  var searchCtx = {};

  /*Search tab*/
  var $tableElem = $('#search .tabledata');
  var tableData = new TableData($tableElem, [
    'facultyId', 'facultyName', 'universityName'
  ], getFaculty, function(newCtx) {
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
    data.facultyId = searchForm.facultyId.value;
    data.facultyName = searchForm.facultyName.value;
    data.universityId = searchForm.universityId.value;
    data.universityName = searchForm.universityName.value;
    return data;
  }

  /*Edit Tab*/
  var $editForm = $('.edit-form');
  var dpt = new CRUD([
    'facultyId', 'facultyName', 'universityId'
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
    updateFaculty(body).then(function(json) {
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
    createFaculty(body).then(function(json) {
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

function getFaculty(body) {
  return ajax('getFacultyAjax', body);
}

function updateFaculty(body) {
  return ajax('updateFacultyAjax', body);
}

function createFaculty(body) {
  return ajax('createFacultyAjax', body);
}

