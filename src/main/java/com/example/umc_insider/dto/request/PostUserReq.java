package com.example.umc_insider.dto.request;

import com.example.umc_insider.controller.KakaoController;
import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostUserReq {
    private String userId;
    private String nickname;
    private String pw;
    private String email;
    private Integer zipCode;
    private String detailAddress;
    private Integer sellerOrBuyer;

    public Users createUserWithAddress() {
        Users newUser = new Users();
        newUser.setUser_id(this.userId);
        newUser.setCreated_at();
        newUser.setUpdated_at();
        newUser.setEmail(this.email);
        newUser.setPw(this.pw);
        newUser.setNickname(this.nickname);
        newUser.setSeller_or_buyer(this.sellerOrBuyer);

        Address newAddress = new Address();
        newAddress.setZipCode(this.getZipCode());
        newAddress.setDetailAddress(this.getDetailAddress());
        newUser.setAddress(newAddress);

        return newUser;
    }

}
