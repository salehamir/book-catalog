package com.as.book.infrastructure.adapters.outbounds.persistence.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "books")
@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class BookEntity implements Serializable {


    private static final long serialVersionUID = 8943762019714642345L;

    @Id
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

    @Version
    private Long version;
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
