package com.example.fran365.member;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Value("jaehoonbucket")
	private String bucketName;

	@Override
	public void create(Member member, MultipartFile file) throws IOException {

		File file2 = new File(file.getOriginalFilename());
		//aws s3 multipartfile을 막바로 올릴 수 없게 되어있다.
		//따라서 파일을 일단 저장한 후에 그 파일을 aws로 올리고 삭제한다.

		try (FileOutputStream fos = new FileOutputStream(file2)) {
			fos.write(file.getBytes());
		}
		//역시 보안등의 이유로 uuid를 사용해도 좋지만 이번엔 다른 방법을 사용해보자

		String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		amazonS3.putObject(new PutObjectRequest(bucketName, filename, file2));

		//파일을 s3으로 올리고 서버에 저장했던 파일은 이제 완전히 삭제한다.
		file2.delete();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setCreateDate(LocalDateTime.now());
		member.setImage(filename);

		member.setRole(Role.ROLE_USER);

		memberRepository.save(member);



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
	public void sendSms(String tel, int number) throws NoSuchAlgorithmException, IOException {

		String charsetType = "UTF-8";

		String sms_url = "";
		sms_url = "https://sslsms.cafe24.com/sms_sender.php"; // SMS 전송요청 URL
		String user_id = "austiny"; // SMS아이디
		String secure = "b05c71225043952fdd34a1a93abaf4ab";//인증키
		String msg = "[Fran365] 본인확인 인증번호(" + number + ")를 입력해 주세요.";
		String rphone = tel;
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

		wr.close();

	}

	@Override
	public void update(Member member, MultipartFile file) throws IOException {

		String filecheck = file.getOriginalFilename();

		if(filecheck != null && !filecheck.trim().isEmpty()) {

			File file2 = new File(file.getOriginalFilename());

			//aws s3 multipartfile을 막바로 올릴 수 없게 되어있다.
			//따라서 파일을 일단 저장한 후에 그 파일을 aws로 올리고 삭제한다.

			try (FileOutputStream fos = new FileOutputStream(file2)) {
				fos.write(file.getBytes());
			}

			//역시 보안등의 이유로 uuid를 사용해도 좋지만 이번엔 다른 방법을 사용해보자

			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			amazonS3.putObject(new PutObjectRequest(bucketName, filename, file2));

			//파일을 s3으로 올리고 서버에 저장했던 파일은 이제 완전히 삭제한다.
			file2.delete();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			member.setPassword(passwordEncoder.encode(member.getPassword()));
			member.setCreateDate(LocalDateTime.now());
			member.setImage(filename);

			member.setRole(Role.ROLE_USER);

			memberRepository.save(member);

			member.setImage(filename);

		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		member.setPassword(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
	}

	@Override
	public List<Member> readlist() {

		return memberRepository.findAll();
	}


}