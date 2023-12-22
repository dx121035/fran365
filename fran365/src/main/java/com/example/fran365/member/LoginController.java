package com.example.fran365.member;



import com.example.fran365.auth.UserDetailService;
import com.example.fran365.member.GoogleLoginResponse;
import com.example.fran365.member.GoogleOAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;




@Controller
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    @Value("https://oauth2.googleapis.com")
    private String googleAuthUrl;

    @Value("https://accounts.google.com")
    private String googleLoginUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("http://localhost:8080/login/oauth2/code/google")
    private String googleRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;


    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/code/{registrationId}")
    public String googleLogin(@RequestParam String code, @PathVariable String registrationId) throws JsonMappingException, JsonProcessingException {

        //1. 구글 로그인이 성공했을 경우 넘겨 받는 토큰 code 확인
        System.out.println("logincontroller : " + code);
        System.out.println("registrationId : " + registrationId);


        //2.넘어온 값으로 객체 생성
        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(code)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();

        RestTemplate restTemplate = new RestTemplate();

        //3.넘겨 받은 토큰을 다시 전송하여 사용자 정보 획득
        ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token", googleOAuthRequest, GoogleLoginResponse.class);
        //4.토큰으로 다시 넘겨 받은 데이터로 사용자 객체 생성
        GoogleLoginResponse googleLoginResponse = apiResponse.getBody();



        String googleToken = googleLoginResponse.getId_token();


        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token",googleToken).toUriString();


        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(resultJson);

        // "email" 데이터만 추출
        String email = rootNode.get("email").asText();

        System.out.println("Email: " + email);

        int result = userDetailService.logincheck(email);

        if (result == 1) {
            return "redirect:/";
        } else {
            return "redirect:/member/create";
        }



    }



}