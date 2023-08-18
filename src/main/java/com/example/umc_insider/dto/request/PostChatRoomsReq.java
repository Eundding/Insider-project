package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostChatRoomsReq {
    private Long sellerId;
    private Long buyerId;
    private Long goodsId;
}
