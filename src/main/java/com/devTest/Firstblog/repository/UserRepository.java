package com.devTest.Firstblog.repository;

import com.devTest.Firstblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// DAO
// 자동으로 bean등록이 된다.
@Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
    //JPA Naming 쿼리 전략 잘 맞춰서 네이밍 하면 쿼리짜줌.
    //SELECT * FROM user WHERE username=?1 AND password = ?2;
    User findByUsernameAndPassword(String username, String password);

//    @Query(value="SELECT * FROM user WHERE username=?1 AND password = ?2", nativeQuery = true )
//    User login(String username, String password);
//  복잡한 거 사용할 때 nativeQuery쓰겟다.
}
