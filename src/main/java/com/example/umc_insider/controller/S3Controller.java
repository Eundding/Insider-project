//package com.example.umc_insider.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//
//@Controller
//public class S3Controller {
//    // 상품 등록할 떄 참고할 예제 - 이미지와 DTO 한꺼번에 보내기 ex) 유저 추가 + 이미지 추가
//    // Response Entity 필요할듯?
//    @PostMapping("/userProfile/{id}")
//    public void createUser(@PathVariable("id") String id, @RequestPart("userProfile") UserProfile userProfile, @RequestPart("image") MultipartFile image) {
//        userProfile.setId(id);
//        userMap.put(id, userProfile);
//        s3Service.uploadFileToS3(image);
//    }
//}
