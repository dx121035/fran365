package com.example.fran365.member;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import com.example.fran365.cart.CartService;

import jakarta.transaction.Transactional;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	MailDto mailDto;

	@Value("bucket-va1rkc")
	private String bucketName;

	@Override
	public void create(Member member, MultipartFile multipartFile) throws IOException {

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
		member.setCreateDate(LocalDateTime.now());
		member.setImage(filename);
		member.setRole("ROLE_USER");
		member.setPosition(null);
		member.setEnabled(0);

		memberRepository.save(member);

		// 카트 생성
		cartService.create(member);
	}

	@Override
	public void sendSms(String subject, String phone) throws NoSuchAlgorithmException, IOException {

		String charsetType = "UTF-8";

		String sms_url = "";
		sms_url = "https://sslsms.cafe24.com/sms_sender.php"; // SMS 전송요청 URL
		String user_id = "austiny"; // SMS아이디
		String secure = "b05c71225043952fdd34a1a93abaf4ab";//인증키
		String msg = subject;
		String rphone = phone;
		String sphone1 = "010";
		String sphone2 = "2737";
		String sphone3 = "3944";
		String mode = "1";
		String smsType = "5";


		// sms 서버 변수값 설정
		String[] host_info = sms_url.split("/");
		String host = host_info[2];
		String path = "/" + host_info[3];
		int port = 80;



		// 데이터 맵핑 변수 정의
		String arrKey[]
				= new String[] {"user_id","secure","msg", "rphone",
				"sphone1","sphone2","sphone3","mode","smsType"};
		String valKey[]= new String[arrKey.length] ;
		valKey[0] = user_id;
		valKey[1] = secure;
		valKey[2] = msg;
		valKey[3] = rphone;
		valKey[4] = sphone1;
		valKey[5] = sphone2;
		valKey[6] = sphone3;
		valKey[7] = mode;
		valKey[8] = smsType;


		String boundary = "";
		Random rnd = new Random();
		String rndKey = Integer.toString(rnd.nextInt(32000));
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytData = rndKey.getBytes();
		md.update(bytData);
		byte[] digest = md.digest();
		for(int i =0;i<digest.length;i++)
		{
			boundary = boundary + Integer.toHexString(digest[i] & 0xFF);
		}
		boundary = "---------------------"+boundary.substring(0,11);

		// 본문 생성
		String data = "";
		String index = "";
		String value = "";
		for (int i=0;i<arrKey.length; i++)
		{
			index =  arrKey[i];
			value = valKey[i];
			data +="--"+boundary+"\r\n";
			data += "Content-Disposition: form-data; name=\""+index+"\"\r\n";
			data += "\r\n"+value+"\r\n";
			data +="--"+boundary+"\r\n";
		}

		//out.println(data);

		InetAddress addr = InetAddress.getByName(host);
		Socket socket = new Socket(host, port);
		// 헤더 전송
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charsetType));
		wr.write("POST "+path+" HTTP/1.0\r\n");
		wr.write("Content-Length: "+data.length()+"\r\n");
		wr.write("Content-type: multipart/form-data, boundary="+boundary+"\r\n");
		wr.write("\r\n");

		// 데이터 전송
		wr.write(data);
		wr.flush();


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
	public String findUsername(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		return username;
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
			member.setImage(filename);

			memberRepository.save(member);
		} else {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
		}
	}

	@Override
	public Optional<Member> findIdUser(String name, String phone) {
		return this.memberRepository.findByNameAndPhone(name, phone);
	}

	//임시 비밀번호로 업데이트
	//임시 비밀번호로 업데이트
	@Override
	public void updatePassword(String newPassword, String username) {
		// Check if the memberRepository is not null before using it
		if (memberRepository != null) {
			Optional<Member> memberOptional = memberRepository.findByUsername(username);
			if (memberOptional.isPresent()) {
				Member member = memberOptional.get();
				member.setPassword(newPassword);
				memberRepository.save(member);
			} else {
				// Handle the case where the user is not found
				System.err.println("User with username " + username + " not found.");
			}
		} else {
			// Handle the case where memberRepository is null
			System.err.println("MemberRepository is not properly initialized.");
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
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		message.setFrom("seula724@naver.com");
		message.setReplyTo("seula724@naver.com");
		mailSender.send(message);
	}

	//비밀번호 변경
	@Override
	public void updatePassWord(Long memberId, String memberPassword) {
		MemberServiceImpl mmr = new MemberServiceImpl();
		mmr.updatePassword(String.valueOf(memberId), memberPassword);
	}

	@Override
	@Transactional
	public void sendTemporaryPassword(String username) {
		Optional<Member> memberOptional = memberRepository.findByUsername(username);

		if (memberOptional.isPresent()) {
			String temporaryPassword = getTempPassword();
			String encryptedPassword = passwordEncoder.encode(temporaryPassword);

			updatePassword(encryptedPassword, username);

			MailDto mailDto = new MailDto();
			mailDto.setAddress(memberOptional.get().getUsername());
			mailDto.setTitle("Temporary Password");
			mailDto.setMessage("Your temporary password is: " + temporaryPassword);

			mailSend(mailDto);

		} else {
			
			System.err.println("User with username " + username + " does not exist.");
		}
	}




	@Transactional
	public MailDto createMailAndChangePassword(String username) {

		return mailDto;
	}

}