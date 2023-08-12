package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.PostChatRoomsRes;
import com.example.umc_insider.service.ChatRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRooms")
public class ChatRoomsController {

    @Autowired
    private ChatRoomsService chatRoomsService;

    // 새로운 채팅방 생성하기
    @PostMapping("/create")
    public BaseResponse<PostChatRoomsRes> createChatRoom(@RequestBody PostChatRoomsReq postChatRoomsReq) throws BaseException {
        PostChatRoomsRes response = chatRoomsService.createChatRooms(postChatRoomsReq);
        return new BaseResponse<>(response);
    }

    // 채팅방에 참가한 유저 목록 조회하기
    @GetMapping("/{chatRoomId}/users")
    public ResponseEntity<List<Users>> getUsersInChatRoom(@PathVariable Long chatRoomId) {
        List<Users> users = chatRoomsService.getUsersInChatRoom(chatRoomId);
        return ResponseEntity.ok(users);
    }

//    // 채팅방 대화 내용 조회하기
//    @GetMapping("/chat-rooms/{chatRoomId}/messages")
//    public ResponseEntity<List<Messages>> getMessagesInChatRoom(@PathVariable Long chatRoomId) {
//        List<Messages> messages = chatRoomsService.getMessagesInChatRoom(chatRoomId);
//        return ResponseEntity.ok(messages);
//    }
}

