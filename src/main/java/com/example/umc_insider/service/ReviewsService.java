package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Reviews;
import com.example.umc_insider.dto.request.PostReviewsReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.GetReviewsRes;
import com.example.umc_insider.dto.response.PostReviewsRes;
import com.example.umc_insider.repository.ReviewsRepository;
import com.example.umc_insider.repository.ChatRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final ChatRoomsRepository chatRoomsRepository;


    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository, ChatRoomsRepository chatRoomsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.chatRoomsRepository = chatRoomsRepository;
    }

    // 후기 등록
    public PostReviewsRes createReviews(PostReviewsReq postReviewsReq) throws BaseException {
        try {
            Reviews reviews = new Reviews();
            reviews.createReviews(postReviewsReq.getGoods_id(), postReviewsReq.getChatRoomsId(), postReviewsReq.getContent(), postReviewsReq.getPoint());

            // Set Chat Room ID to the review
            reviews.setChatRoomsId(postReviewsReq.getChatRoomsId().getId());

            reviewsRepository.save(reviews);

            return new PostReviewsRes(reviews.getContent(), reviews.getPoint());
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Reviews createReview(PostReviewsReq postReviewsReq) throws BaseException {
        // Get the chat room from the repository by id.
        Optional<ChatRooms> optionalChatRoom = chatRoomsRepository.findById(postReviewsReq.getChatRoomsId().getId());

        if (!optionalChatRoom.isPresent()) {
            throw new BaseException(BaseResponseStatus.CHATROOM_NOT_FOUND);
        }

        ChatRooms chatRoom = optionalChatRoom.get();

        // Check if the chat room has been sold or not.
        if (!chatRoom.getSellOrNot()) {
            throw new BaseException(BaseResponseStatus.CHATROOM_NOT_SOLD);
        }

        // Create a review with the provided request and the found ChatRooms entity.
        Reviews review = new Reviews().createReviews(postReviewsReq.getGoods_id(), chatRoom, postReviewsReq.getContent(), postReviewsReq.getPoint());

        // Save and return the created review.
        return reviewsRepository.save(review);
    }

    public PostReviewsRes createReview(Goods goodsId, ChatRooms chatRoomId, String content, Integer point) {
        Reviews review = new Reviews().createReviews(goodsId, chatRoomId,content ,point);
        reviewsRepository.save(review);

        // You might need to adjust the following line based on how you have implemented PostReviewsRes.
        return new PostReviewsRes(review.getId(), review.getContent(), review.getPoint());
    }
    

// sell_or_not 추가 전 코드
//    public PostReviewsRes createReviews(PostReviewsReq postReviewsReq) throws BaseException {
//        try {
//            Reviews reviews = new Reviews();
//            reviews.createReviews(postReviewsReq.getGoods_id(), postReviewsReq.getContent(), postReviewsReq.getPoint());
//            reviewsRepository.save(reviews);
//            return new PostReviewsRes(reviews.getContent(), reviews.getPoint());
//        } catch (Exception exception) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }

    // all 상품 조회
    public List<GetReviewsRes> getAllReviews() throws BaseException {
        try {
            List<Reviews> reviewsList = reviewsRepository.findByOrderByPointDesc(); // 포인트 높은 순으로 리뷰 조회
            List<GetReviewsRes> getReviewsRes = reviewsList.stream()
                    .map(reviews -> new GetReviewsRes(reviews.getId(), reviews.getGoods_id(), reviews.getContent(), reviews.getPoint(), reviews.getCreated_at()))
                    .collect(Collectors.toList());
            return getReviewsRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
