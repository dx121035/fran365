package com.example.fran365.event;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public void create(Event event) {

		eventRepository.save(event);
	}

	@Override
	public List<Event> readList() {

		return eventRepository.findAll();
	}

	@Override
	public void delete(Integer id) {

		eventRepository.deleteById(id);
	}

	@Override
	public void update(Map<String, Object> eventChangeArg) {

		Map<String, Object> eventMap = (Map<String, Object>) eventChangeArg.get("event");
		
		String start = (String) eventMap.get("start");
        String idString = (String) eventMap.get("id");
        Integer id = Integer.parseInt(idString);

        Optional<Event> ou = eventRepository.findById(id);
        Event event = ou.get();
        event.setStartDate(start);
        
        eventRepository.save(event);
	}

}
