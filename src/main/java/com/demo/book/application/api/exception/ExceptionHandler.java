package com.demo.book.application.api.exception;

import com.demo.book.domain.execption.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof BaseException baseException) {
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(baseException.getCode()));
            return exchange.getResponse().writeWith(Mono.just(getDataBuffer(new ErrorResponse(ex.getMessage()))));
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().writeWith(Mono.just(getDataBuffer(new ErrorResponse(ex.getMessage()))));
        }
    }

    private DefaultDataBuffer getDataBuffer(Object response) {
        DefaultDataBuffer db;
        try {
            db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(response));
        } catch (JsonProcessingException e) {
            db = new DefaultDataBufferFactory().wrap(new byte[0]);
        }
        return db;
    }
}
