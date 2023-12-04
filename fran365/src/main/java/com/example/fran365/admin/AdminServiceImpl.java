package com.example.fran365.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.position.PositionService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PositionService positionService;

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

	@Override
	public Map<Object, Object> getPosition() {
		
		Map<Object, Object> map = new HashMap<>();
		
		for (int i = 0; i < positionService.readList().size(); i++) {
			
			int positionNumber = positionService.readList().get(i).getNumber();
			String positionPosition = positionService.readList().get(i).getPosition();
			map.put(positionNumber, positionPosition);
		}

		return map;
	}
	
}
