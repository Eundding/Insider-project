package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.request.*;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.service.GoodsService;
//import com.example.umc_insider.service.S3Service;
import com.example.umc_insider.service.UsersService;
import com.example.umc_insider.utils.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;
//    private final GoodsRepository goodsRepository;
//    private final JwtService jwtService;
//    private final S3Service s3Service;

    //    @Autowired
//    public GoodsController(GoodsService goodsService, S3Service s3Service){
//        this.goodsService = goodsService;
//        this.s3Service = s3Service;
//    }

//    @Autowired
//    public GoodsController(GoodsService goodsService) {
//        this.goodsService = goodsService;
//    }


//    @PostMapping("/goods")
//    public BaseResponse<PostGoodsRes> createGoods(@RequestBody PostGoodsReq postgoodsReq, @RequestPart("image") MultipartFile image) throws BaseException {
//        PostGoodsRes response = goodsService.createGoods(postgoodsReq);
//        image = postgoodsReq.getImageUrl();
//        s3Service.uploadFileToS3(image);
//
//        return new BaseResponse<>(response);
//
//    }

    // 상품등록
    @PostMapping("/create")
    public BaseResponse<PostGoodsRes> createGoods(@RequestBody PostGoodsReq postgoodsReq) throws BaseException {
//        try {
            PostGoodsRes response = goodsService.createGoods(postgoodsReq);
            return new BaseResponse<>(response);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
    }

    // 상품조회
    @GetMapping("/read")
    public BaseResponse<List<GetGoodsRes>> getGoods(@RequestParam(required = false) String email){
        return new BaseResponse<>(goodsService.getGoods());
    }

    // 상품삭제
//    @PostMapping("/delete")
//    public BaseResponse<String> deleteGoods(@RequestParam long id, long users_id, long markets_id) throws BaseException {
//        // jwt에서 idx 추출
//        long userIdByJwt = jwtService.getId();
//        Goods goods = goodsRepository.getReferenceById(id);
//        Users user = goods.getUsers_id();
//
//        PatchGoodsReq patchGoodsReq = new PatchGoodsReq(id, users_id, markets_id);
//        goodsService.deleteGoods(patchGoodsReq);
//        String result = "상품이 삭제되었습니다.";
//        return new BaseResponse<>(result);
//    }

    // 상품 가격변경
    @PostMapping("/modifyPrice")
    public BaseResponse<String> modifyPrice(@RequestBody PostModifyPriceReq postModifyPriceReq) {
        goodsService.modifyPrice(postModifyPriceReq);
        String result = "상품 가격이 변경되었습니다.";
        return new BaseResponse<>(result);
    }
}
