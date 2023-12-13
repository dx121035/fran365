package com.example.fran365.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/department")
@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping("/create")
	public String create(String department) {
		
		departmentService.create(department);
		
		return "redirect:/admin/memberPosition";
	}
	
	@ResponseBody
	@PostMapping("/delete")
	public int delete(Integer id) {
		
		departmentService.delete(id);
		
		return 1;
	}

}
