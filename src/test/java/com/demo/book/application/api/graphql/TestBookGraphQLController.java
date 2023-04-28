package com.demo.book.application.api.graphql;

import com.demo.book.application.api.data.BookInput;
import com.demo.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import com.demo.book.infrastructure.adapters.outbounds.persistence.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestBookGraphQLController {
    @LocalServerPort
    private int port;

    private HttpGraphQlTester graphQlTester;
    @Autowired
    private BookRepository repository;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        WebTestClient testClient =
                WebTestClient.bindToApplicationContext(context)
                        .configureClient()
                        .baseUrl("/graphql")
                        .build();
        this.graphQlTester = HttpGraphQlTester.create(testClient);

        graphQlTester.documentName("createBook")
                .variable("bookInput", BookInput.builder().title("ABC")
                        .isbn("45GH98L").author("amir").year(1995).build()).execute();
        graphQlTester.documentName("createBook")
                .variable("bookInput", BookInput.builder().title("CDE")
                        .isbn("78HS48M").author("saleh").year(2000).build()).execute();
    }
    @Test
    public void testGetAllBooks(){
        this.graphQlTester.documentName("getAllBooks")
                .execute()
                .errors()
                .satisfy(System.out::println);
    }

    @Test
    public void testGetAllBooksByAuthor(){
        String author="amir";
        this.graphQlTester.documentName("getAllBooksByAuthor")
                .variable("author",author)
                .execute()
                .path("getAllBooksByAuthor[0].author").entity(String.class).isEqualTo(author);
    }
    @Test
    public void testGetAllBooksByTitle(){
        String title="ABC";
        this.graphQlTester.documentName("getAllBooksByTitle")
                .variable("title",title)
                .execute()
                .path("getAllBooksByTitle[0].title").entity(String.class).isEqualTo(title);
    }

}
