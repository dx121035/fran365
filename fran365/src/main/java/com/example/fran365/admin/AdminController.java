package com.example.fran365.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fran365.event.EventService;


@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/main")
	public String adminMain(Model model) {
		
		model.addAttribute("events", eventService.readList());
		
		return "admin/main";
	}
	
	@GetMapping("/memberReadList")
	public String memberReadList(Model model) {
		
		model.addAttribute("members", adminService.memberReadList());
		
		return "admin/memberReadList";
	}

}
