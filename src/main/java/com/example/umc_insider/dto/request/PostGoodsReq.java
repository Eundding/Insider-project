package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostGoodsReq {
    private String title;
    private String price;
    private Integer rest;
    private String shelf_life;
    Users usersId;
    Users marketsId;
    Integer sale;
    private String imageUrl;
}
