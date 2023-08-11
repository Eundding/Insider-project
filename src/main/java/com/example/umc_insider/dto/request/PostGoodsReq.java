package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Markets;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostGoodsReq {
    private String title;
    private String price;
    private Integer rest;
    private String shelf_life;
//    Users users_id;
//    Markets markets_id;
//    private Integer sale;
//    private String imageUrl;

//    public Users getUsersId() {
//        return users_id;
//    }
//
//    public Markets getMarketsId() {
//        return markets_id;
//    }
}
