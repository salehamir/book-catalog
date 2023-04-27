package com.as.book.infrastructure.adapters.inbounds.api.graphql;

import com.as.book.application.ports.inbounds.BookUseCase;
import com.as.book.domain.model.Book;
import com.as.book.infrastructure.adapters.inbounds.api.data.BookInput;
import com.as.book.infrastructure.adapters.inbounds.api.data.BookOutput;
import com.as.book.infrastructure.adapters.inbounds.api.mapper.BookDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/demo/graphql/book")
@RequiredArgsConstructor
public class BookGraphQLController {
    private final BookUseCase bookUseCase;
    private final BookDataMapper mapper;
    @MutationMapping("createBook")
    public Mono<BookOutput> createBook(@Argument BookInput input){
        return bookUseCase.save(mapper.toBook(input)).map(mapper::toBookOutput);
    }
    @MutationMapping("updateBook")
    public Mono<BookOutput> createBook(@Argument Long id,@Argument BookInput input){
        return bookUseCase.getBookById(id).map(book -> {
                    Book bookReq = mapper.toBook(input);
                    bookReq.setId(book.getId());
                    return bookReq;
                })
                .flatMap(bookUseCase::save)
                .map(mapper::toBookOutput);
    }
    @QueryMapping("getAllBooks")
    public Flux<BookOutput> getAllBooks(){
        return bookUseCase.getAllBooks().map(mapper::toBookOutput);
    }
    @QueryMapping("getBookById")
    public Mono<BookOutput> getAllBooks(@Argument Long id){
        return bookUseCase.getBookById(id).map(mapper::toBookOutput);
    }

    @QueryMapping("getAllBooksByAuthor")
    public Flux<BookOutput> getAllBooksByAuthor(@Argument String author){
        return bookUseCase.getAllBooksByAuthor(author).map(mapper::toBookOutput);
    }
    @QueryMapping("getAllBooksByTitle")
    public Flux<BookOutput> getAllBooksByTitle(@Argument String title){
        return bookUseCase.getAllBooksByTitle(title).map(mapper::toBookOutput);
    }
}
