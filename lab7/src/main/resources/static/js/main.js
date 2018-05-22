'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var chatListPage = document.querySelector('#chatList-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var chatRoomId = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// 새로운 방 생성 하기  
$(document).ready(function () {
	$("#addCreateBtn").click(function() {
		var name = $("#createRoomName").val();
		//alert(name);
		var notRoomsList = document.querySelector("#notRooms");
		notRoomsList.classList.add('hidden');
        var htmlAppend ="";
        htmlAppend += "<tr><td>"+name+"</td><td><button class='primary' onclick='deleteLine(this);' style='float: right;'>delete</button></td></tr>"
        $("#roomList").append(htmlAppend);

	})
});
// 개설된 방 삭제   
function deleteLine(obj) {
    var tr = $(obj).parent().parent();
    tr.remove();
}

function connect(event) {
    username = document.querySelector('#name').value.trim();
    
    if(username) {
        usernamePage.classList.add('hidden');
        chatListPage.classList.remove('hidden');  
        
        //stompClient.connect({}, onConnected, onError); //초기화  callback 함수 설정  연결 성공했을때 와 에러가 발생했을 때 함수 설정  
    }
    event.preventDefault();
}

// join 버튼 클릭
function joinRoom(event) {
 
    var socket = new SockJS('/ws'); // 최초의 채팅 서버에 접속할때  
    stompClient = Stomp.over(socket); //실제 사용하게 될 변수는 stompClient 초기화
    stompClient.connect({}, onConnected, onError); //초기화  callback 함수 설정  연결 성공했을때 와 에러가 발생했을 때 함수 설정  
    
    event.preventDefault(); // prevent event from happening
}


function onConnected() { //연결이 완료가 되었을때  
    // Subscribe to the Public Topic
	
	chatRoomId = document.querySelector('#joinRoom').value.trim();
	document.getElementById("chattingName").innerHTML = "Spring WebSocket Chat Demo (Room Name: "+ chatRoomId+ " )";
	chatPage.classList.remove('hidden');
	chatListPage.classList.add('hidden');
	
	alert("userName : "+username +"  // RoomName : "+chatRoomId+"  Join!");
    //stompClient.subscribe('/topic/public', onMessageReceived); //public 이 내용이 채팅방 이름, 디폴트 //  
    // /topic/public/길동의 방 이런식으로 수정하기!!!!!!!!!!!!!!!!!!!!!!!!!! 여러개로 분산  
    // 전체 채팅방의 갯수와 개설되어있는 이름이 무엇인지를 서버가 제시 해줘야 함 !!!!!!!!! 
    // 진행 절차에 대해서는 자유  2개 이상 채팅방 !!!! 신규 채팅방을 만들려면 어떻게 할지 자유    
    // 채팅한 내용 
	
	stompClient.subscribe('/topic/'+chatRoomId, onMessageReceived);
    
	///  app/chat.addUser
    // Tell your username to the server /chats/{chatRoomId}
	
    stompClient.send("/app/topic/"+chatRoomId,
        {},
        JSON.stringify({sender: username, type: 'JOIN', roomName: chatRoomId})
    )
	
    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) { // 내가 보내고 싶은 채팅열 !!!!!!!
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            roomName: chatRoomId
        };
        
        // /topic/public 에 가입되어 있고 메시지 보낼때는 /app/chat.sendMessage !!!! chatController로 보
        stompClient.send("/app/topic/"+chatRoomId, {}, JSON.stringify(chatMessage));
//        stompClient.send("/topic/public", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// quit chat
function outChating() {
	stompClient.send("/app/topic/"+chatRoomId,
	        {},
	        JSON.stringify({sender: username, type: 'LEAVE', roomName: chatRoomId})
	)
	
	stompClient.disconnect(function() {
		alert("See you next time!");
		chatPage.classList.add('hidden');
		chatListPage.classList.remove('hidden');
	});
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true) 
joinForm.addEventListener('submit', joinRoom, true)
messageForm.addEventListener('submit', sendMessage, true)
