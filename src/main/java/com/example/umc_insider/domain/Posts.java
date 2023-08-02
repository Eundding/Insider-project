package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users_id;

    @ManyToOne
    @JoinColumn(name = "markets_id")
    private Users markets_id;

    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files_id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Timestamp updated_at;

    public Posts createPost(String title, String content){
        this.users_id = users_id;
        this.markets_id = markets_id;
        this.files_id = files_id;
        this.title = title;
        this.content = content;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
        return this;
    }

    public void modifyTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}