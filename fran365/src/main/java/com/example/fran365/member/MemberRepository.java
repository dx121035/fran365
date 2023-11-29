package com.example.fran365.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByNameAndPhone(@Param("name") String paramString1, @Param("phone") String paramString2);



}
