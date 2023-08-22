package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class GetMyPageRes {
    private Long id;
    private String userId;
    private String nickname;
    private String password;
    private String email;
    private Address address; // fk
}
