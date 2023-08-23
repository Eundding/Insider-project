package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.WishListHasGoods;
import com.example.umc_insider.domain.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListHasGoodsRepository extends JpaRepository<WishListHasGoods, Long> {
    @Query("SELECT w.goods.id FROM WishListHasGoods w WHERE w.wishList.id = :wishListId")
    List<Long> findGoodsIdsByWishListId(@Param("wishListId") Long wishListId);

    @Query("select wh from WishLists w, WishListHasGoods wh where w.user.id= :userId and wh.goods.id = :goodsId")
    List<WishListHasGoods> findByUserIdToWishList(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Query("select wh from WishLists w, WishListHasGoods wh where wh.wishList.id = :wishListId")
    WishListHasGoods findByWishListId(@Param("wishListId") Long wishListId);
}
