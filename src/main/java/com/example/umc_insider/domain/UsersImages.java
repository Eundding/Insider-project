package com.example.umc_insider.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users_images")
public class UsersImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    Users user;

    @Column(name = "image_url")
    private String image_url;

    public void putImg(String image_url) {
        this.image_url = image_url;
        user.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
    }

    public UsersImages createImage(Users user) {
        this.image_url = "NONE";
        this.user = user;
        return this;
    }
}
