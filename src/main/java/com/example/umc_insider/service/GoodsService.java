package com.example.umc_insider.service;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GoodsService {
    private GoodsRepository goodsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository){
        this.goodsRepository = goodsRepository;
    }

    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq){
        Goods goods = new Goods();
        goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(),postGoodsReq.getRest(), postGoodsReq.getShelf_life(), postGoodsReq.getUsersId(), postGoodsReq.getMarketsId(), postGoodsReq.getSale(), postGoodsReq.getImageUrl());
        goodsRepository.save(goods);
        return new PostGoodsRes(goods.getId(), goods.getTitle());
    }
}
