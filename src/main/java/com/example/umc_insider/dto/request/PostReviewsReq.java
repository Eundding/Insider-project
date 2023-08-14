package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReviewsReq {
    private String content;
    private Integer point;
}
