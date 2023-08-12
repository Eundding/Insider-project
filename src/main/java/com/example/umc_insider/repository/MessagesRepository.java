package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
}
