package com.example.fran365.event;

import java.util.List;
import java.util.Map;

public interface EventService {
	
	void create(Event event);
	
	List<Event> readList();
	
	void delete(Integer id);
	
	void update(Map<String, Object> eventChangeArg);
}
