package com.example.fran365.admin;

import java.util.List;
import java.util.Map;

import com.example.fran365.board.Board;
import com.example.fran365.member.Member;

public interface AdminService {
	
	List<Member> memberReadList();
	
	List<Member> memberApprove();
	
	Member memberReadDeatail(Integer id);
	
	void memberDelete(Integer id);
	
	void memberApprove(Integer id, Integer number);
	
	Map<Object, Object> getPosition();
	
	void updatePosition(String username, Integer newPosition);
	
	int getDeliveyNotComplete();
	
	List<Board> noticeReadList();
	
	Board boardReadDetail(Integer id);
	
	List<Board> questionReadList();
	
	List<Board> faqReadList();
	
	List<Board> getQuestionNotComplete();
}
