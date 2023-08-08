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


    // 상품 등록
    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq) throws BaseException {
        try {
            Goods goods = new Goods();
            goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(), postGoodsReq.getRest(), postGoodsReq.getShelf_life());
            goodsRepository.save(goods);
            return new PostGoodsRes(goods.getTitle());
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
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
