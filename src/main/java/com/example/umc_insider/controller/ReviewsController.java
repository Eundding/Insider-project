package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.dto.request.*;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.service.ReviewsService;
import com.example.umc_insider.domain.Reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }


    // 후기등록
    @PostMapping("/create")
    public BaseResponse<PostReviewsRes> createReview(@RequestBody PostReviewsReq postReviewsReq) {
        try {
            return new BaseResponse<>(reviewsService.createReviews(postReviewsReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 포인트 높은 순으로 후기 조회
    @GetMapping("/read")
    public BaseResponse<List<GetReviewsRes>> getReview() {
        try {
            List<GetReviewsRes> reviews = reviewsService.getAllReviews();
            return new BaseResponse<>(reviews);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
