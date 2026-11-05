package io.github.dorgyuk.omgo.exception.dto;

import io.github.dorgyuk.omgo.exception.ErrorCode;

public record ErrorResponse (
   ErrorCode errorCode,
   String message
){}
