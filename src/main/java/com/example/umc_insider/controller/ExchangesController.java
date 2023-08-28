package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.response.PostExchangesRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.service.ExchangesService;
import com.example.umc_insider.dto.request.PostExchangesReq;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/exchanges")
public class ExchangesController {
    private final ExchangesService exchangesService;
    private final S3Service s3Service;
    @Autowired
    public ExchangesController(ExchangesService exchangesService, S3Service s3Service) {
        this.exchangesService = exchangesService;
        this.s3Service = s3Service;
    }

    // 교환 등록
    @PostMapping("/create")
    public BaseResponse<PostExchangesRes> createExchanges(@RequestPart("postExchangesReq") PostExchangesReq postExchangesReq, @RequestPart("image") MultipartFile image) throws BaseException {
        Exchanges newExchanges = exchangesService.createNewExchangesInstance(postExchangesReq, image);
        String url = this.s3Service.uploadExchangesS3(image, newExchanges);
        PostExchangesRes response = new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser().getId());
        return new BaseResponse<>(response);
    }


}
