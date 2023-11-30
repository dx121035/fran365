package com.example.fran365.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RequestMapping("/member")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;


    /*@Autowired
    private SmsService smsService;*/


	@Autowired
	private MemberRepository memberRepository;


	@GetMapping("/create")
	public String create(MemberForm memberForm) {
		return "member/create";
	}

	@PostMapping("/create")
	public String create(@Valid MemberForm memberForm, BindingResult result, Member member,
						 @RequestParam("file") MultipartFile file) throws IOException {

		// 비밀번호와 비밀번호 확인 값이 일치하는지 검사
		if (!memberForm.getPassword().equals(memberForm.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "password.mismatch", "비밀번호가 일치하지 않습니다.");
			return "member/create";
		}


		try {
			memberService.create(member,file);
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			result.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "member/create";
		}catch(Exception e) {
			e.printStackTrace();
			result.reject("signupFailed", e.getMessage());
			return "member/create";
		}

//		// 나머지 로직은 유효성 검사를 통과한 경우에만 실행
//		memberService.create(member, file);

		return "redirect:/";
	}


//비번 2개 일치 여부 확인 후 돌려보내기...

	@PostMapping("/signup")
	public String signup(@Valid MemberForm MemberForm, BindingResult bindingResult,Member member,MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			return "member/create";
		}

		if (!MemberForm.getPassword().equals(MemberForm.getConfirmPassword())) {
			bindingResult.rejectValue("ConfirmPassword", "passwordInCorrect",
					"2개의 패스워드가 일치하지 않습니다.");
			return "member/create";
		}




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
	
	
	



	



	

	@Value("${aws.s3.awspath}")
	private String awspath;
	@GetMapping("/readDetail")
	public String readDetail(Model model) {
		model.addAttribute("member", memberService.readDetailUsername());
		model.addAttribute("awspath", awspath);

		return "member/readDetail";
	}

//	@GetMapping("/update")
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
	public String phoneCheck1(String phone) throws NoSuchAlgorithmException, IOException { // 휴대폰 문자보내기
		int randomNumber = (int) ((Math.random() * (9999 - 1000 + 1)) + 1000);//난수 생성
		System.out.println(phone);
		System.out.println(randomNumber);
		String subject = "인증 번호는 " + randomNumber + "입니다.";
		//smsService.sendSms(subject,phone);


		return Integer.toString(randomNumber);
	}









	// ... 다른 메서드들 생략 ...

	@ResponseBody
	@GetMapping("/checkUsername")
	public ResponseEntity<String> checkUsername(@RequestParam String username) {
		Optional<Member> existingMember = memberRepository.findByUsername(username);
		if (existingMember.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디가 이미 사용 중입니다.");
		} else {
			return ResponseEntity.ok("사용 가능한 아이디입니다.");
		}
	}
}

