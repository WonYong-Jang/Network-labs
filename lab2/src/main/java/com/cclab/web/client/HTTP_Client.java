package com.cclab.web.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket; // 꼭있어야함
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HTTP_Client {

	private static final Logger logger = LoggerFactory.getLogger(HTTP_Client.class);

	public static void main(String[] args) {

		logger.info("hello");
		System.out.println("hello");

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

			System.out.println("\n======================================");
			System.out.println("2. test for HTTP GET hello.js home page");
			System.out.println("======================================");

			out.println("GET /hello.js HTTP/1.1\r\n");
			out.println("\r\n");
			out.flush();

			System.out.println("\n(Request)");
			System.out.println("GET /hello.js HTTP/1.1");
			System.out.flush();

			System.out.println("\n(Reply)");

			for (;;) { // 세번째
				String str = in.readLine();

				// HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것
				if (str.contains(contentlen)) {
					String tempStr = str.substring(16);
					int resultCnt = Integer.parseInt(tempStr);
					cnt = resultCnt;
				}
				
				if (str.isEmpty()) {
					System.out.println();
					break;
				} else {
					System.out.println(str);
				}
			}

			cnt2 = 0;
			for (;;) {
				char[] b = new char[cnt - cnt2];
				int cx = in.read(b, 0, cnt - cnt2);

				// HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것
				
				String str = new String(b);
				cnt2 = b.length;
				System.out.println("bytes=" + cnt2 + "(" + cnt + ") " + str);
				if (cnt2 >= cnt) {
					break;
				}
			}

			System.out.println("\n======================================");
			System.out.println("3. test for HTTP GET hong.html home page");
			System.out.println("======================================");

			out.println("GET /hong.html HTTP/1.1\r\n");
			out.println("\r\n");
			out.flush();

			System.out.println("\n(Request)");
			System.out.println("GET /hong.html HTTP/1.1");
			System.out.flush();

			System.out.println("\n(Reply)");

			for (;;) {
				String str = in.readLine();

				// HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것
				if (str.contains(contentlen)) {
					String tempStr = str.substring(16);
					int resultCnt = Integer.parseInt(tempStr);
					cnt = resultCnt;
				}
				
				if (str.isEmpty()) {
					System.out.println();
					break;
				} else {
					System.out.println(str);
				}
			}

			cnt2 = 0;

			for (;;) {
				char[] b = new char[cnt - cnt2];
				int cx = in.read(b, 0, cnt - cnt2);
				// HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것
				String str = new String(b);
				cnt2 = b.length;
				System.out.println("bytes=" + cnt2 + "(" + cnt + ") " + str);
				if (cnt2 >= cnt) {
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}

}
