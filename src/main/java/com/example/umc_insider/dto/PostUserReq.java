package com.example.umc_insider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserReq {
    // from front
    // id, nick, pw, again_pw, email, address
    private String userId;
    private String nickname;
    private String pw;
    private String email;
}
