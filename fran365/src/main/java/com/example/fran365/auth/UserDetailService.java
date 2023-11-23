package com.example.fran365.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.fran365.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public Member loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("서비스에서 확인: " + username);
		
	Optional<Member> tmember = userDetailRepository.findByusername(username);
	Member member = tmember.get();
	
		if(member == null) {
			throw new UsernameNotFoundException("username" + username + " not found");
		}
	
	return member;
	}
	
	
	
	@Autowired
	private HttpServletRequest req;
	
	public int logincheck(String username) {
		
		Optional<Member> member = userDetailRepository.findByusername(username);
		
	
		if (member.isPresent()) {
			// 디비에 존재하는 회원 이기 때문에 스프링 시큐리티 규격에 맞게 세션 처리
			
			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			SecurityContext sc = SecurityContextHolder.getContext();
			
			sc.setAuthentication(new UsernamePasswordAuthenticationToken(member, null, list));
			
			
			HttpSession session = req.getSession(true);
			
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,sc);
		
			return 1;
			
		} else {
			
			return 0;
		}
	
	
	}
	
}
