package com.example.fran365.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/position")
@Controller
public class PositionController {
	
	@Autowired
	private PositionService positionService;
	
	@PostMapping("/create")
	public String create(String position, Integer number) {
		
		positionService.create(position, number);
		
		return "redirect:/admin/memberPosition";
	}
	
	@ResponseBody
	@PostMapping("/delete")
	public int delete(Integer id) {
		
		positionService.delete(id);
		
		return 1;
	}

}
