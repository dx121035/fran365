package com.example.fran365.status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fran365.delivery.Delivery;

public interface StatusRepository extends JpaRepository<Status,Integer> {
	
	@Query("SELECT d FROM Delivery d WHERE NOT EXISTS (SELECT 1 FROM Status s WHERE s.delivery = d AND s.step = '배송완료')")
    List<Delivery> findByStepNot();
}
