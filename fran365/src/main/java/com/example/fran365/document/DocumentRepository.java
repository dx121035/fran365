package com.example.fran365.document;

import com.example.fran365.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Page<Document> findAll(Pageable pageable);
}
