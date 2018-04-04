package com.cclab.web.server.domain;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Login {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) // id 이면서 primary data 이기때문에 auto 로 자동생성해서 현재 가지고있는 최대값 +1로 
    private Long id;
	private String account;
	private String password;
	private String email;
	private int level;
	public Login() {
		
	}
	public Login(String account,String password,String email,int level) throws ParseException{
		this.account= account;
		this.password = password;
		this.email = email;
		this.level = level;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String toString() {
        StringBuilder value = new StringBuilder("MapEntry(");
        value.append("Id: ");
        value.append(id);
        value.append(", 이름: ");
      
        value.append(")");
        return value.toString();
    }
}
