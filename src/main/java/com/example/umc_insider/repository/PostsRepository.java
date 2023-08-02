//package com.example.umc_insider.repository;
//
//import com.example.umc_insider.domain.Posts;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface PostsRepository {
//    @Query("select p from Posts p where p.title = :title")
//    Posts findPostbyTitle(@Param("title") String title);
//
//    @Query("select p from Posts p where p.content = :content")
//    Posts findPostbyContent(@Param("content") String content);
//
//    @Query("select p from Posts p where p.id = :id")
//    Posts getReferenceById(@Param("id") Long id);
//
//
//
//}
