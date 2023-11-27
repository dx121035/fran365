package com.example.fran365.member;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.fran365.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.fran365.cart.CartService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository userRepository;
	
	@Autowired
	private CartService cartService;
	
	//create
	@Override
	public void create(Member member) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setRole("ROLE_USER");
		member.setCreateDate(LocalDateTime.now());


		userRepository.save(member);
		
		//카트생성
		cartService.create(member);
	
	}

	
	public Member readDetailUsername() {
		
		//현재 로그인 하여 사용중인 사람의 정보 추출
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
		String username= auth.getName();
				
		Optional<Member> uc = userRepository.findByusername(username);
		
		return uc.get();
	}
}
