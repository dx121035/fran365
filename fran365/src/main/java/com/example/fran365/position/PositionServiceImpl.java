package com.example.fran365.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionRepository positionRepository;

	@Override
	public void create(String position, Integer number) {
		
		Position position1 = new Position();
		position1.setPosition(position);
		position1.setNumber(number);
		
		positionRepository.save(position1);
	}

	@Override
	public List<Position> readList() {

		return positionRepository.findAll();
	}

	@Override
	public void delete(Integer id) {

		positionRepository.deleteById(id);
	}

}
