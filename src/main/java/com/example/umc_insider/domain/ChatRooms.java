package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @Column(nullable = false)
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users seller; // FK

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Users buyer; //  FK


    public ChatRooms createChatRooms(Long sellerIdx, Long buyerIdx){
        this.seller = new Users();
        this.seller.setId(sellerIdx);
        this.buyer = new Users();
        this.buyer.setId(buyerIdx);
        this.created_at = new Timestamp(System.currentTimeMillis());
        return this;
    }

    public void setSeller(Users user) {
        this.seller = user;
    }
    public void setBuyer(Users user){this.buyer = user;}
}
