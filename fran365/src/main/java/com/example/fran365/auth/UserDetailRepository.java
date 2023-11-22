package com.example.fran365.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fran365.member.Member;

public interface UserDetailRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByusername(String username);
}
