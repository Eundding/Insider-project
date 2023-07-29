package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.User;
import com.example.umc_insider.dto.*;
import com.example.umc_insider.utils.JwtService;
import com.example.umc_insider.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.umc_insider.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

import static com.example.umc_insider.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException{
        User user = new User();
        user.createUser(postUserReq.getUserId(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getPw() );
        userRepository.save(user);
        return new PostUserRes(user.getId(), user.getNickname());
    }

    public List<GetUserRes> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }

    private List<GetUserRes> mapToUserResponseList(List<User> users) {
        List<GetUserRes> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(new GetUserRes(user.getId(), user.getUser_id(), user.getNickname(), user.getEmail(), user.getPw()));
        }
        return userResponses;
    }

    /**
     * 유저 로그인
     */
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userRepository.findUserByEmail(postLoginReq.getEmail());
        String encryptPw;
        try{
            encryptPw = new SHA256().encrypt(postLoginReq.getPw());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }


        String originalEncryptPw = new SHA256().encrypt(user.getPw());
        if(originalEncryptPw.equals(encryptPw)){
            String jwt = jwtService.createJwt(user.getId());
            return new PostLoginRes(user.getId(), jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }


}
