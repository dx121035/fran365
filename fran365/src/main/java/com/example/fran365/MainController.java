package com.example.fran365;

import com.example.fran365.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@Autowired
	private UserDetailService userDetailService;

	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	@GetMapping("/login")
	public String login(){

		return "login";
	}

	@GetMapping("/logincheck") //카카오 로그인체크
	public String logincheck(@RequestParam String email) {
		int result = userDetailService.logincheck(email);
		if (result == 1) { //디비에 회원정보가 이미 있을 경우 로그인 성공

			return "redirect:/";
		} else {           //디비에 회원정보가 이미 없는 경우 회원 가입

			return "redirect:/member/create";
		}
	}
}
