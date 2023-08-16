package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String pw;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zip_code", referencedColumnName = "zip_code")
    private Address address;

    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Timestamp updated_at;


    public Users createUser(String userId, String nickName, String email, String password) {
        this.user_id = userId;
        this.email = email;
        this.nickname = nickName;
        this.pw = password;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
        this.address = address;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUser_id(String id) {this.user_id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setPw(String pw) {this.pw = pw;}
    public void setCreated_at(){this.created_at =  new Timestamp(System.currentTimeMillis());}
    public void setUpdated_at(){this.updated_at =  new Timestamp(System.currentTimeMillis());}
    public void setAddress(Address address) {this.address = address; }

}
