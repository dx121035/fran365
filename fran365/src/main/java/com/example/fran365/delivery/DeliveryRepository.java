package com.example.fran365.delivery;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
	
	List<Delivery> findByUsername(String username);

    List<Delivery> findByUsername(String username, Sort sort);
}
