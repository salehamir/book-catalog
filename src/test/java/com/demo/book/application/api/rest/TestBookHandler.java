package com.demo.book.application.api.rest;

import com.demo.book.application.api.data.BookInput;
import com.demo.book.application.api.data.BookOutput;
import com.demo.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import com.demo.book.infrastructure.adapters.outbounds.persistence.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestBookHandler {


    @MockBean
    private BookRepository repository;

    @Autowired
    private WebTestClient testClient;

    @Test
    public void testGetBookById() {
        BookEntity bookEntity = BookEntity.builder().id(1L).title("ABC").isbn("45GH98L").author("amir").build();
        given(repository.findById(1L)).willReturn(Mono.just(bookEntity));
        testClient.get()
                .uri("/demo/book/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookOutput.class)
                .value(bookResponse -> {
                            Assertions.assertThat(bookResponse.getId()).isEqualTo(1L);
                            Assertions.assertThat(bookResponse.getTitle()).isEqualTo("ABC");
                            Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("45GH98L");
                            Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("amir");
                        }
                );
    }

    @Test
    public void testGetAllBooks() {
        List<BookEntity> bookList = List.of(
                BookEntity.builder().id(1L).title("Book 1").build(),
                BookEntity.builder().id(2L).title("Book 2").build(),
                BookEntity.builder().id(3L).title("Book 3").build()
        );

        given(repository.findAll()).willReturn(Flux.fromIterable(bookList));

        testClient.get()
                .uri("/demo/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookOutput.class)
                .hasSize(bookList.size());
    }

    @Test
    void testUpdateBook() {
        BookEntity bookEntity = BookEntity.builder().id(1L).title("ABC").isbn("45GH98L").author("amir").build();
        given(repository.findById(1L)).willReturn(Mono.just(bookEntity));
        given(repository.save(bookEntity)).willReturn(Mono.just(bookEntity));

        BookInput input = new BookInput(bookEntity.getAuthor(), bookEntity.getIsbn(), bookEntity.getTitle(),
                bookEntity.getDescription(), bookEntity.getBookYear());

        testClient.put()
                .uri("/demo/book/1")
                .body(Mono.just(input), BookInput.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookOutput.class)
                .value(bookResponse -> {
                            Assertions.assertThat(bookResponse.getId()).isEqualTo(1L);
                            Assertions.assertThat(bookResponse.getTitle()).isEqualTo("ABC");
                            Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("45GH98L");
                            Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("amir");
                        }
                );

        verify(repository).save(bookEntity);
    }

    @Test
    void testCreateBook() {
        BookEntity bookEntity = BookEntity.builder().title("ABC").isbn("45GH98L").author("amir").build();
        given(repository.save(bookEntity)).willReturn(Mono.just(bookEntity));

        BookInput input = new BookInput(bookEntity.getAuthor(), bookEntity.getIsbn(), bookEntity.getTitle(),
                bookEntity.getDescription(), bookEntity.getBookYear());

        testClient.post()
                .uri("/demo/book")
                .body(Mono.just(input), BookInput.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookOutput.class)
                .value(bookResponse -> {
                            Assertions.assertThat(bookResponse.getTitle()).isEqualTo("ABC");
                            Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("45GH98L");
                            Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("amir");
                        }
                );
        verify(repository).save(bookEntity);

    }


}
