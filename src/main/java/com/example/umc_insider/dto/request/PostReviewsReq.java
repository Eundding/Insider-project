package com.example.umc_insider.dto.request;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReviewsReq {
    private Goods goods_id;
    private Users buyer_id;
    private String content;
    private Integer point;
}
