package com.demo.book.domain.execption;

public class BookNotFoundException extends BaseException {

    private static final Integer CODE = 404;

    public BookNotFoundException() {
        super(CODE, "Books not found");
    }

    public BookNotFoundException(String filedName, String value) {
        super(CODE, String.format("Book not found with %s : %s", filedName, value));
    }
}
