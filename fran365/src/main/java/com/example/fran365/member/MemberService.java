package com.example.fran365.member;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

	void create(Member member, MultipartFile file ) throws IOException;
	
	List<Member> readList();
	
	Member readDetailUsername();

	String findUsername();

	void update(Member member, MultipartFile multipartFile) throws IOException;
	Optional<Member> findIdUser(String paramString1, String paramString2);
	//임시 비밀번호로 업데이트
	void updatePassword(String str, String username);
	//랜덤함수로 임시비밀번호 구문 만들기
	String getTempPassword();
	// 메일보내기
	void mailSend(MailDto mailDto);
	//비밀번호 변경
	void updatePassWord(Long Id, String Password);

	//임시번호 암호화 하기
	void sendTemporaryPassword(String username);


}


