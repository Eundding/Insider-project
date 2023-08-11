package com.example.umc_insider.controller;

import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.dto.response.PostLoginRes;
import com.example.umc_insider.dto.response.PostUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.service.UsersService;

import java.util.List;

import static com.example.umc_insider.config.BaseResponseStatus.*;
import static com.example.umc_insider.utils.ValidationRegex.isRegexEmail;

//@RequiredArgsConstructor
@RestController
public class UserController {
    private final UsersService usersService;
    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }
    //회원가입
    @PostMapping("/create")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws BaseException{
        PostUserRes response = usersService.createUser(postUserReq);

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


    @GetMapping("/users")
    public ResponseEntity<List<GetUserRes>> getAllUsers() {
        List<GetUserRes> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    // 로그인
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // null값 체크
            if(postLoginReq.getUserId() == null){
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            if(postLoginReq.getPw() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PW);
            }

            PostLoginRes postLoginRes = usersService.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
