package com.example.umc_insider.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Category;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsRes {
    private Long id;
    private Category category_id;
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
    private Integer userZipCode; // 추가

//    public GetGoodsRes(String title, String price, String weight, int rest, String shelf_life, String img_url, String name){
//        this.title = title;
//        this.price = price;
//        this.weight = weight;
//        this.rest = rest;
//        this.shelf_life = shelf_life;
//        this.img_url = img_url;
//        this.name = name;
//    }

    public GetGoodsRes(Long id, Category categoryId, Users usersId, Markets marketsId, String title, String price, String weight, Integer rest, String shelfLife, Integer sale, String imageUrl) {
        this.id = id;
        this.category_id = categoryId;
        this.users_id = usersId;
        this.markets_id = marketsId;
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.rest = rest;
        this.shelf_life = shelfLife;
        this.sale = sale;
        this.img_url = imageUrl;
    }
}
