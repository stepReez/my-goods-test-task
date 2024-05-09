package org.goods.goods.util;

import lombok.extern.slf4j.Slf4j;
import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException e) {
        log.warn("NotFoundException: " + e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .code(404)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final BadRequestException e) {
        log.warn("BadRequestException: " + e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .code(400)
                .build();
    }
}
