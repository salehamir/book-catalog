package com.as.book.infrastructure.adapters.inbounds.api.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class BookInput implements Serializable {

    private static final long serialVersionUID = -5255114846456397612L;

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
