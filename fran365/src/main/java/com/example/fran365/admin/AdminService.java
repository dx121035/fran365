package com.example.fran365.admin;

import java.util.List;

import com.example.fran365.member.Member;

public interface AdminService {
	
	List<Member> memberReadList();
	
	List<Member> memberApprove();
	
	Member memberReadDeatail(Integer id);
	
	void memberDelete(Integer id);
	
	void memberApprove(Integer id, Integer number);
	
}
