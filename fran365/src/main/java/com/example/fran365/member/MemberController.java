package com.example.fran365.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/create")
	public String create() {

		return "member/create";
	}
	
	@PostMapping("/create")
	public String create(Member member) {
		
		memberService.create(member);
		
		return "redirect:/";
	}
	
}
