package com.as.book.infrastructure.adapters.inbounds.api.rest.config;

import com.as.book.infrastructure.adapters.inbounds.api.data.BookOutput;
import com.as.book.infrastructure.adapters.inbounds.api.data.BookInput;
import com.as.book.infrastructure.adapters.inbounds.api.rest.BookHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {


    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/demo/book/{id}"
                            , produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = BookHandler.class, beanMethod = "updateBook",
                            operation = @Operation(operationId = "updateBook", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "404", description = "Book not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")}, parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BookInput.class))))
                    ),
                    @RouterOperation(path = "/demo/book", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = BookHandler.class, beanMethod = "createBook",
                            operation = @Operation(operationId = "createBook", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BookInput.class)))
                            )),
                    @RouterOperation(path = "/demo/book/{id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = BookHandler.class, method = RequestMethod.GET, beanMethod = "getBookById",
                            operation = @Operation(operationId = "getBook", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "404", description = "Book not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}
                            )),
                    @RouterOperation(path = "/demo/book", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = BookHandler.class, method = RequestMethod.GET, beanMethod = "getAllBooks",
                            operation = @Operation(operationId = "getBooks", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "404", description = "Books not found")}
                            )),
                    @RouterOperation(path = "/demo/book/author/{value}", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = BookHandler.class, method = RequestMethod.GET, beanMethod = "getBookByAuthor",
                            operation = @Operation(operationId = "getBookByAuthor", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "404", description = "Books not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "value")}
                            )),
                    @RouterOperation(path = "/demo/book/title/{value}", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = BookHandler.class, method = RequestMethod.GET, beanMethod = "getBookByTitle",
                            operation = @Operation(operationId = "getBookByTitle", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = BookOutput.class))),
                                    @ApiResponse(responseCode = "404", description = "Books not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "value")}
                            ))

            })

    RouterFunction<ServerResponse> routerFunctionEmployeeDetailByEmployeeIdRouter(
            BookHandler requestHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/demo/book/"),
                        requestHandler::createBook)
                .andRoute(RequestPredicates.PUT(
                                "/demo/book/{bookId}"),
                        requestHandler::updateBook)
                .andRoute(RequestPredicates.GET("/demo/book/{id}"),
                        requestHandler::getBookById)
                .andRoute(RequestPredicates.GET("/demo/book"),
                        requestHandler::getAllBooks)
                .andRoute(RequestPredicates.GET("/demo/book/author/{value}"),
                        requestHandler::getBookByAuthor)
                .andRoute(RequestPredicates.GET("/demo/book/title/{value}"),
                        requestHandler::getBookByTitle);
    }
}