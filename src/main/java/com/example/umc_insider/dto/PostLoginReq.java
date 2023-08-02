package com.example.umc_insider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostLoginReq {
    private String email;
    private String pw;
}