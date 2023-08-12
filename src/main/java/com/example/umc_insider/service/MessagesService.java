//package com.example.umc_insider.service;
//
//import com.example.umc_insider.domain.Messages;
//import com.example.umc_insider.dto.request.PostMessagesReq;
//import com.example.umc_insider.dto.response.PostMessagesRes;
//import com.example.umc_insider.repository.ChatRoomsRepository;
//import com.example.umc_insider.repository.MessagesRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MessagesService {
//    private MessagesRepository messagesRepository;
//    private ChatRoomsRepository chatRoomsRepository;
//
//    @Autowired
//    public MessagesService(MessagesRepository messagesRepository, ChatRoomsRepository chatRoomsRepository){
//        this.messagesRepository = messagesRepository;
//        this.chatRoomsRepository = chatRoomsRepository;
//    }
//
//    // 채팅 보내기
//    public PostMessagesRes createMessages(PostMessagesReq postMessagesReq) {
//        Messages messages = new Messages();
//        messages.createMessages(postMessagesReq.getContent(), postMessagesReq.getChatRoomId(), postMessagesReq.getSenderId());
//    }
//
//}
