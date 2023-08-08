package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.request.PatchGoodsReq;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.request.PostModifyPriceReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.repository.MarketsRepository;

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
    private UserRepository usersRepository;
    private MarketsRepository marketsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository){
        this.goodsRepository = goodsRepository;
    }

//    // 상품등록
//    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq) throws BaseException {
//        try {
//            Users users = usersRepository.findUsersById(postGoodsReq.getUsersId().getId()); // 사용자 정보 가져오기
//            Markets markets = marketsRepository.findMartketsById(postGoodsReq.getMarketsId().getId()); // 시장 정보 가져오기
//
//            Goods newGoods = new Goods();
//            newGoods.setTitle(postGoodsReq.getTitle());
//            newGoods.setPrice(postGoodsReq.getPrice());
//            newGoods.setRest(postGoodsReq.getRest());
//            newGoods.setShelf_life(postGoodsReq.getShelf_life());
//            newGoods.setUsersId(users); // 가져온 사용자 정보로 설정
//            newGoods.setMarketsId(markets); // 가져온 시장 정보로 설정
//            newGoods.setSale(postGoodsReq.getSale());
//            newGoods.setImageUrl(postGoodsReq.getImageUrl());
//
//            Goods savedGoods = goodsRepository.save(newGoods);
//
//            return new PostGoodsRes(
//                    savedGoods.getId(),
//                    savedGoods.getTitle(),
//                    savedGoods.getPrice(),
//                    savedGoods.getImageUrl()
//            );
//
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }


    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq) throws BaseException {
        try {
            Goods goods = new Goods();
            // 유저 검색
            Users user = usersRepository.findUsersById(postGoodsReq.getUsersId().getId());

            // 시장 검색
            Markets market = marketsRepository.findMartketsById(postGoodsReq.getMarketsId().getId());

            // 지역 검색
        /*
        추후 추가
        */

            // 상품 등록
            goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(), postGoodsReq.getRest(), postGoodsReq.getShelf_life(), user, market, postGoodsReq.getSale(), postGoodsReq.getImageUrl());
            goodsRepository.save(goods);
            return new PostGoodsRes(goods.getId(), goods.getTitle(), goods.getPrice(), goods.getImageUrl());
        }

        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

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
