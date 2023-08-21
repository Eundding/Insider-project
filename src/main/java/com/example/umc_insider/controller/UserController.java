package com.example.umc_insider.controller;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PostUserProfileReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.request.PutUserReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.dto.response.PostLoginRes;
import com.example.umc_insider.dto.response.PostUserRes;
import com.example.umc_insider.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.service.UsersService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.umc_insider.config.BaseResponseStatus.*;

//@RequiredArgsConstructor
@RestController
public class UserController {
    private final UsersService usersService;
    private final S3Service s3Service;
    @Autowired
    public UserController(UsersService usersService, S3Service s3Service) {
        this.usersService = usersService;
        this.s3Service = s3Service;
    }
    //회원가입
    @PostMapping("/create")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws BaseException{
        PostUserRes response = usersService.createUser(postUserReq);
        return new BaseResponse<>(response);
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

    // 회원정보 수정
    @PutMapping("/user/modify")
    public BaseResponse<PostUserRes> modifyUser(@RequestBody PutUserReq putUserReq) throws BaseException{
        PostUserRes response = usersService.modifyUser(putUserReq);
        return new BaseResponse<>(response);
    }

     // 유저 프로필 등록
    @PostMapping("/userProfile/register")
    public BaseResponse<PostUserRes> registerProfile(@RequestPart("PostUserProfileReq")PostUserProfileReq postUserProfileReq, @RequestPart("image") MultipartFile image) throws BaseException{
        Users user = usersService.registerProfile(postUserProfileReq, image);

        String url = this.s3Service.uploadProfileS3(image, user);

        PostUserRes response = new PostUserRes(user.getId(), user.getNickname());

        return new BaseResponse<>(response);
    }






}
