package com.example.fran365.resource;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    Page<Resource> findAll(Pageable pageable);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Resource findWithPessimisticLockById(Integer id);
}
