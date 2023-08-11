package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users_id; // PK, FK

    @ManyToOne
    @JoinColumn(name = "markets_id")
    private Markets markets_id; // PK, FK

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String price;

    @Column(nullable = true)
    private String weight;

    @Column(nullable = false)
    private Integer rest;

    @Column(nullable = false)
    private String shelf_life;

    @Column(nullable = true)
    private Integer sale;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Timestamp updated_at;

    public Goods createGoods(String title, String price, Integer rest, String shelf_life){
        this.title = title;
        this.price = price;
        this.rest = rest;
        this.shelf_life = shelf_life;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());

        return this;
    }

    public void deleteGoods(int rest) { this.rest = 0;}

    public void modifyPrice(String price) { this.price = price; }

    public void setId(Long id) {
        this.id = id;
    }

}
