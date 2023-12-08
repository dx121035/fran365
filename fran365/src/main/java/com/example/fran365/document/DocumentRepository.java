package com.example.fran365.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Page<Document> findAll(Pageable pageable);

    //Document findByUsername(String username);

    List<Document> findByReceiver(String receiver);

}
