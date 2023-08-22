package com.example.umc_insider.domain;

import java.io.Serializable;

public class WishListHasGoodsId implements Serializable {
    private Long wishList; // WishLists 엔티티의 기본 키
    private Long goods;    // Goods 엔티티의 기본 키

    // 기본 생성자, getter 및 setter 메서드
}