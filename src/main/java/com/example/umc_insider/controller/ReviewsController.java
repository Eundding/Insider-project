package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.request.*;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.service.ReviewsService;
import com.example.umc_insider.domain.Reviews;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.repository.ChatRoomsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;
    private final ChatRoomsRepository chatRoomsRepository;

    @Autowired
    public ReviewsController(ReviewsService reviewsService, ChatRoomsRepository chatRoomsRepository) {
        this.reviewsService = reviewsService;
        this.chatRoomsRepository = chatRoomsRepository;
    }


    // 후기등록
    @PostMapping("/create/{chatRoomId}")
    public BaseResponse<PostReviewsRes> createReview(@PathVariable Long chatRoomId, @RequestBody PostReviewsReq postReviewsReq) {
        try {
            if (chatRoomId == null) {
                throw new BaseException(BaseResponseStatus.CHATROOM_ID_NOT_PROVIDED);
            }

            Optional<ChatRooms> optionalChatRoom = chatRoomsRepository.findById(chatRoomId);

            if (!optionalChatRoom.isPresent()) {
                throw new BaseException(BaseResponseStatus.CHATROOM_NOT_FOUND);
            }

            ChatRooms foundChatRooms = optionalChatRoom.get();

            if (!foundChatRooms.getSellOrNot()) {
                throw new BaseException(BaseResponseStatus.CHATROOM_NOT_SOLD);
            }

            // Get the goods from the chat room.
            Goods goodsFromChatroom = foundChatRooms.getGoods();

            if (goodsFromChatroom == null) {
                throw new BaseException(BaseResponseStatus.GOODS_NOT_FOUND_IN_CHATROOM);
            }

            String content = postReviewsReq.getContent();
            Integer point = postReviewsReq.getPoint();

            return new BaseResponse<>(reviewsService.createReview(goodsFromChatroom, foundChatRooms, content, point));

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 판매완료 추가 전 코드
//    @PostMapping("/create")
//    public BaseResponse<PostReviewsRes> createReview(@RequestBody PostReviewsReq postReviewsReq) {
//        try {
//            return new BaseResponse<>(reviewsService.createReviews(postReviewsReq));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

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
