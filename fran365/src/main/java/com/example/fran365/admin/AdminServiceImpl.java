package com.example.fran365.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public List<Member> memberReadList() {
		
		return memberRepository.findAll();
	}

	
}
