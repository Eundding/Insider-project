package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostChatRoomsReq {
    private Long sellerIdx;
    private Long buyerIdx;
}
