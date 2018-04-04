package com.cclab.web.server.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cclab.web.server.domain.Login;
import com.cclab.web.server.domain.Member;
import com.cclab.web.server.repository.LoginRepository;
import com.cclab.web.server.repository.MemberRepository;

@Controller
public class HTTP_ServerController {
    
    @Autowired
    MemberRepository repo;
    @Autowired
    LoginRepository repoLogin;
    
    @RequestMapping("/")
    public String index(Model model) {
        return "index.html";
    }
    
    @RequestMapping("/hong.html")
    public String hong(Model model) {
        return "redirect://www.naver.com";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/member", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<Member> getMember() {
        return repo.findAll(); // 레포지터리 약자 멤버 레퍼지토리
    }


    
    
    // --------------------------------------
    // (3-1) <개별고객(검색)> 요청메시지 처리메소드 추가 위치 2번째버튼
    // --------------------------------------
    @RequestMapping(value = "/searchMember", method = RequestMethod.GET)
    public ResponseEntity<Member> searchMember(@RequestParam("id") Long id) {
    	System.out.println("searchingMemeber");
    	Optional<Member> member = repo.findById(id);
    	if (member == null) {
            return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
        }
    	Member m2 = member.get();
    	
    	return new ResponseEntity<Member>(m2, HttpStatus.OK);
    }
    
 // --------------------------------------
    // (3-1) <로그인> 
    // --------------------------------------
    @RequestMapping(value = "/searchLogin", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<Login> getLogin() {
    	
    	return repoLogin.findAll();
    }
    
    
    // --------------------------------------
    // (3-2) <신규고객(추가)> 요청메시지 처리메소드 추가 위치 3번째 버튼
    // --------------------------------------
    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public ResponseEntity<Member> getAddMember(@RequestBody Member member) {
    	System.out.println("getAddMemeber");
    	char ch =' ';
    	int flag =0;
    	
    	if(member == null) {
    		return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
    	}
    	if(member.getName() == "") 
    	{
    		member.setName("#error");
    		flag =1;
    	}
    	if( member.getWeight() == 0)
    	{
    		member.setWeight(-1.0);
    		flag =1;
    	}
    	if(member.getRrid().length()>=8) ch = member.getRrid().charAt(7); //길이 체크
        if(member.getRrid().length()<8 || ch<'1' || ch>'4') 
        { // 앞자리 1,2,3,4 만 허용
            member.setRrid("#error");  // 그 필드에 error 체크하기 위해서
            flag=1;
        }
        System.out.println("//////////1221"+member.getRrid());
    	member.setGender((ch == '1' || ch == '3')  ? "남자" : "여자");
    	
    	if(flag == 1) return new ResponseEntity<Member>(member, HttpStatus.NOT_ACCEPTABLE);
    	
    	repo.save(member);
    	return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    
    // 변경
    @RequestMapping(value = "/member/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody Member member) { // 
    	char ch = ' ';
    	Optional<Member> m = repo.findById(id);
        if (m == null) {
            return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
        }

    	Member m2 = m.get();
    	if(member.getName()!="") m2.setName(member.getName());
    	if(member.getWeight()!=0) m2.setWeight(member.getWeight());
    	if(member.getRrid()!="") {
    		m2.setRrid(member.getRrid());
    		if(member.getRrid().length()>=8) ch = member.getRrid().charAt(7); //길이 체크
            if(member.getRrid().length()<8 || ch<'1' || ch>'4') { // 앞자리 1,2,3,4 만 허용
            	m2.setRrid("#error");  // 그 필드에 error 체크하기 위해서
            	return new ResponseEntity<Member>(m2, HttpStatus.NOT_ACCEPTABLE);
            }
    		m2.setGender((ch == '1' || ch == '3')  ? "남자" : "여자");
    	}

        repo.save(m2);
        return new ResponseEntity<Member>(m2, HttpStatus.OK); // http 상태 200번으로  객체가 제이슨포맷으로 변환되서 200번과 함께 리턴 
        // 멤버클래스 포맷을 참고해서 m2를 변환
    }
    
    // 로그인변경
    @RequestMapping(value = "/login/{loginId}", method = RequestMethod.PUT)
    public ResponseEntity<Login> updateLogin(@PathVariable Long loginId, @RequestBody Login login) { // 
    	
    	Optional<Login> m = repoLogin.findById(loginId);
        if (m == null) {
            return new ResponseEntity<Login>(HttpStatus.NOT_FOUND);
        }

    	Login m2 = m.get();
    	
    	m2.setAccount(login.getAccount());
    	m2.setEmail(login.getEmail());
    	m2.setLevel(login.getLevel());
    	
        repoLogin.save(m2);
        return new ResponseEntity<Login>(m2, HttpStatus.OK); // http 상태 200번으로  객체가 제이슨포맷으로 변환되서 200번과 함께 리턴 
        // 멤버클래스 포맷을 참고해서 m2를 변환
    }
    
    
    // --------------------------------------
    // (3-3) <고객탈퇴(삭제)> 요청메시지 처리메소드 추가 위치
    // --------------------------------------
    @RequestMapping(value = "/delMember/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Long getDelMember(@PathVariable Long id) {
    	System.out.println("////id 찾음"+id);
    	
        repo.deleteById(id);
        return id;
    }
    


}
