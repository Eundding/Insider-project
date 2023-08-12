package com.example.umc_insider.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "message")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRooms chatRoomId; //FK

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender; // PK, FK

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean readOrNot;

    @Column(nullable = false)
    private Timestamp created_at;

    public Messages createMessages(String content){
        this.content = content;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.readOrNot = false; // 읽으면 true
        return this;
    }

    public void setSender(Users user){this.sender = user;}
    public void setChatRoomId(ChatRooms chatroom){this.chatRoomId = chatroom;}



}
