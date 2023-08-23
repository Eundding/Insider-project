package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetWishListsRes {
    private Long goodsId;
    private String title;
    private String price;
    private String weight;
    private Integer rest;
    private String imageUrl;
    private String name;
    private Timestamp createdAt;
}
