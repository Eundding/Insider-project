package com.example.umc_insider.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import com.example.umc_insider.domain.Users;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsRes {
    private Users users_id;
    private Users markets_id;
    private String title;
    private String price;
    private String weight;
    private int rest;
    private String shelf_life;
    private int sale;
}
