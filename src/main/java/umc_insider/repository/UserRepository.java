package umc_insider.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc_insider.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
    @Query("select count(m) from User m where m.email = :email")
    Integer findByEmailCount(@Param("email") String email);

//    @Query("select m from User m where m.userId = :userId")
//    User findUserByUserId(@Param("userId") String userId);

    @Query("select m from User m")
    List<User> findUsers();



//    @Query("select m from User m where m.nickName = :nickName")
//    List<User> findUserByNickName(@Param("nickName") String nickName);

}
