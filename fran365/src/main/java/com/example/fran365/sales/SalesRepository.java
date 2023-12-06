package com.example.fran365.sales;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

    List<Sales>findByBrand_id(int brand_id);

    List<Sales> findByBrand_IdOrderByIdDesc(int brandId);

    @Query("SELECT s FROM Sales s WHERE s.brand.id = :brandId ORDER BY s.id DESC")
    List<Sales> findTopByBrandIdOrderBySalesIdDesc(@Param("brandId") Integer brandId);


}
