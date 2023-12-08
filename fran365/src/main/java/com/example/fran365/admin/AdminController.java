package com.example.fran365.admin;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.fran365.board.BoardService;
import com.example.fran365.event.EventService;
import com.example.fran365.member.MemberService;
import com.example.fran365.position.PositionService;


@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Value("${aws.s3.awspath}")
    private String awspath;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/main")
	public String adminMain(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("members", adminService.memberReadList());
		model.addAttribute("events", eventService.readList());
		model.addAttribute("deliveyNotComplete", adminService.getDeliveyNotComplete());
		model.addAttribute("date", LocalDate.now());
		
		return "admin/main";
	}
	
	@GetMapping("/memberReadList")
	public String memberReadList(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("members", adminService.memberReadList());
		model.addAttribute("positionMap", adminService.getPosition());
		
		return "admin/memberReadList";
	}
	
	@GetMapping("/memberReadDetail")
	public String memberReadDetail(Model model, Integer id) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("member", adminService.memberReadDeatail(id));
		model.addAttribute("positionMap", adminService.getPosition());
		
		return "admin/memberReadDetail";
	}
	
	@GetMapping("/memberDelete")
	public String memberDelete(Integer id) {
		
		adminService.memberDelete(id);
		
		return "redirect:/admin/memberReadList";
	}
	
	@GetMapping("/schedule")
	public String schedule(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("events", eventService.readList());
		
		return "admin/schedule";
	}
	
	@GetMapping("/memberApprove")
	public String memberApprove(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("members", adminService.memberApprove());
		model.addAttribute("positions", positionService.readList());
		
		return "admin/memberApprove";
	}
	
	@ResponseBody
	@PostMapping("/memberApprove")
	public int memberApprove(Integer id, Integer number) {
		
		adminService.memberApprove(id, number);
		
		return 1;
	}
	
	@GetMapping("/memberPosition")
	public String memberPosition(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
        model.addAttribute("positions", positionService.readList());
		
		return "admin/memberPosition";
	}
	
	@ResponseBody
	@PostMapping("/updatePosition")
	public int updatePosition(String username, Integer newPosition) {
		
		adminService.updatePosition(username, newPosition);
		
		return 1;
	}
	
	@GetMapping("/noticeReadList")
	public String noticeReadList(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("notices", adminService.noticeReadList());
		
		return "admin/noticeReadList";
	}
	
	@GetMapping("/noticeReadDetail")
	public String noticeReadDetail(Model model, Integer id) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("notice", adminService.boardReadDetail(id));
		
		return "admin/noticeReadDetail";
	}
	
	@GetMapping("/noticeCreate")
	public String noticeCreate(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		
		return "admin/noticeCreate";
	}
	
	@GetMapping("/noticeUpdate")
	public String noticeUpdate(Model model, Integer id) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("notice", boardService.readDetail(id));
		
		return "admin/noticeUpdate";
	}
	
	@GetMapping("/questionReadList")
	public String questionReadList(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("questions", adminService.questionReadList());
		
		return "admin/questionReadList";
	}
	
	@GetMapping("/questionReadDetail")
	public String questionReadDetail(Model model, Integer id) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("question", adminService.boardReadDetail(id));
		
		return "admin/questionReadDetail";
	}
	
	@GetMapping("/faqReadList")
	public String faqReadList(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("faqs", adminService.faqReadList());
		
		return "admin/faqReadList";
	}
	
	@GetMapping("/faqCreate")
	public String faqCreate(Model model) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		
		return "admin/faqCreate";
	}
	
	@GetMapping("/faqReadDetail")
	public String faqReadDetail(Model model, Integer id) {
		
		model.addAttribute("awspath", awspath);
		model.addAttribute("user", memberService.readDetailUsername());
		model.addAttribute("faq", adminService.boardReadDetail(id));
		
		return "admin/faqReadDetail";
	}

}
