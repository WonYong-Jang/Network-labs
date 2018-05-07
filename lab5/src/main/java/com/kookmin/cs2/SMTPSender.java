package com.kookmin.cs2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPSender {
	public static void snedMail(String smtpServer, String sender,
			String recipient,String subject,String content)throws Exception {
		//connect Socket to SMTP server 25 port
		Socket socket = new Socket(smtpServer,25);
		
		// to read response of socket server
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// to send command to socket server
		PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
		System.out.println("success to connect the server");
		
		// confirm response code
		String line = br.readLine();
		System.out.println("response : "+line);
		if(!line.startsWith("220")) {
			throw new Exception("fail to connect the SMTP server");
		}
		
		// helo command
		System.out.println("send helo command ");
		pw.println("helo localhost"); line = br.readLine();
		System.out.println("response : "+ line);
		if(!line.startsWith("250")) throw new Exception("fail in helo command: "+ line);
		
		// mail from
		System.out.println("send Mail from command");
		pw.println("mail from:"+sender);
		line=br.readLine();
		System.out.println("response: "+line);
		if(!line.startsWith("250")) throw new Exception("fail in Mail from command: "+line);
		
		// rcpt to
		System.out.println("send rcpt command ");
		pw.println("rcpt to:"+recipient);
		line=br.readLine();
		System.out.println("response: "+line);
		if(!line.startsWith("250")) throw new Exception("fail in rcpt to command: "+line);
		
		// data
		System.out.println("send data command");
		pw.println("data");
		line=br.readLine();
		System.out.println("response: "+line);
		if(!line.startsWith("354")) throw new Exception("fail in data command :"+line);
		System.out.println("send text message");
		pw.println("From:"+sender);
		pw.println("Subject:"+subject);
		pw.println("To:"+recipient);
		pw.println(content);
		pw.println(".");
		line=br.readLine();
		System.out.println("response: "+line);
		if(!line.startsWith("250")) throw new Exception("fail to send text messages : "+line);
		
		// quit
		System.out.println("quit connection");
		pw.println("quit");
		
		pw.close();
		br.close();
		socket.close();
	}

	public static void main(String args[]) {
		try {
			SMTPSender.snedMail("localhost", "<jang-won-yong@localhost>", 
					"<object1602@gmail.com>","network lab5 20125164 wonyong jang" ,"network lab5 success");
			System.out.println("Success message send!");
		} catch(Exception e) {
			System.out.println("fail to send message");
			System.out.println(e.toString());
		}
	}
}
