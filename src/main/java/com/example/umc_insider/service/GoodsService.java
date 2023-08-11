package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.request.PostModifyPriceReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.repository.GoodsRepository;

import com.example.umc_insider.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
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
    public GoodsService(GoodsRepository goodsRepository, UserRepository userRepository){
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
    }


    // 상품 등록
    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq) throws BaseException {
        try {
            Goods goods = new Goods();

            Users user = userRepository.findUsersById(postGoodsReq.getUserIdx());
            goods.setUser(user);

            goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(), postGoodsReq.getRest(), postGoodsReq.getShelf_life(), postGoodsReq.getUserIdx());
            goodsRepository.save(goods);
            return new PostGoodsRes(goods.getTitle());
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // all 상품 조회
    public List<GetGoodsRes> getGoods() throws BaseException {
        try {
            List<Goods> goodsList = goodsRepository.findGoods();
            List<GetGoodsRes> getGoodsRes = goodsList.stream()
                    .map(goods -> new GetGoodsRes(goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl()))
                    .collect(Collectors.toList());
            return getGoodsRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // 특정 상품 조회
    public List<GetGoodsRes> getGoodsByTitle(String title) throws BaseException {
        try {
            List<Goods> goodsList = goodsRepository.findGoodsByTitle(title);
            List<GetGoodsRes> GetGoodsRes = goodsList.stream()
                    .map(goods -> new GetGoodsRes(goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl()))
                    .collect(Collectors.toList());
            return GetGoodsRes;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // 상품 삭제
    @Transactional
    public void deleteGoods(long id) {
        Goods goods = (Goods)this.goodsRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
        });
        this.goodsRepository.delete(goods);
    }


    // 상품 가격 변경
    @Transactional
    public void modifyPrice(PostModifyPriceReq postModifyPriceReq) {
        Goods goods = goodsRepository.getReferenceById(postModifyPriceReq.getId());
        goods.modifyPrice(postModifyPriceReq.getPrice());
    }
}
