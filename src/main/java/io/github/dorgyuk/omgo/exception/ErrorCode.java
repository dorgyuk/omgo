package io.github.dorgyuk.omgo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor 
@Getter 
public enum ErrorCode {

    DUPLICATE_NICKNAME(
        HttpStatus.CONFLICT,
        "이미 사용 중인 닉네임입니다."
    ),
    INTERNAL_SERVER_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "서버 내부 오류가 발생했습니다."
    ),
    INVALID_REQUEST(
        HttpStatus.BAD_REQUEST,
        "요청 값의 형식이 올바르지 않습니다."
    );
    
    private final HttpStatus status;
    private final String message;
}
