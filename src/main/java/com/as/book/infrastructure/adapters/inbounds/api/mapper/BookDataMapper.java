package com.as.book.infrastructure.adapters.inbounds.api.mapper;

import com.as.book.domain.model.Book;
import com.as.book.infrastructure.adapters.inbounds.api.data.BookInput;
import com.as.book.infrastructure.adapters.inbounds.api.data.BookOutput;
import org.mapstruct.Mapper;

@Mapper
public interface BookDataMapper {
    Book toBook(BookInput input);
    BookOutput toBookOutput(Book book);

}
