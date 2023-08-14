package com.example.umc_insider.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.GetChatRoomByUserRes;
import com.example.umc_insider.dto.response.GetMessagesRes;
import com.example.umc_insider.dto.response.PostChatRoomsRes;
import com.example.umc_insider.repository.ChatRoomsRepository;
import com.example.umc_insider.repository.MessagesRepository;
import com.example.umc_insider.service.ChatRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRooms")
public class ChatRoomsController {
    private final ChatRoomsService chatRoomsService;
    private final ChatRoomsRepository chatRoomsRepository;
    private final MessagesRepository messagesRepository;

    @Autowired
    public ChatRoomsController(ChatRoomsService chatRoomsService, ChatRoomsRepository chatRoomsRepository, MessagesRepository messagesRepository){
        this.chatRoomsRepository = chatRoomsRepository;
        this.messagesRepository = messagesRepository;
        this.chatRoomsService = chatRoomsService;
    }

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

    // 유저의 채팅방 목록 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<GetChatRoomByUserRes>>  getChatRoomByUser(@PathVariable("id") Long userId) {
        List<GetChatRoomByUserRes> chatRoomByUserResList = chatRoomsService.findChatRoomByUserId(userId);
        return ResponseEntity.ok(chatRoomByUserResList);
    }


}

