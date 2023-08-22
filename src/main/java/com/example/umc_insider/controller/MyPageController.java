package com.example.umc_insider.controller;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PutUserImgReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.service.UsersService;
import com.example.umc_insider.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final UsersService usersService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public MyPageController(UsersService usersService, UserRepository userRepository, JwtService jwtService) {
        this.usersService = usersService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // 특정 유저정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<GetUserRes>> findById(@PathVariable("id") long id) throws BaseException {
        List<GetUserRes> users = usersService.getReferenceById(id);
        return ResponseEntity.ok(users);
    }

}
