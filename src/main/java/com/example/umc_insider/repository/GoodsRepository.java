package com.example.umc_insider.repository;

import java.util.List;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g")
    List<Goods> findGoods();

    @Query("select count(g) from Goods g")
    Integer findByGoodsCount();

    @Query("select g from Goods g where g.title = :title")
    List<Goods> findGoodsByTitle(@Param("title") String title);
//
//    @Query("select g from Goods g where g.users_id = :users_id and g.rest is null")
//    List<Goods> findGoodsByUser(@Param("users_id") Users users_id);
//
//    @Query("select g from Goods g where g.markets_id = :markets_id and g.rest is null")
//    List<Goods> findGoodsByMarket(@Param("markets_id") Markets markets_id);
//
//    @Query("select count(g) from Goods g where g.id = :id")
//    Integer findByGoodsIdCount(@Param("id") Users id);

//    @Query("select count(g) from Goods g where g.users_id = :users_id")
//    Integer findByGoodsUserCount(@Param("users_id") Users users_id);
//
//    @Query("select count(g) from Goods g where g.markets_id = :markets_id")
//    Integer findByGoodsMarketCount(@Param("markets_id") Markets markets_id);

}
