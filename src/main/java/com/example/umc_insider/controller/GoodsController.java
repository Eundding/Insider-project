package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.dto.response.PostUserRes;
import com.example.umc_insider.service.GoodsService;
//import com.example.umc_insider.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GoodsController {
    private final GoodsService goodsService;
//    private final S3Service s3Service;

//    @Autowired
//    public GoodsController(GoodsService goodsService, S3Service s3Service){
//        this.goodsService = goodsService;
//        this.s3Service = s3Service;
//    }

    @Autowired
    public GoodsController(GoodsService goodsService){
        this.goodsService = goodsService;

    }

    @PostMapping("/goods")
    public BaseResponse<PostGoodsRes> createGoods(@RequestBody PostGoodsReq postgoodsReq) throws BaseException {
        PostGoodsRes response = goodsService.createGoods(postgoodsReq);
        return new BaseResponse<>(response);

    }

//    @PostMapping("/goods")
//    public BaseResponse<PostGoodsRes> createGoods(@RequestBody PostGoodsReq postgoodsReq, @RequestPart("image") MultipartFile image) throws BaseException {
//        PostGoodsRes response = goodsService.createGoods(postgoodsReq);
//        image = postgoodsReq.getImageUrl();
//        s3Service.uploadFileToS3(image);
//
//        return new BaseResponse<>(response);
//
//    }
}
