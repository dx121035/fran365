package com.example.fran365.admin;

import java.util.List;
import java.util.Map;

import com.example.fran365.board.Board;
import com.example.fran365.brand.Brand;
import com.example.fran365.delivery.Delivery;
import com.example.fran365.member.Member;
import com.example.fran365.product.Product;
import com.example.fran365.status.Status;

public interface AdminService {
	
	List<Member> memberReadList();
	
	List<Member> memberApprove();
	
	Member memberReadDetail(String username);
	
	void memberDelete(Integer id);
	
	void memberApprove(Integer id, Integer number, String department);
	
	Map<Object, Object> getPosition();
	
	void updatePosition(String username, Integer newPosition);
	
	int getDeliveryNotComplete();
	
	List<Board> noticeReadList();
	
	Board boardReadDetail(Integer id);
	
	List<Board> questionReadList();
	
	List<Board> faqReadList();
	
	List<Board> getQuestionNotComplete();
	
	void replyDelete(Integer replyId);
	
	List<Board> getUserQuestions(String username);
	
	List<Delivery> deliveryReadListByUsername(String username);
	
	void updateDepartment(String username, String newDepartment);
	
	List<Delivery> deliveryReadList();
	
	Delivery deliveryReadDetail(Integer id);
	
	void statusCreate(Integer id, Status status);
	
	List<Brand> brandReadList();
	
	List<Object[]> findTop4Address1();
	
	int etcBrandCount();
	
	List<Product> productReadList();
	
	void productDelete(Integer id);
	
	Brand brandReadDetail(Integer id);
	
}
