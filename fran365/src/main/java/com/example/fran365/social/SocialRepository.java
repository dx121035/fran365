package com.example.fran365.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social,Integer> {


    List<Social> findByUsername(String username);

}
