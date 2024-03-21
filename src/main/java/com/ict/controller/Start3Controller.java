package com.ict.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// 상속받지 않아서 메서드를 만들어야지 실행 가능
@Controller
public class Start3Controller {
	
	// 요청이 오면 실행 해야 된다.
	
	// 옛날 방식
	/*
	@RequestMapping(value = "start3.do", method = RequestMethod.GET)
	@RequestMapping(value = "start3.do", method = RequestMethod.POST)
	@RequestMapping("start3.do")
	
	// 요즘 방식 => 둘중 하나 사용하면 됨.
	@GetMapping("start3.do")
	@PostMapping("start3.do")
	*/
	
	// a링크는 GetMapping 사용
	@GetMapping("start3.do")
	public ModelAndView exec(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("result3");
		mv.addObject("city", "서울");
		return mv;
	}
}


