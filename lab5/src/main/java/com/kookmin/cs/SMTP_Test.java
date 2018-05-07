package com.kookmin.cs;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.RequestWrapper;

/**
 * Hello world!
 *
 */
public class SMTP_Test
{
    public static void main( String[] args )
    {
    	mailSendFunction();
    }

    public static void mailSendFunction() {
    	
    	// 구글인 경우 smtp.gmail.com // 네이버일 경우 smtp.naver.com
    	String host = "smtp.naver.com";
  
    	final String user = "geikil@naver.com"; // id
    	final String password = ""; // password
    	int port = 587; // 포트번호 
    	
    	// SMTP 서버 정보를 설정한다.
    	Properties props = new Properties();
    	props.put("mail.smtp.host", host);
    	props.put("mail.smtp.port", 587);
    	props.put("mail.smtp.auth", "true");
    	
    	Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
    		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
    			return new javax.mail.PasswordAuthentication(user, password);
    		}
    	});
    	
    	try {
    		MimeMessage message = new MimeMessage(session);
    		message.setFrom(new InternetAddress(user)); // sender set
    		message.addRecipient(Message.RecipientType.TO, new InternetAddress("object1602@gmail.com")); // receiver set
    		
    		// 메일 제목 
    		message.setSubject("network lab5 20125164 장원용 ");
    		
    		// 메일 내용 
    		message.setText("networl lab5 success");
    		
    		// send the message 
    		Transport.send(message);
    		
    		System.out.println("Success the message");
    	} catch(MessagingException e) {
    		e.printStackTrace();
    	}
    }
}
