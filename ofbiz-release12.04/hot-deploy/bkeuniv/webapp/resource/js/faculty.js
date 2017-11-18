var app = angular.module('faculty', []);
app.controller('facultyCtrl', function($scope, facultyService) {
  $scope.searchForm = {
    facultyId: '',
    facultyName: '',
    universityId: '',
    universityName: '',
    viewIndex: 0
  };

  $scope.items = [];
  $scope.paging = {
    totalPage: 0
  };

  /*Searching*/

  $scope.search = function() {
    facultyService.searchFaculty($scope.searchForm).then(function(data) {
      $scope.items = data.result.items;
      $scope.paging = data.result.paging;
      calcPage();
    });
  }

  $scope.searchNew = function() {
    $scope.searchForm.viewIndex = 0;
    $scope.search();
  }

  $scope.getItemIndex = function (i) {
    return $scope.paging.viewIndex * $scope.paging.viewSize + 1 + i
  }

  $scope.changePage = function(page) {
    if (page >= $scope.paging.totalPage
      ||page < 0) {
      return;
    }
    $scope.searchForm.viewIndex = page;
    $scope.search();
  }

  $scope.nextPage = function() {
    $scope.changePage($scope.searchForm.viewIndex + 1);
  }

  $scope.prevPage = function() {
    $scope.changePage($scope.searchForm.viewIndex - 1);
  }

  $scope.fisrtPage = function() {
    $scope.changePage(0);
  }

  $scope.lastPage = function() {
    $scope.changePage($scope.paging.totalPage - 1);
  }

  /*Editing*/

  $scope.openEditModal = function(data) {
    $scope.editForm = data;
    $scope.editMode = true;
    $('#editmodal').modal('show');
  }

  $scope.update = function() {
    $('#editmodal').modal('hide');
    facultyService.updateFaculty($scope.editForm).then(function() {
      $scope.search();
    });
  }

  /*Create*/

  $scope.openCreateModal = function() {
    $scope.editForm = null;
    $scope.editMode = false;
    $('#editmodal').modal('show');
  }

  $scope.create = function() {
    $('#editmodal').modal('hide');
    facultyService.createFaculty($scope.editForm).then(function() {
      $scope.searchNew();
    });
  }

  /*Delete*/
  $scope.openDeleteModal = function(data) {
    $scope.deleteForm = data;
    $('#deletemodal').modal('show');
  }

  $scope.delete = function() {
    $('#deletemodal').modal('hide');
    facultyService.deleteFaculty($scope.editForm).then(function() {
      $scope.searchNew();
    });
  }

  function calcPage() {
    var paging = $scope.paging;
    var totalPage = Math.round(paging.totalSize / paging.viewSize);
    if (paging.totalSize % paging.viewSize === 0) {
      totalPage += 1;
    }
    var from = Math.min(paging.viewIndex * paging.viewSize + 1, paging.totalSize);
    var to = Math.min(from + paging.viewSize - 1, paging.totalSize);
    paging.totalPage = totalPage;
    paging.from = from;
    paging.to = to;
  }
});

app.service('facultyService', function($http) {
  this.searchFaculty = function(criteria) {
    return $http({
      url: window.entrypoint + 'getFacultyAjax',
      method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(criteria)
    }).then(function (res) {
      if (res.data.status === 'ok') {
        return res.data;
      }
      throw new Error();
    });
  }

  this.updateFaculty = function(data) {
    return $http({
      url: window.entrypoint + 'updateFacultyAjax',
      method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(data)
    }).then(function (res) {
      if (res.data.status === 'ok') {
        return res.data;
      }
      throw new Error();
    });
  }

  this.createFaculty = function(data) {
    return $http({
      url: window.entrypoint + 'createFacultyAjax',
      method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(data)
    }).then(function (res) {
      if (res.data.status === 'ok') {
        return res.data;
      }
      throw new Error();
    });
  }

  this.deleteFaculty = function(data) {
    return $http({
      url: window.entrypoint + 'deleteFacultyAjax',
      method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(data)
    }).then(function (res) {
      if (res.data.status === 'ok') {
        return res.data;
      }
      throw new Error();
    });
  }
});