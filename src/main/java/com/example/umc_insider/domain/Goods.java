package com.example.umc_insider.domain;

import com.example.umc_insider.dto.request.PostGoodsReq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.sql.Time;
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

    @Column(nullable = true)
    private String name; // name 추가

    public Goods createGoods(String title, String price, Integer rest, String shelf_life, Long userIdx, String name){
        this.title = title;
        this.price = price;
        this.rest = rest;
        this.shelf_life = shelf_life;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
        this.name = name;
        return this;
    }
    public Goods(PostGoodsReq postgoodsReq, Users user) {
        super();
        this.users_id = user;
        this.title = postgoodsReq.getTitle();
        this.price = postgoodsReq.getPrice();
        this.rest = postgoodsReq.getRest();
        this.name = postgoodsReq.getName();
        this.shelf_life = postgoodsReq.getShelf_life();
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at =  new Timestamp(System.currentTimeMillis());
    }

    public void deleteGoods(int rest) { this.rest = 0;}

    public void modifyPrice(String price) { this.price = price; }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {this.title = title;}
    public void setPrice(String price) { this.price = price;}
    public void setRest(Integer rest) { this.rest = rest;}
    public void setShelf_life(String shelf_life){this.shelf_life = shelf_life;}
    public void setCreated_at(){Timestamp created_at = new Timestamp(System.currentTimeMillis());}
    public void setUser(Users user) {
        this.users_id = user;
    }

    public void setImageUrl(String url){
        this.imageUrl = url;
    }

}
