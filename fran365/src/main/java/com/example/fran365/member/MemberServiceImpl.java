package com.example.fran365.member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private MemberRepository mr;

	@Autowired
	private JavaMailSender mailSender;
	//@Autowired
	//private CartService cartService;

	MailDto mailDto;

	@Value("jaehoonbucket")
	private String bucketName;


	
	
	




	@Override
	public Member create(Member member, MultipartFile multipartFile) throws IOException {

		File file = new File(multipartFile.getOriginalFilename());

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(multipartFile.getBytes());
		}

		String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
		amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

		file.delete();


		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));

		member.setUsername(member.getUsername());
		member.setPassword(member.getPassword());
		member.setCreateDate(LocalDateTime.now());
		member.setImage(filename);
		member.setRole("ROLE_USER");
		
		return memberRepository.save(member);

		// 카트 생성
		//cartService.create(user);
	}

	@Override
	public List<Member> readList() {
		return memberRepository.findAll();
	}

	@Override
	public Member readDetailUsername() {

		// 현재 로그인 중인 사용자 정보 추출
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		Optional<Member> om = memberRepository.findByUsername(username);
		Member member = om.get();

		return member;
	}


	@Override
	public void update(Member member, MultipartFile multipartFile) throws IOException {
		String filecheck = multipartFile.getOriginalFilename();

		if (filecheck != null && !filecheck.trim().isEmpty()) {

			File file2 = new File(multipartFile.getOriginalFilename());

			//aws s3 multipartfile을 막바로 올릴 수 없게 되어있다.
			//따라서 파일을 일단 저장한 후에 그 파일을 aws로 올리고 삭제한다.

			try (FileOutputStream fos = new FileOutputStream(file2)) {
				fos.write(multipartFile.getBytes());
			}

			//역시 보안등의 이유로 uuid를 사용해도 좋지만 이번엔 다른 방법을 사용해보자

			String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
			amazonS3.putObject(new PutObjectRequest(bucketName, filename, file2));

			//파일을 s3으로 올리고 서버에 저장했던 파일은 이제 완전히 삭제한다.
			file2.delete();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			member.setPassword(passwordEncoder.encode(member.getPassword()));
			member.setCreateDate(LocalDateTime.now());
			member.setImage(filename);

			//member.setRole(Role.ROLE_USER);

			memberRepository.save(member);

			member.setImage(filename);

		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
	}

	@Override
	public Optional<Member> findIdUser(String name, String phone) {
		return this.memberRepository.findByNameAndPhone(name, phone);
	}

	//임시 비밀번호로 업데이트
	@Override
	public void updatePassword(String str, String username) {

		// Use the autowired memberRepository (mr) to find the member by email
		Optional<Member> memberOptional = mr.findByUsername(username);

		if (memberOptional.isPresent()) {
			Long memberId = Long.valueOf(memberOptional.get().getId());
			MemberServiceImpl mmr = new MemberServiceImpl();
			mmr.updatePassword(String.valueOf(memberId), str);
		} else {
			// Handle the case where the member is not found
			// You might want to throw an exception or handle it based on your application logic
		}
	}

	//랜덤함수로 임시비밀번호 구문 만들기
	@Override
	public String getTempPassword() {
		char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

		StringBuilder str = new StringBuilder();

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str.append(charSet[idx]);
		}
		return str.toString();
	}

	// 메일보내기
	@Override
	public void mailSend(MailDto mailDto) {
		System.out.println("전송 완료!");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		message.setFrom("보낸이@naver.com");
		message.setReplyTo("보낸이@naver.com");
		System.out.println("message" + message);
		mailSender.send(message);
	}

	//비밀번호 변경
	@Override
	public void updatePassWord(Long memberId, String memberPassword) {
		MemberServiceImpl mmr = new MemberServiceImpl();
		mmr.updatePassword(String.valueOf(memberId), memberPassword);
	}

	@Override
	public void sendTemporaryPassword(String username) {

	}


	@Transactional
	public MailDto createMailAndChangePassword(String username) {
		// Your implementation logic here
		// ...

		return mailDto;  // A

	}

}




