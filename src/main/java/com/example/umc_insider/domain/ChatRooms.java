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

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(nullable=true)
    private Boolean seller_or_not;

    @Column(nullable = true)
    private Boolean buyer_or_not;

    public ChatRooms sellerCheck(Boolean seller_or_not) {
        this.seller_or_not = true;
        return this;
    }

    public ChatRooms buyerCheck(Boolean buyer_or_not) {
        this.buyer_or_not = true;
        return this;
    }

    public ChatRooms createChatRooms(Long sellerIdx, Long buyerIdx, Long goodsIdx){
        this.seller = new Users();
        this.seller.setId(sellerIdx);
        this.buyer = new Users();
        this.buyer.setId(buyerIdx);
        this.goods = new Goods();
        this.goods.setId(goodsIdx);

        // Check both seller and buyer
        sellerCheck(true);
        buyerCheck(true);

        this.created_at = new Timestamp(System.currentTimeMillis());
        return this;
    }


    public void setSeller(Users user) {
        this.seller = user;
    }
    public void setBuyer(Users user){this.buyer = user;}
    public void setGoods(Goods goods){this.goods = goods;}


    public Goods getGoods() {
        return this.goods;
    }

}
