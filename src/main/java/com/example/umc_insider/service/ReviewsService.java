package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Reviews;
import com.example.umc_insider.dto.request.PostReviewsReq;
import com.example.umc_insider.dto.response.PostReviewsRes;
import com.example.umc_insider.repository.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    // 후기 등록
    public PostReviewsRes createReviews(PostReviewsReq postReviewsReq) throws BaseException {
        try {
            Reviews reviews = new Reviews();
            reviews.createReviews(postReviewsReq.getGoods_id(), postReviewsReq.getContent(), postReviewsReq.getPoint());
            reviewsRepository.save(reviews);
            return new PostReviewsRes(reviews.getContent(), reviews.getPoint());
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
