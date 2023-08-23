package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.dto.request.PostWishListsReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.GetWishListsRes;
import com.example.umc_insider.dto.response.PostWishListsRes;
import com.example.umc_insider.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListsController {
    public WishListService wishListService;

    @Autowired
    public WishListsController(WishListService wishListService){
        this.wishListService = wishListService;
    }


    @PostMapping("/create")
    public BaseResponse<PostWishListsRes> addGoodsToWishList(@RequestBody PostWishListsReq postWishListsReq) throws BaseException {
        PostWishListsRes response = wishListService.addGoodsToWishList(postWishListsReq);
        return new BaseResponse<>(response);
    }

    // 유저별 위시리스트 조회
//    @GetMapping("/{userId}")
//    public ResponseEntity<List<GetGoodsRes>>getGoodsInWishList(@PathVariable Long userId) {
//        List<GetGoodsRes> goodsList = wishListService.getGoodsInWishList(userId);
//        return new ResponseEntity<>(goodsList, HttpStatus.OK);
//    }

    // 위시리스트 삭제
    @DeleteMapping("/delete/{userId}/{goodsId}")
    public BaseResponse<PostWishListsRes>  deleteWishList(@PathVariable Long userId, @PathVariable Long goodsId) throws BaseException{

        PostWishListsRes response = wishListService.deleteWishList(userId, goodsId);
        return new BaseResponse<>(response);
    }

    // userId, goodsId로 위시리스트에 있는지 true false 반환
    @GetMapping("/check/{userId}/{goodsId}")
    public boolean checkWishList(@PathVariable Long userId, @PathVariable Long goodsId){
        boolean checked = wishListService.checkWishList(userId, goodsId);
        if(checked){
            return true;
        } else{
            return false;
        }
    }
}
