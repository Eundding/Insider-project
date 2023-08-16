package com.example.umc_insider.controller;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.response.GetMyPageRes;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class MyPageController {

    private final UsersService usersService;
    @Autowired
    public MyPageController(UsersService usersService) {
        this.usersService = usersService;
    }

    // 특정 유저정보 조회
    @GetMapping("mypage/{id}")
    public ResponseEntity<List<GetUserRes>> findById(@PathVariable("id") long id) throws BaseException {
        List<GetUserRes> users = usersService.getReferenceById(id);
        return ResponseEntity.ok(users);
    }
}
