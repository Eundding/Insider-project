package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.KakaoProfile;
import com.example.umc_insider.domain.OAuthToken;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetUserByIdRes;
import com.example.umc_insider.dto.response.PostLoginRes;
import com.example.umc_insider.service.KakaoService;
import com.example.umc_insider.service.UsersService;
import com.example.umc_insider.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.UUID;

@RestController
public class KakaoController {
    @Value("${oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.client.registration.kakao.grant-type}")
    private String grantType;

    @Value("${oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Autowired
    private KakaoService kakaoService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/oauth2/callback/kakao")
    public BaseResponse kakaoCallback(String code) throws BaseException { // ResponseBody를 붙이면 데이터를 리턴해주는 컨트롤러 함수가 됨
        // Post 방식으로 key = value 데이터로 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 헤더에 컨텐츠 타입을 담는다 -> 내가 전송한 http body data가 key data라고 헤더에게 알려줌
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 변수 사용
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

        // ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // token을 통한 사용자 정보 조회
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileReq =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileReq,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Users 오브젝트: user_id, pw, email, nickname
//        System.out.println("카카오 아이디: " + kakaoProfile.getId());
//        System.out.println("카카오 닉네임: " + kakaoProfile.getProperties().nickname);
//        System.out.println("카카오 이메일: " + kakaoProfile.getKakaoAccount().email);

        // kakao로부터 받은 3가지 정보를 통해서 insider 회원 구성을 다음과 같이 하였다.
//        System.out.println("insider user_id: " + kakaoProfile.getKakaoAccount().email + "_" + kakaoProfile.getId());
//        System.out.println("insider email: " + kakaoProfile.getKakaoAccount().email);
        UUID garbagePassword = UUID.randomUUID(); // 임시 패스워드 garbage
//        System.out.println("insider pw: " + garbagePassword);

        PostUserReq kakaoUser = PostUserReq.builder()
                .userId(kakaoProfile.getKakaoAccount().email + "_" + kakaoProfile.getId())
                .pw(garbagePassword.toString())
                .nickname(kakaoProfile.getProperties().nickname)
                .email(kakaoProfile.getKakaoAccount().email)
                .build();

        kakaoService.signUpKakaoUser(kakaoUser.getNickname(), kakaoUser.getUserId(), kakaoUser.getPw(), kakaoUser.getEmail());
//
//        Users user = usersService.getUserByUserID(kakaoProfile.getKakaoAccount().email + "_" + kakaoProfile.getId());
//        Users user;
//        try {
//            user = usersService.getUserByUserID(kakaoUser.getUserId());
//            if(user == null) {
//                throw new BaseException(BaseResponseStatus.USERS_EXISTS_USER_ID);  // or your custom exception indicating that the user was not found.
//            }
//        } catch (BaseException e) {
//            // 유저가 없으면 회원가입
//            kakaoService.signUpKakaoUser(kakaoUser.getNickname(), kakaoUser.getUserId(), kakaoUser.getPw(), kakaoUser.getEmail());
//            user = usersService.getUserByUserID(kakaoUser.getUserId());
//        }

        Users user;
        try {
            user = usersService.getUserByUserID(kakaoUser.getUserId());
            if(user == null) {
                throw new BaseException(BaseResponseStatus.USERS_EXISTS_USER_ID);  // or your custom exception indicating that the user was not found.
            }
        } catch (BaseException e) {
            // 유저가 없으면 회원가입
            kakaoService.signUpKakaoUser(kakaoUser.getNickname(), kakaoUser.getUserId(), kakaoUser.getPw(), kakaoUser.getEmail());

            // 데이터 저장 후 다시 조회
            try {
                user = usersService.getUserByUserID(kakaoUser.getUserId());
                if(user == null) {
                    throw new BaseException(BaseResponseStatus.USERS_FAILED_TO_SIGN_UP);  // or your custom exception indicating failed sign-up.
                }
            } catch (BaseException ex) {
                // 실패 처리 로직
                throw ex;
            }
        }


        String jwt = jwtService.createJwt(user.getId());
        PostLoginRes postLoginRes = new PostLoginRes(user.getId(), jwt, user.getSellerOrBuyer());
        postLoginRes.setId(kakaoProfile.getId()); // Kakao API에서 받은 유저 인덱스 값 설정

        return new BaseResponse<>(postLoginRes);

    }
}
