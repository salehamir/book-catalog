package com.as.book.domain.service;

import com.as.book.application.ports.inbounds.BookUseCase;
import com.as.book.application.ports.outbounds.BookOutputPort;
import com.as.book.domain.execption.BookNotFound;
import com.as.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class BookService implements BookUseCase {

    private final BookOutputPort bookOutputPort;


    @Override
    public Mono<Book> save(Book book) {
        return bookOutputPort.save(book)
                .onErrorMap(throwable -> new InternalError(throwable.getMessage()));
    }

    @Override
    public Mono<Book> getBookById(Long id) {
        return bookOutputPort.getBookById(id).switchIfEmpty(Mono.error(new BookNotFound("id",id.toString())));
    }

    @Override
    public Flux<Book> getAllBooksByAuthor(String author) {
        return bookOutputPort.getAllBooksByAuthor(author)
                .switchIfEmpty(Mono.error(new BookNotFound("author", author)));
    }

    @Override
    public Flux<Book> getAllBooksByTitle(String title) {
        return bookOutputPort.getAllBooksByTitle(title)
                .switchIfEmpty(Mono.error(new BookNotFound("title", title)));
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookOutputPort.getAllBooks()
                .switchIfEmpty(Mono.error(new BookNotFound()));
    }
}
