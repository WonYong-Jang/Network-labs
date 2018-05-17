package com.cclab.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * 방문 수를 확인하고 모니터링 하면서 동적으로 관리 하도록 구현 audio.mp3 를 실행 시키고 계속 새로고침하면 자동으로 8090 서버를
 * 만들도록 계속해서 한다면 8091 8092 .....늘려가고 방문객이 더이상 올라가지 않을때는 서버를 자동으로 줄여 가도록 bitwise
 * SSH Server Control Panel 에서 포트번호 확인
 */
@SpringBootApplication
public class LabSshApplication {

	@Autowired
	private static RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(LabSshApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RestTemplate restTemplate) {
		return (args) -> {

			ArrayList<MyThread> threads = new ArrayList<MyThread>();
			int port = 8090;
			int cnt = 0; // max 3 count clone process 
			int acc = 0; // to calculate time
			int vc; // visitCout
			
			ArrayList<MyThread> threads2 = new ArrayList<MyThread>();
			int port2 = 8095;
			int cnt2 =0;
			int acc2 =0;
			int vc2; // visitCount2
			while (true) {

				try {
					// getForObject ==> 기본 http header를 사용하며 결과를 객체를 반환 
					@SuppressWarnings("unchecked")
					Map<String, Integer> metric = restTemplate.getForObject("http://" + "localhost:8080" + "/metric", Map.class);
					
					System.out.println("---------------------------------------------------------------------------------");
					vc = metric.get("VisitCount");
					vc2 = metric.get("VisitCount2");
					System.out.println("VisitCount: " + vc);
					System.out.println("current threads size : "+ threads.size());
					if(threads.size() != 0) 
					{
						for(MyThread mythread : threads) 
						{
							System.out.println("current thread : " + mythread.command);
						}
					}
					System.out.println();
					System.out.println("VisitCount2: "+ vc2);
					System.out.println("current threads2 size : "+ threads2.size());
					if(threads2.size() != 0) 
					{
						for(MyThread mythread : threads2) 
						{
							System.out.println("current thread2 (audio.mp3) : " + mythread.command);
						}
					}
					System.out.println("---------------------------------------------------------------------------------");
					// 
					// x 변수를 thread 에 넣
					 if( vc >=4 && cnt <= 2) { // create clone process
						 System.out.println("New Web Server starts at port="+(port+cnt));
						 String x ="java -jar /Users/jang-won-yong/dev/workspace/lab6-ssh/lab3-0.0.1-SNAPSHOT.jar --server.port="+ (port+cnt);
						 MyThread th1 = new MyThread("a",x);
						 th1.start();
						 threads.add(th1);
						 cnt++;
						 
						 Thread.sleep(4000);
					 }
					 else {
						 if(vc <= 1) acc++;
						 else acc =0;
						 
						 if(acc >= 10) {
							 acc =0;
							 if(threads.size()>0) {
								 System.out.println("Web Server Service stops at port=" + (port+threads.size()-1));
								 MyThread th = threads.remove(threads.size()-1);
								 th.stop();
								 cnt--;
								 Thread.sleep(4000);
							 }
						 }
					 }
					 
					 ///////////////////////////////////visitCount2 audio.mp3////////////////////////////////
					 if(vc2 >=4 && cnt2 <=2 ) {
						 System.out.println("New Web Server starts at port="+(port2+cnt2));
						 String x ="java -jar /Users/jang-won-yong/dev/workspace/lab6-ssh/lab3-0.0.1-SNAPSHOT.jar --server.port="+ (port2+cnt2);
						 MyThread th2 = new MyThread("b",x);
						 th2.start();
						 threads2.add(th2);
						 cnt2++;
						 
						 Thread.sleep(4000);
					 }
					 else {
						 if(vc2 <= 1) acc2++;
						 else acc2 =0;
						 
						 if(acc2 >= 10) {
							 acc2 =0;
							 if(threads2.size() >0) {
								 System.out.println("Web Server Service stops at port=" + (port2+threads2.size()-1));
								 MyThread th = threads2.remove(threads2.size()-1);
								 th.stop();
								 cnt2--;
								 Thread.sleep(4000);
							 }
						 }
					 }
					 	/*
						// (1) 구현 위치. 다음 문장을 수정함
						metric.put("VisitCount", -1);
						// (1) end

						vc = metric.get("VisitCount");
						System.out.println("VisitCount: " + vc);

						// (2), (3) 구현 위치
						*/
						Thread.sleep(2000);
						
				} catch (InterruptedException e) {
				}

			}

		};

	}

}

class MyThread implements Runnable {

	boolean suspended = false;

	boolean stopped = false;

	String command;

	Thread th;
	ChannelExec channelExec;
	Channel channel = null;
	Session session = null;

	MyThread(String name, String command) {
		this.command = command;

		th = new Thread(this, name); // Thread(Runnable r, String name)

	}

	public void run() {

		String username = "";
		String host = "localhost"; // localhost
		int port = 22; // (SSH서버포트번호) 22
		String password = "";
		
		// 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.) 세션를 확인해준다!!!!!!!!1
		try {
			// 1. JSch 객체를 생성한다.
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);

			// 3. 패스워드를 설정한다.
			session.setPassword(password);

			// 4. 세션과 관련된 정보를 설정한다.
			java.util.Properties config = new java.util.Properties();
			// 4-1. 호스트 정보를 검사하지 않는다.
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// 5. 접속한다.
			session.connect();

			// 6. exec 채널을 연다.
			channel = session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

			// 8. SSH/Exec채널객체로 캐스팅한다
			channelExec = (ChannelExec) channel;

			System.out.println("==> Connecting to" + host);

			channelExec.setCommand(command);
			channelExec.connect();
			String msg = null;
            //System.out.println("channelexe.getErrStream:"+channelExec.getErrStream());
            //System.out.println("channelexe.getExitStatus:"+channelExec.getExitStatus());
			while ((msg = in.readLine()) != null) {
				System.out.println(msg);
			}

			System.out.println("==> Connected to" + host + "; (command)= " + command);

			while (true) {
			}

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}

	}

	public void suspend() {

		suspended = true;

		th.interrupt();

		// interrupt(): 특정 객체를 멈추고자 할때 사용, 쓰레드의 권한 중지

		System.out.println("interrupt() in suspend()");

	}

	public void resume() {

		suspended = false;

	}

	public void stop() throws Exception {

		stopped = true;
		
		channelExec.sendSignal("KILL");
		channel.sendSignal("KILL");
		System.out.println("////"+channelExec.getId()+" "+session.toString());
		channel.disconnect();
		session.disconnect();
		
		//th.interrupt();
		//th.stop();

		System.out.println("interrupt() in stop()");

	}

	public void start() {

		th.start();

	}

}

@Configuration
class AppConfiguration {

	// @LoadBalance
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
