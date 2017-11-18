var app = angular.module('department', []);
app.controller('dptCtrl', function($scope, dptService) {
  $scope.searchForm = {
    departmentId: '',
    departmentName: '',
    facultyId: '',
    facultyName: '',
    viewIndex: 0
  };

  $scope.items = [];
  $scope.paging = {
    totalPage: 0
  };

  /*Searching*/

  $scope.search = function() {
    dptService.searchDpt($scope.searchForm).then(function(data) {
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
    dptService.updateDpt($scope.editForm).then(function() {
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
    dptService.createDpt($scope.editForm).then(function() {
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
    dptService.deleteDpt($scope.editForm).then(function() {
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

app.service('dptService', function($http) {
  this.searchDpt = function(criteria) {
    return $http({
      url: window.entrypoint + 'getDepartmentAjax',
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

  this.updateDpt = function(data) {
    return $http({
      url: window.entrypoint + 'updateDepartmentAjax',
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

  this.createDpt = function(data) {
    return $http({
      url: window.entrypoint + 'createDepartmentAjax',
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

  this.deleteDpt = function(data) {
    return $http({
      url: window.entrypoint + 'deleteDepartmentAjax',
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

