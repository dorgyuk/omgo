package io.github.dorgyuk.omgo.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 오류 코드 목록. Notion 명세의 "에러코드"와 일치시킨다.
 *
 * <p>오류는 {@code throw new AppException(ErrorCode.XXX)}로 던진다.
 * 새 코드는 해당 도메인 구역에 추가한다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    // ===== common =====
    INVALID_REQUEST(
        HttpStatus.BAD_REQUEST,
        "요청 값의 형식이 올바르지 않습니다."
    ),
    INTERNAL_SERVER_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "서버 내부 오류가 발생했습니다."
    ),

    // ===== auth =====
    INVALID_CREDENTIALS(
        HttpStatus.UNAUTHORIZED,
        "이메일 또는 비밀번호가 일치하지 않습니다."
    ),
    EXPIRED_TOKEN(
        HttpStatus.UNAUTHORIZED,
        "만료된 토큰입니다."
    ),
    INVALID_TOKEN(
        HttpStatus.UNAUTHORIZED,
        "유효하지 않은 토큰입니다."
    ),

    // ===== user =====
    NICKNAME_ALREADY_EXISTS(
        HttpStatus.CONFLICT,
        "이미 사용 중인 닉네임입니다."
    ),
    EMAIL_ALREADY_EXISTS(
        HttpStatus.CONFLICT,
        "이미 사용 중인 이메일입니다."
    ),
    ACCOUNT_ALREADY_EXISTS(
        HttpStatus.CONFLICT,
        "이미 존재하는 계정입니다."
    );

    private final HttpStatus status;
    private final String message;
}
