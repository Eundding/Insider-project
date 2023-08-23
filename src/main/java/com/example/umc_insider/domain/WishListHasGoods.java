package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wish_lists_has_goods")
@IdClass(WishListHasGoodsId.class)
public class WishListHasGoods {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_lists_id")
    private WishLists wishList;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public void setWishList(WishLists w){this.wishList = w;}
    public void setGoods(Goods g){this.goods = g;}
}
