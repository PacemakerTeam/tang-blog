package com.devTest.Firstblog.service;


import com.devTest.Firstblog.model.Board;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.BoardRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC해준다.
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional //여러 가지 프로세스가 모여서 하나의 서비스가 될 수 있다. 이 전체가 성공해야만 커밋해야한다.
                    //중간에 몇 개만 성공하고 나서 db에 커밋되면 안되잖아. 그래서 @Transactional을 붙여줘서 사용할 수 있다.
    public void writeBoard(Board board, User user){ //title, content
        board.setCount(0);
        board.setUser(user);
        try{
            boardRepository.save(board);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board boardDetail(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다");
                });
    }

    @Transactional
    public void writeDelete(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void writeUpdate(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
            .orElseThrow(()->{
                return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다");
            }); //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시(Service가 종료될 때)- 트랜잭션이 종료됨. 이 때 더티체킹 -자동 업데이트가 됨. db flush
    }
}