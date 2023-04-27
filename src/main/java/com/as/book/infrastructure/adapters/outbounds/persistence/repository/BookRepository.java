package com.as.book.infrastructure.adapters.outbounds.persistence.repository;

import com.as.book.infrastructure.adapters.outbounds.persistence.entity.BookEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<BookEntity,Long> {
    Flux<BookEntity> findBookEntitiesByAuthorContainsIgnoreCase(String author);
    Flux<BookEntity> findBookEntitiesByTitleContainsIgnoreCase(String title);
}
