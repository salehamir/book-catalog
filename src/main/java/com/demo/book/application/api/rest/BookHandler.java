package com.demo.book.application.api.rest;


import com.demo.book.application.api.data.BookInput;
import com.demo.book.application.ports.inbounds.BookUseCase;
import com.demo.book.domain.model.Book;
import com.demo.book.application.api.data.BookOutput;
import com.demo.book.application.api.mapper.BookDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class BookHandler {

    private final BookUseCase bookUseCase;
    private final BookDataMapper mapper;

    public Mono<ServerResponse> createBook(ServerRequest request) {
        return request.bodyToMono(BookInput.class)
                .map(mapper::toBook)
                .flatMap(bookUseCase::save)
                .map(mapper::toBookOutput)
                .flatMap(bookOutput -> ServerResponse.ok().bodyValue(bookOutput));
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {
        String id = request.pathVariable("id");
        return bookUseCase.getBookById(Long.parseLong(id)).flatMap(book -> request.bodyToMono(BookInput.class)
                        .map(input -> {
                            Book bookReq = mapper.toBook(input);
                            bookReq.setId(book.getId());
                            return bookReq;
                        })
                        .flatMap(bookUseCase::save)
                        .map(mapper::toBookOutput)
                        .flatMap(bookOutput -> ServerResponse.ok().bodyValue(bookOutput)))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> getBookById(ServerRequest request) {
        String id = request.pathVariable("id");
        return bookUseCase.getBookById(Long.parseLong(id))
                .map(mapper::toBookOutput)
                .flatMap(bookOutput -> ServerResponse.ok().bodyValue(bookOutput));
    }

    public Mono<ServerResponse> getAllBooks(ServerRequest request) {

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookUseCase.getAllBooks().map(mapper::toBookOutput), BookOutput.class);
    }

    public Mono<ServerResponse> getBookByAuthor(ServerRequest request) {
        String author = request.pathVariable("value");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookUseCase.getAllBooksByAuthor(author).map(mapper::toBookOutput), BookOutput.class);
    }

    public Mono<ServerResponse> getBookByTitle(ServerRequest request) {
        String title = request.pathVariable("value");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookUseCase.getAllBooksByTitle(title).map(mapper::toBookOutput), BookOutput.class);
    }
}
