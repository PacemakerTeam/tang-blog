package com.devTest.Firstblog.controller.api;

import com.devTest.Firstblog.config.auth.PrincipalDetail;
import com.devTest.Firstblog.dto.ReplySaveRequestDto;
import com.devTest.Firstblog.dto.ResponseDto;
import com.devTest.Firstblog.model.Board;
import com.devTest.Firstblog.model.Reply;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.service.BoardService;
import com.devTest.Firstblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.writeBoard(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.writeDelete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        //근데 이건 논리삭제가 아니네...물리적 삭제네;
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.writeUpdate(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는 게 좋다.
    //dto 사용하지 않은 이유는!!!(VO얘기하는건가?)
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto reply){

        boardService.writeReply(reply);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
