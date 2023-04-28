package com.demo.book.infrastructure.adapters.outbounds.persistence;

import com.demo.book.application.ports.outbounds.BookOutputPort;
import com.demo.book.domain.model.Book;
import com.demo.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import com.demo.book.infrastructure.adapters.outbounds.persistence.mapper.BookPersistenceMapper;
import com.demo.book.infrastructure.adapters.outbounds.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookOutputPort {

    private final BookRepository repository;
    private final BookPersistenceMapper mapper;

    @Override
    public Mono<Book> save(Book book) {
        if(book.getId()!=null&&book.getId()>0) {
            return repository.findById(book.getId()).map(bookEntity -> {
                        mapper.updateBookEntity(bookEntity, book);
                        return bookEntity;
                    }).flatMap(this::PersistenceDB)
                    .switchIfEmpty(Mono.just(mapper.toBookEntity(book)).flatMap(this::PersistenceDB));
        }else {
            return Mono.just(mapper.toBookEntity(book)).flatMap(this::PersistenceDB);
        }


    }

    private Mono<Book> PersistenceDB(BookEntity entity) {
        return repository.save(entity).map(mapper::toBook);
    }

    @Override
    public Mono<Book> getBookById(Long id) {
        return repository.findById(id).map(mapper::toBook);
    }

    @Override
    public Flux<Book> getAllBooksByAuthor(String author) {
        return repository.findBookEntitiesByAuthorContainsIgnoreCase(author).map(mapper::toBook);
    }

    @Override
    public Flux<Book> getAllBooksByTitle(String title) {
        return repository.findBookEntitiesByTitleContainsIgnoreCase(title).map(mapper::toBook);
    }

    @Override
    public Flux<Book> getAllBooks() {
        return repository.findAll().map(mapper::toBook);
    }
}
