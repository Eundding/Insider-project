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
        try {
            PostGoodsRes response = goodsService.createGoods(postgoodsReq);
            return new BaseResponse<>(response);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 상품조회
    @GetMapping("/read")
    public BaseResponse<List<GetGoodsRes>> getGoods(@RequestParam(required = false) String title){
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음
        try{
            if (title == null) { // query string인 title이 없을 경우, 그냥 전체 goods 정보를 불러온다.
                return new BaseResponse<>(goodsService.getGoods());
            }
            // query string인 title이 있을 경우, 조건을 만족하는 goods 정보들을 불러온다.
            return new BaseResponse<>(goodsService.getGoodsByTitle(title));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상품삭제
    @DeleteMapping ("/delete/{id}")
    public long deleteGoods(@PathVariable long id){
        this.goodsService.deleteGoods(id);
        return id;
    }

    // 상품 가격변경
    @PostMapping("/modifyPrice")
    public BaseResponse<String> modifyPrice(@RequestBody PostModifyPriceReq postModifyPriceReq) {
        goodsService.modifyPrice(postModifyPriceReq);
        String result = "상품 가격이 변경되었습니다.";
        return new BaseResponse<>(result);
    }
}
