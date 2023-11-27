package com.example.fran365.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialRepository extends JpaRepository<Social,Integer> {

    List<Social> findAll(String username);
}
