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

    // goods zipcode 추가하는 건 어떤가요?

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

        return this;
    }


//    public Goods createGoods(String title, String price, Integer rest, String shelf_life, Users usersId, Markets marketsId, Integer sale, String imageUrl) {
//        this.title = title;
//        this.price = price;
//        this.rest = rest;
//        this.shelf_life = shelf_life;
//        this.users_id = usersId;
//        this.markets_id = marketsId;
//        this.sale = sale;
//        this.imageUrl = imageUrl;
//        this.created_at = new Timestamp(System.currentTimeMillis());
//        this.updated_at = new Timestamp(System.currentTimeMillis());
//
//        return this;
//    }

    public void setTitle(String title) {
        this.title = title;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setPrice(String price) {
        this.price = price;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setRest(Integer rest) {
        this.rest = rest;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setUsersId(Users usersId) {
        this.users_id = usersId;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setMarketsId(Markets marketsId) {
        this.markets_id = marketsId;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setSale(Integer sale) {
        this.sale = sale;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void deleteGoods() {this.rest = 0;}

    public void modifyPrice(String price) { this.price = price; }

}
