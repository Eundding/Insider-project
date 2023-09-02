package com.example.umc_insider.service;

import com.example.umc_insider.config.PasswordEncoderConfig;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.KakaoUserInfo;
import com.example.umc_insider.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired KakaoService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void signUpKakaoUser(String nickname, String userId, String pw, String email) {
        // 비밀번호 암호화
        String encPw = passwordEncoder.encode(pw);

        // Users 엔티티 생성
        Users user = new Users(nickname, userId, encPw, email);

        // DB에 저장
        userRepository.save(user);
    }

}
