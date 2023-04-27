package com.as.book.infrastructure.adapters.outbounds.persistence.mapper;


import com.as.book.domain.model.Book;
import com.as.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BookPersistenceMapper {
    BookEntity toBookEntity(Book book);
    Book toBook(BookEntity entity);
    void updateBookEntity(@MappingTarget BookEntity entity, Book book);
}
