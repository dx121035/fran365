package com.example.fran365.position;

import java.util.List;

public interface PositionService {
	
	void create(String position, Integer number);
	
	List<Position> readList();
	
	void delete(Integer id);
	
}
