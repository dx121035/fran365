package com.example.fran365.member;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Member  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username; //아이디를 이메일로 강제

	private String password;

	private boolean enabled;

	private String name;

	private String phone;

	private String image;

	private String address;

	private String bid; //가입한 사람 매장주소

	private String role;

	private LocalDateTime createDate;


}