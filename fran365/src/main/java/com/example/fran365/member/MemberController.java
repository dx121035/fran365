package com.example.fran365.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	
	@GetMapping("/create")
	public String create() {

		return "member/create";
	}

	@PostMapping("/create")
	public String create(Member member, MultipartFile file) throws IOException {

		memberService.create(member, file);

		return "redirect:/";
	}


	@ResponseBody
	@PostMapping("/tel")
	public int tel(String tel) throws NoSuchAlgorithmException, IOException {

		int number = (int)(Math.random() * (90000)) + 100000;
		memberService.sendSms(tel, number);

		return number;
	}




	@GetMapping("/readDetail")
	public String readDetail(Model model) {

		model.addAttribute("member", memberService.readDetailUsername());

		return "member/readDetail";
	}




	@GetMapping("/update")
	public String update(Model model) {

		model.addAttribute("member", memberService.readDetailUsername());

		return "member/update";
	}



	@PostMapping("/update")
	public String update(Model model, Member member, MultipartFile file) throws IOException {

		memberService.update(member, file);
		model.addAttribute("member", memberService.readDetailUsername());

		return "member/readDetail";
	}




	@GetMapping("/readList")
	public String readList(Model model) {

		model.addAttribute("lists", memberService.readlist());

		return "member/readList";
	}
}
