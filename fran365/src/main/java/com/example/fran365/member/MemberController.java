package com.example.fran365.member;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/member")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

    /*@Autowired
    private SmsService smsService;*/



	@GetMapping("/create")
	public String create() {
		return "member/create";
	}

	@PostMapping("/create")
	public String create(Member member, @RequestParam("filename") MultipartFile file) throws IOException {

		memberService.create(member, file);

		String to = member.getName();
		String phone = member.getPhone();

		String subject = to + "님의 회원가입을 환영합니다.";

		/*smsService.sendSms(subject,phone);*/

		return "redirect:/";
	}

	@GetMapping("/readList")
	public String readList(Model model) {
		model.addAttribute("members", memberService.readList());

		return "member/readlist";
	}

	@GetMapping("/readDetail")
	public String readdetail(Model model) {

		Member member = memberService.readDetailUsername();

		model.addAttribute("member", member);

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

	@PostMapping("/phoneCheck")
	@ResponseBody
	public String phoneCheck(String phone) throws NoSuchAlgorithmException, IOException { // 휴대폰 문자보내기
		int randomNumber = (int) ((Math.random() * (9999 - 1000 + 1)) + 1000);//난수 생성
		System.out.println(phone);
		System.out.println(randomNumber);
		String subject = "인증 번호는 " + randomNumber + "입니다.";
		//smsService.sendSms(subject,phone);

		return Integer.toString(randomNumber);
	}

	@GetMapping("/findId")
	public String findId() {
		return "member/findId";  // Make sure this matches the template file name
	}

	@ResponseBody
	@PostMapping(value = {"/findId"}, produces = {"application/json;charset=UTF-8"})
	public Map<String, String> submitFindId(@RequestParam String name, @RequestParam String phone) {
		Map<String, String> response = new HashMap<>();

		Optional<Member> member = this.memberService.findIdUser(name, phone);

		if (member.isPresent()) {
			response.put("message", "찾으시는 아이디는 <span style=\"color:green\">" + member.get().getUsername()+"</span>입니다.</p>");
		} else {
			response.put("error", "아이디가 존재하지 않습니다<br> 이름과전화번호를 확인해주세요.");
		}

		return response;
	}

	@GetMapping("/sendEmail")
	public String sendEmail() {
		return "member/sendEmail";
	}
	@Transactional
	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestParam("username") String username) {
		try {
			memberService.sendTemporaryPassword(username);
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			// Log the exception for debugging purposes
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		}
	}
}
