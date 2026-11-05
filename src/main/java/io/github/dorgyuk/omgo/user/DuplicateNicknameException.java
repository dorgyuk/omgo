package io.github.dorgyuk.omgo.user;

import io.github.dorgyuk.omgo.exception.AppException;
import io.github.dorgyuk.omgo.exception.ErrorCode;

public class DuplicateNicknameException extends AppException{
    
    public DuplicateNicknameException() {
        super(ErrorCode.DUPLICATE_NICKNAME);
    }
}
