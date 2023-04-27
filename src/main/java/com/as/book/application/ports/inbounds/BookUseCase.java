package com.as.book.application.ports.inbounds;

import com.as.book.domain.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookUseCase {


    Mono<Book> save(Book book);

    Mono<Book> getBookById(Long id);
    Flux<Book> getAllBooksByAuthor(String author);

    Flux<Book> getAllBooksByTitle(String title);
    Flux<Book> getAllBooks();
}
