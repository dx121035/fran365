package com.example.fran365.admin;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Member memberReadDeatail(Integer id) {
		
		Optional<Member> om = memberRepository.findById(id);
		Member member = om.get();

		return member;
	}

	@Override
	public void memberDelete(Integer id) {
		
		Optional<Member> om = memberRepository.findById(id);
		Member member = om.get();
		
		memberRepository.delete(member);
	}

	@Override
	public List<Member> memberApprove() {

		return memberRepository.findByEnabled(false);
	}

	@Override
	public void memberApprove(Integer id, Integer number) {

		Optional<Member> om = memberRepository.findById(id);
		Member member = om.get();
		
		member.setEnabled(true);
		member.setPosition(number);
		memberRepository.save(member);
	}
	
}
