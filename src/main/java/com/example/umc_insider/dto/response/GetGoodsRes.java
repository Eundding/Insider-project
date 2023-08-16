package com.example.umc_insider.dto.response;

import com.example.umc_insider.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Markets;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsRes {
    private Long id;
    private Users users_id;
    private Markets markets_id;
    private String title;
    private String price;
    private String weight;
    private Integer rest;
    private String shelf_life;
    private Integer sale;
    private String img_url;
    private String name;

    public GetGoodsRes(String title, String price, String weight, int rest, String shelf_life, String img_url, String name){
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.rest = rest;
        this.shelf_life = shelf_life;
        this.img_url = img_url;
        this.name = name;
    }
}
