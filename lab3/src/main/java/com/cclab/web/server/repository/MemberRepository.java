package com.cclab.web.server.repository; // JDBC 를 통해서 티비와 소프트웨어 사이 자동 연결

import org.springframework.data.jpa.repository.JpaRepository;
import com.cclab.web.server.domain.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
// findAll () 전체 리스트 달라 // 이런식으로 검색을 해보면 함수가 추상화로 내장되어 있음 // jpa 검색
	
	// 이미 완성되어있는 함수 말고도 findbyid(id) // save // setRid("#error");
	
	// redirection:/n~
}


