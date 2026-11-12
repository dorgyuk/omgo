package io.github.dorgyuk.omgo.user.exception;

import io.github.dorgyuk.omgo.exception.AppException;
import io.github.dorgyuk.omgo.exception.ErrorCode;

public class NicknameAlreadyExistsException extends AppException{
    
    public NicknameAlreadyExistsException() {
        super(ErrorCode.NICKNAME_ALREADY_EXISTS);
    }
}
