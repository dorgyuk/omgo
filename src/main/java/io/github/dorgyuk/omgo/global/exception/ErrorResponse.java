package io.github.dorgyuk.omgo.global.exception;


public record ErrorResponse (
   ErrorCode errorCode,
   String message
){}
