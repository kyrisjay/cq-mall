package club.banyuan.mgt.advice;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(RequestFailException.class)
    public ResponseResult requestFailException(RequestFailException e) {
        return ResponseResult.badRequest(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseResult.badRequest(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult runtimeException(RuntimeException e) {
        return ResponseResult.failed(e.getMessage());
    }
}
