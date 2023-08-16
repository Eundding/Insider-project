package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserReq {
    private String userId;
    private String nickname;
    private String pw;
    private String email;
    private Integer zipCode;
    private String detailAddress;

    // PostUserReq.java

// ... 기존 코드 ...

    public Users createUserWithAddress() {
        Users newUser = new Users();
        // 사용자 관련 필드 설정 (예: newUser.setName(this.getName());)
        newUser.setUser_id(this.userId);
        newUser.setCreated_at();
        newUser.setUpdated_at();
        newUser.setEmail(this.email);
        newUser.setPw(this.pw);
        newUser.setNickname(this.nickname);

        Address newAddress = new Address();
        newAddress.setZipCode(this.getZipCode());
        newAddress.setDetailAddress(this.getDetailAddress());
        newUser.setAddress(newAddress);

        return newUser;
    }

}
