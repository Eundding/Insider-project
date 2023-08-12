package com.example.umc_insider.repository;

import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {


}