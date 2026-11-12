package io.github.dorgyuk.omgo.user.exception;

import io.github.dorgyuk.omgo.exception.AppException;
import io.github.dorgyuk.omgo.exception.ErrorCode;

public class EmailAlreadyExistsException extends AppException{
    
    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
