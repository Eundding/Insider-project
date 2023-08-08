package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.dto.response.PostLoginRes;
import com.example.umc_insider.dto.response.PostUserRes;
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
public class UsersService {
    private UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UsersService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException{
        Users users = new Users();
        users.createUser(postUserReq.getUserId(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getPw() );
        userRepository.save(users);
        return new PostUserRes(users.getId(), users.getNickname());
//        try {
//            User user = userRepository.findUserByEmail(postUserReq.getUserId());
//            if (user != null) {
//                throw new BaseException(BaseResponseStatus.USERS_EXISTS_USER_ID);
//            }
//
//            user = userRepository.findUserByNickname(postUserReq.getNickname());
//            if (user != null) {
//                throw new BaseException(BaseResponseStatus.USERS_EXISTS_NICKNAME);
//            }
//
//            user = new User();
//            user.createUser(postUserReq.getUserId(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getPw());
//            userRepository.save(user);
//            return new PostUserRes(user.getId(), user.getNickname());
//        } catch (BaseException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw new BaseException(BaseResponseStatus.FAILED_TO_SIGNUP);
//        }



    }

    public List<GetUserRes> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }

    private List<GetUserRes> mapToUserResponseList(List<Users> users) {
        List<GetUserRes> userResponses = new ArrayList<>();
        for (Users user : users) {
            userResponses.add(new GetUserRes(user.getId(), user.getUser_id(), user.getNickname(), user.getEmail(), user.getPw()));
        }
        return userResponses;
    }

    /**
     * 유저 로그인
     */
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        Users users = userRepository.findUserByEmail(postLoginReq.getEmail());
        String encryptPw;
        try{
            encryptPw = new SHA256().encrypt(postLoginReq.getPw());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }


        String originalEncryptPw = new SHA256().encrypt(users.getPw());
        if(originalEncryptPw.equals(encryptPw)){
            String jwt = jwtService.createJwt(users.getId());
            return new PostLoginRes(users.getId(), jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }


}
