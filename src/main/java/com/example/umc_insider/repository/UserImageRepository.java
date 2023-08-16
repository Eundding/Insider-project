package com.example.umc_insider.repository;

import com.example.umc_insider.domain.UsersImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserImageRepository extends JpaRepository<UsersImages, Long> {
    @Query("select ui.image_url from UsersImages ui where ui.id = :id")
    String findUrl(@Param("id") long id);
}
