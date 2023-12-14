package com.example.fran365.admin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.fran365.board.Board;
import com.example.fran365.board.BoardRepository;
import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.delivery.Delivery;
import com.example.fran365.delivery.DeliveryRepository;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.member.MemberService;
import com.example.fran365.position.PositionService;
import com.example.fran365.product.Product;
import com.example.fran365.product.ProductRepository;
import com.example.fran365.reply.Reply;
import com.example.fran365.reply.ReplyRepository;
import com.example.fran365.status.Status;
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
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private StatusRepository statusrepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ProductRepository productRepository;

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
	public void memberApprove(Integer id, Integer number, String department) {

		Optional<Member> om = memberRepository.findById(id);
		Member member = om.get();
		
		member.setEnabled(1);
		member.setPosition(number);
		member.setDepartment(department);
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
	public int getDeliveryNotComplete() {
		
		List<Delivery> getDeliveyNotComplete = statusRepository.findByStepNot();
		int DeliveryNotComplete = getDeliveyNotComplete.size();

		return DeliveryNotComplete;
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

	@Override
	public void replyDelete(Integer replyId) {

		Optional<Reply> or = replyRepository.findById(replyId);
		Reply reply = or.get();
		
		replyRepository.delete(reply);
	}

	@Override
	public List<Board> getUserQuestions() {
		
		String username = memberService.readDetailUsername().getUsername();
		List<String> excludedCategories = Arrays.asList("공지", "FAQ");

		return boardRepository.findByUsernameAndCategoryNotIn(username, excludedCategories, Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public List<Delivery> deliveryReadListByUsername() {
		
		String username = memberService.readDetailUsername().getUsername();

		return deliveryRepository.findByUsername(username, Sort.by(Sort.Direction.DESC, "id"));
	}
	
	@Override
	public void updateDepartment(String username, String newDepartment) {

		Optional<Member> om = memberRepository.findByUsername(username);
		Member member = om.get();
		
		member.setDepartment(newDepartment);
		memberRepository.save(member);
	}

	@Override
	public List<Delivery> deliveryReadList() {

		return deliveryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Delivery deliveryReadDetail(Integer id) {
		
		Optional<Delivery> od = deliveryRepository.findById(id);
		Delivery delivery = od.get();
		
		return delivery;
	}

	@Override
	public void statusCreate(Integer id, Status status) {
		
		Optional<Delivery> od = deliveryRepository.findById(id);
		Delivery delivery = od.get();
		
		status.setDelivery(delivery);
		status.setCreateDate(LocalDateTime.now());

		statusrepository.save(status);
	}

	@Override
	public List<Brand> brandReadList() {

		return brandRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public List<Object[]> findTop4Address1() {
		
		Pageable pageable = PageRequest.of(0, 4);

		return brandRepository.findTop4Address1(pageable);
	}

	@Override
	public int etcBrandCount() {

		int etcBrandCount = brandReadList().size();
		for (int i = 0; i < findTop4Address1().size(); i++) {
			
		etcBrandCount = etcBrandCount - ((Long) findTop4Address1().get(i)[1]).intValue();
		}
		
		return etcBrandCount;
	}

	@Override
	public List<Product> productReadList() {

		return productRepository.findAll();
	}

	@Override
	public void productDelete(Integer id) {

		productRepository.deleteById(id);
	}
	
}
