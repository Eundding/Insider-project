package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPostsReq {
    private String title;
    private String content;
}
