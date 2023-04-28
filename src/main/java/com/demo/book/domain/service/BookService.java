package com.demo.book.domain.service;

import com.demo.book.application.ports.inbounds.BookUseCase;
import com.demo.book.application.ports.outbounds.BookOutputPort;
import com.demo.book.domain.execption.BookNotFoundException;
import com.demo.book.domain.execption.InternalException;
import com.demo.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class BookService implements BookUseCase {

    private final BookOutputPort bookOutputPort;


    @Override
    public Mono<Book> save(Book book) {
        return bookOutputPort.save(book)
                .onErrorMap(throwable -> new InternalException(throwable.getMessage()));
    }

    @Override
    public Mono<Book> getBookById(Long id) {
        return bookOutputPort.getBookById(id).switchIfEmpty(Mono.error(new BookNotFoundException("id",id.toString())));
    }

    @Override
    public Flux<Book> getAllBooksByAuthor(String author) {
        return bookOutputPort.getAllBooksByAuthor(author)
                .switchIfEmpty(Mono.error(new BookNotFoundException("author", author)));
    }

    @Override
    public Flux<Book> getAllBooksByTitle(String title) {
        return bookOutputPort.getAllBooksByTitle(title)
                .switchIfEmpty(Mono.error(new BookNotFoundException("title", title)));
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookOutputPort.getAllBooks()
                .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }
}
