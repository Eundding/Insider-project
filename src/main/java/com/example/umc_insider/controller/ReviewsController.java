//package com.example.umc_insider.controller;
//
//import com.example.umc_insider.config.BaseException;
//import com.example.umc_insider.config.BaseResponse;
//import com.example.umc_insider.config.BaseResponseStatus;
//import com.example.umc_insider.dto.request.*;
//import com.example.umc_insider.dto.response.*;
//import com.example.umc_insider.service.ReviewsService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/reviews")
//public class ReviewsController {
//
//    private final ReviewsService reviewsService;
//
//    @Autowired
//    public ReviewsController(ReviewsService reviewsService) {
//        this.reviewsService = reviewsService;
//    }
//
//
//    // 후기등록
//    @PostMapping("/create")
//    public BaseResponse<PostReviewsRes> createReview(@RequestBody PostReviewsReq postReviewsReq) {
//        try {
//            return new BaseResponse<>(reviewsService.createReviews(postReviewsReq));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//}
