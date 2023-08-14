package com.example.umc_insider.repository;

import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    @Query("select m from ChatRooms m where m.id = :id")
    ChatRooms findChatRoomsById(@Param("id") long id);

    @Query("SELECT c FROM ChatRooms c WHERE c.seller.id = :id OR c.buyer.id = :id")
    List<ChatRooms> findBySellerIdOrBuyerId(Long id);


}
