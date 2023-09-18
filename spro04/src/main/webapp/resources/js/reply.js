/**
 * replyService.add 로 호출 시켜 사용
 * 매개변수는 똑같이 맞춰주지 않아도 됨은 javascript
 *
 * 이는 모듈 패턴이라고 해서, replyService를 매서드를 여러개 만들어 두고 호출하도록 하려고 한다.
 * https://findawayer.tistory.com/entry/IIE%EC%9D%98-%EC%9D%98%EB%AF%B8%EB%8A%94
 * 설명도 참고하면 좋음
 */

console.log("reply module...");

const replyService = (function () {
  function add(reply, callback, error) {
    console.log(".......add reply");

    $.ajax({
      type: "POST",
      url: "/reply/new",
      data: JSON.stringify(reply),
      contentType: "application/json; charset=UTF-8",
      success: function (result, status, xhr) {
        if (callback) {
          callback(result);
        }
      },
      error: function (xhr, status, err) {
        if (error) {
          error(err);
        }
      },
    });
  }

  function getList(param, callback, error) {
    const bno = param.bno;
    const page = param.page || 1;

    $.getJSON("/reply/pages/" + bno + "/" + page + ".json", function (data) {
      if (callback) {
        callback(data);
      }
    }).fail(function (xhr, status, err) {
      if (error) {
        error(err);
      }
    });
  }

  function remove(rno, callback, error) {
    $.ajax({
      type: "delete",
      url: "/reply/" + rno,
      success: function (deleteResult, status, xhr) {
        if (callback) {
          callback(deleteResult);
        }
      },
      error: function (xhr, status, err) {
        if (error) {
          error(err);
        }
      },
    });
  }

  function update(reply, callback, error) {
    $.ajax({
      type: "put",
      url: "/reply/" + reply.rno,
      data: JSON.stringify(reply),
      contentType: "application/json; charset=UTF-8",
      success: function (result, status, xhr) {
        if (callback) {
          callback(result);
        }
      },
      error: function (xhr, status, err) {
        if (error) {
          error(err);
        }
      },
    });
  }

  // 리턴할수 있도록 등록
  return {
    add: add,
    getList: getList,
    remove: remove,
    update: update,
  };
})();
