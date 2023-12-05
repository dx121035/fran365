package com.example.fran365.sales;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

    List<Sales>findByBrand_id(int brand_id);

    List<Sales> findByBrand_IdOrderByIdDesc(int brandId);

}
