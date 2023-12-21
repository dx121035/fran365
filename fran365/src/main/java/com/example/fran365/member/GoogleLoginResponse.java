package com.example.fran365.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleLoginResponse {
    private String access_token; // �븷�뵆由ъ��씠�뀡�씠 Google API �슂泥��쓣 �듅�씤�븯湲� �쐞�빐 蹂대궡�뒗 �넗�겙
    private String expires_in;   // Access Token�쓽 �궓�� �닔紐�
    private String refreshToken;    // �깉 �븸�꽭�뒪 �넗�겙�쓣 �뼸�뒗 �뜲 �궗�슜�븷 �닔 �엳�뒗 �넗�겙
    private String scope;
    private String token_type;   // 諛섑솚�맂 �넗�겙 �쑀�삎(Bearer 怨좎젙)
    private String id_token;
}