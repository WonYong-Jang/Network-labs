package com.cclab.web.server.domain;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member { // inmemory database // test용이기 때문에 메인 메모리에서 사용

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // id 이면서 primary data 이기때문에 auto 로 자동생성해서 현재 가지고있는 최대값 +1로 
    private Long id;
    private String name;
    private double weight;
    private String rrid; //주민번호
    private String gender;

    public Member(String name, double weight, String rrid)
            throws ParseException {
        this.name = name;
        this.weight = weight;
        this.rrid = rrid;
        char ch = rrid.charAt(7);
        this.gender = (ch == '1' || ch == '3')  ? "남자" : "여자";
    }

    public Member(Member m) {
    	this.id = m.id;
        this.name = m.name;
        this.weight = m.weight;
        this.rrid = m.rrid;
        this.gender = m.gender;
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        StringBuilder value = new StringBuilder("MapEntry(");
        value.append("Id: ");
        value.append(id);
        value.append(", 이름: ");
        value.append(name);
        value.append(", 몸무게: ");
        value.append(weight);
        value.append(", 주민번호: ");
        value.append(rrid);
        if(gender != null) {
        	value.append(", 성별: ");
        	value.append(gender);
        }
        value.append(")");
        return value.toString();
    }
}
