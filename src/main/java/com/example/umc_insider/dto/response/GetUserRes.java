package com.example.umc_insider.dto.response;

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
    private String password;
    private String email;
    private Integer zipCode;
}
