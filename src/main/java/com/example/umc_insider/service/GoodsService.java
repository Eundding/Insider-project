package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.request.PatchGoodsReq;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.request.PostModifyPriceReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.repository.GoodsRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {
    private GoodsRepository goodsRepository;
    private UserRepository userRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository){
        this.goodsRepository = goodsRepository;
    }

    // 상품등록
    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq) throws BaseException {
        try {
            Goods newGoods = new Goods();
            newGoods.setTitle(postGoodsReq.getTitle());
            newGoods.setPrice(postGoodsReq.getPrice());
            newGoods.setRest(postGoodsReq.getRest());
            newGoods.setShelf_life(postGoodsReq.getShelf_life());
            newGoods.setUsersId(postGoodsReq.getUsersId());
            newGoods.setMarketsId(postGoodsReq.getMarketsId());
            newGoods.setSale(postGoodsReq.getSale());
            newGoods.setImageUrl(postGoodsReq.getImageUrl());

            Goods savedGoods = goodsRepository.save(newGoods);

            return new PostGoodsRes(savedGoods.getId(), savedGoods.getTitle(), savedGoods.getPrice());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

//    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq){
//        Goods goods = new Goods();
//        // 유저 검색
//        Users user = userRepository.findUsersById(postGoodsReq.getUsersId());
//
//        // 시장 검색
//        Users market = userRepository.findUsersById(postGoodsReq.getMarketsId());
//
//        // 지역 검색
//        /*
//        추후 추가
//        */
//
//        // 상품 등록
//        goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(),postGoodsReq.getRest(), postGoodsReq.getShelf_life(), user, market, postGoodsReq.getSale(), postGoodsReq.getImageUrl());
//        goodsRepository.save(goods);
//        return new PostGoodsRes(goods.getId(), goods.getTitle());
//    }

    // 상품 조회
    public List<GetGoodsRes> getGoods() {
        List<Goods> goodsList = goodsRepository.findGoods();
        List<GetGoodsRes> getGoodsRes = goodsList.stream()
                .map(goods -> new GetGoodsRes(goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale()))
                .collect(Collectors.toList());
        return getGoodsRes;
    }

    // 상품 삭제
    @Transactional
    public void deleteGoods(PatchGoodsReq patchGoodsReq) {
        Goods goods = goodsRepository.getReferenceById(patchGoodsReq.getId());
        goods.deleteGoods();
    }

    // 상품 가격 변경
    @Transactional
    public void modifyPrice(PostModifyPriceReq postModifyPriceReq) {
        Goods goods = goodsRepository.getReferenceById(postModifyPriceReq.getId());
        goods.modifyPrice(postModifyPriceReq.getPrice());
    }
}
