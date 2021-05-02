package com.devTest.Firstblog.handler;

import com.devTest.Firstblog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

//예외 처리를 해주는 글로벌 ExceptionHandler
@ControllerAdvice //어디에서든 exception발생하면 이쪽으로 오세욘 @ControllerAdvice.
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value=IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e){
        return "<h1>"+e.getMessage()+"</h1>";
    }
//    @ExceptionHandler(value=Exception.class)
//    public String handleException(IllegalArgumentException e){
//        return "<h1> Exception"+e.getMessage()+"</h1>";
//    }
    @ExceptionHandler(value=Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e){
        System.out.println("핸들러 exception");
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
