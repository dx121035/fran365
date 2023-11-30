package com.example.fran365.member;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MemberService {

	void create(Member member, MultipartFile file) throws IOException;

	Member readDetailUsername();

	void sendSms(String tel, int number) throws NoSuchAlgorithmException, IOException;

	void update(Member member, MultipartFile file) throws IOException;

	List<Member> readlist();



}