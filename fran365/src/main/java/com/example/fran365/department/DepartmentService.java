package com.example.fran365.department;

import java.util.List;

public interface DepartmentService {
	
	void create(String department);
	
	List<Department> readList();
	
	void delete(Integer id);
	
}
