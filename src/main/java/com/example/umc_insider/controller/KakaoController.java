package com.example.umc_insider.controller;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.KakaoUserInfo;
import com.example.umc_insider.service.GeoCodingService;
import com.example.umc_insider.service.KakaoService;
import com.example.umc_insider.service.S3Service;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.service.UsersService;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController

public class KakaoController {

    private final UsersService usersService;
    private final S3Service s3Service;
    private final GeoCodingService geoCodingService;

    private final KakaoService kakaoService;
    @Autowired
    public KakaoController(UsersService usersService, S3Service s3Service, GeoCodingService geoCodingService, KakaoService kakaoService) {
        this.usersService = usersService;
        this.s3Service = s3Service;
        this.geoCodingService = geoCodingService;
        this.kakaoService = kakaoService;
    }

    // kakaoLogin
    @PostMapping("/kakao")
    public String signup(String accessToken) {
        // Get user info from Kakao API using the access token.
        KakaoUserInfo userInfo = kakaoService.getUserInfo(accessToken);

        // Create a new user entity.
        Users user = new Users();
        user.setKakaoName(userInfo.getNickname());
        user.setKakaoEmail(userInfo.getEmail());

        // Save the user entity in the database.
        usersService.signUpUser(user);

        return "redirect:/";
    }

    @GetMapping("/login/oauth2/callback/kakao")
    public String handleKakaoCallback() {
        // 인증 코드를 받아 액세스 토큰을 얻고 사용자 정보를 조회하는 등의 작업 수행
        return "/home";  // home 뷰로 리디렉션
    }

    @PostMapping("/oauth2/callback/kakao")
    public String kakaoCallback(String code) { // 데이터를 리턴해주는 컨트롤러 함수

        // 1. 액세스 토큰 얻기
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "{cff93a78bd63fdc66fd6671549730716}");
        params.add("redirect_uri", "{http://localhost:8080/login/oauth2/callback/kakao}");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple 등의 라이브러리로 파싱하여 필요한 값 추출
        Gson gson = new Gson();
        Map<String, Object> tokenMap = gson.fromJson(response.getBody(), Map.class);


        // 2. 사용자 정보 조회
        String accessToken = (String)tokenMap.get("access_token");

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+accessToken);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        return "home";
    }

}
