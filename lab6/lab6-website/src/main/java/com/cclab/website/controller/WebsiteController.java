package com.cclab.website.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Controller
public class WebsiteController {
    
    @RequestMapping("/")
    public String index(Model model) {
        return "index.html";
    }
    
    @RequestMapping("/hong.html")
    public String index4(Model model) {
    	return "redirect://www.naver.com/index.html";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/metric", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Map<String, Integer> getMetric(HttpServletRequest request) {
    	Map<String, Integer> m = new HashMap<String, Integer>();
    	m.put("VisitCount", (Integer) request.getAttribute("VisitCount")); //방문 기록을 알려주는 컨트롤러 (누적된 카운틀를 받아서 리턴) 
    	
    	// (4) 구현 위치 #(2/2)    	
    	m.put("VisitCount2",(Integer) request.getAttribute("VisitCount2"));
    	
        return m;
    }

}
