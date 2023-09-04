package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserByIdRes {
    private Long id;
    private String nickname;
    private String userId;
    private String pw;
    private String email;
    private Integer zipCode;
    private String detailAddress;
    private String img;
    private Integer sellerOrBuyer;
    private Long registerNum;

    public GetUserByIdRes(String nickname, String userId, String pw, String email, Integer zipCode, String detailAddress, String imageUrl, Integer sellerOrBuyer, Long registerNumber) {
        this.nickname = nickname;
        this.userId = userId;
        this.pw = pw;
        this.email = email;
        this.zipCode = zipCode;
        this.detailAddress = detailAddress;
        this.img = imageUrl;
        this.sellerOrBuyer = sellerOrBuyer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeller_or_buyer() {
        return sellerOrBuyer;
    }
}
