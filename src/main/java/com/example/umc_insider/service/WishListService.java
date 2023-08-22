package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.WishListHasGoods;
import com.example.umc_insider.domain.WishLists;
import com.example.umc_insider.dto.request.PostWishListsReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.GetWishListsRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.dto.response.PostWishListsRes;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.repository.WishListHasGoodsRepository;
import com.example.umc_insider.repository.WishListsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {
    private WishListsRepository wishListsRepository;
    private WishListHasGoodsRepository wishListHasGoodsRepository;
    private UserRepository userRepository;
    private GoodsRepository goodsRepository;
    @Autowired
    public WishListService(WishListsRepository wishListsRepository, WishListHasGoodsRepository wishListHasGoodsRepository, UserRepository userRepository, GoodsRepository goodsRepository){
        this.wishListsRepository = wishListsRepository;
        this.wishListHasGoodsRepository = wishListHasGoodsRepository;
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
    }

    @Transactional
    public PostWishListsRes addGoodsToWishList(PostWishListsReq postWishListsReq) throws BaseException {
        if(postWishListsReq.getUserId() != null && postWishListsReq.getGoodsId() != null){
            Users user = userRepository.findUsersById(postWishListsReq.getUserId());
            Goods goods = goodsRepository.findGoodsById(postWishListsReq.getGoodsId());

            WishLists wishList = new WishLists();
            wishList.setUser(user);
            wishList.setCreatedAt();

            WishLists savedWishList = wishListsRepository.save(wishList);

            WishListHasGoods wishListHasGoods = new WishListHasGoods();
            wishListHasGoods.setWishList(savedWishList);
            wishListHasGoods.setGoods(goods);
            wishListHasGoodsRepository.save(wishListHasGoods);
            return new PostWishListsRes(wishList.getId(), wishList.getUser().getId(), wishListHasGoods.getGoods().getId(), wishList.getCreatedAt());
        }
        else {
            // 예외 처리: 요청이 잘못된 경우
            throw new IllegalArgumentException("요청이 잘못되었습니다.");
        }
    }

    // 유저의 위시리스트 조회
    @Transactional
    public List<GetWishListsRes> getGoodsInWishList(Long userId) {
        List<GetWishListsRes> goodsList = new ArrayList<>();
        // userId를 기반으로 해당 사용자의 WishLists를 찾습니다.
        List<WishLists> wishLists = wishListsRepository.findByUserId(userId);

        // 사용자의 WishLists에 연결된 goodsId를 모읍니다.
        List<Long> goodsIds = wishLists.stream()
                .map(wishList -> wishListHasGoodsRepository.findGoodsIdsByWishListId(wishList.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        for (Long goodsId : goodsIds) {
            Goods goods = goodsRepository.findGoodsById(goodsId);

            if (goods != null) {
                GetWishListsRes goodsDTO = new GetWishListsRes(goods.getId(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getImageUrl(), goods.getName());
                goodsDTO.setGoodsId(goods.getId());
                goodsDTO.setTitle(goods.getTitle());
                goodsDTO.setName(goods.getName());
                goodsDTO.setImageUrl(goods.getImageUrl());

                goodsList.add(goodsDTO);
            }
        }

        return goodsList;
    }

    // 위시리스트 삭제
    public boolean deleteWishList(Long userId, Long goodsId) {
        Users user = userRepository.findUsersById(userId);
        Goods goods = goodsRepository.findGoodsById(goodsId);

        if (user != null && goods != null) {
            List<WishListHasGoods> wlhg = wishListHasGoodsRepository.findByUserIdToWishList(userId, goodsId);
            if (wlhg.isEmpty()) {
                return false;
            }
            WishLists wishList = wlhg.get(0).getWishList();

            if(wishList == null){
                throw new RuntimeException("WishList is null");
            }

            Users users = wishList.getUser();
            if (users == null) {
                throw new RuntimeException("User is null");
            }

            WishListHasGoods temp = wishListHasGoodsRepository.findByWishListId(wishList.getId());
            wishListHasGoodsRepository.delete(temp);
            wishListsRepository.delete(wishList);
            return true;
        }
        return false;
    }


}
