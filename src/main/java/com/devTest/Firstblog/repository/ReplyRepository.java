package com.devTest.Firstblog.repository;

import com.devTest.Firstblog.dto.ReplySaveRequestDto;
import com.devTest.Firstblog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1,?2,?3,now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); //업데이트 된 행의 개수 리턴
}
