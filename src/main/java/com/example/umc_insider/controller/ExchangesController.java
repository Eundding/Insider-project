package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostExchangesRes;
import com.example.umc_insider.dto.response.PostGoodsRes;
import com.example.umc_insider.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.service.ExchangesService;
import com.example.umc_insider.dto.request.PostExchangesReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

    // 교환 조회
    @GetMapping("/read")
    public BaseResponse<List<PostExchangesRes>> getAllExchanges(@RequestParam(required = false) String title) throws BaseException{
        try{
            if (title == null) {
                return new BaseResponse<>(exchangesService.getExchanges());
            }
            return new BaseResponse<>(exchangesService.getExchangesByTitle(title));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 교환삭제
//    @DeleteMapping ("/delete/{id}")
//    public long deleteGoods(@PathVariable long id){
//        this.exchangesService.deleteGoods(id);
//        return id;
//    }

//    // id로 Goods 조회
//    @GetMapping("/{id}")
//    public GetGoodsRes getGoodsById(@PathVariable Long id){
//        GetGoodsRes getGoodsRes = goodsService.getGoodsById(id);
//        return getGoodsRes;
//    }
//
//    // category_id로 Goods 조회
//    @GetMapping("/category/{category_id}")
//    public ResponseEntity<List<GetGoodsRes>> getGoodsByCategoryId(@PathVariable Long category_id) {
//        return ResponseEntity.ok(goodsService.getGoodsByCategoryId(category_id));
//    }
//
//    // 상품수정 - PUT
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Goods> update(@PathVariable Long id, @RequestBody Goods goods) {
//        Goods updatedGoods = goodsService.update(id, goods);
//        if (updatedGoods != null) {
//            return ResponseEntity.ok(updatedGoods);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
