package com.example.fran365.event;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/event")
@Controller
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping("/create")
	public String create(Event event) {
		
		eventService.create(event);
		
		return "redirect:/admin/schedule";
	}
	
	@GetMapping("/delete")
	public String delete(Integer id) {
		
		eventService.delete(id);
		
		return "redirect:/admin/schedule";
	}
	
	@ResponseBody
	@PostMapping("/update")
	public void update(@RequestBody Map<String, Object> eventChangeArg) {
		
		eventService.update(eventChangeArg);
	}

}
