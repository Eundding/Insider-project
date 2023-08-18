package com.example.umc_insider.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.GetChatRoomByUserRes;
import com.example.umc_insider.dto.response.GetMessagesRes;
import com.example.umc_insider.dto.response.PostChatRoomsRes;
import com.example.umc_insider.repository.ChatRoomsRepository;
import com.example.umc_insider.repository.GoodsRepository;
import com.example.umc_insider.repository.MessagesRepository;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {
    private ChatRoomsRepository chatRoomsRepository;
    private MessagesRepository messagesRepository;
    private UserRepository userRepository;
    private MessagesService messagesService;
    private GoodsRepository goodsRepository;

    @Autowired
    public ChatRoomsService(UserRepository userRepository, ChatRoomsRepository chatRoomsRepository, MessagesRepository messagesRepository, MessagesService messagesService, GoodsRepository goodsRepository) {
        this.userRepository = userRepository;
        this.chatRoomsRepository = chatRoomsRepository;
        this.messagesRepository = messagesRepository;
        this.messagesService = messagesService;
        this.goodsRepository = goodsRepository;
    }


    // 새로운 채팅방 생성하기
    public PostChatRoomsRes createChatRooms(PostChatRoomsReq postChatRoomsReq) throws BaseException {
        ChatRooms chatRooms = chatRoomsRepository.findBySellerIdAndBuyerIdAndGoodsId(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getGoodsId());
        if(chatRooms != null){
            return new PostChatRoomsRes(chatRooms.getId());
        }

        chatRooms = new ChatRooms();
        chatRooms.createChatRooms(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getGoodsId());

        Users seller = userRepository.findById(postChatRoomsReq.getSellerId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 판매자입니다."));
        Users buyer = userRepository.findById(postChatRoomsReq.getBuyerId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 구매자입니다."));
        Goods goods = goodsRepository.findById(postChatRoomsReq.getGoodsId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));

        chatRooms.setSeller(seller);
        chatRooms.setBuyer(buyer);
        chatRooms.setGoods(goods);

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

    // 유저의 채팅방 목록 조회
    public List<GetChatRoomByUserRes> findChatRoomByUserId(Long id) {
        List<ChatRooms> chatRoomsList = chatRoomsRepository.findBySellerIdOrBuyerId(id);
        Users user = userRepository.findUsersById(id);

        return chatRoomsList.stream().map(chatRoom -> {
            Long chatRoomId = chatRoom.getId();
            Long otherUserId = (chatRoom.getSeller().getId().equals(id)) ? chatRoom.getBuyer().getId() : chatRoom.getSeller().getId();

            Users otherUser = userRepository.findUsersById(otherUserId);
            String otherNickName = otherUser.getNickname(); // 상대방 닉네임

            Messages lastMessageObj = messagesService.getLastMessage(chatRoomId);
            String lastMessage = lastMessageObj != null ? lastMessageObj.getContent() : "";

            return new GetChatRoomByUserRes(chatRoomId, otherNickName, lastMessage);
        }).collect(Collectors.toList());

    }
}



