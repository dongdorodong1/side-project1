package play.board1.common.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import play.board1.common.exception.BaseException;
import play.board1.common.exception.MemberException;

@Slf4j
@RestControllerAdvice
public class CmmExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public BaseException memberException(MemberException me) {
        return new BaseException("BAD-REQUEST", me.getMessage());
    }
}
