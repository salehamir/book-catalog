package com.demo.book.domain.execption;

import org.springframework.http.HttpStatus;

public class InternalException extends BaseException {
    private static final Integer CODE=500;

    public InternalException(String message) {
        super(CODE,message);
    }
}
