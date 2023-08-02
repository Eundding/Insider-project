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

    @Column(nullable = false)
    private String name; // nickname

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = true)
    private String weight;

    @Column(nullable = false)
    private String rest;

    @Column(nullable = false)
    private String person;

    @Column(nullable = false)
    private String shelf_life;

    @Column(nullable = false)
    private Long users_id; // PK, FK

    @Column(nullable = false)
    private Long markets_id; // PK, FK

    @Column(nullable = false)
    private Integer sale;

    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Timestamp updated_at;

    public Goods createGoods(String name, Integer price, String weight, String rest, String person, String shelf_life, Long usersId, Long marketsId, Integer sale) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.rest = rest;
        this.person = person;
        this.shelf_life = shelf_life;
        this.users_id = usersId;
        this.markets_id = marketsId;
        this.sale = sale;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());

        return this;
    }

}
