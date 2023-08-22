package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wish_lists")
public class WishLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @OneToMany(mappedBy = "wishList")
    private List<WishListHasGoods> wishListHasGoodsList = new ArrayList<>();



    public void setCreatedAt(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
