package com.javalec.spring_pjt_board;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBConnection connection = new DBConnection();
		System.out.println("관리자 여부 : "+ connection.isAdmin("admin", "admin"));
	}

}
