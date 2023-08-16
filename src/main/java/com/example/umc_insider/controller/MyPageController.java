//package com.example.umc_insider.controller;
//import com.example.umc_insider.domain.Users;
//import com.example.umc_insider.dto.response.GetMyPageRes;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.umc_insider.service.GoodsService;
//
//import java.util.List;
//
//@RestController
//public class MyPageController {
//    private final GoodsService goodsService;
//
//    public MyPageController(GoodsService goodsService) {
//        this.goodsService = goodsService;
//    }
//
//    // MyPage 조회
//    @GetMapping("/mypage/{users_id}")
//    public ResponseEntity<List<GetMyPageRes>> getGoodsByUserId(@PathVariable Users users_id) {
//        List<GetMyPageRes> goodsList = goodsService.getGoodsByUser(users_id);
//        return ResponseEntity.ok(goodsList);
//    }
//}
