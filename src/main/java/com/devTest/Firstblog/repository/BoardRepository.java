package com.devTest.Firstblog.repository;

import com.devTest.Firstblog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 생략 가능하다.
public interface BoardRepository extends JpaRepository<Board, Integer> {

}