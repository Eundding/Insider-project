package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PostUserProfileReq {
    private Long id;


    public PostUserProfileReq() {
        // 디폴트 생성자
    }

    public PostUserProfileReq(Long id) {
        this.id = id;
    }
}
