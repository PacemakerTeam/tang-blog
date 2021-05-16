package com.devTest.Firstblog.repository;

import com.devTest.Firstblog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
