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
  showChatrooms(userId);
  });

  // 3. 새로운 메시지 알림 구독
  stompClient.subscribe('/sub/chats/news', (chatMessage) => {
    const notification = JSON.parse(chatMessage.body);

    // 새 메시지 아이콘 표시
    toggleNewMessageIcon(notification.chatroomId, true);

    // 메시지 보낸 유저 기준으로 채팅방 목록 갱신
    showChatrooms(notification.userId);
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
      memberId: 1,
      title: $("#chatroom-title").val()
    }),
    success: function (data) {
      console.log('data: ', data);
      showChatrooms();
      enterChatroom(data.id, true);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    },
  })
}

function showChatrooms(userId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: '/chats/'+ userId,
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

function renderChatrooms(chatrooms) {
  $("#chatroom-list").html("");
  for (let i = 0; i < chatrooms.length; i++) {
    $("#chatroom-list").append(
        "<tr onclick='joinChatroom(" + chatrooms[i].id + ")'><td>"
        + chatrooms[i].id + "</td><td>" + chatrooms[i].title
        + "<img src='new.png' id='new_" + chatrooms[i].id + "' style='display: "
        + getDisplayValue(chatrooms[i].hasNewMessage) + "'/></td><td>"
        + chatrooms[i].memberCount + "</td><td>" + chatrooms[i].createdAt
        + "</td></tr>"
    );
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
  console.log("enterChatroom");
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
    stompClient.publish({
      destination: "/pub/chats/" + chatroomId,
      body: JSON.stringify({ [userId]: "님이 방에 들어왔습니다." })
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
  const sender = chatMessage.sender || userId;
  const message = chatMessage.message || "님이 방에 들어왔습니다.";
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
      memberId: 1,
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
    data: JSON.stringify({ chatroomId: chatroomId , memberId: 1}), // JSON 바디 전송
    success: function (data) {
      console.log('data: ', data);
      showChatrooms();
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