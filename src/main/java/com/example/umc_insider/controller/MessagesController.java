package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.dto.request.PostMessagesReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetMessagesRes;
import com.example.umc_insider.dto.response.PostMessagesRes;
import com.example.umc_insider.service.ChatRoomsService;
import com.example.umc_insider.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final MessagesService messagesService;
    private final ChatRoomsService chatRoomsService;

    @Autowired
    public MessagesController(MessagesService messagesService, ChatRoomsService chatRoomsService) {
        this.messagesService = messagesService;
        this.chatRoomsService = chatRoomsService;
    }

    @PostMapping("/send")
    public BaseResponse<PostMessagesRes> createMessages(@RequestBody PostMessagesReq postMessagesReq) {
        PostMessagesRes response = messagesService.createMessages(postMessagesReq);
        return new BaseResponse<>(response);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<GetMessagesRes>> getMessagesInChatRoom(@PathVariable Long chatRoomId) {
//        List<GetMessagesRes> messages = chatRoomsService.findMessagesInChatRoom(chatRoomId);
        List<GetMessagesRes> messages = messagesService.findMessagesInChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Messages> getMessageById(@PathVariable("id") Long id) {
//        Messages message = messagesService.getMessageById(id);
//        return ResponseEntity.ok(message);
//    }
//
//    @GetMapping("/inbox")
//    public ResponseEntity<List<Messages>> getInboxForUser(@RequestParam("userId") Long userId) {
//        List<Messages> inbox = messagesService.getInboxForUser(userId);
//        return ResponseEntity.ok(inbox);
//    }
//
//    @GetMapping("/sent")
//    public ResponseEntity<List<Messages>> getSentMessagesForUser(@RequestParam("userId") Long userId) {
//        List<Messages> sentMessages = messagesService.getSentMessagesForUser(userId);
//        return ResponseEntity.ok(sentMessages);
//    }


}
