package com.example.umc_insider.controller;

import com.example.umc_insider.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.service.UserService;

import java.util.List;

import static com.example.umc_insider.config.BaseResponseStatus.*;
import static com.example.umc_insider.utils.ValidationRegex.isRegexEmail;

//@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //회원가입
    @PostMapping("/create")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws BaseException{
        PostUserRes response = userService.createUser(postUserReq);
       // return new BaseResponse<>(userService.createUser(postUserReq));
        return new BaseResponse<>(response);
//        if (!ValidationRegex.isRegexEmail(postUserReq.getEmail())) {
//            return new BaseResponse(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
//        } else {
//            try {
//                return new BaseResponse<>(userService.createUser(postUserReq));
//            } catch (BaseException exception) {
//                return new BaseResponse<>((exception.getStatus()));
//            }
//        }
    }
//    public Long createUser(@RequestBody PostUserReq postGoodsReq){
//        return userService.save(postGoodsReq);
//    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserRes>> getAllUsers() {
        List<GetUserRes> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    //로그인
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // null값 체크
            if(postLoginReq.getEmail() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if(postLoginReq.getPw() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PW);
            }

            // 이메일 형식인지 체크
            if(!isRegexEmail(postLoginReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }




            PostLoginRes postLoginRes = userService.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
//            if(!isRegexEmail(postLoginReq.getEmail())) return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//            return new BaseResponse<>(userService.login(postLoginReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
