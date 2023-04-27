package com.as.book.domain.execption;

import org.springframework.http.HttpStatus;

public class BookNotFound extends BaseException{

    private static final HttpStatus HTTP_STATUS=HttpStatus.NOT_FOUND;
    public BookNotFound() {
        super(HTTP_STATUS,"Books not found");
    }

    public BookNotFound(String filedName, String value){
        super(HTTP_STATUS,String.format("Book not found with %s : %s",filedName,value));
    }
}
