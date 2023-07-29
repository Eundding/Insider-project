package com.example.umc_insider.controller;

import com.example.umc_insider.dto.GetUserRes;
import com.example.umc_insider.dto.PostUserReq;
import com.example.umc_insider.dto.PostUserRes;
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
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq){
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

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    //로그인
//    @PostMapping({"/log-in"})
//    public BaseResponse<PostLoginRes> loginUser(@RequestBody PostLoginReq postLoginReq) {
//        try {
//            return !ValidationRegex.isRegexUserId(postLoginReq.getUserId()) ? new BaseResponse(BaseResponseStatus.RESPONSE_ERROR) : new BaseResponse(this.userService.login(postLoginReq));
//        } catch (BaseException var3) {
//            return new BaseResponse(var3.getStatus());
//        }
//    }


    /**
     * 회원 조회
     * nickname이 파라미터에 없을 경우 모두 조회
     */
//    @GetMapping("Read")
//    public BaseResponse<List<GetUserRes>> getMembers(@RequestParam(required = false) String nickName){
//        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
//        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
//        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음
//        try{
//            if (nickName == null) { // query string인 nickname이 없을 경우, 그냥 전체 유저정보를 불러온다.
//                return new BaseResponse<>(userService.getUsers());
//            }
//            // query string인 nickname이 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
//            return new BaseResponse<>(userService.getUsersByNickname(nickName));
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


}
