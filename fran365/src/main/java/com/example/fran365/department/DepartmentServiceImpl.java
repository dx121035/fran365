package com.example.fran365.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public void create(String department) {
		
		Department department1 = new Department();
		department1.setDepartment(department);
		
		departmentRepository.save(department1);
	}

	@Override
	public List<Department> readList() {

		return departmentRepository.findAll();
	}

	@Override
	public void delete(Integer id) {

		departmentRepository.deleteById(id);
	}

}
