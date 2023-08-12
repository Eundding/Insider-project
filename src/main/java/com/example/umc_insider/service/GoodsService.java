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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {
    private GoodsRepository goodsRepository;
    private UserRepository userRepository;
    private final S3Service s3Service;
    @Autowired
    public GoodsService(GoodsRepository goodsRepository, UserRepository userRepository, S3Service s3Service){
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }


    // 상품 등록
    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq, String imageUrl) throws BaseException {
        try {
            Goods goods = new Goods();

            Users user = userRepository.findUsersById(postGoodsReq.getUserIdx());
            goods.setUser(user);

            goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(), postGoodsReq.getRest(), postGoodsReq.getShelf_life(), postGoodsReq.getUserIdx());
            goods.setImageUrl(imageUrl);
            goodsRepository.save(goods);
            return new PostGoodsRes(goods.getId(),goods.getTitle());
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
            //List<Goods> goodsList = goodsRepository.findGoodsByTitle(title);
            List<Goods> goodsList = goodsRepository.findByTitleContaining(title);
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

    public Goods createNewGoodsInstance(PostGoodsReq postgoodsReq, MultipartFile file) {
        Users user = userRepository.findUsersById(postgoodsReq.getUserIdx());
        Goods newGoods = new Goods(postgoodsReq, user);

        // 먼저 Goods 객체 저장
        goodsRepository.save(newGoods);

        // S3에 이미지 업로드 및 URL 받기
        String imageUrl = s3Service.uploadFileToS3(file, newGoods);

        // 이미지 URL 설정 후, 객체 업데이트
        newGoods.setImageUrl(imageUrl);
        goodsRepository.save(newGoods);

        return newGoods;
    }


}
