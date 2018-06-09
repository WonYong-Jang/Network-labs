#Network-labs

## Lab1 - HTTP 프로토콜 : 클라이언트(Chrome브라우저), 서버-(node.js&javascript)
 - lab1.js 완성하기 (node.js express 플러그인 추가(npm install express) / 실행 명령 : node lab1.js)
```
// 모듈을 추출합니다.
const express = require('express');
// 서버를 생성합니다.
const app = express();
app.use(express.static('public'));
// request 이벤트 리스너를 설정합니다. 정적파일(js,css,image) 다루기위해

app.get('/hong.html',(request,response)=> {
    response.redirect('http://www.naver.com');
});
app.get('/hong2.html',(request,response)=> {
    response.redirect('http://ktis.kookmin.ac.kr');
});
app.get('*',(request,response)=> {
    response.send(404);
    response.send('해당 경로에는 아무것도 없습니다.');
});
//서버를 실행합니다.
app.listen(8080, ()=> {
    console.log('Server running at http://192.168.33.197:8080');
});
```

## Lab2 - HTTP 프로토콜 : Spring-boot, 서버-(node.js&javascript)
 - HTTP_Client.java 완성하기 (프로그램 실행시 "lab2_log.txt" 처럼 출력되도록 구현)
 - 소스코드 일부와 결과사진
 ```
 try { // 소켓라이브러리
			String host;
			host = "192.168.33.107"; // 인터넷 주소 쓰기 192.168.33.138

			String fileEncoding = System.getProperty("file.encoding");
			System.out.println("file.encoding = " + fileEncoding);

			String contentlen = new String("Content-Length:");

			System.out.println("\n=================================================");
			System.out.println("1. test for HTTP GET default(index.html) home page");
			System.out.println("=================================================");

			Socket t = new Socket(host, 8080); // 8080 포트 번호 오픈
			BufferedReader in = new BufferedReader(new InputStreamReader(t.getInputStream())); // 서버에 연결되어있는 in,out
			PrintWriter out = new PrintWriter(new OutputStreamWriter(t.getOutputStream()));

			out.println("GET /index.html HTTP/1.1\r\n"); // GET~ 인터넷 서버로 흘러감
			out.println("\r\n");
			out.flush();

			System.out.println("\n(Request)"); // 응답 메시지 받기
			System.out.println("GET /index.html HTTP/1.1");
			System.out.flush();

			int cnt = 0;
			int cnt2 = 0;
			System.out.println("\n(Reply)");

			for (;;) { // 헤더 부분을 받아오는 것
				String str = in.readLine();

				if (str.contains(contentlen)) {
					String tempStr = str.substring(16);
					int resultCnt = Integer.parseInt(tempStr);
					cnt = resultCnt;
				}
				
				// HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것 CONTENT LANGTH 로 시작하는 문자열을
				// 발견하면 그 이후 존재하는 숫자를 읽어와서 인티저로 바꾸고

				// 헤더의 마지막까지 읽어야됨
				if (str.isEmpty()) {
					System.out.println();
					break;
				} else {
					System.out.println(str);
				}
			}

			for (;;) { // REPONSE 바디 부분을 361 받아오고 해결 //
				char[] b = new char[cnt - cnt2]; //
				int cx = in.read(b, 0, cnt-cnt2);
				
				// HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것
				
				String strResult = new String(b);
					
				cnt2 = b.length;
				System.out.println("bytes=" + cnt2 + "(" + cnt + ") " + strResult);
				
				if (cnt2 >= cnt) {
					break;
				}
			}
```
<img src="https://user-images.githubusercontent.com/26623547/39110125-d776aaa8-470a-11e8-828a-e7558ca5b85c.JPG" width="500" height="400">


