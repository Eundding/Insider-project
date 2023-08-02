package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor

public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "posts_id")
    Posts posts_id;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private Timestamp created_at;

}