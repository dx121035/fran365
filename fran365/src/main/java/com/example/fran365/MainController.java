package com.example.fran365;

import com.example.fran365.auth.UserDetailService;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MainController {
	@Value("${aws.s3.awspath}")
	private String awspath;

	@Autowired
	private MemberService memberService;

	@Autowired
	private UserDetailService userDetailService;
	@GetMapping("/")
	public String index(Model model, Principal principal) {

		if (principal != null) {

			model.addAttribute("awspath", awspath);
			model.addAttribute("member", memberService.readDetailUsername());
			return "index";
		}
		return "login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/naverLogin")
	public String naverlogin() {
		return "naverLogin";
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
