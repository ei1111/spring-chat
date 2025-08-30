const stompClient = new StompJs.Client({
  // brokerURL 대신 SockJS 연결
  webSocketFactory: () => new SockJS('http://localhost:8080/stomp/chats'),
});
let userId;


function getCurrentUser(callback) {
  $.ajax({
    type: 'GET',
    url: '/consultants/me',
    xhrFields: { withCredentials: true }, // 쿠키 포함
    success: function(data) {
      userId = data.userId;
      callback(data.userId);
    },
    error: function(err) {
      console.error('Failed to get current user', err);
    }
  });
}

// 2. 연결 시 처리
stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  setConnected(true);

  getCurrentUser((userId) => {
    console.log('Logged in user:', userId);
  showChatrooms(userId, 0);
  });

  // 3. 새로운 메시지 알림 구독
  stompClient.subscribe('/sub/chats/news', (chatMessage) => {
    const notification = JSON.parse(chatMessage.body);

    // 새 메시지 아이콘 표시
    toggleNewMessageIcon(notification.chatroomId, true);

    // 메시지 보낸 유저 기준으로 채팅방 목록 갱신
    showChatrooms(notification.userId, 0);
  });
};

function toggleNewMessageIcon(chatroomId, toggle) {
  if (toggle) {
    $("#new_" + chatroomId).show();
  } else {
    $("#new_" + chatroomId).hide();
  }
}

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#create").prop("disabled", !connected);
}

function connect() {
  console.log("connect");
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  let chatroomId = $("#chatroom-id").val();
  stompClient.publish({
    destination: "/pub/chats/" + chatroomId,
    body: JSON.stringify(
        {'message': $("#message").val()})
  });
  $("#message").val("")
}

function createChatroom() {
  $.ajax({
    type: 'POST',
    url: '/chats',
    contentType: 'application/json',   // JSON 전송
    data: JSON.stringify({
      userId: userId,
      title: $("#chatroom-title").val()
    }),
    success: function (data) {
      console.log('data: ', data);
      showChatrooms(userId,0)
      enterChatroom(data.id, true);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  })
}

function showChatrooms(userId, pageNumber) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: '/chats/' + userId + '?page=' + pageNumber ,
    success: function (data) {
      console.log('data: ', data);
      renderChatrooms(data);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  })
}

function renderChatrooms(page) {
  let chatrooms = page.content;
  $("#chatroom-list").html("");
  for (let i = 0; i < chatrooms.length; i++) {
    $("#chatroom-list").append(
        "<tr onclick='joinChatroom(" + chatrooms[i].id + ")'><td>"
        + chatrooms[i].id + "</td><td>" + chatrooms[i].title
        + "<img src='new.png' id='new_" + chatrooms[i].id + "' style='display: "
        + getDisplayValue(chatrooms[i].hasNewMessage)
        + "'/></td><td id='memberCount_" + chatrooms[i].id + "'>"
        + chatrooms[i].chatCount + "</td><td>" + chatrooms[i].createdAt
        + "</td></tr>"
    );
  }

  // 이전 페이지 버튼
  $("#prev").off("click"); // 기존 이벤트 제거

  if (page.first) {
    $("#prev").prop("disabled", true);
  } else {
    $("#prev").prop("disabled", false).click(
        () => showChatrooms(userId,page.number - 1));
  }

  // 다음 페이지 버튼
  $("#next").off("click"); // 기존 이벤트 제거
  if (page.last) {
    $("#next").prop("disabled", true);
  } else {
    $("#next").prop("disabled", false).click(
        () => showChatrooms(userId, page.number + 1));
  }
}

function getDisplayValue(hasNewMessage) {
  if (hasNewMessage) {
    return "inline";
  }

  return "none";
}

let subscription;

function enterChatroom(chatroomId, newMember) {
  $("#chatroom-id").val(chatroomId);
  $("#messages").html("");
  showMessages(chatroomId);
  $("#conversation").show();
  $("#send").prop("disabled", false);
  $("#leave").prop("disabled", false);
  toggleNewMessageIcon(chatroomId, false);

  if (subscription != undefined) {
    subscription.unsubscribe();
  }

  subscription = stompClient.subscribe('/sub/chats/' + chatroomId,
      (chatMessage) => {
        showMessage(JSON.parse(chatMessage.body));
      });

  if (newMember) {
    console.log("newMember:", newMember);
    stompClient.publish({
      destination: "/pub/chats/" + chatroomId,
      body: JSON.stringify({ userId : "님이 방에 들어왔습니다." })
    })
  }
}

function showMessages(chatroomId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: '/chats/' + chatroomId + '/messages',
    success: function (data) {
      console.log('data: ', data);
      for (let i = 0; i < data.length; i++) {
        showMessage(data[i]);
      }
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  })
}

function showMessage(chatMessage) {
  console.log("chatMessage:", chatMessage);
  const sender = chatMessage.sender || userId;
  const message = chatMessage.message || "님이 방에 들어왔습니다."
  $("#messages").append(
      "<tr><td>" + sender + " : " + message
      + "</td></tr>");
}

function joinChatroom(chatroomId) {
  $.ajax({
    type: 'POST',
    url: '/chats/chatrooms',
    contentType: 'application/json',   // JSON 전송
    data: JSON.stringify({
      userId: userId,
      chatroomId: chatroomId,
      currentChatroomId : $("#chatroom-id").val()
    }),
    success: function (data) {
      console.log('data: ', data);
      enterChatroom(chatroomId, data);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  })
}

function getRequestParam(currentChatroomId) {
  console.log("getRequestParam");
  if (currentChatroomId == "") {
    return "";
  }

  return "?currentChatroomId=" + currentChatroomId;
}

function leaveChatroom() {
  console.log("leaveChatroom");
  let chatroomId = $("#chatroom-id").val();
  $.ajax({
    type: 'DELETE',
    url: '/chats',
    contentType: 'application/json',   // JSON 타입 지정
    data: JSON.stringify({ chatroomId: chatroomId , userId: userId}), // JSON 바디 전송
    success: function (data) {
      console.log('data: ', data);
      showChatrooms(userId,0);
      exitChatroom(chatroomId);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  });
}

function exitChatroom(chatroomId) {
  $("#chatroom-id").val("");
  $("#conversation").hide();
  $("#send").prop("disabled", true);
  $("#leave").prop("disabled", true);
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#create").click(() => createChatroom());
  $("#leave").click(() => leaveChatroom());
  $("#send").click(() => sendMessage());
});