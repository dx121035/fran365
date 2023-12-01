/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable pageable);
}



