package com.demo.book.domain.execption;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    protected Integer code;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
