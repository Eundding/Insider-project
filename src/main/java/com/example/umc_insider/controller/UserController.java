package com.example.umc_insider.controller;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.KakaoUserInfo;
import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PutUserProfileReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.request.PutUserReq;
import com.example.umc_insider.dto.response.*;
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
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.service.UsersService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.example.umc_insider.config.BaseResponseStatus.*;
@RestController
public class UserController {
    private final UsersService usersService;
    private final S3Service s3Service;
    private final GeoCodingService geoCodingService;

    private final KakaoService kakaoService;
    @Autowired
    public UserController(UsersService usersService, S3Service s3Service, GeoCodingService geoCodingService, KakaoService kakaoService) {
        this.usersService = usersService;
        this.s3Service = s3Service;
        this.geoCodingService = geoCodingService;
        this.kakaoService = kakaoService;
    }

    // 회원가입
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
    @PutMapping("/userProfile/register")
    public BaseResponse<PostUserRes> registerProfile(@RequestPart("PutUserProfileReq") PutUserProfileReq putUserProfileReq, @RequestPart("image") MultipartFile image) throws BaseException{
        Users user = usersService.registerProfile(putUserProfileReq, image);

        String url = this.s3Service.uploadProfileS3(image, user);

        PostUserRes response = new PostUserRes(user.getId(), user.getNickname());

        return new BaseResponse<>(response);
    }

    // id로 유저 정보 조회
    @GetMapping("/user/{id}")
    public GetUserByIdRes getUserById(@PathVariable Long id){
        GetUserByIdRes getUserByIdRes = usersService.getUserById(id);
        return getUserByIdRes;
    }

    @GetMapping("/address/{zipCode}")
    public GetLatLngRes getLatLng(@PathVariable String zipCode){
        GetLatLngRes getLatLngRes = geoCodingService.getLatLngByAddress(zipCode);
        return getLatLngRes;
    }


}
