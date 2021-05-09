package com.devTest.Firstblog.controller.api;

import com.devTest.Firstblog.config.auth.PrincipalDetail;
import com.devTest.Firstblog.dto.ResponseDto;
import com.devTest.Firstblog.model.Board;
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
}
