package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRoomByUserRes {
    private Long chatRoomId;
    private String otherNickName;
    private String lastMessage;
    // 상대방 프로필?
}
