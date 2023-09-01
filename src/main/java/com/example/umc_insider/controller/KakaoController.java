package com.example.umc_insider.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

@RestController
public class KakaoController {
    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirectUri}")
    private String redirectUri;

    @Value("${kakao.grantType}")
    private String grantType;

    @Value("${kakao.clientSecret}")
    private String clientSecret;

    @GetMapping("/oauth2/callback/kakao")
    public @ResponseBody String kakaoCallback(String code){ // ResponseBody를 붙이면 데이터를 리턴해주는 컨트롤러 함수가 됨
        // Post 방식으로 key = value 데이터로 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 헤더에 컨텐츠 타입을 담는다 -> 내가 전송한 http body data가 key data라고 헤더에게 알려줌
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        // 인가 코드는 동적
        params.add("code", code);
        params.add("client_secret", clientSecret);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq =
                new HttpEntity<>(params, headers); // kakaoTokenReq은 body 부분과 headers값을 가지고 있는 엔티티가 됨

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenReq,
                String.class
        );

        return "카카오 토큰 인증 완료 : 토큰 요청에 대한 응답 :" + response;
    }
}
