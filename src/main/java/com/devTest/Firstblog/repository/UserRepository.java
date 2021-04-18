package com.devTest.Firstblog.repository;

import com.devTest.Firstblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// w자동으로 bean등록이 된다.
//@Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {

}
