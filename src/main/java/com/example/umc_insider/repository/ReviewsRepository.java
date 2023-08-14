package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository {
    List<Reviews> findReviews();

    List<Reviews> findByPointContaining(Integer point);

    List<Reviews> save(Reviews reviews);
}

