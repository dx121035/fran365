package com.example.fran365.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    List<History> findBySellerContaining(String username);

    List<History> findByPurchaserContaining(String username);
}
