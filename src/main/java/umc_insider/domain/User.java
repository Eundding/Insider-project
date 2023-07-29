package umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor

@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String pw;

//    @Column(nullable = true)
//    private String address;


    public User createUser(String userId, String nickName, String email, String password) {
        this.user_id = userId;
        this.email = email;
        this.nickname = nickName;
        this.pw = password;
       // this.address = address;
        return this;
    }


}
