package com.cclab.web.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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
		
	    try {
	        String host; 
	        host = "localhost"; 

	        String fileEncoding=System.getProperty("file.encoding");
	        System.out.println("file.encoding = "+fileEncoding);      
	        
	        String contentlen = new String("Content-Length:");
	        
	        System.out.println("\n=================================================");
	        System.out.println("1. test for HTTP GET default(index.html) home page");
	        System.out.println("=================================================");
	        
	        Socket t = new Socket(host, 8080); 
	        BufferedReader in = new BufferedReader(new InputStreamReader(t.getInputStream())); 
	        PrintWriter out = new PrintWriter(new OutputStreamWriter(t.getOutputStream())); 

	    	out.println("(HTTP명령문 넣을 것)\r\n"); 
	    	out.println("\r\n"); 
	    	out.flush();

	        System.out.println("\n(Request)");
	    	System.out.println("(HTTP명령문 넣을 것)"); 
	    	System.out.flush();

	    	int cnt = 0;
	        int cnt2 = 0;
	        System.out.println("\n(Reply)");
	      
	        for (;;) {
	          String str = in.readLine(); 


	          // HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것


	          if (str.isEmpty()) {
	          	System.out.println();
	            break; 
	          } else {
	            System.out.println(str); 
	          }
	        }
	       
	        for (;;) {
	       	  char[] b = new char[cnt-cnt2];
	       	  int cx = in.read(b, 0, cnt-cnt2);


	            // HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것


	        	  String str = new String(b);
	           System.out.println("bytes=" + cnt2 + "(" + cnt + ") " + str); 
	           if (cnt2 >= cnt) {
	             break; 
	           }
	         }
	       
	       
	       
	        System.out.println("\n======================================");
	        System.out.println("2. test for HTTP GET hello.js home page");
	        System.out.println("======================================");
	       
	   	    out.println("(HTTP명령문 넣을 것)\r\n"); 
	   	    out.println("\r\n"); 
	   	    out.flush();

	        System.out.println("\n(Request)");
	   	    System.out.println("(HTTP명령문 넣을 것)"); 
	   	    System.out.flush();

	        System.out.println("\n(Reply)");
	      
	        for (;;) {
	         String str = in.readLine(); 


	         // HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것


	         if (str.isEmpty()) {
	         	System.out.println();
	           break; 
	         } else {
	           System.out.println(str); 
	         }
	        }
	        
	        cnt2 = 0;
	        for (;;) {
	       	  char[] b = new char[cnt-cnt2];
	       	  int cx = in.read(b, 0, cnt-cnt2);


	            // HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것


	          String str = new String(b);
	          System.out.println("bytes=" + cnt2 + "(" + cnt + ") " + str); 
	          if (cnt2 >= cnt) {
	            break; 
	          }
	        }
	      
	      
	        System.out.println("\n======================================");
	        System.out.println("3. test for HTTP GET hong.html home page");
	        System.out.println("======================================");
	      
	  	    out.println("(HTTP명령문 넣을 것)\r\n"); 
	  	    out.println("\r\n"); 
	  	    out.flush();

	        System.out.println("\n(Request)");
	  	    System.out.println("(HTTP명령문 넣을 것)"); 
	  	    System.out.flush();

	        System.out.println("\n(Reply)");
	    
	        for (;;) {
	          String str = in.readLine(); 


	          // HTTP header부분의 바이트 길이를 읽어 변수(cnt)에 저장하는 코드 추가할 것


	          if (str.isEmpty()) {
	       	    System.out.println();
	            break; 
	          } else {
	            System.out.println(str); 
	          }
	        }
	    
	        cnt2 = 0;
	    
	        for (;;) {
	       	  char[] b = new char[cnt-cnt2];
	       	  int cx = in.read(b, 0, cnt-cnt2);


	            // HTTP body부분의 바이트 길이를 읽어 변수(cnt2)에 누적저장하는 코드 추가할 것


	          String str = new String(b);
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
