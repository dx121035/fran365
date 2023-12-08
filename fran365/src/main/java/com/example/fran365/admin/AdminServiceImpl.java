package com.example.fran365.admin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.fran365.board.Board;
import com.example.fran365.board.BoardRepository;
import com.example.fran365.delivery.Delivery;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.position.PositionService;
import com.example.fran365.status.StatusRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@Override
	public List<Member> memberReadList() {
		
		return memberRepository.findByEnabled(1);
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

		return memberRepository.findByEnabled(0);
	}

	@Override
	public void memberApprove(Integer id, Integer number) {

		Optional<Member> om = memberRepository.findById(id);
		Member member = om.get();
		
		member.setEnabled(1);
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

	@Override
	public void updatePosition(String username, Integer newPosition) {

		Optional<Member> om = memberRepository.findByUsername(username);
		Member member = om.get();
		
		member.setPosition(newPosition);
		memberRepository.save(member);
	}

	@Override
	public int getDeliveyNotComplete() {
		
		List<Delivery> getDeliveyNotComplete = statusRepository.findByStepNot();
		int DeliveyNotComplete = getDeliveyNotComplete.size();

		return DeliveyNotComplete;
	}

	@Override
	public List<Board> noticeReadList() {

		return boardRepository.findByCategory("공지", Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Board boardReadDetail(Integer id) {

		Optional<Board> ob = boardRepository.findById(id);
		Board board = ob.get();
		
		return board;
	}

	@Override
	public List<Board> questionReadList() {
		
	    List<String> categories = Arrays.asList("상품", "배송", "재고");
	    
	    return boardRepository.findByCategoryIn(categories, Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public List<Board> faqReadList() {

		return boardRepository.findByCategory("FAQ");
	}
	
	@Override
	public List<Board> getQuestionNotComplete() {
        return boardRepository.findByStatus("답변대기");
    }
	
}
