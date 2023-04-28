package com.demo.book.application.api.mapper;

import com.demo.book.application.api.data.BookInput;
import com.demo.book.application.api.data.BookOutput;
import com.demo.book.domain.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDataMapper {
    Book toBook(BookInput input);
    BookOutput toBookOutput(Book book);

}
