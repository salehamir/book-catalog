package com.as.book.domain.execption;

import org.springframework.http.HttpStatus;

public class InternalError extends BaseException {
    private static final HttpStatus HTTP_STATUS=HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalError(String message) {
        super(HTTP_STATUS,message);
    }
}