## Lab3 - Restful : Spring-boot, javascript / 고객관리 시스템 구현
 - 서버측 : HTTP_ServerController.java
 - 클라이언트측 : RESTful.html
 - 다음 주소를 브라우저에 입력 ( http://(서버주소):8080/RESTful.html )
 - 기능 : 고객리스트(검색) , 고객업데이트(변경), 신규고객(추가), 고객탈퇴(삭제), 로그인정보 검색, 변경기능 
 - 결과 화면
 
 ![1234](https://user-images.githubusercontent.com/26623547/39163335-001afcb6-47b5-11e8-8735-c41004b8fa12.jpg)
 
## Lab4 - Restful : Spring-boot, javascript / 투표상황 발생데이터 실시간으로 보여주는 시스템 구현
 - 투표상황을 실시간으로 보여주는 서비스 : ( http://(서버주소):8080/PollRTV.html )
 - 투표상황 발생데이터를 입력 : ( http://(서버주소):8080/PollUpdate.html )
 - 기능 : 5초에 한번씩 실시간 투표상황을 주기적으로 갱신
 - 결과 화면
 
![default](https://user-images.githubusercontent.com/26623547/39163155-30da771a-47b4-11e8-9875-112ff26e69a2.jpg)
![pollrtv](https://user-images.githubusercontent.com/26623547/39163157-3217d6ae-47b4-11e8-8542-d80d14dbf13d.jpg)

## Lab 5
 ** SMTP **
 - SMTP 프로토콜은 Simple Mail Transfer Protocol의 약어로 인터넷상에서 이메일을 전송하기 위해서 사용되는 통신 규약 중에 하나
 - 이메일을 송수신하는 서버를 SMTP 서버
 - SMPT 서버를 구축하기 위해서는 물리적인 서버를 구축하여 서버를 설치하고 네트워크 환경을 잡아줘야 하지만 네이버와 구글에서 계정에 대한 SMTP를 제공 
 - 메일을 연동하기 위해선 우선 해당 메일에 접속해서 POP3/IMAP 설정을 해주어야 한다.
 - POP3과 IMAP 은 통신 규약 중 하나로 가장 큰 차이점은 동기화
 - POP3 : 새로운 메일을 가져올 때 이외에는 서버상과 그 어떤 통신도 하지 않는다. 즉, 로컬(Mail앱)에 있는 메일을 삭제하여도
서버 (Naver)상의 메이리은 그대로 남아있는다. 
 - IMAP은 서버와 지속적으로 통신하며 동기화함. 즉, 로컬에서 이루어지는 일련의 작업이 서버에 영향을 미친다.
==> SMTP 프로토콜을 이용하여 java 프로그램 작성 후 이메일 보내기

## lab 6 - SSH 프로토콜기반 LifeCycle서비스
1) 현재 LifeCycle프로그램(lab6-ssh)에서 웹서버프로그램(lab6-website)이 자체수집한 "페이지접속횟수"를 읽어오는 코드가 빠져있다.
   2초에 한번씩 읽어와 콘솔창에 출력하는 코드를 작성
2) 접속횟수가 4회 이상인 경우 최대 3개까지 클론프로세스(lab3-0.0.1-SNAPSHOT.jar)를 원격 실행하는 코드를 작성하라.
3) 20초동안 연속적으로 접속횟수가 1 이하인 경우 클론프로세스를 한개 줄이도록 코드를 작성하라.
4) 서버관리자의 분석을 통하여, 외부에서의 "audio.mp3" 요청이 타 페이지 요청에 비하여 훨씬 많은 리소스를 사용하는 사실이 밝혀져
   "audio.mp3" 요청횟수를 새로운 측정지표(VisitCount2)로 사용하기로 했다. 웹서버프로그램(lab6-website)에서는 이를 구현하고
   LifeCycle프로그램(lab6-ssh)에서는 측정지표(VisitCount2)를 읽어와 사용하도록 구현
- 참고 : 제공되는 LifeCycle프로그램은 SSH-exec기능을 구현한 자바라이브러리 (Jsch라이브러리,http://www.leafcats.com/177)를 적용하여          MyThread run()메소드 안에 구현하고 있다.

## lab 7 - Chatting on Spring-Boot & STOMP-WebSocket
==> 기존 존재하는 오픈소스 채팅프로그램을 이용하여 다수의 채팅방을 생성할 수 있는 기능을 추가하고, 각 채팅방의 메시지를 공유 및 외부에 노출이 되지 않도록 구현한다.

## lab 8 - Collaboration on Spring-boot & STOMP-WebSocket
==> 채팅프로그램 구현과 그림판을 동시에 사용할수 있는 프로그램 구현
