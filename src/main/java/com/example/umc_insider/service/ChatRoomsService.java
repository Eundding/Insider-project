package com.example.umc_insider.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.PostChatRoomsRes;
import com.example.umc_insider.repository.ChatRoomsRepository;
import com.example.umc_insider.repository.MessagesRepository;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {
    private ChatRoomsRepository chatRoomsRepository;
    private MessagesRepository messagesRepository;
    private UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public ChatRoomsService(UserRepository userRepository, JwtService jwtService, ChatRoomsRepository chatRoomsRepository, MessagesRepository messagesRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.chatRoomsRepository = chatRoomsRepository;
        this.messagesRepository = messagesRepository;
    }


    // 새로운 채팅방 생성하기
    public PostChatRoomsRes createChatRooms(PostChatRoomsReq postChatRoomsReq) throws BaseException {
        ChatRooms chatRooms = new ChatRooms();
        chatRooms.createChatRooms(postChatRoomsReq.getSellerIdx(), postChatRoomsReq.getBuyerIdx());
        Users seller = userRepository.findById(postChatRoomsReq.getSellerIdx())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 판매자 입니다."));
        Users buyer = userRepository.findById(postChatRoomsReq.getBuyerIdx())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 구매자 입니다."));

        chatRooms.setSeller(seller);
        chatRooms.setBuyer(buyer);

        chatRoomsRepository.save(chatRooms);
        return new PostChatRoomsRes(chatRooms.getId());

    }

    // 채팅방에 참가한 유저 목록 조회하기
    public List<Users> getUsersInChatRoom(Long chatRoomId) {
        ChatRooms chatRooms = chatRoomsRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));
        List<Users> users = new ArrayList<>();
        users.add(chatRooms.getSeller());
        users.add(chatRooms.getBuyer());
        return users;
    }
//
//    // 채팅방 대화 내용 조회하기
//    public List<Messages> getMessagesInChatRoom(Long chatRoomId) {
//        chatRoomsRepository.findById(chatRoomId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));
//
//        return messagesRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId);
//    }
}
