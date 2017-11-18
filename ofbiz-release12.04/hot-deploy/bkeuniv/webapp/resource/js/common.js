function pagination($wrapper, paging, cb) {
  var $ul = $('<ul class="pagination"></ul>');
  var hasMod = paging.totalSize % paging.viewSize;
  var totalPage = Math.floor(paging.totalSize / paging.viewSize);
  totalPage += hasMod ? 1 : 0;
  var finalPage = totalPage - 1;
  var page = paging.viewIndex;

  var $temp = $('<li><a>«</a></li>');
  if (page === 0) {
    $temp.addClass('disabled');
  } else {
    $temp.click(function() {
      cb(0);
    });
  }
  $ul.append($temp);

  var $temp = $('<li><a>←</a></li>');
  if (page === 0) {
    $temp.addClass('disabled');
  } else {
    $temp.click(function() {
      cb(page - 1);
    });
  }
  $ul.append($temp);

  if (totalPage > 5) {
    $ul.append(item(0));
    if (page - 2 >= 2) {
      $ul.append('<li><a>...</a></li>');
      if (page + 2 < finalPage - 1) {
        for (var i = page - 2; i <= page + 2; i++) {
          $ul.append(item(i));
        }
        $ul.append('<li><a>...</a></li>');
      } else {
        for (var i = finalPage - 4; i < finalPage; i++) {
          $ul.append(item(i));
        }
      }
    } else {
      for (var i = 1; i < 5; i++) {
        $ul.append(item(i));
      }
      $ul.append('<li><a>...</a></li>');
    }
    $ul.append(item(finalPage));
  } else {
    for (var i = 0; i < totalPage; i++) {
      $ul.append(item(i));
    }
  }

  var $temp = $('<li><a>→</a></li>');
  if (page === finalPage) {
    $temp.addClass('disabled');
  } else {
    $temp.click(function() {
      cb(page + 1);
    });
  }
  $ul.append($temp);

  $temp = $('<li><a>»</a></li>');
  if (page === finalPage) {
    $temp.addClass('disabled');
  } else {
    $temp.click(function() {
      cb(finalPage);
    });
  }
  $ul.append($temp);

  $wrapper.html($ul);

  function item(p) {
    var $item = $('<li><a>' + (p + 1) +'</a></li>');
    $item.click(function() {
      cb(p);
    });
    if (p == page) {
      $item.addClass('active');
    }
    return $item;
  }
}

function ajax(path, body) {

  return fetch(window.entrypoint + path, {
    method: 'POST',
    body: createFormData(body)
  })
  .then(function(res) {
    return res.json();
  })

  function createFormData(body) {
    var form = new FormData();
    for (var key in body) {
      form.append(key, body[key]);
    }
    return form;
  }
}