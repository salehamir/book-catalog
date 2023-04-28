package com.demo.book.infrastructure.adapters.outbounds.persistence.mapper;


import com.demo.book.domain.model.Book;
import com.demo.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookPersistenceMapper {
    @Mapping(target="bookYear", source="book.year")
    BookEntity toBookEntity(Book book);
    @Mapping(target="year", source="entity.bookYear")
    Book toBook(BookEntity entity);
    @Mapping(target="bookYear", source="book.year")
    void updateBookEntity(@MappingTarget BookEntity entity, Book book);
}
