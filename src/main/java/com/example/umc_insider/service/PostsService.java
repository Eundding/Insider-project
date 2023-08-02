//package com.example.umc_insider.service;
//
//import com.example.umc_insider.config.BaseException;
//import com.example.umc_insider.domain.Posts;
//import com.example.umc_insider.dto.request.PatchPostsReq;
//import com.example.umc_insider.dto.request.PostPostsReq;
//import com.example.umc_insider.dto.response.PostPostsRes;
//import com.example.umc_insider.repository.PostsRepository;
//import com.example.umc_insider.repository.UserRepository;
//import com.example.umc_insider.repository.UsersRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class PostsService {
//    private final PostsRepository postsRepository;
//    private final UserRepository userRepository;
//
//    // 게시물 생성
//    public PostPostsRes createPost(PostPostsReq postPostsReq) throws BaseException {
//        try {
//            Posts post = new Posts();
//            // 유저 검색
//
//            // 시장 검색
//
//            // 파일 ?
//
//            // 게시물 등록
//
//            post.createPost(postPostsReq.getTitle(), postPostsReq.getContent());
//            postsRepository.save(post);
//            return new PostPostsRes(postPostsReq.getTitle(), postPostsReq.getContent());
//        } catch (Exception e) {
//            throw new BaseException();
//        }
//    }
//
//    // 게시물 제목, 내용 수정
//    @Transactional
//    public void modifyTitleContent(PatchPostsReq patchPostsReq){
//        Posts post = postsRepository.getReferenceById(patchPostsReq.getId());
//        post.modifyTitleContent(patchPostsReq.getTitle(), patchPostsReq.getContent());
//    }
//
//
//}
//
