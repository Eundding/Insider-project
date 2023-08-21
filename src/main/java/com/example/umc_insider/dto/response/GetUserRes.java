package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private Long id;
    private String userId;
    private String nickname;
    private String pw; // add
   // private String password;
    private String email;
    private Address address; // fk


    public GetUserRes(Long id, String userId, String nickname, String email, String pw, Address address) {
        this.id = id;
        this.userId = userId;
        this.pw= pw;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
    }
}
