package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class PostWishListsRes {
    private Long wishListId;
    private Long userId;
    private Long goodsId;
    private Timestamp createdAt;
}
