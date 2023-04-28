package com.demo.book.domain.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1114229151588451274L;

    private Long id;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;
    @NotBlank
    private String title;

    @Size(max=4000)
    private String description;

    private Integer year;

}
