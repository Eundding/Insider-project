package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Category;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.request.PostModifyPriceReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.repository.CategoryRepository;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.service.ChatRoomsService;
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
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private S3Service s3Service;
    private ChatRoomsService chatRoomsService;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository, UserRepository userRepository, ChatRoomsService chatRoomsService, S3Service s3Service, CategoryRepository categoryRepository) {
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
        this.chatRoomsService = chatRoomsService;
        this.s3Service = s3Service;
        this.categoryRepository = categoryRepository;
    }


    // 상품 등록
    public PostGoodsRes createGoods(PostGoodsReq postGoodsReq, String imageUrl) throws BaseException {
        try {
            Goods goods = new Goods();

            Users user = userRepository.findUsersById(postGoodsReq.getUserIdx());
            Category category = (Category) categoryRepository.findCategoryByCategoryId(postGoodsReq.getCategoryId());
            goods.setUser(user);
            goods.setCategory(category);

            goods.createGoods(postGoodsReq.getTitle(), postGoodsReq.getPrice(), postGoodsReq.getRest(), postGoodsReq.getShelf_life(), postGoodsReq.getUserIdx(), postGoodsReq.getName());
            goods.setImageUrl(imageUrl);
            goodsRepository.save(goods);
            return new PostGoodsRes(goods.getId(), goods.getTitle());
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    // all 상품 조회
    public List<GetGoodsRes> getGoods() throws BaseException {
        try {
            List<Goods> goodsList = goodsRepository.findGoods();
            List<GetGoodsRes> getGoodsRes = goodsList.stream()
                    .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl()))
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
                    .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl()))
                    .collect(Collectors.toList());
            return GetGoodsRes;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // 상품 삭제
    @Transactional
    public void deleteGoods(long id) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 해당 상품을 참조하는 채팅방 레코드의 상품 ID를 NULL로 업데이트
        chatRoomsService.updateGoodsIdToNullForChatRooms(id);

        goodsRepository.delete(goods);
    }

    // 상품 가격 변경
    @Transactional
    public void modifyPrice(PostModifyPriceReq postModifyPriceReq) {
        Goods goods = goodsRepository.getReferenceById(postModifyPriceReq.getId());
        goods.modifyPrice(postModifyPriceReq.getPrice());
    }


    public Goods createNewGoodsInstance(PostGoodsReq postgoodsReq, MultipartFile file) {
        Users user = userRepository.findUsersById(postgoodsReq.getUserIdx());
        Category category = categoryRepository.findCategoryByCategoryId(postgoodsReq.getCategoryId());
        Goods newGoods = new Goods(postgoodsReq, user, category);
//        newGoods.setCategory(categoryRepository.findCategoryByCategoryId(postgoodsReq.getCategoryId()));
        // 먼저 Goods 객체 저장
        goodsRepository.save(newGoods);

        // S3에 이미지 업로드 및 URL 받기
        String imageUrl = s3Service.uploadFileToS3(file, newGoods);

        // 이미지 URL 설정 후, 객체 업데이트
        newGoods.setImageUrl(imageUrl);

        goodsRepository.save(newGoods);

        return newGoods;
    }

    // id로 goods 조회
    public GetGoodsRes getGoodsById(Long id) {
        Goods goods = goodsRepository.findGoodsById(id);
        return new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl(), goods.getName());
    }

    // category_id로 goods 조회
    public List<GetGoodsRes> getGoodsByCategoryId(Long category_id) {
        List<Goods> goodsList = goodsRepository.findByCategory_Id(category_id);
        //return goodsList.stream().map(goods -> new GetGoodsRes(goods.getId(), goods.getUsers_id(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getImageUrl(), goods.getName())).collect(Collectors.toList());
        List<GetGoodsRes> GetGoodsRes = goodsList.stream()
                .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getSale(), goods.getImageUrl(), goods.getName()))
                .collect(Collectors.toList());
        return GetGoodsRes;

    }

    // put 상품 모든 항목 수정
    public Goods update(Long id, Goods goods) {
        Goods existingGoods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));

        existingGoods.setTitle(goods.getTitle());
        existingGoods.setPrice(goods.getPrice());
        existingGoods.setShelf_life(goods.getShelf_life());
        existingGoods.setImageUrl(goods.getImageUrl());
        existingGoods.setCategory(goods.getCategory());
        existingGoods.setRest(goods.getRest());
        existingGoods.setWeight(goods.getWeight());

        return goodsRepository.save(existingGoods);
    }
}

