package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostGoodsRes {
    private Long id;
    private String title;
    private String price;
    private String imageUrl;

    public PostGoodsRes(Long id, String title, String price) {
    }
}
